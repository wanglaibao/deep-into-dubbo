package com.laibao.dubbo.hsf.rpc.client;

import com.laibao.dubbo.hsf.rpc.Codecs;
import com.laibao.dubbo.hsf.rpc.RequestWrapper;
import com.laibao.dubbo.hsf.rpc.ResponseWrapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


/**
 * Common Client,support invoke
 * 
 */
public abstract class AbstractClient implements Client {

	private static final Log LOGGER = LogFactory.getLog(AbstractClient.class);

	private static final boolean isDebugEnabled = LOGGER.isDebugEnabled();
	
	private static final boolean isWarnEnabled = LOGGER.isWarnEnabled();

	protected static ConcurrentHashMap<Integer, ArrayBlockingQueue<Object>> responses = new ConcurrentHashMap<Integer, ArrayBlockingQueue<Object>>();
	
	public Object invoke(Object message, int timeout, int codecType, int protocolType){
		RequestWrapper wrapper = new RequestWrapper(message, timeout, codecType, protocolType);
		return invokeSyncInternal(wrapper);
	}

	public Object invoke(String targetInstanceName, String methodName,
			String[] argTypes, Object[] args, int timeout, int codecType, int protocolType) {
		byte[][] argTypeBytes = new byte[argTypes.length][];
		for(int i =0; i < argTypes.length; i++) {
		    argTypeBytes[i] =  argTypes[i].getBytes();
		}
		RequestWrapper wrapper = new RequestWrapper(targetInstanceName.getBytes(),
				methodName.getBytes(), argTypeBytes, args, timeout, codecType, protocolType);
		return invokeSyncInternal(wrapper);
	}

	private Object invokeSyncInternal(RequestWrapper wrapper){
		long beginTime = System.currentTimeMillis();
		ArrayBlockingQueue<Object> responseQueue = new ArrayBlockingQueue<Object>(1);
		responses.put(wrapper.getId(), responseQueue);
		ResponseWrapper responseWrapper = null;
		try {
			if(isDebugEnabled){
				// for performance trace
				LOGGER.debug("client ready to send message,request id: "+wrapper.getId());
			}
			getClientFactory().checkSendLimit();
			sendRequest(wrapper, wrapper.getTimeout());
			if(isDebugEnabled){
				// for performance trace
				LOGGER.debug("client write message to send buffer,wait for response,request id: "+wrapper.getId());
			}
		} 
		catch (Exception e) {
			responses.remove(wrapper.getId());
			responseQueue = null;
			LOGGER.error("send request to os sendbuffer error", e);
			throw new RuntimeException("send request to os sendbuffer error", e.getCause());
		}
		Object result = null;
		try {
			result = responseQueue.poll(
					wrapper.getTimeout() - (System.currentTimeMillis() - beginTime),
					TimeUnit.MILLISECONDS);
		}
		catch(Exception e){
			responses.remove(wrapper.getId());
			LOGGER.error("Get response error", e);
			throw new RuntimeException("Get response error", e.getCause());
		}
		responses.remove(wrapper.getId());
		

		if (result == null) {
			String errorMsg = new StringBuilder("receive response timeout ").append(wrapper.getTimeout())
					.append(" ms, ").append("server is: ").append(getServerIP() + ":" + getServerPort())
					.append( " request id is:" + wrapper.getId()).toString();

			throw new RuntimeException(errorMsg);
		}
		
		if(result instanceof ResponseWrapper){
			responseWrapper = (ResponseWrapper) result;
		}
		else if(result instanceof List){
			@SuppressWarnings("unchecked")
			List<ResponseWrapper> responseWrappers = (List<ResponseWrapper>) result;
			for (ResponseWrapper response : responseWrappers) {
				if(response.getRequestId() == wrapper.getId()){
					responseWrapper = response;
				}
				else{
					putResponse(response);
				}
			}
		}
		else{
			throw new RuntimeException("only receive ResponseWrapper or List as response");
		}
		try{
			// do deserialize in business threadpool
			if (responseWrapper.getResponse() instanceof byte[]) {
				String responseClassName = null;
				if(responseWrapper.getResponseClassName() != null){
					responseClassName = new String(responseWrapper.getResponseClassName());
				}
				// avoid server no return object
				if(((byte[])responseWrapper.getResponse()).length == 0){
					responseWrapper.setResponse(null);
				}
				else{
					Object responseObject = Codecs.getDecoder(responseWrapper.getCodecType()).decode(
						responseClassName,(byte[]) responseWrapper.getResponse());
					if (responseObject instanceof Throwable) {
						responseWrapper.setException((Throwable) responseObject);
					} 
					else {
						responseWrapper.setResponse(responseObject);
					}
				}
			}
		}
		catch(Exception e){
			LOGGER.error("Deserialize response object error", e);
			throw new RuntimeException("Deserialize response object error", e);
		}
		if (responseWrapper.isError()) {
			Throwable t = responseWrapper.getException();
			t.fillInStackTrace();
			String errorMsg = "server error,server is: " + getServerIP()
					+ ":" + getServerPort() + " request id is:"
					+ wrapper.getId();
			LOGGER.error(errorMsg, t);
			throw new RuntimeException(errorMsg, t);
		}
		return responseWrapper.getResponse();
	}

	/**
	 * receive response
	 */
	public void putResponse(ResponseWrapper wrapper){
		if (!responses.containsKey(wrapper.getRequestId())) {
			LOGGER.warn("give up the response,request id is:" + wrapper.getRequestId() + ",maybe because timeout!");
			return;
		}
		try {
			ArrayBlockingQueue<Object> queue = responses.get(wrapper.getRequestId());
			if (queue != null) {
				queue.put(wrapper);
			} 
			else {
				LOGGER.warn("give up the response,request id is:"
						+ wrapper.getRequestId() + ",because queue is null");
			}
		} 
		catch (InterruptedException e) {
			LOGGER.error("put response error,request id is:" + wrapper.getRequestId(), e);
		}
	}
	
	/**
	 * receive responses
	 */
	public void putResponses(List<ResponseWrapper> wrappers){
		for (ResponseWrapper wrapper : wrappers) {
			if (!responses.containsKey(wrapper.getRequestId())) {
				LOGGER.warn("give up the response,request id is:" + wrapper.getRequestId() + ",maybe because timeout!");
				continue;
			}
			try {
				ArrayBlockingQueue<Object> queue = responses.get(wrapper.getRequestId());
				if (queue != null) {
					queue.put(wrappers);
					break;
				} 
				else {
					LOGGER.warn("give up the response,request id is:"
							+ wrapper.getRequestId() + ",because queue is null");
				}
			} 
			catch (InterruptedException e) {
				LOGGER.error("put response error,request id is:" + wrapper.getRequestId(), e);
			}
		}
	}

	/**
	 * send request to os sendbuffer,must ensure write result
	 */
	public abstract void sendRequest(RequestWrapper wrapper, int timeout);

}

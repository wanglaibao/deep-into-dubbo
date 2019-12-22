package com.laibao.dubbo.hsf.rpc.server;
import com.laibao.dubbo.hsf.rpc.Codecs;
import com.laibao.dubbo.hsf.rpc.RequestWrapper;
import com.laibao.dubbo.hsf.rpc.ResponseWrapper;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Direct Call RPC Server Handler
 * 
 */
public class SimpleProcessorServerHandler implements ServerHandler{


	private Map<String, ServerProcessor> processors = new ConcurrentHashMap<String, ServerProcessor>();
	
	public void registerProcessor(String instanceName,Object instance){
		processors.put(instanceName, (ServerProcessor)instance);
	}
	
	public ResponseWrapper handleRequest(final RequestWrapper request){
		Objects.requireNonNull(request);
		ResponseWrapper responseWrapper = new ResponseWrapper(request.getId(),request.getCodecType(),request.getProtocolType());
		try{
			String argType = null;
			if(request.getArgTypes() != null && request.getArgTypes()[0] != null){
				argType = new String(request.getArgTypes()[0]);
			}
			Object requestObject = Codecs.getDecoder(request.getCodecType()).decode(argType,(byte[])request.getMessage());
			responseWrapper.setResponse(processors.get(requestObject.getClass().getName()).handle(requestObject));
		}
		catch(Exception e){
			responseWrapper.setException(e);
		}
		return responseWrapper;
	}
}

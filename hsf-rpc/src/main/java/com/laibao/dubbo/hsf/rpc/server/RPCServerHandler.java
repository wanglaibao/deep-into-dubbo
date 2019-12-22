package com.laibao.dubbo.hsf.rpc.server;
import com.laibao.dubbo.hsf.rpc.Codecs;
import com.laibao.dubbo.hsf.rpc.RequestWrapper;
import com.laibao.dubbo.hsf.rpc.ResponseWrapper;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


/**
 * Reflection RPC Server Handler 
 * 
 */
public class RPCServerHandler implements ServerHandler {


	// Server Processors     key: servicename    value: service instance
	private static Map<String, Object> processors = new HashMap<String, Object>();
	
	// Cached Server Methods  key: instanceName#methodname$argtype_argtype
	private static Map<String, Method> cacheMethods = new HashMap();

	/**
	 *
	 * @param instanceName
	 * @param instance
	 */
	public void registerProcessor(String instanceName,Object instance){
		processors.put(instanceName, instance);
		Class<?> instanceClass = instance.getClass();
		Method[] methods = instanceClass.getMethods();
		for (Method method : methods) {
			Class<?>[] argTypes = method.getParameterTypes();
			StringBuilder methodKeyBuilder = new StringBuilder();
			methodKeyBuilder.append(instanceName).append("#");
			methodKeyBuilder.append(method.getName()).append("$");
			for (Class<?> argClass : argTypes) {
				methodKeyBuilder.append(argClass.getName()).append("_");
			}
			cacheMethods.put(methodKeyBuilder.toString(), method);
		}
	}


	/**
	 *
	 * @param request
	 * @return ResponseWrapper
	 */
	public ResponseWrapper handleRequest(final RequestWrapper request){
		Objects.requireNonNull(request);
		ResponseWrapper responseWrapper = new ResponseWrapper(request.getId(),request.getCodecType(),request.getProtocolType());
		String targetInstanceName = new String(request.getTargetInstanceName());
		String methodName = new String(request.getMethodName());
		byte[][] argTypeBytes  = request.getArgTypes();
		String[] argTypes = new String[argTypeBytes.length];
		for(int i = 0; i <argTypeBytes.length; i++) {
		    argTypes[i] = new String(argTypeBytes[i]);
		}
		Object[] requestObjects = null;
		Method method = null;
		try{
			Object processor = processors.get(targetInstanceName);
			if(processor == null){
				throw new Exception("no "+targetInstanceName+" instance exists on the server");
			}
			if (argTypes != null && argTypes.length > 0) {
				StringBuilder methodKeyBuilder = new StringBuilder();
				methodKeyBuilder.append(targetInstanceName).append("#");
				methodKeyBuilder.append(methodName).append("$");
				Class<?>[] argTypeClasses = new Class<?>[argTypes.length];
				for (int i = 0; i < argTypes.length; i++) {
					methodKeyBuilder.append(argTypes[i]).append("_");
					argTypeClasses[i] = Class.forName(argTypes[i]);
				}
				requestObjects = new Object[argTypes.length];
				method = cacheMethods.get(methodKeyBuilder.toString());
				if(method == null){
					throw new RuntimeException("no method: "+methodKeyBuilder.toString()+" find in "+targetInstanceName+" on the server");
				}
				Object[] tmprequestObjects = request.getRequestObjects();
				for (int i = 0; i < tmprequestObjects.length; i++) {
					try{
						requestObjects[i] = Codecs.getDecoder(request.getCodecType()).decode(argTypes[i],(byte[])tmprequestObjects[i]);
					}
					catch(Exception ex){
						throw new RuntimeException("decode request object args error",ex.getCause());
					}
				}
			} 
			else {
				method = processor.getClass().getMethod(methodName, new Class<?>[] {});
				if(method == null){
					throw new RuntimeException("no method: "+methodName+" find in "+targetInstanceName+" on the server");
				}
				requestObjects = new Object[] {};
			}
			responseWrapper.setResponse(method.invoke(processor, requestObjects));
		}
		catch(Exception e){
			responseWrapper.setException(e);
		}
		return responseWrapper;
	}
}

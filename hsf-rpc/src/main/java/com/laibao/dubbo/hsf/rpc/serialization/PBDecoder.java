package com.laibao.dubbo.hsf.rpc.serialization;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import com.google.protobuf.Message;

/**
 * ProtocolBuf Decoder
 * 
 */
public class PBDecoder implements Decoder {
	
	private static ConcurrentHashMap<String, Message> messages = new ConcurrentHashMap<String, Message>();

	public static void addMessage(String className,Message message){
		messages.putIfAbsent(className, message);
	}

	/**
	 *
	 * @param className
	 * @param bytes
	 * @return Object
	 */
	public Object decode(String className,byte[] bytes){
		Objects.requireNonNull(bytes);
		try{
			Message message = messages.get(className);
			return message.newBuilderForType().mergeFrom(bytes).build();
		}catch (Exception ex) {
			throw new RuntimeException("PB decode failure,",ex.getCause());
		}
	}

}

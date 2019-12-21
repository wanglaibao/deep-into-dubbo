package com.laibao.dubbo.hsf.rpc.serialization.impl;
import com.laibao.dubbo.hsf.rpc.serialization.Decoder;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.util.Objects;

/**
 * Java Native Decoder
 * 
 */
public class JavaNativeDecoder implements Decoder {

	/**
	 *
	 * @param className
	 * @param bytes
	 * @return Object
	 */
	public Object decode(String className,byte[] bytes){
		Objects.requireNonNull(bytes);
		try{
			ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
			Object resultObject = objectInputStream.readObject();
			objectInputStream.close();
			return resultObject;
		}catch (Exception ex) {
			throw new RuntimeException("java native decode failure,",ex.getCause());
		}
	}

}

package com.laibao.dubbo.hsf.rpc.serialization.impl;
import com.laibao.dubbo.hsf.rpc.serialization.Encoder;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Objects;

/**
 * Java Native Encoder
 * 
 */
public class JavaNativeEncoder implements Encoder {

	/**
	 *
	 * @param obj
	 * @return byte[]
	 */
	public byte[] encode(Object obj){
		Objects.requireNonNull(obj);
		try{
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			ObjectOutputStream output = new ObjectOutputStream(byteArray);
			output.writeObject(obj);
			output.flush();
			output.close();
			return byteArray.toByteArray();
		}catch (Exception ex) {
			throw new RuntimeException("java native encode failure,",ex.getCause());
		}
	}

}

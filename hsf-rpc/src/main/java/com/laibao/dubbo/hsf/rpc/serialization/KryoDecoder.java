package com.laibao.dubbo.hsf.rpc.serialization;


import com.esotericsoftware.kryo.io.Input;
import com.laibao.dubbo.hsf.rpc.serialization.utils.KryoUtils;

import java.util.Objects;

/**
 * Kryo Decoder
 * 
 */
public class KryoDecoder implements Decoder {

	/**
	 *
	 * @param className
	 * @param bytes
	 * @return Object
	 */
	public Object decode(String className, byte[] bytes){
		Objects.requireNonNull(bytes);
		try{
			Input input = new Input(bytes);
			return KryoUtils.getKryo().readClassAndObject(input);
		}catch (Exception ex){
			throw new RuntimeException("Kryo decode failure,",ex.getCause());
		}

	}
}

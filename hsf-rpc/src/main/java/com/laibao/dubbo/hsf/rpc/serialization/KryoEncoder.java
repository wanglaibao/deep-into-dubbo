package com.laibao.dubbo.hsf.rpc.serialization;


import com.esotericsoftware.kryo.io.Output;
import com.laibao.dubbo.hsf.rpc.serialization.utils.KryoUtils;

import java.util.Objects;

/**
 * Kryo Encoder
 * 
 */
public class KryoEncoder implements Encoder {

	/**
	 *
	 * @param obj
	 * @return byte[]
	 */
	public byte[] encode(Object obj){
		Objects.requireNonNull(obj);
		try{
			Output output = new Output(256);
			KryoUtils.getKryo().writeClassAndObject(output, obj);
			return output.toBytes();
		}catch (Exception ex) {
			throw new RuntimeException("Kryo encode failure,",ex.getCause());
		}
	}

}

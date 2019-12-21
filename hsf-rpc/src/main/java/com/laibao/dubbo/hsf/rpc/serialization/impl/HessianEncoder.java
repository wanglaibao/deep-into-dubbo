package com.laibao.dubbo.hsf.rpc.serialization.impl;
import java.io.ByteArrayOutputStream;
import java.util.Objects;

import com.caucho.hessian.io.Hessian2Output;
import com.laibao.dubbo.hsf.rpc.serialization.Encoder;

/**
 * Hessian Encoder,using Hessian2
 *
 */
public class HessianEncoder implements Encoder {

	/**
	 *
	 * @param obj
	 * @return byte[]
	 */
	public byte[] encode(Object obj){
		Objects.requireNonNull(obj);
		try{
			ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
			Hessian2Output hessian2Output = new Hessian2Output(byteArray);
			hessian2Output.writeObject(obj);
			hessian2Output.close();
			byte[] bytes = byteArray.toByteArray();
			return bytes;
		}catch (Exception ex) {
			throw new RuntimeException("hessian encode failure,",ex.getCause());
		}
	}

}

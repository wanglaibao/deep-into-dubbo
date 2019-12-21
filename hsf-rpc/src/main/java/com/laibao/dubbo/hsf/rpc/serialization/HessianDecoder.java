package com.laibao.dubbo.hsf.rpc.serialization;
import java.io.ByteArrayInputStream;
import java.util.Objects;

import com.caucho.hessian.io.Hessian2Input;
/**
 * Hessian Decoder,using Hessian2
 * 
 */
public class HessianDecoder implements Decoder {

	/**
	 *
	 * @param className
	 * @param bytes
	 * @return Object
	 */
	public Object decode(String className,byte[] bytes){
		Objects.requireNonNull(bytes);
		try{
			Hessian2Input hessian2Input = new Hessian2Input(new ByteArrayInputStream(bytes));
			Object resultObject = hessian2Input.readObject();
			hessian2Input.close();
			return resultObject;
		}catch (Exception ex) {
			throw new RuntimeException("hessian decode failure,",ex.getCause());
		}
	}

}

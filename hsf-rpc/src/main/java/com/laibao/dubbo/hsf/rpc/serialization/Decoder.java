package com.laibao.dubbo.hsf.rpc.serialization;
/**
 * Decoder Interface
 * 
 */
public interface Decoder {

	/**
	 * decode byte[] to Object
	 */
	Object decode(String className, byte[] bytes);
	
}

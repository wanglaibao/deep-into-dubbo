package com.laibao.dubbo.hsf.rpc.serialization;

/**
 * Encoder Interface
 * 
 */
public interface Encoder {

	/**
	 * Encode Object to byte[]
	 */
	byte[] encode(Object obj);
	
}

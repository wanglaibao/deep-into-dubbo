package com.laibao.dubbo.hsf.rpc.protocol;
/**
 * Protocol Interface,for custom network protocol
 * 
 */
public interface Protocol {
	
	/**
	 * encode Message to byte & write to network framework
	 * 
	 * @param message
	 * @param byteBuffer
	 * @throws Exception
	 */
	ByteBufferWrapper encode(Object message, ByteBufferWrapper byteBuffer);

	/**
	 * decode stream to object
	 * 
	 * @param byteBuffer
	 * @param errorObject stream not enough,then return this object
	 * @return Object 
	 */
	Object decode(ByteBufferWrapper byteBuffer, Object errorObject, int... originPosition);

}
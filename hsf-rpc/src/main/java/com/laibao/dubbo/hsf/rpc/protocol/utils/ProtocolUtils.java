package com.laibao.dubbo.hsf.rpc.protocol.utils;


import com.laibao.dubbo.hsf.rpc.ProtocolFactory;
import com.laibao.dubbo.hsf.rpc.RequestWrapper;
import com.laibao.dubbo.hsf.rpc.ResponseWrapper;
import com.laibao.dubbo.hsf.rpc.protocol.ByteBufferWrapper;
import com.laibao.dubbo.hsf.rpc.protocol.Protocol;

/**
 *  Protocol Header
 * 	VERSION(1B): Protocol Version
 *  TYPE(1B):    Protocol Type,so we can custom our protocol
 *  CUSTOM PROTOCOL (such as RPCProtocol)
 * 
 */
public class ProtocolUtils {

	public static final int HEADER_LEN = 2;
	
	public static final byte CURRENT_VERSION = (byte)1;

	/**
	 *
	 * @param message
	 * @param bytebufferWrapper
	 * @return ByteBufferWrapper
	 */
	public static ByteBufferWrapper encode(Object message, ByteBufferWrapper bytebufferWrapper) {
		Integer type = 0;
		if(message instanceof RequestWrapper){
			type = ((RequestWrapper)message).getProtocolType();
		}
		else if(message instanceof ResponseWrapper){
			type = ((ResponseWrapper)message).getProtocolType();
		}
		return ProtocolFactory.getProtocol(type).encode(message, bytebufferWrapper);
	}

	/**
	 *
	 * @param wrapper
	 * @param errorObject
	 * @return Object
	 */
	public static Object decode(ByteBufferWrapper wrapper, Object errorObject){
		final int originPos = wrapper.readerIndex();
		if(wrapper.readableBytes() < 2){
			wrapper.setReaderIndex(originPos);
        	return errorObject;
        }
		int version = wrapper.readByte();
		if(version == 1){
			int type = wrapper.readByte();
			Protocol protocol = ProtocolFactory.getProtocol(type);
			if(protocol == null){
				throw new RuntimeException("Unsupport protocol type: "+type);
			}
			return ProtocolFactory.getProtocol(type).decode(wrapper, errorObject, new int[]{originPos});
		}
		else{
			throw new RuntimeException("Unsupport protocol version: "+ version);
		}
	}

}

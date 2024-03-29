package com.laibao.dubbo.hsf.rpc.protocol.impl;


import com.laibao.dubbo.hsf.rpc.Codecs;
import com.laibao.dubbo.hsf.rpc.RequestWrapper;
import com.laibao.dubbo.hsf.rpc.ResponseWrapper;
import com.laibao.dubbo.hsf.rpc.protocol.ByteBufferWrapper;
import com.laibao.dubbo.hsf.rpc.protocol.Protocol;
import com.laibao.dubbo.hsf.rpc.protocol.utils.ProtocolUtils;

import java.util.Objects;

/**
 * Protocol Header
 * 	VERSION(1B): Protocol Version
 *  TYPE(1B):    Protocol Type,so u can custom your protocol
 *  Simple Processor Protocol
 * 	VERSION(1B):   
 *  TYPE(1B):      request/response 
 *  CODECTYPE(1B):  serialize/deserialize type
 *  KEEPED(1B):    
 *  KEEPED(1B):    
 *  KEEPED(1B):    
 *  ID(4B):        request id
 *  TIMEOUT(4B):   request timeout
 *  BodyClassNameLen(4B): body className Len
 *  LENGTH(4B):    body length
 *  BodyClassName: if need than set
 *  BODY
 *  
 */
public class SimpleProcessorProtocol implements Protocol {
	
	public static final int TYPE = 2;
	
	private static final int CUSTOMPROTOCOL_HEADER_LEN = 1 * 6 + 4 * 4;
	
	private static final byte VERSION = (byte)1;
	
	private static final byte REQUEST = (byte)0;
	
	private static final byte RESPONSE = (byte)1;

	/**
	 *
	 * @param message
	 * @param bytebufferWrapper
	 * @return ByteBufferWrapper
	 */
	public ByteBufferWrapper encode(Object message, ByteBufferWrapper bytebufferWrapper){
		Objects.requireNonNull(message);
		Objects.requireNonNull(bytebufferWrapper);
		if(!(message instanceof RequestWrapper) && !(message instanceof ResponseWrapper)){
			throw new RuntimeException("only support send RequestWrapper && ResponseWrapper");
		}
		int id = 0;
		byte type = REQUEST;
		byte[] body = null;
		int timeout = 0;
		int codecType = 0;
		byte[] className = null;
		if(message instanceof RequestWrapper){
			try{
				RequestWrapper wrapper = (RequestWrapper) message;
				codecType = wrapper.getCodecType();
				body = Codecs.getEncoder(codecType).encode(wrapper.getMessage());
				id = wrapper.getId();
				timeout = wrapper.getTimeout();
				if(codecType == Codecs.PB_CODEC)
					className = wrapper.getMessage().getClass().getName().getBytes();
			}
			catch(Exception ex){
				throw new RuntimeException("encode request object error",ex.getCause());
			}
		}
		else{
			ResponseWrapper wrapper = (ResponseWrapper) message;
			try{
				codecType = wrapper.getCodecType();
				body = Codecs.getEncoder(codecType).encode(wrapper.getResponse()); 
				id = wrapper.getRequestId();
				if(codecType == Codecs.PB_CODEC)
					className = wrapper.getResponse().getClass().getName().getBytes();
			} catch(Exception e){
				// still create response,so client can get it
				wrapper.setResponse(new Exception("encode response object error",e));
				if(codecType == Codecs.PB_CODEC)
					className = Exception.class.getName().getBytes();
				body = Codecs.getEncoder(wrapper.getCodecType()).encode(wrapper.getResponse()); 
			}
			type = RESPONSE;
		}
		int capacity = ProtocolUtils.HEADER_LEN + CUSTOMPROTOCOL_HEADER_LEN  + body.length;
		if(codecType == Codecs.PB_CODEC){
			capacity += className.length;
		}
		ByteBufferWrapper byteBuffer = bytebufferWrapper.getCapacity(capacity);
		byteBuffer.writeByte(ProtocolUtils.CURRENT_VERSION);
		byteBuffer.writeByte((byte)TYPE);
		byteBuffer.writeByte(VERSION);
		byteBuffer.writeByte(type);
		byteBuffer.writeByte((byte)codecType);
		byteBuffer.writeByte((byte)0);
		byteBuffer.writeByte((byte)0);
		byteBuffer.writeByte((byte)0);
		byteBuffer.writeInt(id);
		byteBuffer.writeInt(timeout);
		if(codecType == Codecs.PB_CODEC){
			byteBuffer.writeInt(className.length);
		}
		else{
			byteBuffer.writeInt(0);
		}
		byteBuffer.writeInt(body.length);
		if(codecType == Codecs.PB_CODEC)
			byteBuffer.writeBytes(className);
		byteBuffer.writeBytes(body);
		return byteBuffer;
	}

	/**
	 *
	 * @param wrapper
	 * @param errorObject stream not enough,then return this object
	 * @param originPosArray
	 * @return Object
	 */
	public Object decode(ByteBufferWrapper wrapper,Object errorObject,int...originPosArray){
		final int originPos;
		if(originPosArray!=null && originPosArray.length == 1){
			originPos = originPosArray[0];
		}
		else{
			originPos = wrapper.readerIndex();
		}
		if(wrapper.readableBytes() < CUSTOMPROTOCOL_HEADER_LEN){
			wrapper.setReaderIndex(originPos);
        	return errorObject;
        }
        byte version = wrapper.readByte();
        if(version == (byte)1){
        	byte type = wrapper.readByte();
        	int codecType = wrapper.readByte();
    		wrapper.readByte();
    		wrapper.readByte();
    		wrapper.readByte();
    		int requestId = wrapper.readInt();
    		int timeout = wrapper.readInt();
    		int classNameLen = wrapper.readInt();
    		int bodyLen = wrapper.readInt();
    		if(wrapper.readableBytes() < bodyLen + classNameLen){
    			wrapper.setReaderIndex(originPos);
    			return errorObject;
    		}
    		byte[] classNameByte = null;
    		if(codecType == Codecs.PB_CODEC){
	    		classNameByte = new byte[classNameLen];
	    		wrapper.readBytes(classNameByte);
    		}
    		byte[] body = new byte[bodyLen];
    		wrapper.readBytes(body);
    		int messageLen = ProtocolUtils.HEADER_LEN + CUSTOMPROTOCOL_HEADER_LEN + classNameLen + bodyLen;
        	if(type == REQUEST){
        		RequestWrapper requestWrapper = new RequestWrapper(body,timeout,requestId,codecType, TYPE);
        		requestWrapper.setMessageLen(messageLen);
        		requestWrapper.setArgTypes(new byte[][]{classNameByte});
        		return requestWrapper;
        	}
        	else if(type == RESPONSE){
        		ResponseWrapper responseWrapper = new ResponseWrapper(requestId,codecType,TYPE);
            	responseWrapper.setResponse(body);
            	responseWrapper.setMessageLen(messageLen);
            	responseWrapper.setResponseClassName(classNameByte);
	        	return responseWrapper;
        	}
        	else{
        		throw new UnsupportedOperationException("protocol type : "+type+" is not supported!");
        	}
        }
        else{
        	throw new UnsupportedOperationException("protocol version :"+version+" is not supported!");
        }
	}

}

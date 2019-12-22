package com.laibao.dubbo.hsf.rpc.protocol;
/**
 * ByteBufferWrapper interface,help for integrate different network framework
 * 
 */
public interface ByteBufferWrapper {

	 ByteBufferWrapper getCapacity(int capacity);
	
	 void writeByte(int index, byte data);
	
	 void writeByte(byte data);
	
	 byte readByte();
	
	 void writeInt(int data);
	
	 void writeBytes(byte[] data);
	
	 int readableBytes();
	
	 int readInt();
	
	 void readBytes(byte[] dst);
	
	 int readerIndex();
	
	 void setReaderIndex(int readerIndex);
	
}

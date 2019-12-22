package com.laibao.dubbo.hsf.rpc.client;

import java.util.List;

import com.laibao.dubbo.hsf.rpc.ResponseWrapper;


/**
 * RPC Client Interface
 * 
 */
public interface Client {

	/**
	 * invoke via simple call
	 * @param message
	 * @param timeout
	 * @param codecType serialize/deserialize type
	 * @param protocolType
	 */
	Object invoke(Object message, int timeout, int codecType, int protocolType);

	/**
	 * invoke  via rpc
	 * @param targetInstanceName
	 *            server instance name
	 * @param methodName
	 *            server method name
	 * @param argTypes
	 *            server method arg types
	 * @param args
	 *            send to server request args
	 * @param timeout
	 *            rcp timeout
	 * @param codecType
	 *            serialize/deserialize type
	 * @param protocolType
	 * @return Object return response
	 */
	Object invoke(String targetInstanceName, String methodName,
                             String[] argTypes, Object[] args, int timeout, int codecType, int protocolType);

	/**
	 * receive response from server
	 */
	void putResponse(ResponseWrapper response);
	
	/**
	 * receive responses from server
	 */
	void putResponses(List<ResponseWrapper> responses);
	
	/**
	 * server address
	 * 
	 * @return String
	 */
	String getServerIP();

	/**
	 * server port
	 * 
	 * @return int
	 */
	int getServerPort();

	/**
	 * connect timeout
	 * 
	 * @return int
	 */
	int getConnectTimeout();
	
	/**
	 * get sending bytes size
	 * 
	 * @return long
	 */
	long getSendingBytesSize();
	
	/**
	 * Get factory
	 */
	public ClientFactory getClientFactory();

}

package com.laibao.dubbo.hsf.rpc.server;

import com.laibao.dubbo.hsf.rpc.RequestWrapper;
import com.laibao.dubbo.hsf.rpc.ResponseWrapper;

/**
 * Server Handler interface,
 * when server receive message,it will handle
 * 
 */
public interface ServerHandler {

	/**
	 * register business handler,provide for Server
	 */
	void registerProcessor(String instanceName, Object instance);

	/**
	 * handle the request
	 */
	ResponseWrapper handleRequest(RequestWrapper request);

}
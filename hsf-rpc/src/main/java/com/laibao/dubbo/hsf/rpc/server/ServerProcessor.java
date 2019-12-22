package com.laibao.dubbo.hsf.rpc.server;
/**
 * Direct RPC Call Server Processor Interface
 * 
 */
public interface ServerProcessor {

	/**
	 * Handle request,then return Object
	 * 
	 * @param request
	 * @return Object
	 */
	Object handle(Object request);
	
}

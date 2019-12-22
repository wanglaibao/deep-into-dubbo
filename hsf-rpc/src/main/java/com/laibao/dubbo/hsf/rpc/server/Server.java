package com.laibao.dubbo.hsf.rpc.server;
import java.util.concurrent.ExecutorService;

/**
 * RPC Server Interface
 * 
 */
public interface Server {

	/**
	 * start server and listener server Port,
	 * requests will be handled in business ThreadPool
	 */
	void start(int serverPort, ExecutorService businessThreadPool);
	
	/**
	 * register business handler
	 */
	void registerProcessor(int protocolType, String serviceName, Object serviceInstance);
	
	/**
	 * stop server
	 */
	void stop();
	
}

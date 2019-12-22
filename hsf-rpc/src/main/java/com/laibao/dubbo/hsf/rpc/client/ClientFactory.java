package com.laibao.dubbo.hsf.rpc.client;
/**
 * RPC ClientFactory Interface,help for get appropriate nums client
 * 
 */
public interface ClientFactory {

	long javaHeapSize = Runtime.getRuntime().maxMemory();
	
	/**
	 * when the size of sending bytes in queue reach percent * -Xmx,then do sth based on sendLimitPolicy 
	 * 	to avoid oom
	 * default is 50
	 * 
	 * for example:
	 * 	ClientFactory.sendLimitPercent = 50 -Xmx1g
	 *   if sending bytes size reaches 500m,when u call client.invokeSync then it'll throw NFSRPCRejectException
	 */
	int sendLimitPercent = 50;
	
	SendLimitPolicy sendLimitPolicy = SendLimitPolicy.REJECT;
	
	/**
	 * get client,default targetIP:targetPort --> one connection
	 * u can give custom the key by give customKey
	 */
	Client get(String targetIP, int targetPort, int connectTimeout, String... customKey);

	/**
	 * get client,create clientNums connections to targetIP:targetPort(or your custom key)
	 */
	Client get(String targetIP, int targetPort, int connectTimeout, int clientNums, String... customKey);

	/**
	 * remove some error client
	 */
	void removeClient(String key, Client client);
	
	/**
	 * check if exceed the send limit,if exceed then do sth based on SendLimitPolicy
	 */
	void checkSendLimit();
	
	/**
	 * Enable Send limit,default is false;
	 */
	void enableSendLimit();

}
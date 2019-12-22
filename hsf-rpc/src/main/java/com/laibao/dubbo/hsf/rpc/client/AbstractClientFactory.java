package com.laibao.dubbo.hsf.rpc.client;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.FutureTask;

/**
 * Abstract Client Factory,create custom nums client
 * 
 */
public abstract class AbstractClientFactory implements ClientFactory {

	// Cache client
	private static ConcurrentHashMap<String, FutureTask<List<Client>>> clients = new ConcurrentHashMap();
	
	private static boolean isSendLimitEnabled = false;

	public Client get(final String targetIP, final int targetPort,
			final int connectTimeout, String... customKey){
		return get(targetIP, targetPort, connectTimeout, 1, customKey);
	}

	public Client get(final String targetIP, final int targetPort,
			final int connectTimeout, final int clientNums, String... customKey) {
		String key = targetIP + ":" + targetPort;
		if (customKey != null && customKey.length == 1) {
			key = customKey[0];
		}
		if (clients.containsKey(key)) {
			if (clientNums == 1) {
				try {
					return clients.get(key).get().get(0);
				} catch (Exception e) {
					throw new RuntimeException(e.getCause());
				}
			} else {
				Random random = new Random();
				try {
					return clients.get(key).get().get(random.nextInt(clientNums));
				} catch (Exception e) {
					throw new RuntimeException(e.getCause());
				}
			}
		}
		else {
			final String cacheKey = key;
			FutureTask<List<Client>> task = new FutureTask<List<Client>>(
					new Callable<List<Client>>() {
						public List<Client> call() throws Exception {
							List<Client> clients = new ArrayList<Client>(
									clientNums);
							for (int i = 0; i < clientNums; i++) {
								clients.add(createClient(targetIP, targetPort,
										connectTimeout, cacheKey));
							}
							return clients;
						}
					});
			FutureTask<List<Client>> currentTask = clients.putIfAbsent(key,task);
			if (currentTask == null) {
				task.run();
			} 
			else {
				task = currentTask;
			}
			if (clientNums == 1) {
				try {
					return task.get().get(0);
				} catch (Exception e) {
					throw new RuntimeException(e.getCause());
				}
			}
			else {
				Random random = new Random();
				try {
					return task.get().get(random.nextInt(clientNums));
				} catch (Exception e) {
					throw new RuntimeException(e.getCause());
				}
			}
		}
	}

	public void removeClient(String key, Client client) {
		try {
			// TODO: Fix It
			clients.remove(key);
//			clients.get(key).get().remove(client);
//			clients.get(key)
//					.get()
//					.add(createClient(client.getServerIP(),
//							client.getServerPort(), client.getConnectTimeout(),
//							key));
		} catch (Exception e) {
			// IGNORE
		}
	}
	
	public void enableSendLimit(){
		isSendLimitEnabled = true;
	}
	
	/**
	 * check if sending bytes size exceed limit threshold
	 */
	public void checkSendLimit(){
		if(!isSendLimitEnabled)
			return;
		long threshold =  javaHeapSize * sendLimitPercent / 100;
		long sendingBytesSize = getSendingBytesSize();
		if(sendingBytesSize >= threshold){
			if(sendLimitPolicy == SendLimitPolicy.REJECT){
				throw new RuntimeException("sending bytes size exceed threshold,size: "+sendingBytesSize+", threshold: "+threshold);
			}
			else{
				try {
					Thread.sleep(1000);
					sendingBytesSize = getSendingBytesSize();
					if(sendingBytesSize >= threshold){
						throw new RuntimeException("sending bytes size exceed threshold,size: "+sendingBytesSize+", threshold: "+threshold);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}
	
	private long getSendingBytesSize(){
		long sendingBytesSize = 0;
		for (FutureTask<List<Client>> clientListTask : clients.values()) {
			List<Client> clientList = null;
			try {
				clientList = clientListTask.get();
			} catch (Exception e) {
				throw new RuntimeException(e.getCause());
			}
			for (Client client : clientList) {
				sendingBytesSize += client.getSendingBytesSize();
			}
		}
		return sendingBytesSize;
	}

	public static ClientFactory getInstance() {
		throw new UnsupportedOperationException(
				"should be implemented by true class");
	}

	protected abstract Client createClient(String targetIP, int targetPort, int connectTimeout, String key) throws Exception;

}

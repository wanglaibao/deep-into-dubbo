package com.laibao.dubbo.hsf.rpc.client;

/**
 * when send bytes size reaches limit size,then do sth.
 *
 */
public enum SendLimitPolicy {

	/**
	 *  Reject send request and throw exception
	 */
	REJECT,

	/**
	 * Wait 1 second then retry,if failed again,then throw exception
	 */
	WAIT1SECOND
	
}

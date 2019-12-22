package com.laibao.dubbo.hsf.rpc;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Help for ThreadPool to set thread name
 * 
 */
public class NamedThreadFactory implements ThreadFactory {

	private static final AtomicInteger poolNumber = new AtomicInteger(1);

    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final String namePrefix;
    private final boolean isDaemon;

    public NamedThreadFactory() {
        this("pool");
    }

    public NamedThreadFactory(String name) {
        this(name, false);
    }

    public NamedThreadFactory(String preffix, boolean daemon) {
        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        namePrefix = preffix + "-" + poolNumber.getAndIncrement() + "-thread-";
        isDaemon = daemon;
    }


	public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(group, runnable, namePrefix + threadNumber.getAndIncrement(), 0);
        thread.setDaemon(isDaemon);
        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }

}


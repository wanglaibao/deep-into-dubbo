package com.laibao.dubbo.rpc;

import java.lang.reflect.Proxy;

public class RpcProxy<T> {


    /**
     * 创建动态代理对象
     * @param host
     * @param port
     * @param interfaceType
     * @return
     */
   public T getProxy(final String host,final int port,final Class interfaceType) {

       return (T)Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{interfaceType},new RemoteInvocationHandler(host,port,interfaceType));

   }
}

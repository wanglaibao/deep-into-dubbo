package com.laibao.dubbo.serviceref.test;

import com.laibao.dubbo.serviceref.proxy.ProxyFactory;
import com.laibao.dubbo.serviceref.service.HelloService;
import org.junit.Test;

public class ProxyFactoryTest {

    @Test
    public void testProxyFactory(){
        ProxyFactory proxyFactor = new ProxyFactory(HelloService.class);
        HelloService helloService = proxyFactor.getProxyObject();
        helloService.sayHello();
        /**
         *
         * public abstract void com.laibao.dubbo.serviceref.service.HelloService.sayHello()
         *
         * 进行编码
         * 发送网络请求
         * 将网络请求结果进行解码并返回
         *
         */
    }
}

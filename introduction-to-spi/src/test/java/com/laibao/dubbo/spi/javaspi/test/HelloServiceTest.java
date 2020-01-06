package com.laibao.dubbo.spi.javaspi.test;

import com.laibao.dubbo.spi.javaspi.factory.HelloServiceFactory;
import com.laibao.dubbo.spi.javaspi.interfaces.HelloService;
import org.junit.Test;

public class HelloServiceTest {

    public static String name = "com.laibao.dubbo.spi.javaspi.interfaces.impl.EnglishHelloServiceImpl";

    @Test
    public void testHelloService() {
        HelloService helloService = HelloServiceFactory.newHelloService(name);
        if (helloService != null) {
            System.out.println(helloService.sayHello());
        }else {
            System.out.println("it is null");
        }

    }


}

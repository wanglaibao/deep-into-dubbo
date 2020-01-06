package com.laibao.dubbo.spi.javaspi.factory;

import com.laibao.dubbo.spi.javaspi.interfaces.HelloService;

import java.util.Iterator;
import java.util.ServiceLoader;

public class HelloServiceFactory {

    public HelloServiceFactory(){ }

    public static HelloService newHelloService(String name){
        ServiceLoader<HelloService> serviceLoader = ServiceLoader.load(HelloService.class);
        Iterator<HelloService> services = serviceLoader.iterator();
        while(services.hasNext()){
            HelloService helloService = services.next();
            if(helloService.getClass().toString().contains(name)){
                return helloService;
            }
        }
        return null;
    }
}

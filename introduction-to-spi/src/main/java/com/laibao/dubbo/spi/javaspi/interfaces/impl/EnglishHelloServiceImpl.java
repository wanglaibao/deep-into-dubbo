package com.laibao.dubbo.spi.javaspi.interfaces.impl;

import com.laibao.dubbo.spi.javaspi.interfaces.HelloService;

public class EnglishHelloServiceImpl implements HelloService {
    @Override
    public String sayHello() {
        return "hello,jinge";
    }
}

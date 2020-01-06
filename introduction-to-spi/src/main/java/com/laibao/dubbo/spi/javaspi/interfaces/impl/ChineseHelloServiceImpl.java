package com.laibao.dubbo.spi.javaspi.interfaces.impl;

import com.laibao.dubbo.spi.javaspi.interfaces.HelloService;

public class ChineseHelloServiceImpl implements HelloService {
    @Override
    public String sayHello() {
        return "你好,金戈";
    }
}

package com.laibao.dubbo.nettyrpc.server.service.impl;

import com.laibao.dubbo.nettyrpc.api.service.HelloService;

public class HelloServiceImpl implements HelloService {

    @Override
    public String sayHello(String name) {
        return "Hello RPC World";
    }
}

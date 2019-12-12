package com.laibao.dubbo.rpc.app;

public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "welcome to hangzhou "+name;
    }
}

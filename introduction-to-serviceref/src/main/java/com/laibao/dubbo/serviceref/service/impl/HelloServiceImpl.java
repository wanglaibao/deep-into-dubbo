package com.laibao.dubbo.serviceref.service.impl;

import com.laibao.dubbo.serviceref.service.HelloService;

public class HelloServiceImpl implements HelloService {

    @Override
    public void sayHello() {
        System.out.println("the is com.laibao.dubbo.serviceref.service.impl.HelloServiceImpl");
    }
}

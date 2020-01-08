package com.laibao.dubbo.nettyrpc;

import com.laibao.dubbo.nettyrpc.api.service.HelloService;
import com.laibao.dubbo.nettyrpc.client.RpcClientProxy;

public class ClientConsumer {
    public static void main(String[] args) {
        HelloService service = new RpcClientProxy().createClientProxy(HelloService.class);
        System.out.println(service.sayHello("金戈, 欢迎你"));
        // service.hashCode();
        // service.toString();
    }
}

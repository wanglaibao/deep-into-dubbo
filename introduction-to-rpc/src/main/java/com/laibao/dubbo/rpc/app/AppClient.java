package com.laibao.dubbo.rpc.app;

import com.laibao.dubbo.rpc.RpcProxy;

public class AppClient {

    public static void main(String[] args) {
        RpcProxy<HelloService> rpcProxy = new RpcProxy<>();

        HelloService helloService = rpcProxy.getProxy("localhost",8088,HelloService.class);

        helloService.sayHello("jinge");
    }

}

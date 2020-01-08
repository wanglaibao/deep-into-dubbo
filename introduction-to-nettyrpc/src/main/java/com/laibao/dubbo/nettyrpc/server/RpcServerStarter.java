package com.laibao.dubbo.nettyrpc.server;

public class RpcServerStarter {

    public static void main(String[] args) throws Exception {
        new RpcServer().publish("com.laibao.dubbo.nettyrpc.api.service");
    }
}

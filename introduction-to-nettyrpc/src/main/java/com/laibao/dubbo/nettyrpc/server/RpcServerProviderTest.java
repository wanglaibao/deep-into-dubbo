package com.laibao.dubbo.nettyrpc.server;

public class RpcServerProviderTest {
    public static void main(String[] args) {
        new RpcServer().getProviderClass("com.laibao.dubbo.nettyrpc.api.service");
    }
}

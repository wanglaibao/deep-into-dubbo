package com.laibao.dubbo.rpc.app;

import com.laibao.dubbo.rpc.RpcServer;

public class AppServer {

    public static void main(String[] args) {
        RpcServer rpcServer = new RpcServer();
        try{
            rpcServer.startServer(8088);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}

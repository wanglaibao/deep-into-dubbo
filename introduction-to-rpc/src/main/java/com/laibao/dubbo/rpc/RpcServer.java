package com.laibao.dubbo.rpc;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcServer {

    public void start(final int port) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        try{

            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket =serverSocket.accept();
                executorService.execute(new ProcessHandler(socket));
            }

        }catch (Exception e){

        }finally {

        }
    }
}

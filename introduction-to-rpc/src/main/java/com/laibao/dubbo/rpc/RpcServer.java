package com.laibao.dubbo.rpc;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcServer {

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public void startServer(final int port) throws Exception{
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            Socket socket =serverSocket.accept();
            executorService.execute(new ProcessHandler(socket));
        }
    }
}

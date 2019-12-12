package com.laibao.dubbo.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;
import static com.laibao.dubbo.rpc.RpcRegistry.map;

public class ProcessHandler implements Runnable{

    private final Socket socket;

    public ProcessHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            //接收客户端发送过来的消息 并进行解析
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            RpcMessage rpcMessage = (RpcMessage) objectInputStream.readObject();

            //反射调用本地方法
            String className = rpcMessage.getClassName();
            Class clazz = null;
            if (map.contains(className)) {
                clazz = map.get(className);
            }

            Method method = clazz.getMethod(rpcMessage.getMethodName(),rpcMessage.getParameterTypes());

            Object result = method.invoke(clazz.newInstance(),rpcMessage.getParameterValues());

            //返回结果到客户端
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(result);
            objectOutputStream.flush();

        }catch (Exception e){

        }finally {
            try{
                objectInputStream.close();
                objectOutputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

package com.laibao.dubbo.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

public class RemoteInvocationHandler implements InvocationHandler {

    private final String host;
    private final int port;
    private final Class interfaceType;

    public RemoteInvocationHandler(String host, int port, Class interfaceType) {
        this.host = host;
        this.port = port;
        this.interfaceType = interfaceType;
    }

    /**
     *
     * @param proxy
     * @param method
     * @param args
     * @return Object
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        try{
            //1：客户端建立Socket网络连接
            Socket socket = new Socket(host,port);

            //2：组装待发送的RPC消息以便发送到服务端【消息数据包含: 接口类型 方法名称 方法参数 参数类型】
            RpcMessage rpcMessage = new RpcMessage(interfaceType.getName(),method.getName(),args,method.getExceptionTypes());

            //3：创建输出流以便向服务端发送消息数据
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(rpcMessage);
            objectOutputStream.flush();

            //4：创建输入流以便接收服务端返回的结果
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object result = objectInputStream.readObject();
            return result;
        }catch (Exception ex){

        }finally {
            objectOutputStream.close();
            objectInputStream.close();
        }

        return null;
    }
}

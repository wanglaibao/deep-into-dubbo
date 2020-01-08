package com.laibao.dubbo.nettyrpc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RpcServer {

    // 存放指定包中所有实现类的类名
    private List<String> classCache = new ArrayList<>();

    // 服务注册表
    private Map<String, Object> registerMap = new ConcurrentHashMap<>();


    // 将指定包下的提供者发布出去
    public void publish(String providerPackage) throws Exception {
        // 将指定包下的提供者名称写入到classCache中
        getProviderClass(providerPackage);
        // 将服务名称与提供者实例之间的映射关系写入到registerMap
        doRegister();

        // 完成对客户端调用请求的处理（调用提供者对应的方法）
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    // 指定用户存放客户端请求的队列的长度，默认50
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    // 使用心跳机制维护长连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 添加解码器
                            pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE,
                                    ClassResolvers.cacheDisabled(null)));
                            // 添加编码器
                            pipeline.addLast(new ObjectEncoder());
                            // 添加自定义处理器
                            pipeline.addLast(new RpcServerHandler(registerMap));
                        }
                    });
            ChannelFuture future = bootstrap.bind(8888).sync();
            System.out.println("服务器已启动");
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


    // 将指定包下的提供者名称写入到classCache中
    // com.kkb.rpc.service.xxx
    public void getProviderClass(String providerPackage) {
        // 将字符串的包转化为了URL对象资源
        URL resource = this.getClass().getClassLoader().getResource(providerPackage.replaceAll("\\.", "/"));
        // 将URL转化为File
        File dir = new File(resource.getFile());
        // 启动指定包的目录，查找.class文件
        for (File file : dir.listFiles()) {
            // 若当前遍历的是目录，则递归
            if(file.isDirectory()) {
                getProviderClass(providerPackage + "." + file.getName());
            } else if(file.getName().endsWith(".class")) {
                // 获取去掉.class扩展名的简单类名
                String fileName = file.getName().replace(".class", "").trim();
                // 将全限定性类名写入到classCache中
                classCache.add(providerPackage + "." + fileName);
            }
        }
        // System.out.println("classCache = " + classCache);
    }


    // 将服务名称与提供者实例之间的映射关系写入到registerMap
    private void doRegister() throws Exception {
        // 若没有提供者类，则无需注册
        if(classCache.size() == 0) return;;

        // registerMap的key为接口名，value为该接口对应的实现类的实例
        for (String className : classCache) {
            Class<?> clazz = Class.forName(className);
            registerMap.put(clazz.getInterfaces()[0].getName(), clazz.newInstance());
        }
    }

}

package com.laibao.dubbo.nettyrpc.server;

import com.laibao.dubbo.nettyrpc.api.bean.RpcInvocation;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;

public class RpcServerHandler extends SimpleChannelInboundHandler<RpcInvocation> {

    private Map<String, Object> registerMap;

    public RpcServerHandler(Map<String, Object> registerMap) {
        this.registerMap = registerMap;
    }


    protected void channelRead0(ChannelHandlerContext ctx, RpcInvocation invocation) throws Exception {
        Object result = "没有指定的提供者";
        // 判断注册中心中是否存在指定名称的服务
        if(registerMap.containsKey(invocation.getInterfaceName())) {
            // 获取指定名称的服务提供者实例
            Object provider = registerMap.get(invocation.getInterfaceName());
            result = provider.getClass()
                    .getMethod(invocation.getMethodName(), invocation.getParamTypes())
                    .invoke(provider, invocation.getParamValues());
        }
        // 将该运算结果发送给客户端
        ctx.channel().writeAndFlush(result);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

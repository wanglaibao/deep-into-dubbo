package com.laibao.dubbo.rpc;

import java.util.concurrent.ConcurrentHashMap;

public class RpcRegistry {

    /**
     * key: 接口名
     * value: 接口的实现类
     */
    public static ConcurrentHashMap<String,Class> map = new ConcurrentHashMap<>();
}

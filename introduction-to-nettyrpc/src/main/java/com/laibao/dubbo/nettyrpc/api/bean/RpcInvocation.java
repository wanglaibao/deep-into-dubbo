package com.laibao.dubbo.nettyrpc.api.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class RpcInvocation implements Serializable {

    /**
     * 接口名
     */
    private String interfaceName;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 方法参数类型列表
     */
    private Class<?>[] paramTypes;

    /**
     * 方法参数值
     */
    private Object[] paramValues;
}

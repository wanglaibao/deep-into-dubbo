package com.laibao.dubbo.rpc;

import java.io.Serializable;

public final class RpcMessage implements Serializable {

    private String className;

    private String methodName;

    private Object[] parameters;

    private Class[] types;

    public RpcMessage(String className, String methodName, Object[] parameters, Class[] types) {
        this.className = className;
        this.methodName = methodName;
        this.parameters = parameters;
        this.types = types;
    }
}

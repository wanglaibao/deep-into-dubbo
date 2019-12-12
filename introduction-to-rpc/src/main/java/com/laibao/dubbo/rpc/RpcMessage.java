package com.laibao.dubbo.rpc;

import java.io.Serializable;

public final class RpcMessage implements Serializable {

    private String className;

    private String methodName;

    private Object[] parameters;

    private Class[] parameterTypes;


    public RpcMessage() {
    }

    public RpcMessage(String className, String methodName, Object[] parameters, Class[] parameterTypes) {
        this.className = className;
        this.methodName = methodName;
        this.parameters = parameters;
        this.parameterTypes = parameterTypes;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }
}

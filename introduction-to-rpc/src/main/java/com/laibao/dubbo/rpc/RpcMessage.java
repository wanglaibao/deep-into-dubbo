package com.laibao.dubbo.rpc;

import java.io.Serializable;

public final class RpcMessage implements Serializable {

    private String className;

    private String methodName;

    private Object[] parameterValues;

    private Class[] parameterTypes;


    public RpcMessage() {
    }

    public RpcMessage(String className, String methodName, Object[] parameterValues, Class[] parameterTypes) {
        this.className = className;
        this.methodName = methodName;
        this.parameterValues = parameterValues;
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

    public Object[] getParameterValues() {
        return parameterValues;
    }

    public void setParameterValues(Object[] parameterValues) {
        this.parameterValues = parameterValues;
    }

    public Class[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }
}

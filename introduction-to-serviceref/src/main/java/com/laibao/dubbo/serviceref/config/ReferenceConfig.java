package com.laibao.dubbo.serviceref.config;

import com.laibao.dubbo.serviceref.proxy.ProxyFactory;

public class ReferenceConfig<T> {

    private Class<?> interfaceClass;

    // 接口代理类引用
    private transient volatile T ref;

    public synchronized T get() {
        if (ref == null) {
            init();
        }
        return ref;
    }

    private void init() {
        ref = new ProxyFactory(interfaceClass).getProxyObject();
    }

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }
}

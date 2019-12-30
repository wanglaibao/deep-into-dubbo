package com.laibao.dubbo.serviceref.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class ServiceRefNamespaceHandler extends NamespaceHandlerSupport {
    public void init() {
        registerBeanDefinitionParser("reference", new ServiceRefBeanDefinitionParser());
    }
}

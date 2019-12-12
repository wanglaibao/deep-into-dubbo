package com.laibao.dubbo.spi.dubbospi.interfaces.impl;

import com.laibao.dubbo.spi.dubbospi.interfaces.ShapeContainAdaptiveMethod;
import org.apache.dubbo.common.URL;

public class ShapeContainAdaptiveMethodImpl implements ShapeContainAdaptiveMethod {

    @Override
    public void drawWithUrl(URL url) {
        String parameter = url.getParameter("demo");
        System.out.println("我是ShapeContainAdaptiveMethodImpl"+url);
        System.out.println(parameter);
    }
}

package com.laibao.dubbo.spi.dubbospi.interfaces;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

@SPI
public interface ShapeContainAdaptiveMethod {

    @Adaptive("demo")
    void drawWithUrl(URL url);
}

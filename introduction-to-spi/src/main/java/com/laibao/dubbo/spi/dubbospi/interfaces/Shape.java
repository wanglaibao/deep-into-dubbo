package com.laibao.dubbo.spi.dubbospi.interfaces;

import org.apache.dubbo.common.extension.SPI;

@SPI("circle")
public interface Shape {

    void draw();
}

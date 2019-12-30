package com.laibao.dubbo.toyspi.log;

import com.laibao.dubbo.toyspi.annotation.SPI;

@SPI("simpleLog")
public interface Log {

    void say();

}

package com.laibao.dubbo.spi.javaspi.interfaces.impl;

import com.laibao.dubbo.spi.javaspi.interfaces.Robot;

public class OptimusPrime implements Robot {
    @Override
    public void sayHello() {
        System.out.println("Hello, I am Optimus Prime.");
    }
}

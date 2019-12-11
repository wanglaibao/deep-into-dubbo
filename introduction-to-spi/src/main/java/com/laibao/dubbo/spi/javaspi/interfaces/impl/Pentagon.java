package com.laibao.dubbo.spi.javaspi.interfaces.impl;


import com.laibao.dubbo.spi.javaspi.interfaces.Shape;

public class Pentagon implements Shape {
    @Override
    public void draw() {
        System.out.println("我是五边形");
    }
}

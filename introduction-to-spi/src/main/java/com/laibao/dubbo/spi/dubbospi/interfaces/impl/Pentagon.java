package com.laibao.dubbo.spi.dubbospi.interfaces.impl;


import com.laibao.dubbo.spi.dubbospi.interfaces.Shape;

public class Pentagon implements Shape {
    @Override
    public void draw() {
        System.out.println("我是五边形");
    }
}

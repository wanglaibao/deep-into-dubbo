package com.laibao.dubbo.spi.dubbospi.interfaces.impl;


import com.laibao.dubbo.spi.dubbospi.interfaces.Shape;

public class Hexagon implements Shape {
    @Override
    public void draw() {
        System.out.println("我是六边形");
    }
}

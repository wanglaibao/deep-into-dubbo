package com.laibao.dubbo.spi.dubbospi.interfaces.impl;


import com.laibao.dubbo.spi.dubbospi.interfaces.Shape;

public class Triangle implements Shape {
    @Override
    public void draw() {
        System.out.println("我是三角形");
    }

}

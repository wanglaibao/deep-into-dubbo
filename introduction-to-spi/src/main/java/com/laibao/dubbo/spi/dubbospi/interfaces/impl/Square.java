package com.laibao.dubbo.spi.dubbospi.interfaces.impl;


import com.laibao.dubbo.spi.dubbospi.interfaces.Shape;

public class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("我是正方形形");
    }
}

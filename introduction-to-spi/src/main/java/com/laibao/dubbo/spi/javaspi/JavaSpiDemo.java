package com.laibao.dubbo.spi.javaspi;


import com.laibao.dubbo.spi.javaspi.interfaces.Shape;

import java.util.ServiceLoader;

/**
 * 1:在META-INF/services下面创建接口的全限定名文件【com.laibao.dubbo.spi.javaspi.interfaces.Shape】
 *
 * 2:在接口的全限定名文件中分行写入实现类的全限定类名
 */
public class JavaSpiDemo {

    public static void main(String[] args) {
        ServiceLoader<Shape> serviceLoader = ServiceLoader.load(Shape.class);
        System.out.println("ddddddddddddd");
        serviceLoader.forEach(Shape::draw);
    }
}

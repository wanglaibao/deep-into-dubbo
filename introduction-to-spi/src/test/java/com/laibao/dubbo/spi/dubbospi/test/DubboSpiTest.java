package com.laibao.dubbo.spi.dubbospi.test;

import com.laibao.dubbo.spi.dubbospi.interfaces.Shape;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.Test;

public class DubboSpiTest {

    @Test
    public void getShapeByName() {
        ExtensionLoader<Shape> extensionLoader = ExtensionLoader.getExtensionLoader(Shape.class);
        Shape shape = extensionLoader.getExtension("square");
        shape.draw();
    }
}

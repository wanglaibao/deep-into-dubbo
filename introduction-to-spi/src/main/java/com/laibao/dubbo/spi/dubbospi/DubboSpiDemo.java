package com.laibao.dubbo.spi.dubbospi;

import com.laibao.dubbo.spi.dubbospi.interfaces.Shape;
import org.apache.dubbo.common.extension.ExtensionLoader;

/**
 *
 */
public class DubboSpiDemo {

    /**
     * private static final String SERVICES_DIRECTORY = "META-INF/services/";
     *
     * private static final String DUBBO_DIRECTORY = "META-INF/dubbo/";
     *
     * private static final String DUBBO_INTERNAL_DIRECTORY = DUBBO_DIRECTORY + "internal/";
     *
     */

    public static void main(String[] args) {
        ExtensionLoader<Shape> extensionLoader = ExtensionLoader.getExtensionLoader(Shape.class);
        Shape shape = extensionLoader.getDefaultExtension();
        shape.draw();
    }
}

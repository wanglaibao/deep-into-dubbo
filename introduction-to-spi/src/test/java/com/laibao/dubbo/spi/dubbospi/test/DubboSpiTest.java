package com.laibao.dubbo.spi.dubbospi.test;

import com.laibao.dubbo.spi.dubbospi.interfaces.Shape;
import com.laibao.dubbo.spi.dubbospi.interfaces.ShapeContainAdaptiveMethod;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.Test;

public class DubboSpiTest {

    //cachedClasses        缓存 非@Adaptive 注解的类    通过getExtension(String name)方式来获取

    //cachedAdaptiveClass  缓存 @Adaptive 注解的类      通过getAdaptiveExtension()方式来获取

    //cachedWrapperClasses 缓存 Wrapper包装的类


    /**
     *
     *     Holder<Map<String, Class<?>>> cachedClasses = new Holder<>();
     *
     *     Map<String, Object> cachedActivates = new ConcurrentHashMap<>();
     *
     *
     *     ConcurrentMap<String, Holder<Object>> cachedInstances = new ConcurrentHashMap<>();
     *
     *     Holder<Object> cachedAdaptiveInstance = new Holder<>();
     *
     *     private volatile Class<?> cachedAdaptiveClass = null;
     *
     *     private Set<Class<?>> cachedWrapperClasses;
     */


    @Test
    public void getShapeByName() {
        ExtensionLoader<Shape> extensionLoader = ExtensionLoader.getExtensionLoader(Shape.class);
        Shape shape = extensionLoader.getExtension("square");
        shape.draw();
    }


    /**
     * @Adaptive 注解位于类上面
     */
    @Test
    public void getAdaptiveShapeOnClass() {
        ExtensionLoader<Shape> extensionLoader = ExtensionLoader.getExtensionLoader(Shape.class);
        Shape shape = extensionLoader.getAdaptiveExtension();
        shape.draw();
    }



    /**
     * @Adaptive 注解位于方法上面
     */
    @Test
    public void getAdaptiveShapeOnMethod() {
        //配置在方法上面 解析url参数拿到相应的key 动态生成$Adaptive class
        //先通过字符串拼接出Java代码 再通过compiler进行编译(javassist)
        URL url = URL.valueOf("dubbo://localhost/test?demo=adaptivemethod");
        ExtensionLoader<ShapeContainAdaptiveMethod> extensionLoader = ExtensionLoader.getExtensionLoader(ShapeContainAdaptiveMethod.class);
        ShapeContainAdaptiveMethod shape = extensionLoader.getAdaptiveExtension();
        shape.drawWithUrl(url);
    }
}

package com.laibao.dubbo.toyspi.test;

import com.laibao.dubbo.toyspi.loader.SimpleExtensionLoader;
import com.laibao.dubbo.toyspi.log.Log;
import org.junit.Test;

public class SPITest {

    @Test
    public void testLog() {

        //获取默认实现类
        Log defaultLog = SimpleExtensionLoader.getExtensionLoader(Log.class).getDefaultExtension();
        defaultLog.say();

        //指定特定的实现类,例如配置的tobyLog
        Log toyLog = SimpleExtensionLoader.getExtensionLoader(Log.class).getExtension("toyLog");
        toyLog.say();

    }
}

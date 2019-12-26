package com.laibao.dubbo.spi.javaspi.test;

import com.laibao.dubbo.spi.javaspi.interfaces.Robot;
import org.junit.Test;

import java.util.ServiceLoader;

public class JavaSPITest {

    @Test
    public void testSayHello(){
        ServiceLoader<Robot> serviceLoader = ServiceLoader.load(Robot.class);
        System.out.println("Java SPI");
        serviceLoader.forEach(Robot::sayHello);
    }
}

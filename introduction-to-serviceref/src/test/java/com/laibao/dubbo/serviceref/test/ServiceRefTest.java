package com.laibao.dubbo.serviceref.test;

import com.laibao.dubbo.serviceref.service.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:META-INF/spring/service-ref.xml")
public class ServiceRefTest {

    @Autowired
    private HelloService helloService;

    @Test
    public void testHelloService() {
        helloService.sayHello();
    }
}

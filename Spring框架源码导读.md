#                           Spring框架源码导读

   * IOC根容器BeanFactory概述
   * BeanFactory和FactoryBean详解


### 重要的核心类源码

* BeanFactory 和 ApplicationContext 是两大基础核心容器要掌握
* BeanFactory【用来创建和管理Bean对象的】
* FactoryBean【工厂Bean是用来创建其他Bean对象的】
* BeanDefinition
* ListableBeanFactory
* ConfigurableBeanFactory
* DefaultListableBeanFactory
* XmlBeanFactory【已经被废弃掉了】
* ApplicationContext
* WebApplicationContext

* Resource
* ClassPathResource

* BeanDefinition
* BeanDefinitionReader
* XmlBeanDefinitionReader
* BeanDefinitionRegistry
* DefaultListableBeanFactory与资源加载Resource



### 核心设计模式


* 工厂模式

* 抽象工厂模式

* 单例模式

* 建造者模式

* 原型模式

* 适配器模式

* 桥接模式

* 标准模式

* 组合模式

* 装饰器模式

* 外观模式

* 享元模式

* 代理模式

* 责任链模式

* 命令模式

* 解释器模式

* 迭代器模式

* 中介者模式

* 备忘录模式

* 观察者模式

* 状态模式

* 空对象模式

* 策略模式

* 模板模式

* 拦截过滤器模式





https://repo.spring.io/libs-release/org/springframework/spring/  【Spring框架的源码资源库】


在3.1后不建议再使用XmlBeanFactory,z这个类已经被标记为废弃了[@Deprecated]
建议使用DefaultListableBeanFactroy这个父类了,结合XMLBeanDefinitionReader来完成IOC容器.


Resource extends InputStreamSouce

ClassPathResource  resource = new ClassPathResource("beans.xml");

DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory)

reader.loaderBeandifiniton(resource)




https://github.com/onlyliuxin/litespring/tree/testcase-1/src/main/java/org/litespring 【从零造Spring 源码地址】
https://www.52pojie.cn/thread-975952-1-1.html 【从零造Spring 视频资料下载地址】
https://blog.csdn.net/u014534808/article/details/81071452  【从零开始造Spring04---补充之ASM的原理以及在Spring中的应用】
https://blog.csdn.net/alinyua/category_9282041.html   【从零开始写Spring注解版框架】
https://blog.csdn.net/qq_30258957/category_7749551.html  【闲聊Spring】
https://blog.csdn.net/qq_30258957/article/details/80767746 【闲聊Spring-2.BasicBeanFactory】
https://www.jianshu.com/p/cb39f2f46a7f    【从零开始写Spring AOP框架-（基本技术）】
https://www.cnblogs.com/jjpp/p/12103601.html   【spring源码学习笔记-第一周（BeanFactory）刘欣从零开始造spring】
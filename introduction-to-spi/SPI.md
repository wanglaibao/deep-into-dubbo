#               SPI 简介

### JDK SPI  VS Dubbo SPI

* JDK 标准的 SPI 会一次性实例化扩展点所有实现，如果有扩展实现初始化很耗时，但如果没用上也加载，会很浪费资源

* JDK的spi要用for循环,然后if判断才能获取到指定的spi对象,dubbo用指定的key就可以获取

* JDK的spi不支持默认值,dubbo增加了默认值的设计

* 增加了对扩展点 IoC 和 AOP 的支持，一个扩展点可以直接 setter 注入其它扩展点

* 装饰者设计模式->静态代理->JDK、cglib、Javassist优缺点对比->Spring AOP源码


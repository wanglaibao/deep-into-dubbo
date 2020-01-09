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





https://repo.spring.io/libs-release/org/springframework/spring/  【Spring框架的源码资源库】


在3.1后不建议再使用XmlBeanFactory,z这个类已经被标记为废弃了[@Deprecated]
建议使用DefaultListableBeanFactroy这个父类了,结合XMLBeanDefinitionReader来完成IOC容器.


Resource extends InputStreamSouce

ClassPathResource  resource = new ClassPathResource("beans.xml");

DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory)

reader.loaderBeandifiniton(resource)



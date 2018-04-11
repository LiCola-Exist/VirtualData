

# [VirtualData](https://github.com/LiCola/VirtualData)
虚拟数据生成

[ ![Download](https://api.bintray.com/packages/licola/maven/VirtualData/images/download.svg) ](https://bintray.com/licola/maven/VirtualData/_latestVersion)

# 项目起源
在编写单元测试代码时，MVP模式下，P层通过Mock对象实现测试，V层测试需要验证界面元素的显示，很多时候需要手动初始化VO视图对象。

直到我看到一篇文章

> 利用JAVA反射机制获取所述数据对象的属性信息；根据所述数据对象的类型和属性信息，按照设定的规则生成用于进行插入测试或/和更新测试的测试用数据
> ---[测试用数据的生成方法、单元测试方法以及单元测试系统](https://patents.google.com/patent/CN102760096A/zh)。

# 原理
基本原理就是反射，通过反射了解类的信息，字段名，根据字段名随机生成值并赋值。
采用Builder建造者模式，灵活配置。


# 使用
```java
CollectionUserModel models = DataVirtualBuilder.virtual(CollectionUserModel.class)
        .build();
```
一行代码解决。

> 因为这个项目的使用目的就是在单元测试中使用，所以没有特别考虑性能，没有缓存构造方法结构等优化处理。


# 缺点
目前反射的是Model的空参构造方法。所以对Model的构造方法有限制。使用时请注意。



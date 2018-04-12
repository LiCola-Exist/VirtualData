

# [VirtualData](https://github.com/LiCola/VirtualData)
虚拟数据生成

[ ![Download](https://api.bintray.com/packages/licola/maven/VirtualData/images/download.svg) ](https://bintray.com/licola/maven/VirtualData/_latestVersion)

# 项目起源
在编写单元测试代码时，MVP模式下，P层通过Mock对象实现测试，但是Mock对象本身是没有任何数据的。
在V层进行单元测试时，测试需要验证界面元素的显示，很多时候需要手动初始化VO视图对象。然后还要去验证显示。


直到我看到一篇文章

> 利用JAVA反射机制获取所述数据对象的属性信息；根据所述数据对象的类型和属性信息，按照设定的规则生成用于进行插入测试或/和更新测试的测试用数据
> ---[测试用数据的生成方法、单元测试方法以及单元测试系统](https://patents.google.com/patent/CN102760096A/zh)。

所以可以通过该库实现快速的数据生成，然后再针对特殊的测试字段，手动控制。省略其他无关的操作和赋值。

# 原理
基本原理就是反射，通过反射了解类的信息，字段名，根据字段名随机生成值并赋值。
采用Builder建造者模式，灵活配置，提供各种方法配置参数。


# 使用
```java
CollectionUserModel models = VirtualDataBuilder.virtual(CollectionUserModel.class)
        .build();

List<UserModel> userModels = VirtualDataBuilder.virtual(UserModel.class).buildList();

```
基本就是一行代码解决问题。而且支持生成中文内容。

更多使用参考VirtualDataBuilderUnitTest测试类


# 缺点
目前反射的是Model的空参构造方法。所以对Model的构造方法有要求。使用时请注意。





# [VirtualData](https://github.com/LiCola/VirtualData)


[ ![Download](https://api.bintray.com/packages/licola/maven/VirtualData/images/download.svg) ](https://bintray.com/licola/maven/VirtualData/_latestVersion)

# 项目起源
在开发调试中我们需要观察数据Model是否正常的显示在UI中，在单元测试中需要检测的是View页面是否正常显示。它们本质其实就是验证ViewObject（VO）表现层对象的正常表现。

在一般的开发我们怎样模拟VO，手动生成？
```java
 UserModel userModel=new UserModel("userId","userName","用户等级100");
```
像这样，然后用这个model传入到View层显示起来，然后检测正常显示。

这样的方式有几个问题：

* Model的内部字段如果很多，构造参数就会很长，使用不方便。
* Model的内部结构变化，新增或删除字段都会影响构造参数，就会影响其测试模块构造VO部分的代码。

在早期的开发中，如果界面结构还在调整中VO的结构经常会发生改变。
在产品的迭代中产品新的界面形式也可能导致VO结构的修改，这些都是常常发生的。
如果每次修改都需要我们的修改VO的模拟代码，就是很痛苦的事情了。


一篇文章给了我提示

> 利用JAVA反射机制获取所述数据对象的属性信息；根据所述数据对象的类型和属性信息，按照设定的规则生成用于进行插入测试或/和更新测试的测试用数据
> ---[测试用数据的生成方法、单元测试方法以及单元测试系统](https://patents.google.com/patent/CN102760096A/zh)。

# 引用

```java
   implementation 'com.licola:virtual:1.2.5'
```

# 使用
```java
UserModel userModel = VirtualData.virtual(UserModel.class).build();
        
List<CommodityModel> userModels = VirtualData.virtual(CommodityModel.class).buildList();
        
//外部规则设置
 CommodityModel commodityModel = VirtualData
        .virtual(CommodityModel.class, new MyVirtualDataBuilder())
        .build();

```
基本就是一行代码解决问题。
更多使用参考[VirtualDataUnitTest](https://github.com/LiCola/VirtualData/blob/master/app/src/test/java/com/model/licola/virtualdata/VirtualDataUnitTest.java)测试类的代码

如果需要修改数据规则，继承`VirtualDataDefaultBuilder`重写`injectRuleXX`注入新的类型名称数据规则。
参考[MyVirtualDataBuilder](https://github.com/LiCola/VirtualData/blob/master/app/src/main/java/com/model/licola/virtualdata/MyVirtualDataBuilder.java)定新的数据规则

# 更新
- 1.2.1:添加Queue类的支持`buildQueue`，修复错误。
- 1.2.2:调整模块类命名，添加一些数据规则。 
- 1.2.5:增加通过系统Api（sun.misc.Unsafe）方式构造对象，优化对Virtual模拟类的限制（不再需要声明空参构造方法也能构造对象）

# 原理
基本原理就是反射，通过反射了解类的信息，字段名，根据字段名匹配赋值规则。
采用Builder建造者模式，灵活配置，提供各种方法配置参数。

# 新特性
参考[Gson](https://github.com/google/gson/blob/master/gson/src/main/java/com/google/gson/internal/ConstructorConstructor.java)
对通过Class类对象构造实例对象的处理，目前解除对Virtual模拟类的限制，不再需要声明空参构造方法也能构造对象。



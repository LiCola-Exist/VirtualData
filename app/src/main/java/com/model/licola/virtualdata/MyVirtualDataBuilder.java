package com.model.licola.virtualdata;

import com.model.licola.virtualdata.model.CommodityModel;
import com.model.licola.virtualdata.model.ImageModel;
import java.util.LinkedHashMap;
import java.util.Map;
import virtual.RandomInterface;
import virtual.RandomRule.RandomStringPhoneNumber;
import virtual.VirtualDataDefaultBuilder;

/**
 * Created by LiCola on 2018/5/9.
 * 继承原有默认规则 修改或重写部分内容
 */
public class MyVirtualDataBuilder extends VirtualDataDefaultBuilder {

  /**
   * 注入Object类型即 自定义的model数据规则
   * 实现全新的定义规则
   */
  @Override
  public Map<String, RandomInterface<Object>> injectRuleModel(
      Map<String, RandomInterface<Object>> map) {
    //直接添加数据规则
    map.put("commodity", new RandomInterface<Object>() {
      @Override
      public Object getRandomData() {
        return new CommodityModel("手动设置的title", 100, 1);
      }
    });

    //Map结构因为包含两个类型 无法直接使用数据命名规则匹配 只有特殊字段名直接赋值
    map.put("userMap", new RandomInterface<Object>() {
      @Override
      public Object getRandomData() {
        Map<String, Integer> hashMap = new LinkedHashMap<>();
        hashMap.put("因为map结构有两个类型，无法统一处理，只能直接返回", 100);
        return hashMap;
      }
    });

    //针对一些有特殊含义的Model，里面的字段有具体含义 不能根据类型模拟 只有针对特殊的字段名做数据处理
    map.put("imageModel", new RandomInterface<Object>() {
      @Override
      public Object getRandomData() {
        return new ImageModel("特殊Model的id有具体的含义，无需模拟", "特殊Model的hash有具体含义，无需模拟");
      }
    });
    map.put("avatar", new RandomInterface<Object>() {
      @Override
      public Object getRandomData() {
        return new ImageModel("用户的头像图片id", "用户的头像图片hash");
      }
    });


    //直接返回 入参
    return map;
  }


  /**
   * 注入String类型的数据规则
   * 实现覆盖部分规则，并继承其他规则
   */
  @Override
  public Map<String, RandomInterface<String>> injectRuleString(
      Map<String, RandomInterface<String>> map) {

    //调起父类方法 得到原有规则
    Map<String, RandomInterface<String>> original = super.injectRuleString(map);
    //覆盖原有phone字段规则
    original.put("phone", new RandomStringPhoneNumber(11, "180"));
    //返回覆盖数据后的原有数据
    return original;
  }
}

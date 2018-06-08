package com.model.licola.virtualdata;

import com.model.licola.virtualdata.model.CommodityModel;
import com.model.licola.virtualdata.model.ImageModel;
import java.util.HashMap;
import java.util.Map;
import virtual.RandomInterface;
import virtual.RandomRule.RandomStringPhoneNumber;
import virtual.RandomUtils;
import virtual.VirtualDataDefaultBuilder;

/**
 * Created by LiCola on 2018/5/9.
 * 继承{@link VirtualDataDefaultBuilder}修改数据规则
 * 1：可以直接添加新规则 put
 * 2：可以通过super调起父类方法实现继承全部原有规则，并put覆盖
 * 3：通过预留的injectRuleModel方法，添加特殊的model类，如Map、有特殊意义的图片对象ImageModel
 */
public class MyVirtualDataBuilder extends VirtualDataDefaultBuilder {

  /**
   * 注入int和Integer类的数据规则
   *
   * @param map 直接通过put添加规则
   * @return 全新定义的数据规则
   */
  @Override
  public Map<String, RandomInterface<Integer>> injectRuleInteger(
      Map<String, RandomInterface<Integer>> map) {
    map.put("price", new RandomInterface<Integer>() {
      @Override
      public Integer getRandomData() {
        return RandomUtils.getIntegerLength(4);//4位数的价格
      }
    });
    return map;
  }


  /**
   * 注入String类型的数据规
   *
   * @param map 先调起super父类方法注入原有规则，再put实现覆盖部分规则
   * @return 父类和子类覆盖+新增的数据规则
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

  /**
   * 注入Object类型即 自定义的model数据规则
   * 添加新的定义规则
   */
  @Override
  public Map<String, RandomInterface<Object>> injectRuleModel(
      Map<String, RandomInterface<Object>> map) {

    //直接添加包装类 数据规则
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
        Map<String, Integer> hashMap = new HashMap<>();
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
}

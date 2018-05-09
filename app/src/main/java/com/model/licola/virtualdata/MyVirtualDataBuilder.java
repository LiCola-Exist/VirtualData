package com.model.licola.virtualdata;

import com.model.licola.virtualdata.model.CommodityModel;
import java.util.Map;
import virtual.RandomRule.RandomInterface;
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
    map.put("commodityModel", new RandomInterface<Object>() {
      @Override
      public Object getRandomData() {
        return new CommodityModel("嵌套在其他model的commodityModel", 1, 100);
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

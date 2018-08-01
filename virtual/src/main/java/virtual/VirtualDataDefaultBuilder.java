package virtual;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import virtual.VirtualRules.VirtualBoolean;
import virtual.VirtualRules.VirtualDouble;
import virtual.VirtualRules.VirtualFloat;
import virtual.VirtualRules.VirtualInteger;
import virtual.VirtualRules.VirtualIntegerWithLength;
import virtual.VirtualRules.VirtualLong;
import virtual.VirtualRules.VirtualLongWithLength;
import virtual.VirtualRules.VirtualStringAlphabet;
import virtual.VirtualRules.VirtualStringAlphabetNumber;
import virtual.VirtualRules.VirtualStringChinese;
import virtual.VirtualRules.VirtualStringNumber;
import virtual.VirtualRules.VirtualStringPhoneNumber;
import virtual.VirtualRules.VirtualStringSymbol;

/**
 * Created by LiCola on 2018/5/4.
 * 实现Builder接口，提供默认规则
 */
public class VirtualDataDefaultBuilder implements VirtualDataBuilder {

  public static VirtualDataBuilder create() {
    return new VirtualDataDefaultBuilder();
  }

  @Override
  public boolean throwConstructorException() {
    return false;
  }

  @Override
  public Map<String, VirtualApi<Boolean>> injectRuleBoolean(
      Map<String, VirtualApi<Boolean>> map) {
    map.put("is", new VirtualBoolean());
    return map;
  }

  @Override
  public Map<String, VirtualApi<Integer>> injectRuleInteger(
      Map<String, VirtualApi<Integer>> map) {
    map.put("id", new VirtualIntegerWithLength(10));
    map.put("price", new VirtualInteger(10000));
    map.put("money", new VirtualInteger(10000));
    map.put("age", new VirtualInteger(120));
    map.put("level", new VirtualInteger(100));
    map.put("grade", new VirtualInteger(100));
    map.put("process", new VirtualInteger(100));
    return map;
  }

  @Override
  public Map<String, VirtualApi<Long>> injectRuleLong(
      Map<String, VirtualApi<Long>> map) {
    map.put("time", new VirtualApi<Long>() {
      @Override
      public Long onVirtual() {
        return System.currentTimeMillis() / 1000;
      }
    });
    map.put("price", new VirtualLong(10000));
    map.put("money", new VirtualLong(10000));
    map.put("id", new VirtualLongWithLength(14));
    return map;
  }

  @Override
  public Map<String, VirtualApi<Float>> injectRuleFloat(
      Map<String, VirtualApi<Float>> map) {
    map.put("level", new VirtualFloat(0, 100));
    map.put("grade", new VirtualFloat(0, 100));
    map.put("process", new VirtualFloat(0, 100));
    map.put("scale", new VirtualFloat(0, 100));
    return map;
  }

  @Override
  public Map<String, VirtualApi<Double>> injectRuleDouble(
      Map<String, VirtualApi<Double>> map) {
    map.put("level", new VirtualDouble(0, 100));
    map.put("grade", new VirtualDouble(0, 100));
    map.put("process", new VirtualDouble(0, 100));
    map.put("scale", new VirtualDouble(0, 100));
    return map;
  }

  @Override
  public Map<String, VirtualApi<String>> injectRuleString(
      Map<String, VirtualApi<String>> map) {
    map.put("id", new VirtualStringNumber(14));
    map.put("name", new VirtualStringChinese(6));
    map.put("number", new VirtualStringAlphabet(8));
    map.put("phone", new VirtualStringPhoneNumber(11, "170"));//11位手机号 前缀固定
    map.put("title", new VirtualStringChinese(8));
    map.put("content", new VirtualStringChinese(24));
    map.put("desc", new VirtualStringChinese(20));
    map.put("value", new VirtualStringSymbol(8));
    map.put("tag", new VirtualStringNumber(4));
    map.put("token", new VirtualStringAlphabetNumber(12));
    map.put("time", new VirtualApi<String>() {
      @Override
      public String onVirtual() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm")
            .format(new Date(System.currentTimeMillis()));
      }
    });
    map.put("url", new VirtualApi<String>() {
      @Override
      public String onVirtual() {
        return "https://github.com/LiCola/VirtualData";
      }
    });

    return map;
  }

  @Override
  public Map<String, VirtualApi<Object>> injectRuleModel(
      Map<String, VirtualApi<Object>> map) {
    return map;
  }
}

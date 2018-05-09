package virtual;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import virtual.RandomRule.RandomBoolean;
import virtual.RandomRule.RandomDouble;
import virtual.RandomRule.RandomFloat;
import virtual.RandomRule.RandomInteger;
import virtual.RandomRule.RandomIntegerWithLength;
import virtual.RandomRule.RandomLong;
import virtual.RandomRule.RandomLongWithLength;
import virtual.RandomRule.RandomStringAlphabet;
import virtual.RandomRule.RandomStringChinese;
import virtual.RandomRule.RandomStringNumber;
import virtual.RandomRule.RandomStringPhoneNumber;
import virtual.RandomRule.RandomStringSymbol;

/**
 * Created by LiCola on 2018/5/4.
 */
public class VirtualDataDefaultBuilder implements VirtualDataBuilder {

  @Override
  public Map<String, RandomInterface<Boolean>> injectRuleBoolean(
      Map<String, RandomInterface<Boolean>> map) {
    map.put("is", new RandomBoolean());
    return map;
  }

  @Override
  public Map<String, RandomInterface<Integer>> injectRuleInteger(
      Map<String, RandomInterface<Integer>> map) {
    map.put("id", new RandomIntegerWithLength(10));
    map.put("price", new RandomInteger(10000));
    map.put("money", new RandomInteger(10000));
    map.put("age", new RandomInteger(120));
    map.put("level", new RandomInteger(100));
    map.put("grade", new RandomInteger(100));
    map.put("process", new RandomInteger(100));
    return map;
  }

  @Override
  public Map<String, RandomInterface<Long>> injectRuleLong(
      Map<String, RandomInterface<Long>> map) {
    map.put("time", new RandomInterface<Long>() {
      @Override
      public Long getRandomData() {
        return System.currentTimeMillis() / 1000;
      }
    });
    map.put("price", new RandomLong(10000));
    map.put("money", new RandomLong(10000));
    map.put("id", new RandomLongWithLength(14));
    return map;
  }

  @Override
  public Map<String, RandomInterface<Float>> injectRuleFloat(
      Map<String, RandomInterface<Float>> map) {
    map.put("level", new RandomFloat(0, 100));
    map.put("grade", new RandomFloat(0, 100));
    map.put("process", new RandomFloat(0, 100));
    map.put("scale", new RandomFloat(0, 100));
    return map;
  }

  @Override
  public Map<String, RandomInterface<Double>> injectRuleDouble(
      Map<String, RandomInterface<Double>> map) {
    map.put("level", new RandomDouble(0, 100));
    map.put("grade", new RandomDouble(0, 100));
    map.put("process", new RandomDouble(0, 100));
    map.put("scale", new RandomDouble(0, 100));
    return map;
  }

  @Override
  public Map<String, RandomInterface<String>> injectRuleString(
      Map<String, RandomInterface<String>> map) {
    map.put("id", new RandomStringNumber(14));
    map.put("name", new RandomStringChinese(6));
    map.put("number", new RandomStringAlphabet(8));
    map.put("phone", new RandomStringPhoneNumber(11,"170"));//11位手机号 前缀固定
    map.put("title", new RandomStringChinese(8));
    map.put("content", new RandomStringChinese(24));
    map.put("desc", new RandomStringChinese(20));
    map.put("value", new RandomStringSymbol(8));
    map.put("tag", new RandomStringNumber(4));
    map.put("time", new RandomInterface<String>() {
      @Override
      public String getRandomData() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm")
            .format(new Date(System.currentTimeMillis()));
      }
    });
    map.put("url", new RandomInterface<String>() {
      @Override
      public String getRandomData() {
        return "https://github.com/LiCola/VirtualData";
      }
    });

    return map;
  }

  @Override
  public Map<String, RandomInterface<Object>> injectRuleModel(
      Map<String, RandomInterface<Object>> map) {
    return map;
  }
}

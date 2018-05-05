package virtual;

import static virtual.Util.getMapCapacity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import virtual.RandomRule.RandomBoolean;
import virtual.RandomRule.RandomDouble;
import virtual.RandomRule.RandomFloat;
import virtual.RandomRule.RandomInteger;
import virtual.RandomRule.RandomIntegerWithLength;
import virtual.RandomRule.RandomInterface;
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
  public LinkedHashMap<String, RandomInterface<Boolean>> getRandomRuleBoolean() {
    LinkedHashMap<String, RandomInterface<Boolean>> map = makeMapBySize(1);
    map.put("is", new RandomBoolean());
    return map;
  }

  @Override
  public LinkedHashMap<String, RandomInterface<Integer>> getRandomRuleInteger() {
    LinkedHashMap<String, RandomInterface<Integer>> map = makeMapBySize(7);
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
  public LinkedHashMap<String, RandomInterface<Long>> getRandomRuleLong() {
    LinkedHashMap<String, RandomInterface<Long>> map = makeMapBySize(4);
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
  public LinkedHashMap<String, RandomInterface<Float>> getRandomRuleFloat() {
    LinkedHashMap<String, RandomInterface<Float>> map = makeMapBySize(4);
    map.put("level", new RandomFloat(0, 100));
    map.put("grade", new RandomFloat(0, 100));
    map.put("process", new RandomFloat(0, 100));
    map.put("scale", new RandomFloat(0, 100));
    return map;
  }

  @Override
  public LinkedHashMap<String, RandomInterface<Double>> getRandomRuleDouble() {
    LinkedHashMap<String, RandomInterface<Double>> map = makeMapBySize(4);
    map.put("level", new RandomDouble(0, 100));
    map.put("grade", new RandomDouble(0, 100));
    map.put("process", new RandomDouble(0, 100));
    map.put("scale", new RandomDouble(0, 100));

    return map;
  }

  @Override
  public LinkedHashMap<String, RandomInterface<String>> getRandomRuleString() {
    LinkedHashMap<String, RandomInterface<String>> hashMap = makeMapBySize(11);

    hashMap.put("id", new RandomStringNumber(14));
    hashMap.put("name", new RandomStringChinese(6));
    hashMap.put("number", new RandomStringAlphabet(8));
    hashMap.put("phone", new RandomStringPhoneNumber());
    hashMap.put("title", new RandomStringChinese(8));
    hashMap.put("content", new RandomStringChinese(24));
    hashMap.put("desc", new RandomStringChinese(20));
    hashMap.put("value", new RandomStringSymbol(8));
    hashMap.put("tags", new RandomStringNumber(4));
    hashMap.put("time", new RandomInterface<String>() {
      @Override
      public String getRandomData() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm")
            .format(new Date(System.currentTimeMillis()));
      }
    });
    hashMap.put("url", () -> "https://github.com/LiCola/VirtualData");

    return hashMap;
  }

  @Override
  public LinkedHashMap<String, RandomInterface<Object>> getRandomRuleModel() {
    return null;
  }

  private static <T> LinkedHashMap<String, RandomInterface<T>> makeMapBySize(int fixSize) {
    return new LinkedHashMap<>(getMapCapacity(fixSize));
  }





}

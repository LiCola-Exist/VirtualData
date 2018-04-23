package virtual;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import virtual.RandomRuleData.RandomDataInteger;
import virtual.RandomRuleData.RandomDataInterface;
import virtual.RandomRuleData.RandomDataStringChinese;
import virtual.RandomRuleData.RandomDataStringNumber;
import virtual.RandomRuleData.RandomDataStringSymbol;

/**
 * Created by 李可乐 on 2017/4/17.
 * 虚拟数据生成器
 * 内部机制：反射。
 * 作用：初始化出目标类的实例，并给每个字段赋值，它虚拟出实例，且字段值都有值，可以用于比对和显示。
 * 特别说明：针对特定的字段名有对应的默认数据生成规则，外部也可以为某个字段注入备选数据，它会随机抽取。
 *
 * 缺点：目前只能通过反射空参构造方法，然后遍历字段名设置虚拟值，对Model对象有要求。
 *
 * 应用场景：主要用于MVP单元测试的V层测试，让V层测试时，有可显示数据用于测试，避免手动生成测试数据。
 */

public class VirtualDataBuilder<T> {

  //默认配置
  private static final HashMap<String, RandomRuleData.RandomDataInterface<String>> defaultStrings = getDefaultStringRule();
  private static final HashMap<String, RandomDataInterface<Integer>> defaultIntegers = getDefaultIntegerRule();
  private static final HashMap<String, RandomDataInterface<Long>> defaultLongs = getDefaultLongRule();
  private static final int defaultListSize = 8;

  //必要参数
  private Class<T> classTarget;

  //非必要参数

  //外部设置 提供给某些字段名的 随机值数组
  private HashMap<String, int[]> keyInts = new HashMap<>();
  private HashMap<String, long[]> keyLongs = new HashMap<>();
  private HashMap<String, String[]> keyStrings = new HashMap<>();

  //抛出构造实例时的异常 默认打开
  private boolean throwNewInstanceException = true;

  //设置的容器大小
  private int sizeCollection=defaultListSize;
  String fieldName;//变量名

  private static HashMap<Class, Constructor> cacheConstructor = new HashMap<>();

  public static List<String> VirtualStrings(int size) {
    return VirtualSimpleList(size, new RandomDataStringSymbol(10));
  }

  public static <T> List<T> VirtualSimpleList(int size,
      RandomDataInterface<T> dataInterface) {
    checkSizeArg(size);

    List<T> dataList = new ArrayList<>(size);
    for (int i = 0; i < size; i++) {
      dataList.add(dataInterface.getRandomData());
    }
    return dataList;
  }

  /**
   * 虚拟Model实例
   */
  public static <T> VirtualDataBuilder<T> virtual(Class<T> classTarget) {
    return new VirtualDataBuilder<>(classTarget);
  }

  private VirtualDataBuilder(Class<T> classTarget) {
    this.classTarget = classTarget;
  }

  public VirtualDataBuilder<T> addKeyInts(String keyField, int[] data) {
    keyInts.put(keyField, data);
    return this;
  }

  public VirtualDataBuilder<T> addKeyStrings(String keyField, String[] data) {
    keyStrings.put(keyField, data);
    return this;
  }

  public VirtualDataBuilder<T> addKeyLongs(String keyField, long[] data) {
    keyLongs.put(keyField, data);
    return this;
  }

  public VirtualDataBuilder<T> setSizeCollection(int size) {
    this.sizeCollection = size;
    return this;
  }

  public VirtualDataBuilder<T> setFieldName(String name) {
    this.fieldName = name;
    return this;
  }

  public VirtualDataBuilder<T> closeThrowNewInstanceException() {
    this.throwNewInstanceException = false;
    return this;
  }

  /**
   * 构造泛型对象
   *
   * @return 虚拟数据实例
   */
  public T build() {

    try {
      return getVirtualData();
    } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }


  public List<T> buildList() {
    return buildList(defaultListSize);
  }

  /**
   * 构造泛型的List对象
   *
   * @return 虚拟数据List实例
   */
  public List<T> buildList(int size){
    checkSizeArg(size);
    List<T> list = new ArrayList<>(size);
    sizeCollection=size;
    try {
      for (int i = 0; i < size; i++) {
        list.add(getVirtualData());
      }
      return list;
    } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  public Set<T> buildSet(){
    return buildSet(defaultListSize);
  }

  public Set<T> buildSet(int size) {
    checkSizeArg(size);
    Set<T> set = new HashSet<>(getMapCapacity(size));
    sizeCollection=size;
    try {
      for (int i = 0; i < size; i++) {
        set.add(getVirtualData());
      }
      return set;
    } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  private static void checkSizeArg(int sizeList) {
    if (sizeList <= 0) {
      throw new IllegalArgumentException("size 不能小于等于0");
    }
  }

  private T getVirtualData()
      throws IllegalAccessException, InstantiationException, InvocationTargetException {

    Class<?> tClass = classTarget;

    if (tClass.isInterface()) {
      if (throwNewInstanceException) {
        throw new InstantiationException("无法实例化接口，请输入明确的类class");
      } else {
        return null;
      }
    }

    Constructor constructor = cacheConstructor.get(tClass);

    if (constructor == null) {
      //遍历构造方法 查询是否有空参构造方法
      for (Constructor item : tClass.getConstructors()) {
        if (item.getParameterTypes().length == 0) {
          constructor = item;
          cacheConstructor.put(tClass, item);
          break;
        }
      }
    }

    if (constructor == null) {
      throw new InstantiationException(
          "无法初始化该类：" + classTarget.getName() + " 没有空参构造方法 请添加该类的空参构造方法");
    }

    Object object = constructor.newInstance();

    Field[] fields = tClass.getDeclaredFields();//获取该类声明的全部域

    for (Field itemField : fields) {
      Object objectData;

      String fieldName = itemField.getName();
      Class<?> fieldClass = itemField.getType();
      itemField.setAccessible(true);//访问private必须设置

      if (fieldClass.isAssignableFrom(List.class)) {
        //List类
        Class<?> parameterClass = getParameterClass(itemField);
        List list = new ArrayList(sizeCollection);
        for (int i = 0; i < sizeCollection; i++) {
          list.add(getDataByTypeAndName(fieldName, parameterClass));
        }
        objectData = list;
      } else if (fieldClass.isAssignableFrom(Set.class)) {
        //Set类
        Class<?> parameterClass = getParameterClass(itemField);
        Set set = new HashSet(getMapCapacity(sizeCollection));
        for (int i = 0; i < sizeCollection; i++) {
          set.add(getDataByTypeAndName(fieldName, parameterClass));
        }
        objectData = set;
      } else if (fieldClass.isAssignableFrom(Map.class)) {
        //Map类 key-value两个参数不能明确参数名 无法构造
        objectData = Collections.EMPTY_MAP;
      } else {
        //其他类 包括基类类型和包装类型
        objectData = getDataByTypeAndName(fieldName, fieldClass);
      }
      //设置对象的各字段的变量值
      itemField.set(object, objectData);
    }

    return (T) object;
  }

  private Class<?> getParameterClass(Field itemField) {
    ParameterizedType parameterType = (ParameterizedType) itemField.getGenericType();
    return (Class<?>) parameterType.getActualTypeArguments()[0];
  }

  /**
   * 根据字段名 和 类型 返回数据
   */
  private Object getDataByTypeAndName(String fieldName, Class<?> fieldClass) {
    if (fieldClass.isAssignableFrom(boolean.class) || fieldClass.isAssignableFrom(Boolean.class)) {
      //布尔类型 （基本类型和包装类）
      return RandomUtils.getBoolean();
    } else if (fieldClass.isAssignableFrom(float.class) || fieldClass
        .isAssignableFrom(Float.class)) {
      //float浮点类型
      return RandomUtils.getFloat();
    } else if (fieldClass.isAssignableFrom(double.class) || fieldClass
        .isAssignableFrom(Double.class)) {
      //double浮点类型
      return RandomUtils.getDouble();
    } else if (fieldClass.isAssignableFrom(int.class) || fieldClass
        .isAssignableFrom(Integer.class)) {
      //整数类型（基本类型和包装类）
      Integer data = checkDefaultRule(fieldName, defaultIntegers, 0);
      if (checkSetKeyMap(fieldName, keyInts)) {
        int[] choiceData = keyInts.get(fieldName);
        data = choiceData[RandomUtils.getInt(choiceData.length)];
      }
      return data;
    } else if (fieldClass.isAssignableFrom(long.class) || fieldClass.isAssignableFrom(Long.class)) {
      //长整数整型 （基本类型和包装类）
      Long date = checkDefaultRule(fieldName, defaultLongs, 0L);
      if (checkSetKeyMap(fieldName, keyLongs)) {
        long[] choiceData = keyLongs.get(fieldName);
        date = choiceData[RandomUtils.getInt(choiceData.length)];
      }
      return date;
    } else if (fieldClass.isAssignableFrom(String.class)) {
      //String
      String date = checkDefaultRule(fieldName, defaultStrings, "default");
      if (checkSetKeyMap(fieldName, keyStrings)) {//检查 设置的特殊key
        String[] choiceData = keyStrings.get(fieldName);
        date = choiceData[RandomUtils.getInt(choiceData.length)];
      }
      return date;
    } else {
      //其他类 一般指 自定义的类
      return virtual(fieldClass).build();
    }

  }

  /**
   * 检查默认规则 如果匹配返回特定规则下的随机方法 否则返回默认值
   */
  private <K> K checkDefaultRule(String key, HashMap<String, RandomDataInterface<K>> hashMap,
      K defaultValue) {
    String offsetKey = key.toLowerCase();

    //优化查找过程 如果全匹配
    RandomDataInterface<K> value = hashMap.get(offsetKey);
    if (value != null) {
      return value.getRandomData();
    }
    for (String mapKey : hashMap.keySet()) {
      if (offsetKey.contains(mapKey)) {
        //参数名 匹配规则
        return hashMap.get(mapKey).getRandomData();
      }
    }
    return defaultValue;//默认值
  }

  private <K> boolean checkSetKeyMap(String fieldName, HashMap<String, K> hashMap) {
    return !(hashMap == null || hashMap.isEmpty()) && hashMap.containsKey(fieldName);
  }


  /**
   * 提供String类型的默认设置规则
   * 保存的是key-接口对象，使用时调用接口方法
   * 当字段名包含key时 应用规则 注意顺序：当字段名包含多个key时 匹配最先一个
   */
  private static HashMap<String, RandomDataInterface<String>> getDefaultStringRule() {
    HashMap<String, RandomRuleData.RandomDataInterface<String>> hashMap = new HashMap<>();
    hashMap.put("id", new RandomDataStringNumber(8));
    hashMap.put("name", new RandomDataStringChinese(6));
    hashMap.put("phone", new RandomDataStringNumber(11));
    hashMap.put("title", new RandomDataStringChinese(8));
    hashMap.put("content", new RandomDataStringChinese(20));
    hashMap.put("desc", new RandomDataStringChinese(20));
    hashMap.put("value", new RandomDataStringSymbol(8));
    hashMap.put("tags", new RandomDataStringNumber(3));
    hashMap.put("time", new RandomDataInterface<String>() {
      @Override
      public String getRandomData() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm")
            .format(new Date(System.currentTimeMillis()));
      }
    });
    hashMap.put("url", new RandomDataInterface<String>() {
      @Override
      public String getRandomData() {
        return "https://github.com/LiCola/VirtualData";
      }
    });

    return hashMap;
  }

  /**
   * 提供Long类型的默认规则
   */
  private static HashMap<String, RandomDataInterface<Long>> getDefaultLongRule() {
    HashMap<String, RandomDataInterface<Long>> hashMap = new HashMap<>();
    hashMap.put("time", new RandomDataInterface<Long>() {
      @Override
      public Long getRandomData() {
        return System.currentTimeMillis() / 1000;
      }
    });
    return hashMap;
  }

  private static HashMap<String, RandomDataInterface<Integer>> getDefaultIntegerRule() {
    HashMap<String, RandomDataInterface<Integer>> hashMap = new HashMap<>();
    hashMap.put("price", new RandomDataInteger(100000));
    hashMap.put("money", new RandomDataInteger(10000));
    hashMap.put("age", new RandomDataInteger(100));
    return hashMap;
  }


  public static final int MAX_POWER_OF_TWO = 1 << (Integer.SIZE - 2);
  private int getMapCapacity(int fixSize) {
    if (fixSize < 3) {
      return fixSize + 1;
    }

    if (fixSize < MAX_POWER_OF_TWO) {
      return fixSize + fixSize / 3;
    }
    return Integer.MAX_VALUE;
  }
}

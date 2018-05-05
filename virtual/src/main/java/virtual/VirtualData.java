package virtual;

import static virtual.Util.getMapCapacity;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import virtual.RandomRule.RandomInterface;

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

public class VirtualData<T> {

  //默认配置
  private final int defaultListSize = 10;
  private static final Object defaultModel = null;
  private static final Boolean defaultBoolean = false;
  private static final Integer defaultInteger = 0;
  private static final Long defaultLong = 0L;
  private static final Float defaultFloat = 0.0f;
  private static final Double defaultDouble = 0.0d;
  private static final String defaultString = "default";

  //必要参数
  private Class<T> classTarget;
  private VirtualDataBuilder builder;

  //非必要参数
  private String fieldName;//字段名

  //外部设置 提供给某些字段名的 随机值数组
  private HashMap<String, Boolean[]> keyBoolean = new HashMap<>();
  private HashMap<String, Integer[]> keyInts = new HashMap<>();
  private HashMap<String, Long[]> keyLongs = new HashMap<>();
  private HashMap<String, Float[]> keyFloats = new HashMap<>();
  private HashMap<String, Double[]> keyDoubles = new HashMap<>();
  private HashMap<String, String[]> keyStrings = new HashMap<>();


  //设置的容器大小
  private int sizeCollection = defaultListSize;

  private static HashMap<Class, Constructor> cacheConstructor = new HashMap<>();


  /**
   * 外观方法 封装使用
   */
  public static <T> VirtualData<T> virtual(Class<T> classTarget) {
    return virtual(classTarget, new VirtualDataDefaultBuilder());
  }

  public static <T> VirtualData<T> virtual(Class<T> classTarget, VirtualDataBuilder builder) {
    return new VirtualData<>(classTarget, builder);
  }

  public VirtualData(Class<T> classTarget, VirtualDataBuilder builder) {
    this.classTarget = classTarget;
    this.builder = builder;
  }

  public VirtualData<T> addKeyInts(String keyField, Integer[] data) {
    keyInts.put(keyField, data);
    return this;
  }

  public VirtualData<T> addKeyStrings(String keyField, String[] data) {
    keyStrings.put(keyField, data);
    return this;
  }

  public VirtualData<T> addKeyLongs(String keyField, Long[] data) {
    keyLongs.put(keyField, data);
    return this;
  }

  public VirtualData<T> addKeyFloats(String keyField, Float[] data) {
    keyFloats.put(keyField, data);
    return this;
  }

  public VirtualData<T> addKeyDoubles(String keyField, Double[] data) {
    keyDoubles.put(keyField, data);
    return this;
  }

  public VirtualData<T> addKeyBoolean(String keyField, Boolean data) {
    keyBoolean.put(keyField, new Boolean[]{data});
    return this;
  }

  public VirtualData<T> setFieldName(String fieldName) {
    this.fieldName = fieldName;
    return this;
  }

  public VirtualData<T> setSizeCollection(int size) {
    checkSizeArg(size);
    this.sizeCollection = size;
    return this;
  }

  /**
   * 构造泛型对象
   *
   * @return 虚拟数据实例
   */
  public T build() {

    try {
      return getVirtualData(classTarget, fieldName);
    } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }


  public List<T> buildList() {
    return buildList(classTarget, fieldName, sizeCollection);
  }

  /**
   * 构造泛型的List对象
   *
   * @return 虚拟数据ArrayList实例
   */
  private List<T> buildList(Class<?> tClass, String fieldName, int size) {
    List<T> list = new ArrayList<>(size);
    try {
      for (int i = 0; i < size; i++) {
        list.add(getVirtualData(tClass, fieldName));
      }
      return list;
    } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  public Set<T> buildSet() {
    return buildSet(classTarget, fieldName, sizeCollection);
  }

  /**
   * 构造泛型的Set对象
   *
   * @return @return 虚拟HashSet数据实例
   */
  private Set<T> buildSet(Class<?> tClass, String fieldName, int size) {
    Set<T> set = new HashSet<>(getMapCapacity(size));
    try {
      for (int i = 0; i < size; i++) {
        set.add(getVirtualData(tClass, fieldName));
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

  private T getVirtualData(Class<?> tClass, String fieldName)
      throws IllegalAccessException, InstantiationException, InvocationTargetException {

    Object model = checkDefaultRuleAndGet(tClass, fieldName, builder.getRandomRuleModel(),
        defaultModel);
    if (model != null) {
      return (T) model;
    }

    if (tClass.isInterface()) {
      throw new InstantiationException("无法实例化接口:" + tClass.getName() + " 请输入明确的类class");
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
          "无法初始化该类：" + tClass.getName() + " 没有空参构造方法 请添加该类的空参构造方法");
    }

    Object object = constructor.newInstance();

    Field[] fields = tClass.getDeclaredFields();//获取该类声明的全部域

    for (Field itemField : fields) {
      Object objectData;

      int modifiers = itemField.getModifiers();
      if (Modifier.isFinal(modifiers) && Modifier.isStatic(modifiers)) {
        break;
      }

      String itemFieldName = itemField.getName();
      Class<?> fieldClass = itemField.getType();

      itemField.setAccessible(true);//访问private必须设置

      if (fieldClass.isAssignableFrom(List.class)) {
        //List类
        Class<?> parameterClass = getParameterClass(itemField);
        objectData = buildList(parameterClass, itemFieldName, sizeCollection);
      } else if (fieldClass.isAssignableFrom(Set.class)) {
        //Set类
        Class<?> parameterClass = getParameterClass(itemField);
        objectData = buildSet(parameterClass, itemFieldName, sizeCollection);
      } else {
        //其他类 包括基类类型和包装类型

        objectData = checkDefaultRuleAndGet(fieldClass, itemFieldName, builder.getRandomRuleModel(),
            defaultModel);
        if (objectData == null) {
          objectData = getDataByClassAndName(itemFieldName, fieldClass);
        }

        if (objectData == null) {
          objectData = getVirtualData(fieldClass, itemFieldName);
        }
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
  private Object getDataByClassAndName(String fieldName, Class<?> fieldClass) {
    if (fieldClass.isAssignableFrom(boolean.class) || fieldClass.isAssignableFrom(Boolean.class)) {
      //布尔类型 （基本类型和包装类）
      Boolean data = checkDefaultRuleAndGet(fieldName, builder.getRandomRuleBoolean(),
          defaultBoolean);
      data = checkKeyMapAndGet(fieldName, keyBoolean, data);
      return data;
    } else if (fieldClass.isAssignableFrom(float.class) || fieldClass
        .isAssignableFrom(Float.class)) {
      Float data = checkDefaultRuleAndGet(fieldName, builder.getRandomRuleFloat(), defaultFloat);
      data = checkKeyMapAndGet(fieldName, keyFloats, data);
      return data;
    } else if (fieldClass.isAssignableFrom(double.class) || fieldClass
        .isAssignableFrom(Double.class)) {
      //double浮点类型
      Double data = checkDefaultRuleAndGet(fieldName, builder.getRandomRuleDouble(), defaultDouble);
      data = checkKeyMapAndGet(fieldName, keyDoubles, data);
      return data;
    } else if (fieldClass.isAssignableFrom(int.class) || fieldClass
        .isAssignableFrom(Integer.class)) {
      //整数类型（基本类型和包装类）
      Integer data = checkDefaultRuleAndGet(fieldName, builder.getRandomRuleInteger(),
          defaultInteger);
      data = checkKeyMapAndGet(fieldName, keyInts, data);
      return data;
    } else if (fieldClass.isAssignableFrom(long.class) || fieldClass.isAssignableFrom(Long.class)) {
      //长整数整型 （基本类型和包装类）
      Long data = checkDefaultRuleAndGet(fieldName, builder.getRandomRuleLong(), defaultLong);
      data = checkKeyMapAndGet(fieldName, keyLongs, data);
      return data;
    } else if (fieldClass.isAssignableFrom(String.class)) {
      //String
      String data = checkDefaultRuleAndGet(fieldName, builder.getRandomRuleString(), defaultString);
      data = checkKeyMapAndGet(fieldName, keyStrings, data);
      return data;
    } else {
      return null;
    }

  }


  private Object checkDefaultRuleAndGet(Class<?> tClass, String key,
      Map<String, RandomInterface<Object>> map, Object defaultModel) {

    if (Util.isEmpty(map) || Util.isEmpty(key)) {
      return defaultModel;
    }

    RandomInterface<Object> value = map.get(key);
    if (value == null) {
      String offsetKey = key.toLowerCase();
      value = map.get(offsetKey);
    }

    if (value != null) {
      Object data = value.getRandomData();
      if (data.getClass() == tClass) {
        return data;
      }
    }

    return defaultModel;
  }

  /**
   * 检查默认规则的返回值
   * 如果匹配或者部分匹配 返回特定规则下的随机值
   * 否则 返回默认值
   */
  private <K> K checkDefaultRuleAndGet(String key, Map<String, RandomInterface<K>> hashMap,
      K defaultValue) {
    String offsetKey = key.toLowerCase();

    //优化查找过程 如果全匹配
    RandomInterface<K> value = hashMap.get(offsetKey);

    //没有全匹配 使用部分匹配查找 如果匹配多个规则 使用最后匹配
    if (value == null) {
      for (String mapKey : hashMap.keySet()) {
        if (offsetKey.contains(mapKey)) {
          //参数名 匹配规则
          value = hashMap.get(mapKey);
        }
      }
    }

    if (value != null) {
      return value.getRandomData();
    }

    return defaultValue;//默认值
  }

  /**
   * 检查设置的key的返回值
   * 如果匹配（完全匹配） 返回外部设置的数组中随机位的值
   * 否则 返回默认值
   */
  private <K> K checkKeyMapAndGet(String fieldName, Map<String, K[]> hashMap, K defaultValue) {

    if (hashMap.isEmpty()) {
      return defaultValue;
    }
    K[] values = hashMap.get(fieldName);
    if (values == null || values.length == 0) {
      return defaultValue;
    }
    return values[RandomUtils.getInt(values.length)];

  }

}

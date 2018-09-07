package virtual;

import static virtual.Utils.getMapCapacity;

import com.licola.llogger.LLogger;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import virtual.internal.ObjectConstructor;
import virtual.internal.ObjectConstructorApi;

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

  private static final String TAG = "Virtual";

  //默认配置
  private static final int defaultListSize = 10;

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

  //builder配置 对应类型的数据规则
  private Map<String, VirtualApi<Boolean>> ruleBoolean;
  private Map<String, VirtualApi<Integer>> ruleInteger;
  private Map<String, VirtualApi<Long>> ruleLong;
  private Map<String, VirtualApi<Float>> ruleFloat;
  private Map<String, VirtualApi<Double>> ruleDouble;
  private Map<String, VirtualApi<String>> ruleString;
  private Map<String, VirtualApi<Object>> ruleModel;

  //设置的容器大小
  private int sizeCollection = defaultListSize;

  private static HashMap<Class, Constructor> cacheConstructor;

  static {
    cacheConstructor = new HashMap<>();
    LLogger.init(true, TAG);
  }

  /**
   * 外观方法 封装使用
   */
  public static <T> VirtualData<T> virtual(Class<T> classTarget) {
    return virtual(classTarget, VirtualDataDefaultBuilder.create());
  }

  /**
   * 外观方法 可以传入自定义的虚拟数据规则
   */
  public static <T> VirtualData<T> virtual(Class<T> classTarget, VirtualDataBuilder builder) {
    return new VirtualData<>(classTarget, builder);
  }

  /**
   * 使用api填充虚拟数据，返回原数组
   */
  public static <T> T[] virtualArray(T[] sourceArray, VirtualApi<T> api) {
    for (int i = 0; i < sourceArray.length; i++) {
      sourceArray[i] = api.onVirtual();
    }
    return sourceArray;
  }

  private VirtualData(Class<T> classTarget, VirtualDataBuilder builder) {
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

  /**
   * 构造泛型List集合对象
   *
   * @return ArrayList数据
   */
  public List<T> buildList() {
    return buildList(classTarget, fieldName, sizeCollection);
  }

  /**
   * 构造泛型的List集合对象
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

  /**
   * 构造泛型Set集合对象
   */
  public Set<T> buildSet() {
    return buildSet(classTarget, fieldName, sizeCollection);
  }

  /**
   * 构造泛型的Set集合对象
   *
   * @return 虚拟HashSet数据实例
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

  /**
   * 构造泛型Queue集合对象
   *
   * @return 虚拟的ArrayDeque数据实例
   */
  public Queue<T> buildQueue() {
    return buildQueue(classTarget, fieldName, sizeCollection);
  }

  /**
   * 构造泛型Queue集合对象
   *
   * @return 虚拟的ArrayDeque数据实例
   */
  private Queue<T> buildQueue(Class<?> tClass, String fieldName, int size) {
    Queue<T> queue = new ArrayDeque<>();
    try {
      for (int i = 0; i < size; i++) {
        queue.add(getVirtualData(tClass, fieldName));
      }
      return queue;
    } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  private static void checkSizeArg(int sizeList) {
    if (sizeList <= 0) {
      throw new IllegalArgumentException("size 不能小于等于0");
    }
  }


  /**
   * 真正的虚拟数据获取方法
   */
  private T getVirtualData(Class<?> tClass, String fieldName)
      throws IllegalAccessException, InstantiationException, InvocationTargetException {

    ruleModel = ruleModel == null ? builder
        .injectRuleModel(new LinkedHashMap<String, VirtualApi<Object>>()) : ruleModel;

    Object model = onVirtualOrDefault(tClass, fieldName, ruleModel,
        defaultModel);
    if (model != null) {
      return (T) model;
    }

    if (checkException(tClass)) {
      return (T) defaultModel;
    }

    ObjectConstructorApi<?> constructorApi = ObjectConstructor.get(tClass);
    if (constructorApi == null) {
      return (T) defaultModel;
    }

    Object object = constructorApi.construct();

    Field[] fields = tClass.getDeclaredFields();//获取该类声明的全部域

    Object dataFieldValue;
    for (Field itemField : fields) {

      if (checkFieldInvalid(itemField)) {
        continue;
      }

      String itemFieldName = itemField.getName();
      Class<?> fieldClass = itemField.getType();

      itemField.setAccessible(true);//访问private必须设置

      if (fieldClass.isAssignableFrom(List.class)) {
        //List接口
        Class<?> parameterClass = getParameterSingleClass(itemField);
        dataFieldValue = buildList(parameterClass, itemFieldName, sizeCollection);
      } else if (fieldClass.isAssignableFrom(Set.class)) {
        //Set接口
        Class<?> parameterClass = getParameterSingleClass(itemField);
        dataFieldValue = buildSet(parameterClass, itemFieldName, sizeCollection);
      } else if (fieldClass.isAssignableFrom(Queue.class)) {
        //Queue接口
        Class<?> parameterClass = getParameterSingleClass(itemField);
        dataFieldValue = buildQueue(parameterClass, itemFieldName, sizeCollection);
      } else {
        //其他类 包括基类类型和包装类型

        //1：尝试 自定义类数据规则
        dataFieldValue = onVirtualOrDefault(fieldClass, itemFieldName, ruleModel,
            defaultModel);

        //2：尝试 基础类以及它的包装类和String数据规则
        if (dataFieldValue == null) {
          dataFieldValue = getValueByClassAndName(itemFieldName, fieldClass);
        }

        //3：最后 递归处理自定义类 重复步骤
        if (dataFieldValue == null) {
          if (fieldClass.isAssignableFrom(Map.class)) {
            dataFieldValue = defaultModel;
          } else {
            dataFieldValue = getVirtualData(fieldClass, itemFieldName);
          }
        }
      }
      //设置对象的各字段的变量值
      itemField.set(object, dataFieldValue);
    }

    return (T) object;
  }


  private boolean checkException(Class<?> tClass) throws InstantiationException {
    if (tClass.isArray()) {
      String msg = "无法处理数组 因为无法创建泛型数组";
      if (builder.throwConstructorException()) {
        throw new InstantiationException(msg);
      } else {
        LLogger.w(msg);
        return true;
      }
    }

    if (tClass.isInterface()) {
      String msg = "无法实例化接口:" + tClass.getName() + " 请输入明确的类class";
      if (builder.throwConstructorException()) {
        throw new InstantiationException(msg);
      } else {
        LLogger.w(msg);
        return true;
      }
    }
    return false;
  }


  /**
   * 检查无效设置的字段field
   */
  private boolean checkFieldInvalid(Field field) {
    int modifiers = field.getModifiers();
    return Modifier.isStatic(modifiers);
  }

  private Class<?> getParameterSingleClass(Field itemField) {
    ParameterizedType parameterType = (ParameterizedType) itemField.getGenericType();
    return (Class<?>) parameterType.getActualTypeArguments()[0];
  }


  /**
   * 根据字段名 和 类型 返回数据
   */
  private Object getValueByClassAndName(String fieldName, Class<?> fieldClass) {
    if (fieldClass.isAssignableFrom(boolean.class) || fieldClass.isAssignableFrom(Boolean.class)) {
      //布尔类型 （基本类型和包装类）
      ruleBoolean = ruleBoolean == null ? builder
          .injectRuleBoolean(new LinkedHashMap<String, VirtualApi<Boolean>>())
          : ruleBoolean;
      Boolean data = onVirtualOrDefault(fieldName, ruleBoolean,
          defaultBoolean);
      data = onKeyMapOrDefault(fieldName, keyBoolean, data);
      return data;
    } else if (fieldClass.isAssignableFrom(float.class) || fieldClass
        .isAssignableFrom(Float.class)) {
      ruleFloat = ruleFloat == null ? builder
          .injectRuleFloat(new LinkedHashMap<String, VirtualApi<Float>>()) : ruleFloat;
      Float data = onVirtualOrDefault(fieldName, ruleFloat, defaultFloat);
      data = onKeyMapOrDefault(fieldName, keyFloats, data);
      return data;
    } else if (fieldClass.isAssignableFrom(double.class) || fieldClass
        .isAssignableFrom(Double.class)) {
      //double浮点类型
      ruleDouble = ruleDouble == null ? builder
          .injectRuleDouble(new LinkedHashMap<String, VirtualApi<Double>>()) : ruleDouble;
      Double data = onVirtualOrDefault(fieldName, ruleDouble, defaultDouble);
      data = onKeyMapOrDefault(fieldName, keyDoubles, data);
      return data;
    } else if (fieldClass.isAssignableFrom(int.class) || fieldClass
        .isAssignableFrom(Integer.class)) {
      //整数类型（基本类型和包装类）
      ruleInteger = ruleInteger == null ? builder
          .injectRuleInteger(new LinkedHashMap<String, VirtualApi<Integer>>())
          : ruleInteger;
      Integer data = onVirtualOrDefault(fieldName, ruleInteger,
          defaultInteger);
      data = onKeyMapOrDefault(fieldName, keyInts, data);
      return data;
    } else if (fieldClass.isAssignableFrom(long.class) || fieldClass.isAssignableFrom(Long.class)) {
      //长整数整型 （基本类型和包装类）
      ruleLong = ruleLong == null ? builder
          .injectRuleLong(new LinkedHashMap<String, VirtualApi<Long>>()) : ruleLong;
      Long data = onVirtualOrDefault(fieldName, ruleLong, defaultLong);
      data = onKeyMapOrDefault(fieldName, keyLongs, data);
      return data;
    } else if (fieldClass.isAssignableFrom(String.class)) {
      //String
      ruleString = ruleString == null ? builder
          .injectRuleString(new LinkedHashMap<String, VirtualApi<String>>()) : ruleString;
      String data = onVirtualOrDefault(fieldName, ruleString, defaultString);
      data = onKeyMapOrDefault(fieldName, keyStrings, data);
      return data;
    } else {
      return null;
    }

  }


  private Object onVirtualOrDefault(Class<?> tClass, String key,
      Map<String, VirtualApi<Object>> map, Object defaultModel) {

    if (Utils.isEmpty(map) || Utils.isEmpty(key)) {
      return defaultModel;
    }

    VirtualApi<Object> value = map.get(key);
    if (value == null) {
      String offsetKey = key.toLowerCase();
      value = map.get(offsetKey);
    }

    if (value != null) {
      Object data = value.onVirtual();

      if (tClass.isInterface()) {
        for (Class<?> aClass : data.getClass().getInterfaces()) {
          if (aClass == tClass) {
            return data;
          }
        }
      } else {
        Class<?> aClass = data.getClass();
        while (aClass != Object.class) {
          if (aClass == tClass) {
            return data;
          }
          aClass = aClass.getSuperclass();
        }
      }

    }

    return defaultModel;
  }


  /**
   * 检查匹配规则的返回值
   * 如果匹配或者部分匹配 返回特定规则下的虚拟值
   * 否则 返回默认值
   */
  private <K> K onVirtualOrDefault(String key, Map<String, VirtualApi<K>> map,
      K defaultValue) {
    if (Utils.isEmpty(map)) {
      return defaultValue;
    }

    String offsetKey = key.toLowerCase();

    //优化查找过程 如果全匹配
    VirtualApi<K> value = map.get(offsetKey);

    //没有全匹配 使用部分匹配查找 如果匹配多个规则 使用最后匹配
    if (value == null) {
      for (String mapKey : map.keySet()) {
        if (offsetKey.contains(mapKey)) {
          //参数名 匹配规则
          value = map.get(mapKey);
        }
      }
    }

    if (value != null) {
      return value.onVirtual();
    }

    return defaultValue;//默认值
  }

  /**
   * 检查设置的key的返回值
   * 如果匹配（完全匹配） 返回外部设置的数组中随机位的值
   * 否则 返回默认值
   */
  private <K> K onKeyMapOrDefault(String fieldName, Map<String, K[]> hashMap, K defaultValue) {

    if (hashMap.isEmpty()) {
      return defaultValue;
    }
    K[] values = hashMap.get(fieldName);
    if (values == null || values.length == 0) {
      return defaultValue;
    }

    return values[VirtualUtils.getInt(values.length)];

  }

}

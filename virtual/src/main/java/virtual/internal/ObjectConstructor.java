package virtual.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 对象的构造方法获取类
 *
 * @author LiCola
 * @date 2018/9/7
 */
public class ObjectConstructor {

  public static <T> ObjectConstructorApi<T> get(Class<T> tClass) {
    ObjectConstructorApi<T> defaultConstructor = newDefaultConstructor(tClass);
    if (defaultConstructor != null) {
      return defaultConstructor;
    }

    return newUnsafeConstructor(tClass);
  }

  /**
   * 通过Unsafe系统api方式 获取构造方法api
   */
  private static <T> ObjectConstructorApi<T> newUnsafeConstructor(final Class<T> tClass) {
    return new ObjectConstructorApi<T>() {
      final ObjectUnsafeConstructor unsafeConstructor = ObjectUnsafeConstructor.create();

      @Override
      public T construct() {
        try {
          return unsafeConstructor.newInstance(tClass);
        } catch (Exception e) {
          throw new RuntimeException("无法通过Unsafe方式构造对象:" + tClass + ",请尝试添加空参构造方法修复问题", e);
        }
      }
    };
  }

  /**
   * 通过类声明的构造方法获取构造方法api
   */
  private static <T> ObjectConstructorApi<T> newDefaultConstructor(Class<T> tClass) {

    try {
      final Constructor<T> constructor = tClass.getDeclaredConstructor();
      if (!constructor.isAccessible()) {
        constructor.setAccessible(true);
      }

      return new ObjectConstructorApi<T>() {
        @Override
        public T construct() {
          Object[] args = null;
          try {
            return constructor.newInstance(args);
          } catch (InstantiationException e) {
            throw new RuntimeException("Failed to invoke" + constructor + " with no args", e);
          } catch (IllegalAccessException e) {
            throw new AssertionError(e);
          } catch (InvocationTargetException e) {
            throw new RuntimeException("Failed to invoke" + constructor + " with no args",
                e.getTargetException());
          }
        }
      };

    } catch (NoSuchMethodException e) {

    }
    return null;
  }
}

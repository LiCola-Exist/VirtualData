package virtual.internal;

/**
 *
 * 构造对象接口，用于统一构造方法的调用
 * @author LiCola
 * @date 2018/9/7
 */
public interface ObjectConstructorApi<T> {

  T construct();
}

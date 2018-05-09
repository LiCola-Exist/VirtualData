package virtual;


import static virtual.RandomUtils.getAlphabetString;
import static virtual.RandomUtils.getChineseSimple;
import static virtual.RandomUtils.getNumberString;
import static virtual.RandomUtils.getPhoneNumberString;
import static virtual.RandomUtils.getSymbolString;

/**
 * Created by 李可乐 on 2017/4/20.
 * 规定的随机数据生成规则
 */

public class RandomRule {

  /**
   * 泛型通用接口
   */
  public interface RandomInterface<T> {

    T getRandomData();
  }


  /**
   * 随机数字字符串
   */
  public static class RandomStringNumber implements RandomInterface<String> {

    private int length;

    RandomStringNumber(int length) {
      this.length = length;
    }

    @Override
    public String getRandomData() {
      return getNumberString(length);
    }
  }

  public static class RandomStringAlphabet implements RandomInterface<String> {

    private int length;

    RandomStringAlphabet(int length) {
      this.length = length;
    }

    @Override
    public String getRandomData() {
      return getAlphabetString(length);
    }
  }

  public static class RandomStringSymbol implements RandomInterface<String> {

    private int length;

    RandomStringSymbol(int length) {
      this.length = length;
    }

    @Override
    public String getRandomData() {
      return getSymbolString(length);
    }
  }


  public static class RandomStringChinese implements RandomInterface<String> {

    private int length;

    RandomStringChinese(int length) {
      this.length = length;
    }

    @Override
    public String getRandomData() {
      return getChineseSimple(length);
    }
  }

  public static class RandomStringPhoneNumber implements RandomInterface<String> {

    int total;
    String prefix;

    public RandomStringPhoneNumber(int total, String prefix) {
      this.total = total;
      this.prefix = prefix;
    }

    @Override
    public String getRandomData() {
      return getPhoneNumberString(total,prefix);
    }
  }

  /**
   * 在0~max范围内的随机整数
   */
  public static class RandomInteger implements RandomInterface<Integer> {

    private int max;

    public RandomInteger(int max) {
      this.max = max;
    }

    @Override
    public Integer getRandomData() {
      return RandomUtils.getInt(max + 1);
    }
  }

  /**
   * 在规定数值位数的随机整数
   * 如 length=2，就是整数两位范围，即10~99
   */
  public static class RandomIntegerWithLength implements RandomInterface<Integer> {

    private int length;

    RandomIntegerWithLength(int length) {
      this.length = length;
    }

    @Override
    public Integer getRandomData() {
      return RandomUtils.getIntegerLength(length);
    }
  }


  public static class RandomLong implements RandomInterface<Long> {

    private int max;

    public RandomLong(int max) {
      this.max = max;
    }

    @Override
    public Long getRandomData() {
      return RandomUtils.getLong(max + 1);
    }
  }

  /**
   * 在规定数值位数的随机长整数
   * 如 length=2，就是长整数两位范围，即10~99
   */
  public static class RandomLongWithLength implements RandomInterface<Long> {

    private int length;

    RandomLongWithLength(int length) {
      this.length = length;
    }

    @Override
    public Long getRandomData() {
      return RandomUtils.getLongLength(length);
    }
  }

  public static class RandomFloat implements RandomInterface<Float> {

    private double origin;
    private double bound;

    public RandomFloat(double origin, double bound) {
      this.origin = origin;
      this.bound = bound;
    }

    @Override
    public Float getRandomData() {
      return (float) RandomUtils.getDouble(origin, bound);
    }

  }

  public static class RandomDouble implements RandomInterface<Double> {

    private double origin;
    private double bound;

    public RandomDouble(double origin, double bound) {
      this.origin = origin;
      this.bound = bound;
    }

    @Override
    public Double getRandomData() {
      return RandomUtils.getDouble(origin, bound);
    }

  }

  public static class RandomBoolean implements RandomInterface<Boolean> {

    @Override
    public Boolean getRandomData() {
      return RandomUtils.getBoolean();
    }
  }

}

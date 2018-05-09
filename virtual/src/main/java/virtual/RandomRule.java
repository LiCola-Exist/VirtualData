package virtual;


import static virtual.RandomUtils.getAlphabetString;
import static virtual.RandomUtils.getChineseSimple;
import static virtual.RandomUtils.getNumberString;
import static virtual.RandomUtils.getPhoneNumberString;
import static virtual.RandomUtils.getSymbolString;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by 李可乐 on 2017/4/20.
 * 通用的数据生成规则
 * 如定长(固定长度)字符串、定长中文字符串、定长整数、手机号。
 */

public class RandomRule {

  /**
   * 定长随机数字字符串
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

  /**
   * 定长随机字母字符串
   */
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

  /**
   * 定长符号/字母/数字字符串
   */
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


  /**
   * 定长简体中文字符串
   */
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

  /**
   * 手机号
   * 固定总长、前缀得到随机手机号
   * 如：total：11，prefix：170，得到170前缀固定的11位手机号
   * 如：total：13，prefix：0571-，得到0571-前缀固定的13位座机号
   */
  public static class RandomStringPhoneNumber implements RandomInterface<String> {

    int total;
    String prefix;

    public RandomStringPhoneNumber(int total, String prefix) {
      this.total = total;
      this.prefix = prefix;
    }

    @Override
    public String getRandomData() {
      return getPhoneNumberString(total, prefix);
    }
  }

  /**
   * 在0~max范围内的随机整数
   * 注：max包含在生成数的范围内
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
   * 定长随机整数，即固定整数位范围的数值
   * 如：length=2，就是整数两位范围，即10~99
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

  /**
   * 在0~max范围内的随机长整数
   * 注：max包含在生成数的范围内
   */
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
   * 定长随机长整数，即固定长整数位范围的数值
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

  /**
   * 范围内的浮点数
   * 具体参见{@link ThreadLocalRandom#nextDouble(double, double)}
   */
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

  /**
   * 范围内的浮点数
   * 具体参见{@link ThreadLocalRandom#nextDouble(double, double)}
   */
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

  /**
   * 随机布尔值
   */
  public static class RandomBoolean implements RandomInterface<Boolean> {

    @Override
    public Boolean getRandomData() {
      return RandomUtils.getBoolean();
    }
  }

}

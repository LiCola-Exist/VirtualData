package virtual;


import static virtual.VirtualUtils.getAlphabetString;
import static virtual.VirtualUtils.getChineseSimple;
import static virtual.VirtualUtils.getNumberString;
import static virtual.VirtualUtils.getPhoneNumberString;
import static virtual.VirtualUtils.getSymbolString;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by 李可乐 on 2017/4/20.
 * 通用的数据生成规则
 * 如定长(固定长度)字符串、定长中文字符串、定长整数、手机号。
 */

public class VirtualRules {

  /**
   * 定长随机数字字符串
   */
  public static class VirtualStringNumber implements VirtualApi<String> {

    private int length;

    VirtualStringNumber(int length) {
      this.length = length;
    }

    @Override
    public String onVirtual() {
      return getNumberString(length);
    }
  }

  /**
   * 定长随机字母字符串
   */
  public static class VirtualStringAlphabet implements VirtualApi<String> {

    private int length;

    VirtualStringAlphabet(int length) {
      this.length = length;
    }

    @Override
    public String onVirtual() {
      return getAlphabetString(length);
    }
  }

  /**
   * 定长符号/字母/数字字符串
   */
  public static class VirtualStringSymbol implements VirtualApi<String> {

    private int length;

    VirtualStringSymbol(int length) {
      this.length = length;
    }

    @Override
    public String onVirtual() {
      return getSymbolString(length);
    }
  }


  /**
   * 定长简体中文字符串
   */
  public static class VirtualStringChinese implements VirtualApi<String> {

    private int length;

    VirtualStringChinese(int length) {
      this.length = length;
    }

    @Override
    public String onVirtual() {
      return getChineseSimple(length);
    }
  }

  /**
   * 手机号
   * 固定总长、前缀得到随机手机号
   * 如：total：11，prefix：170，得到170前缀固定的11位手机号
   * 如：total：13，prefix：0571-，得到0571-前缀固定的13位座机号
   */
  public static class VirtualStringPhoneNumber implements VirtualApi<String> {

    int total;
    String prefix;

    public VirtualStringPhoneNumber(int total, String prefix) {
      this.total = total;
      this.prefix = prefix;
    }

    @Override
    public String onVirtual() {
      return getPhoneNumberString(total, prefix);
    }
  }

  /**
   * 在0~max范围内的随机整数
   * 注：max包含在生成数的范围内
   */
  public static class VirtualInteger implements VirtualApi<Integer> {

    private int max;

    public VirtualInteger(int max) {
      this.max = max;
    }

    @Override
    public Integer onVirtual() {
      return VirtualUtils.getInt(max + 1);
    }
  }

  /**
   * 定长随机整数，即固定整数位范围的数值
   * 如：length=2，就是整数两位范围，即10~99
   */
  public static class VirtualIntegerWithLength implements VirtualApi<Integer> {

    private int length;

    VirtualIntegerWithLength(int length) {
      this.length = length;
    }

    @Override
    public Integer onVirtual() {
      return VirtualUtils.getIntegerLength(length);
    }
  }

  /**
   * 在0~max范围内的随机长整数
   * 注：max包含在生成数的范围内
   */
  public static class VirtualLong implements VirtualApi<Long> {

    private int max;

    public VirtualLong(int max) {
      this.max = max;
    }

    @Override
    public Long onVirtual() {
      return VirtualUtils.getLong(max + 1);
    }
  }

  /**
   * 定长随机长整数，即固定长整数位范围的数值
   * 如 length=2，就是长整数两位范围，即10~99
   */
  public static class VirtualLongWithLength implements VirtualApi<Long> {

    private int length;

    VirtualLongWithLength(int length) {
      this.length = length;
    }

    @Override
    public Long onVirtual() {
      return VirtualUtils.getLongLength(length);
    }
  }

  /**
   * 范围内的浮点数
   * 具体参见{@link ThreadLocalRandom#nextDouble(double, double)}
   */
  public static class VirtualFloat implements VirtualApi<Float> {

    private double origin;
    private double bound;

    public VirtualFloat(double origin, double bound) {
      this.origin = origin;
      this.bound = bound;
    }

    @Override
    public Float onVirtual() {
      return (float) VirtualUtils.getDouble(origin, bound);
    }

  }

  /**
   * 范围内的浮点数
   * 具体参见{@link ThreadLocalRandom#nextDouble(double, double)}
   */
  public static class VirtualDouble implements VirtualApi<Double> {

    private double origin;
    private double bound;

    public VirtualDouble(double origin, double bound) {
      this.origin = origin;
      this.bound = bound;
    }

    @Override
    public Double onVirtual() {
      return VirtualUtils.getDouble(origin, bound);
    }

  }

  /**
   * 随机布尔值
   */
  public static class VirtualBoolean implements VirtualApi<Boolean> {

    @Override
    public Boolean onVirtual() {
      return VirtualUtils.getBoolean();
    }
  }

}

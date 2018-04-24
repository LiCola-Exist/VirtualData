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
   * @param <T>
   */
  public interface RandomInterface<T> {

    T getRandomData();
  }


  /**
   * 随机数字字符串
   */
  static class RandomStringNumber implements RandomInterface<String> {

    private int length;

    RandomStringNumber(int length) {
      this.length = length;
    }

    @Override
    public String getRandomData() {
      return getNumberString(length);
    }
  }

  static class RandomStringAlphabet implements RandomInterface<String>{

    private int length;

    RandomStringAlphabet(int length) {
      this.length = length;
    }

    @Override
    public String getRandomData() {
      return getAlphabetString(length);
    }
  }

  static class RandomStringSymbol implements RandomInterface<String> {

    private int length;

    RandomStringSymbol(int length) {
      this.length = length;
    }

    @Override
    public String getRandomData() {
      return getSymbolString(length);
    }
  }


  static class RandomStringChinese implements RandomInterface<String> {

    private int length;

    RandomStringChinese(int length) {
      this.length = length;
    }

    @Override
    public String getRandomData() {
      return getChineseSimple(length);
    }
  }

  static class RandomStringPhoneNumber implements RandomInterface<String>{

    @Override
    public String getRandomData() {
      return getPhoneNumberString();
    }
  }

  static class RandomInteger implements RandomInterface<Integer> {

    private int max;

    public RandomInteger(int max) {
      this.max = max;
    }

    @Override
    public Integer getRandomData() {
      return RandomUtils.getInt(max+1);
    }
  }

  static class RandomLong implements RandomInterface<Long>{

    private int max;

    public RandomLong(int max) {
      this.max = max;
    }

    @Override
    public Long getRandomData() {
      return RandomUtils.getLong(max+1);
    }
  }


}

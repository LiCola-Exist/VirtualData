package com.model.licola.virtual;


import static com.model.licola.virtual.RandomUtils.getChineseSimple;
import static com.model.licola.virtual.RandomUtils.getNumberString;
import static com.model.licola.virtual.RandomUtils.getSymbolString;

/**
 * Created by 李可乐 on 2017/4/20.
 */

public class RandomRuleData {

  public interface RandomDataInterface<T> {

    T getData();
  }


  static class RandomDataStringNumber implements RandomDataInterface<String> {

    private int length;

    RandomDataStringNumber(int length) {
      this.length = length;
    }

    @Override
    public String getData() {
      return getNumberString(length);
    }
  }

  static class RandomDataStringSymbol implements RandomDataInterface<String> {

    private int length;

    RandomDataStringSymbol(int length) {
      this.length = length;
    }

    @Override
    public String getData() {
      return getSymbolString(length);
    }
  }


  static class RandomDataStringChinese implements RandomDataInterface<String> {

    private int length;

    RandomDataStringChinese(int length) {
      this.length = length;
    }

    @Override
    public String getData() {
      return getChineseSimple(length);
    }
  }

  static class RandomDataInteger implements RandomDataInterface<Integer> {

    private int length;

    public RandomDataInteger(int length) {
      this.length = length;
    }

    @Override
    public Integer getData() {
      return RandomUtils.getInt(length);
    }
  }


}

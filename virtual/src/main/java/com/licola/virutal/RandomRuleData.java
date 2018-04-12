package com.licola.virutal;


import static com.licola.virutal.RandomUtils.getChineseSimple;
import static com.licola.virutal.RandomUtils.getNumberString;
import static com.licola.virutal.RandomUtils.getSymbolString;

/**
 * Created by 李可乐 on 2017/4/20.
 */

public class RandomRuleData {

  public interface RandomDataInterface<T> {

    T getRandomData();
  }


  static class RandomDataStringNumber implements RandomDataInterface<String> {

    private int length;

    RandomDataStringNumber(int length) {
      this.length = length;
    }

    @Override
    public String getRandomData() {
      return getNumberString(length);
    }
  }

  static class RandomDataStringSymbol implements RandomDataInterface<String> {

    private int length;

    RandomDataStringSymbol(int length) {
      this.length = length;
    }

    @Override
    public String getRandomData() {
      return getSymbolString(length);
    }
  }


  static class RandomDataStringChinese implements RandomDataInterface<String> {

    private int length;

    RandomDataStringChinese(int length) {
      this.length = length;
    }

    @Override
    public String getRandomData() {
      return getChineseSimple(length);
    }
  }

  static class RandomDataInteger implements RandomDataInterface<Integer> {

    private int length;

    public RandomDataInteger(int length) {
      this.length = length;
    }

    @Override
    public Integer getRandomData() {
      return RandomUtils.getInt(length);
    }
  }


}

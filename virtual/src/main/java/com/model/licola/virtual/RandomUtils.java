package com.model.licola.virtual;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by 李可乐 on 2017/4/18.
 */

public class RandomUtils {

  private static ThreadLocalRandom getRandomInstance() {
    return ThreadLocalRandom.current();
  }

  public static int getInt(int max) {
    return getRandomInstance().nextInt(max);
  }

  public static int getInt(int begin,int end) {
    return getRandomInstance().nextInt(begin,end);
  }

  public static long getLong(int max) {
    return getRandomInstance().nextLong(max);
  }

  public static boolean getBoolean() {
    return getRandomInstance().nextBoolean();
  }

  public static float getFloat() {
    return getRandomInstance().nextFloat();
  }

  public static double getDouble() {
    return getRandomInstance().nextDouble();
  }

  /**
   * 得到数字组成的随机字符串
   */
  public static String getNumberString(int length) {
    StringBuilder stringBuilder = new StringBuilder(length);
    if (length <= 1) {//一位长度
      return stringBuilder.append(getRandomInstance().nextInt(10)).toString();
    }
    for (int i = 0; i < length; i++) {
      if (i == 0) {//防止出现首位为0的数字
        stringBuilder.append(getRandomInstance().nextInt(1, 10));
      } else {
        stringBuilder.append(getRandomInstance().nextInt(10));
      }
    }
    return stringBuilder.toString();
  }



  /**
   * 得到随机符号组成的字符串
   */
  public static String getSymbolString(int length) {
    StringBuilder stringBuilder = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      stringBuilder.append((char) getRandomInstance().nextInt(33, 128));
    }
    return stringBuilder.toString();
  }

  /**
   * 得到随机中文字符串
   */
  public static String getChinese(int length) {
    char[] chars = new char[length];
    for (int i = 0; i < chars.length; i++) {
      chars[i] = getChineseChar();
    }
    return new String(chars);
  }

  private static char getChineseChar() {
    return (char) (0x4e00 + (int) (getRandomInstance().nextDouble() * (0x9fa5 - 0x4e00
        + 1)));
  }

  /**
   * 得到随机中文简体字符串
   */
  public static String getChineseSimple(int length) {
    StringBuilder stringBuilder = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      stringBuilder.append(getChineseCharSimple());
    }
    return stringBuilder.toString();
  }

  private static String getChineseCharSimple() {
    String str = null;
    // 定义高低位
    int highPos;
    int lowPos;
    highPos = (176 + Math.abs(getRandomInstance().nextInt(39))); //获取高位值
    lowPos = (161 + Math.abs(getRandomInstance().nextInt(93))); //获取低位值
    byte[] b = new byte[2];
    b[0] = (Integer.valueOf(highPos).byteValue());
    b[1] = (Integer.valueOf(lowPos).byteValue());
    try {
      str = new String(b, "GBk"); //转成中文
    } catch (UnsupportedEncodingException ex) {
      ex.printStackTrace();
    }
    return str;
  }


}

package virtual;

import java.util.Map;

/**
 * Created by LiCola on 2018/5/5.
 */
public class Util {

  public static boolean isEmpty(CharSequence str) {
    return str == null || str.length() == 0;
  }

  public static boolean isEmpty(Map map) {
    return map == null || map.isEmpty();
  }

  private static final int MAX_POWER_OF_TWO = 1 << (Integer.SIZE - 2);

  public static int getMapCapacity(int fixSize) {
    if (fixSize < 3) {
      return fixSize + 1;
    }

    if (fixSize < MAX_POWER_OF_TWO) {
      return fixSize + fixSize / 3;
    }
    return Integer.MAX_VALUE;
  }
}

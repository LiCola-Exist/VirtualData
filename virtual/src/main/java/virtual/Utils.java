package virtual;

import java.util.Map;

/**
 * Created by LiCola on 2018/5/5.
 */
class Utils {

  static boolean isEmpty(CharSequence str) {
    return str == null || str.length() == 0;
  }

  static boolean isEmpty(Map map) {
    return map == null || map.isEmpty();
  }

  static final int MAX_POWER_OF_TWO = 1 << (Integer.SIZE - 2);

  static int getMapCapacity(int fixSize) {
    if (fixSize < 3) {
      return fixSize + 1;
    }

    if (fixSize < MAX_POWER_OF_TWO) {
      return fixSize + fixSize / 3;
    }
    return Integer.MAX_VALUE;
  }
}

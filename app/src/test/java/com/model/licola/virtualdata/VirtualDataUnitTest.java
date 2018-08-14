package com.model.licola.virtualdata;

import static org.junit.Assert.assertEquals;

import com.licola.llogger.LLogger;
import com.model.licola.virtualdata.model.CollectionUserModel;
import com.model.licola.virtualdata.model.CommodityModel;
import com.model.licola.virtualdata.model.UserModel;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import org.junit.Test;
import virtual.VirtualApi;
import virtual.VirtualData;
import virtual.VirtualRules;
import virtual.VirtualRules.VirtualIntegerWithLength;
import virtual.VirtualUtils;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class VirtualDataUnitTest {

  @Test
  public void testVirtualUtils() {
    String numberString = VirtualUtils.getNumberString(2);
    assertEquals(false, numberString.isEmpty());
    LLogger.a(numberString);

    String chineseSimple = VirtualUtils.getChineseSimple(4);
    assertEquals(false, chineseSimple.isEmpty());
    LLogger.a(chineseSimple);

    String symbolString = VirtualUtils.getSymbolString(5);
    assertEquals(false, symbolString.isEmpty());
    LLogger.d(symbolString);

    String alphabetString = VirtualUtils.getAlphabetString(5);
    assertEquals(false, alphabetString.isEmpty());
    LLogger.d(alphabetString);

    String alphabetNumber = VirtualUtils.getAlphabetNumber(5);
    assertEquals(false, alphabetNumber.isEmpty());
    LLogger.d(alphabetNumber);
  }

  @Test
  public void testModels() {
    UserModel model = VirtualData.virtual(UserModel.class)
        .build();
    assertEquals(true, model != null);
    LLogger.a(model);
  }

  @Test
  public void testArray() {
    String[] strings = VirtualData
        .virtualArray(new String[3], new VirtualRules.VirtualStringChinese(3));
    assertEquals(true, !strings[0].isEmpty());
    LLogger.d(strings);

    Integer[] integers = VirtualData.virtualArray(new Integer[3], new VirtualIntegerWithLength(3));
    assertEquals(true, integers[0] > 100);
    LLogger.d(integers);

    UserModel[] userModels = VirtualData
        .virtualArray(new UserModel[3], new VirtualApi<UserModel>() {
          @Override
          public UserModel onVirtual() {
            return VirtualData.virtual(UserModel.class).build();
          }
        });
    LLogger.d(userModels);
  }


  @Test
  public void testList() {
    List<UserModel> userModels = VirtualData.virtual(UserModel.class).buildList();
    assertEquals(true, !userModels.isEmpty());
    for (UserModel model : userModels) {
      LLogger.a(model);
    }
  }

  @Test
  public void testSet() {
    Set<UserModel> userModels = VirtualData.virtual(UserModel.class).buildSet();
    assertEquals(true, !userModels.isEmpty());
    for (UserModel model : userModels) {
      LLogger.a(model);
    }
  }

  @Test
  public void testQueue() {
    Queue<UserModel> userModels = VirtualData.virtual(UserModel.class).buildQueue();
    assertEquals(true, !userModels.isEmpty());
    for (UserModel model : userModels) {
      LLogger.a(model);
    }
  }

  @Test
  public void testModelsNest() throws Exception {

    CollectionUserModel model = VirtualData.virtual(CollectionUserModel.class)
        .setSizeCollection(1)
        .addKeyInts("times", new Integer[]{10, 20, 30})
        .build();
    assertEquals(true, model.userModels != null);
    assertEquals(true, model.times > 0);

    LLogger.a(model);
  }

  @Test
  public void testMyBuilder() {

    CommodityModel model = VirtualData
        .virtual(CommodityModel.class, new MyVirtualDataBuilder())
        .build();
    assertEquals(true, model.getUserMap() != null);
    assertEquals(true, model.getImageModel() != null);

    LLogger.a(model);

  }

}
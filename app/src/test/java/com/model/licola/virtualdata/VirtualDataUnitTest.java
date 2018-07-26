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
import virtual.VirtualUtils;
import virtual.VirtualData;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class VirtualDataUnitTest {

  @Test
  public void testVirtualRules() {
    String numberString = VirtualUtils.getNumberString(2);
    assertEquals(false, numberString.isEmpty());
    LLogger.a(numberString);

    String chineseSimple = VirtualUtils.getChineseSimple(4);
    assertEquals(false,chineseSimple.isEmpty());
    LLogger.a(chineseSimple);
  }

  @Test
  public void testModels()  {
    UserModel model = VirtualData.virtual(UserModel.class)
        .build();
    assertEquals(true, model != null);
    LLogger.a(model);
  }


  @Test
  public void testModelList() {
    List<UserModel> userModels = VirtualData.virtual(UserModel.class).buildList();
    assertEquals(true, !userModels.isEmpty());
    for (UserModel model : userModels) {
      LLogger.a(model);
    }
  }

  @Test
  public void testModelSet() {
    Set<UserModel> userModels = VirtualData.virtual(UserModel.class).buildSet();
    assertEquals(true, !userModels.isEmpty());
    for (UserModel model : userModels) {
      LLogger.a(model);
    }
  }

  @Test
  public void testModelQueue() {
    Queue<UserModel> userModels = VirtualData.virtual(UserModel.class).buildQueue();
    assertEquals(true, !userModels.isEmpty());
    for (UserModel model : userModels) {
      LLogger.a(model);
    }
  }

  @Test
  public void testModelsNest() throws Exception {

    CollectionUserModel model = VirtualData.virtual(CollectionUserModel.class)
        .setSizeCollection(2)
        .addKeyInts("times", new Integer[]{10, 20, 30})
        .build();
    assertEquals(true, !model.userModels.isEmpty());
    assertEquals(true, model.times > 0);

    LLogger.a(model);
  }

  @Test
  public void testMyBuilder() {

    CommodityModel model = VirtualData
        .virtual(CommodityModel.class, new MyVirtualDataBuilder())
        .build();
    assertEquals(true, !model.getUserMap().isEmpty());
    assertEquals(true, model.getImageModel() != null);

    LLogger.a(model);

  }

}
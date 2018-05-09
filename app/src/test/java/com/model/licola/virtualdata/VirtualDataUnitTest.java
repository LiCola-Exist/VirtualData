package com.model.licola.virtualdata;

import static org.junit.Assert.assertEquals;

import com.model.licola.virtualdata.model.CollectionUserModel;
import com.model.licola.virtualdata.model.CommodityModel;
import com.model.licola.virtualdata.model.UserModel;
import java.util.List;
import org.junit.Test;
import virtual.RandomUtils;
import virtual.VirtualData;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class VirtualDataUnitTest {

  @Test
  public void testRandomRule() {
    String numberString = RandomUtils.getNumberString(2);
    System.out.println(numberString);
    assertEquals(false, numberString.equals(""));
  }

  @Test
  public void testModels() throws Exception {
    UserModel userModel = VirtualData.virtual(UserModel.class)
        .build();
    assertEquals(true, userModel != null);
    System.out.println(userModel.toString());
  }


  @Test
  public void testModelList() {
    List<CommodityModel> userModels = VirtualData.virtual(CommodityModel.class).buildList();
    assertEquals(true, !userModels.isEmpty());
    for (CommodityModel model : userModels) {
      System.out.println(model);
    }
  }

  @Test
  public void testModelsNest() throws Exception {

    CollectionUserModel models = VirtualData.virtual(CollectionUserModel.class)
        .setSizeCollection(2)
        .addKeyInts("times", new Integer[]{10, 20, 30})
        .build();

    System.out.println(models.toString());
    assertEquals(true, !models.userModels.isEmpty());
    assertEquals(true, models.times > 0);
  }

  @Test
  public void testMyBuilder() {

    CommodityModel commodityModel = VirtualData
        .virtual(CommodityModel.class, new MyVirtualDataBuilder())
        .build();
    System.out.println(commodityModel);
    assertEquals(true, !commodityModel.getUserMap().isEmpty());
    assertEquals(true, commodityModel.getImageModel() != null);

  }

}
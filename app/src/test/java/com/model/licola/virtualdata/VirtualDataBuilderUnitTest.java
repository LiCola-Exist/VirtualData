package com.model.licola.virtualdata;

import static org.junit.Assert.assertEquals;

import com.model.licola.virtualdata.model.CollectionUserModel;
import com.model.licola.virtualdata.model.UserModel;
import java.util.List;
import org.junit.Test;
import virtual.RandomUtils;
import virtual.VirtualDataBuilder;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class VirtualDataBuilderUnitTest {


  @Test
  public void testRandomRule() {
    String numberString = RandomUtils.getNumberString(2);
    System.out.println(numberString);
    assertEquals(false, numberString.equals(""));
  }

  @Test
  public void testString() throws Exception {
    int size = 10;
    List<String> names = VirtualDataBuilder.VirtualStrings(size);
    assertEquals(size, names.size());
    System.out.println(names.toString());
  }

  @Test
  public void testModelList() {
    List<UserModel> userModels = VirtualDataBuilder.virtual(UserModel.class).buildList();
    assertEquals(true, !userModels.isEmpty());
    for (UserModel userModel : userModels) {
      System.out.println(userModel);
    }

  }

  @Test
  public void testModels() throws Exception {
    CollectionUserModel models = VirtualDataBuilder.virtual(CollectionUserModel.class)
        .addKeyInts("times", new Integer[]{10, 20, 30})
//        .addKeyInts("level",new int[]{1,2,3})
        .build();

    assertEquals(true, !models.userModels.isEmpty());
    assertEquals(true, models.commodityModel != null);
    System.out.println(models.toString());
  }

}
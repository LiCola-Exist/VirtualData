package com.model.licola.virtualdata;

import static org.junit.Assert.assertEquals;

import com.licola.virutal.VirtualDataBuilder;
import com.model.licola.virtualdata.model.CollectionUserModel;
import com.model.licola.virtualdata.model.UserModel;
import java.util.List;
import java.util.Set;
import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


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
    int size = 3;
    CollectionUserModel models = VirtualDataBuilder.virtual(CollectionUserModel.class, size)
        .addKeyInts("times", new int[]{10, 20, 30})
        .build();

    assertEquals(size, models.userModels.size());
    assertEquals(true, models.commodityModel != null);
    System.out.println(models.toString());
  }

}
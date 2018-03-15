package com.model.licola.virtualdata;

import com.model.licola.virtual.DataVirtualBuilder;
import com.model.licola.virtualdata.model.CollectionUserModel;
import com.model.licola.virtualdata.model.UserModel;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {


  @Test
  public void testString() throws Exception {
    int size=10;
    List<String> names=DataVirtualBuilder.VirtualCollectionsString(size);
    assertEquals(size,names.size());
    System.out.println(names.toString());
  }

  @Test
  public void testModels() throws Exception {
    int size=3;
    CollectionUserModel models = DataVirtualBuilder.virtual(CollectionUserModel.class,size)
        .addKeyInts("times",new int[]{10,20,30})
        .build();
    assertEquals(size,models.userModels.size());
    assertEquals(true,models.commodityModel!=null);
    System.out.println(models.toString());
  }

}
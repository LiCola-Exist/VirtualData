package com.model.licola.virtualdata;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.model.licola.virtualdata.model.CommodityModel;
import com.model.licola.virtualdata.model.UserModel;
import virtual.VirtualData;

/**
 * 开启 instant-run会对代码结构产生影响 已经处理好
 */
public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);
    TextView tvSimple = findViewById(R.id.tv_simple);

    UserModel userModel = VirtualData.virtual(UserModel.class).build();
    tvSimple.setText(userModel.toString());

    TextView tvCustom = findViewById(R.id.tv_custom);
    CommodityModel commodityModel = VirtualData.virtual(CommodityModel.class,new MyVirtualDataBuilder())
        .build();
    tvCustom.setText(commodityModel.toString());
  }
}

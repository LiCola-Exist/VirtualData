package com.model.licola.virtualdata;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.model.licola.virtualdata.model.UserModel;
import virtual.VirtualData;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    TextView textView = findViewById(R.id.txt_content);

    //开启 instant-run会对代码结构产生影响 已经处理好
    UserModel userModel = VirtualData.virtual(UserModel.class).build();
    textView.setText(userModel.toString());

  }
}

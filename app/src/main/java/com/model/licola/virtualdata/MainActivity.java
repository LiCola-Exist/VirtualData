package com.model.licola.virtualdata;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.model.licola.virtualdata.model.CollectionUserModel;
import virtual.VirtualDataBuilder;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    TextView textView = findViewById(R.id.txt_content);

    CollectionUserModel models = VirtualDataBuilder.virtual(CollectionUserModel.class)
        .addKeyInts("times", new int[]{10, 20, 30})
        .closeThrowNewInstanceException()
        .build();
    textView.setText(models.toString());

  }
}

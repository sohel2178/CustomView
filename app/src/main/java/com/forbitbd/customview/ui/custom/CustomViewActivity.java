package com.forbitbd.customview.ui.custom;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.forbitbd.customview.R;
import com.forbitbd.customview.customView.GradView;
import com.forbitbd.customview.customView.TableFanView;

public class CustomViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        TableFanView tableFanView = findViewById(R.id.table_fan);
        GradView bulb = findViewById(R.id.bulb);

        Button btnToggle = findViewById(R.id.toggle);
        btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnToggle.getText().equals("ON")){
                    btnToggle.setText("OFF");
                    tableFanView.setState("ON");
                }else{
                    btnToggle.setText("ON");
                    tableFanView.setState("OFF");
                }
            }
        });

        Button btnBulb = findViewById(R.id.toggle_bulb);
        btnBulb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnBulb.getText().equals("ON")){
                    btnBulb.setText("OFF");
                    bulb.setState("ON");
                }else{
                    btnBulb.setText("ON");
                    bulb.setState("OFF");
                }
            }
        });
    }
}
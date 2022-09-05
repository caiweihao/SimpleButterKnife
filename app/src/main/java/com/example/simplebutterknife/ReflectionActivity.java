package com.example.simplebutterknife;

import android.os.Bundle;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

public class ReflectionActivity extends AppCompatActivity {

    @ReflectBindView(R.id.tv_hello)
    TextView mHelloTv;
    @ReflectBindView(R.id.tv_name)
    TextView mNameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ReflectBindViewUtil.bind(this);
        mHelloTv.setText("ReflectionActivity TV 你好!");
        mNameTv.setText("ReflectionActivity name 你好!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ReflectBindViewUtil.showMyBindField(this);
        ReflectBindViewUtil.unbind(this);
        ReflectBindViewUtil.showMyBindField(this);
    }
}

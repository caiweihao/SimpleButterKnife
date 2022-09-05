package com.example.simplebutterknife;

import android.os.Bundle;
import android.widget.TextView;


import com.example.lib_annotation.BindView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_hello)
    TextView mHelloTv;
    @BindView(R.id.tv_name)
    TextView mNameTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProcessBindViewUtil.bind(this);
        mHelloTv.setText("MainActivity TV 你好!");
        mNameTv.setText("MainActivity name 你好!");
    }
}

package com.mars.aoploginsimple.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mars.ioclibrary.InjectManager;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 帮助子类进行布局、空间、事件的注入
        InjectManager.inject(this);
    }
}

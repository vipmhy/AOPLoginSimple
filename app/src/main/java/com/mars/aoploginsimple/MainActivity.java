package com.mars.aoploginsimple;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mars.aoploginsimple.annotation.ClickBehavior;
import com.mars.aoploginsimple.annotation.LoginCheck;
import com.mars.aoploginsimple.base.BaseActivity;
import com.mars.ioclibrary.annotation.ContentView;
import com.mars.ioclibrary.annotation.InjectView;
import com.mars.ioclibrary.annotation.OnClick;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    private final static String TAG = "mars >>>";

    @InjectView(R.id.info_tv)
    private TextView infoTv;

    @InjectView(R.id.btn_login)
    private Button btnLogin;

    @InjectView(R.id.btn_area)
    private Button btnArea;

    @InjectView(R.id.btn_coupon)
    private Button btnCoupon;

    @InjectView(R.id.btn_score)
    private Button btnScore;

    @OnClick(R.id.btn_login)// 点击事件
    @ClickBehavior("登录")// 登录点击事件（用户行为统计）
    public void login(View v) {
        infoTv.setText(((Button) v).getText() + "成功！");
        btnLogin.setTextColor(Color.RED);
        Log.e(TAG, "模拟接口请求……验证通过，登录成功！");
    }

    @OnClick(R.id.btn_area)// 点击事件
    @ClickBehavior("我的专区")// 用户行为统计（友盟统计？！后台要求自己统计）
    @LoginCheck // 登录验证检查
    public void area(View v) {
        Log.e(TAG, "开始跳转到 -> 我的专区 Activity");
        startActivity(new Intent(this, OtherActivity.class));
    }

    @OnClick(R.id.btn_coupon)// 点击事件
    @ClickBehavior("我的优惠券-RV") // 用户行为统计
    @LoginCheck // 登录验证检查
    public void coupon() {
        Log.e(TAG, "开始跳转到 -> 我的优惠券 Activity");
        startActivity(new Intent(this, RViewActivity.class));
    }

    @OnClick(R.id.btn_score)// 点击事件
    @ClickBehavior("我的积分")// 用户行为统计
    @LoginCheck // 登录验证检查
    public void score() {
        Log.e(TAG, "开始跳转到 -> 我的积分 Activity");
        startActivity(new Intent(this, OtherActivity.class));
    }

}

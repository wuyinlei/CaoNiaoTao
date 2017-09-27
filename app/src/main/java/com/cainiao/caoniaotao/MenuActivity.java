package com.cainiao.caoniaotao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ali.auth.third.ui.context.CallbackContext;
import com.alibaba.baichuan.trade.biz.login.AlibcLogin;
import com.alibaba.baichuan.trade.biz.login.AlibcLoginCallback;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 登录淘宝
     */
    public void login(View view){

        AlibcLogin alibcLogin = AlibcLogin.getInstance();
        alibcLogin.showLogin(new AlibcLoginCallback() {
            @Override
            public void onSuccess(int i) {
                Toast.makeText(MenuActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(MenuActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });

    }


    /**
     * 退出授权
     */
    public void logout(View view){

        AlibcLogin alibcLogin = AlibcLogin.getInstance();
        alibcLogin.logout(new AlibcLoginCallback() {
            @Override
            public void onSuccess(int i) {
                Toast.makeText(MenuActivity.this, "退出授权成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(MenuActivity.this, "退出授权失败", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 电商交易
     */
    public void trade(View view){
        Intent transactionIntent = new Intent(this, AliSdkTransactionActivity.class);
        startActivity(transactionIntent);
    }

    /**
     * 查看订单和购物车
     */
    public void orderAndCart(View view) {
        Intent mineIntent = new Intent(this, AliSdkOrderActivity.class);
        startActivity(mineIntent);
    }

    /**
     * webview代理
     */
    public void webview(View view){
        Intent webviewIntent = new Intent(this, AliSdkWebViewProxyActivity.class);
        startActivity(webviewIntent);
    }

    //登录须重写onActivityResult方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        CallbackContext.onActivityResult(requestCode, resultCode, data);
    }




}

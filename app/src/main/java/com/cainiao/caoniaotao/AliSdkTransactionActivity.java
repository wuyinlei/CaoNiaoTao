package com.cainiao.caoniaotao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcAddCartPage;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcDetailPage;
import com.alibaba.baichuan.android.trade.page.AlibcPage;
import com.alibaba.baichuan.android.trade.page.AlibcShopPage;
import com.alibaba.baichuan.trade.biz.context.AlibcResultType;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuyinlei on 2017/9/27.
 * <p>
 * 功能说明：电商SDK 电商交易界面，包含直接URL加载，商品加载，店铺加载等相关内容
 */

public class AliSdkTransactionActivity extends AppCompatActivity {

    private EditText etUrl;
    private EditText etItemId;
    private EditText etShopId;

    private AlibcShowParams alibcShowParams;//页面打开方式，默认，H5，Native
    private AlibcTaokeParams alibcTaokeParams = null;//淘客参数，包括pid，unionid，subPid
    private Boolean isTaoke = false;//是否是淘客商品类型
    private String itemId = "542651106234";//默认商品id
    private String shopId = "60552065";//默认店铺id
    private Map<String, String> exParams;//yhhpass参数

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_transaction);
        setTitle(getResources().getString(R.string.transaction_test));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        alibcShowParams = new AlibcShowParams(OpenType.Auto, false);
        exParams = new HashMap<>();
        exParams.put("isv_code", "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改

        initView();

    }

    private void initView() {
        etUrl = (EditText) findViewById(R.id.alisdk_et_url);
        etItemId = (EditText) findViewById(R.id.alisdk_et_itemId);
        etShopId = (EditText) findViewById(R.id.alisdk_et_shopId);
    }

    /**
     * 打开指定链接
     *
     * @param view
     */
    public void showUrl(View view) {

        String text = etUrl.getText().toString();
        if (TextUtils.isEmpty(text)) {
            Toast.makeText(AliSdkTransactionActivity.this, "URL为空",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        alibcTaokeParams = new AlibcTaokeParams(); // 若非淘客taokeParams设置为null即可
        alibcTaokeParams.adzoneid = "57328044";
        alibcTaokeParams.pid = "mm_26632322_6858406_23810104";
        alibcTaokeParams.subPid = "mm_26632322_6858406_23810104";
        alibcTaokeParams.extraParams = new HashMap<>();
        alibcTaokeParams.extraParams.put("taokeAppkey", "23373400");

        AlibcTrade.show(this, new AlibcPage(text), alibcShowParams, alibcTaokeParams, exParams, new DemoTradeCallback());
    }

    /**
     * @param view 显示商品详情页
     */
    public void showDetail(View view) {

        AlibcBasePage alibcBasePage;
        if (!TextUtils.isEmpty(etItemId.getText().toString())) {
            alibcBasePage = new AlibcDetailPage(etItemId.getText().toString());
        } else {
            alibcBasePage = new AlibcDetailPage(itemId.trim());
        }

        if (isTaoke) {
            //参数设置地址 https://baichuan.bbs.taobao.com/detail.html?spm=a3c0d.7971500.0.0.56b5951fkDzB3w&postId=7927184
            alibcTaokeParams = new AlibcTaokeParams(); // 若非淘客taokeParams设置为null即可
            alibcTaokeParams.adzoneid = "57328044";
            alibcTaokeParams.pid = "mm_26632322_6858406_23810104";
            alibcTaokeParams.subPid = "mm_26632322_6858406_23810104";
            alibcTaokeParams.extraParams = new HashMap<>();
            alibcTaokeParams.extraParams.put("taokeAppkey", "23373400");
        } else {
            alibcTaokeParams = null;
        }

        AlibcTrade.show(this, alibcBasePage, alibcShowParams, alibcTaokeParams, exParams, new DemoTradeCallback());
    }

    /**
     * @param view 显示店铺
     */
    public void showShop(View view) {

        AlibcBasePage alibcBasePage;

        if (!TextUtils.isEmpty(etShopId.getText().toString())) {
            alibcBasePage = new AlibcShopPage(etShopId.getText().toString());
        } else {
            alibcBasePage = new AlibcShopPage(shopId.trim());
        }

        AlibcTrade.show(this, alibcBasePage, alibcShowParams, alibcTaokeParams, exParams, new DemoTradeCallback());
    }

    //设置打开方式：默认方式
    public void defaultChecked(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            alibcShowParams = new AlibcShowParams(OpenType.Auto, false);
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    //设置打开方式：taobao方式
    public void taobaoChecked(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            alibcShowParams = new AlibcShowParams(OpenType.Native, false);
            alibcShowParams.setClientType("taobao_scheme");
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    //设置打开方式：tmall方式
    public void tmallChecked(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            alibcShowParams = new AlibcShowParams(OpenType.Native, false);
            alibcShowParams.setClientType("tmall_scheme");
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    //设置打开方式：H5方式
    public void h5Checked(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            alibcShowParams = new AlibcShowParams(OpenType.H5, false);
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    //设置商品类型：普通商品
    public void commonChecked(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            isTaoke = false;
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    //设置商品类型：淘客商品
    public void taokeChecked(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            isTaoke = true;
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    //设置商品id是否混淆：不混淆
    public void notConfuseChecked(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            itemId = "522166121586";
            if (etItemId.getText().toString().equals("AAHPt-dcABxGVVui-VRMv5Gm")) {
                etItemId.setText(itemId);
            }
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    //设置商品id是否混淆：混淆
    public void confuseChecked(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            itemId = "AAHPt-dcABxGVVui-VRMv5Gm";
            if (etItemId.getText().toString().equals("522166121586")) {
                etItemId.setText(itemId);
            }
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        //调用了AlibcTrade.show方法的Activity都需要调用AlibcTradeSDK.destory()
        AlibcTradeSDK.destory();
        super.onDestroy();
    }

    /**
     * @param view 添加到购物车
     */
    public void addToCart(View view) {

        AlibcBasePage alibcBasePage;

        if (!TextUtils.isEmpty(etItemId.getText().toString())) {
            alibcBasePage = new AlibcAddCartPage(etItemId.getText().toString());
        } else {
            alibcBasePage = new AlibcAddCartPage(itemId.trim());
        }

        if (isTaoke) {
            alibcTaokeParams = new AlibcTaokeParams(); // 若非淘客taokeParams设置为null即可
            alibcTaokeParams.adzoneid = "57328044";
            alibcTaokeParams.pid = "mm_26632322_6858406_23810104";
            alibcTaokeParams.subPid = "mm_26632322_6858406_23810104";
        } else {
            alibcTaokeParams = null;
        }

        AlibcTrade.show(this, alibcBasePage, alibcShowParams, alibcTaokeParams, exParams, new DemoTradeCallback());
    }


    private class DemoTradeCallback implements AlibcTradeCallback {

        @Override
        public void onTradeSuccess(AlibcTradeResult tradeResult) {
            //当addCartPage加购成功和其他page支付成功的时候会回调

            if (tradeResult.resultType.equals(AlibcResultType.TYPECART)) {
                //加购成功
                Toast.makeText(CNApplication.application, "加购成功", Toast.LENGTH_SHORT).show();
            } else if (tradeResult.resultType.equals(AlibcResultType.TYPEPAY)) {
                //支付成功
                Toast.makeText(CNApplication.application, "支付成功,成功订单号为" + tradeResult.payResult.paySuccessOrders, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(int errCode, String errMsg) {
            Toast.makeText(CNApplication.application, "电商SDK出错,错误码=" + errCode + " / 错误消息=" + errMsg, Toast.LENGTH_SHORT).show();
        }
    }


}

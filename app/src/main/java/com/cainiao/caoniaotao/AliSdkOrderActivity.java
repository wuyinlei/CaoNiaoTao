package com.cainiao.caoniaotao;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.alibaba.baichuan.android.trade.AlibcTrade;
import com.alibaba.baichuan.android.trade.AlibcTradeSDK;
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback;
import com.alibaba.baichuan.android.trade.model.AlibcShowParams;
import com.alibaba.baichuan.android.trade.model.OpenType;
import com.alibaba.baichuan.android.trade.page.AlibcBasePage;
import com.alibaba.baichuan.android.trade.page.AlibcMyCartsPage;
import com.alibaba.baichuan.android.trade.page.AlibcMyOrdersPage;
import com.alibaba.baichuan.trade.biz.context.AlibcResultType;
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuyinlei on 2017/9/27.
 * <p>
 * 查看我的订单界面
 */

public class AliSdkOrderActivity extends AppCompatActivity {

    private int orderType = 0;//订单页面参数，仅在H5方式下有效
    private AlibcShowParams alibcShowParams;//页面打开方式，默认，H5，Native
    private Map<String, String> exParams;//yhhpass参数

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mine);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        alibcShowParams = new AlibcShowParams(OpenType.Auto, false);

        exParams = new HashMap<>();
        exParams.put("isv_code", "appisvcode");
        exParams.put("alibaba", "阿里巴巴");//自定义参数部分，可任意增删改
    }

    /**
     * 默认打开方式
     *
     * @param view View
     */
    public void defaultChecked(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            alibcShowParams = new AlibcShowParams(OpenType.Auto, false);
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * H5 打开方式
     *
     * @param view view
     */
    public void h5Checked(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked){
            alibcShowParams = new AlibcShowParams(OpenType.H5,false);
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 手淘打开方式
     *
     * @param view view
     */
    public void taobaoChecked(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked){
            alibcShowParams = new AlibcShowParams(OpenType.Native,false);
            alibcShowParams.setClientType("taobao_scheme");
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 天猫打开方式
     *
     * @param view view
     */
    public void tmallChecked(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked){
            alibcShowParams = new AlibcShowParams(OpenType.Native,false);
            alibcShowParams.setClientType("tmall_scheme");
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 查看全部订单
     *
     * @param view view
     */
    public void allChecked(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            orderType = 0;
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 待支付订单
     *
     * @param view view
     */
    public void topayChecked(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            orderType = 1;
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 代发货订单
     *
     * @param view view
     */
    public void tosendChecked(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            orderType = 2;
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 待收货订单
     *
     * @param view view
     */
    public void toreceiveChecked(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            orderType = 3;
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 待评论订单
     *
     * @param view view
     */
    public void tocommentChecked(View view) {
        boolean isChecked = ((RadioButton) view).isChecked();
        if (isChecked) {
            orderType = 4;
            Toast.makeText(this, ((RadioButton) view).getText().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 按区域查看订单
     *
     * @param view view
     */
    public void showOrder(View view) {
        AlibcBasePage alibcBasePage = new AlibcMyOrdersPage(orderType, false);
        AlibcTrade.show(this, alibcBasePage, alibcShowParams, null, exParams, new DemoTradeCallback());
    }

    /**
     * 查看所有订单
     *
     * @param view view
     */
    public void showAllOrder(View view) {
        AlibcBasePage alibcBasePage = new AlibcMyOrdersPage(orderType, true);
        AlibcTrade.show(this, alibcBasePage, alibcShowParams, null, exParams, new DemoTradeCallback());

    }


    /**
     * 显示购物车
     *
     * @param view view
     */
    public void showCart(View view) {
        AlibcBasePage alibcBasePage = new AlibcMyCartsPage();
        AlibcTrade.show(this, alibcBasePage, alibcShowParams, null, exParams, new DemoTradeCallback());
    }

    class DemoTradeCallback implements AlibcTradeCallback {

        @Override
        public void onTradeSuccess(AlibcTradeResult tradeResult) {
            //当addCartPage加购成功和其他page支付成功的时候会回调

            if(tradeResult.resultType.equals(AlibcResultType.TYPECART)){
                //加购成功
                Toast.makeText(CNApplication.application, "加购成功", Toast.LENGTH_SHORT).show();
            }else if (tradeResult.resultType.equals(AlibcResultType.TYPEPAY)){
                //支付成功
                Toast.makeText(CNApplication.application, "支付成功,成功订单号为"+tradeResult.payResult.paySuccessOrders, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onFailure(int errCode, String errMsg) {
            Toast.makeText(CNApplication.application, "电商SDK出错,错误码="+errCode+" / 错误消息="+errMsg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        //调用了AlibcTrade.show方法的Activity都需要调用AlibcTradeSDK.destory()
        AlibcTradeSDK.destory();
        super.onDestroy();
    }


}

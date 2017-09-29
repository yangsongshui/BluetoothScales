package myapplication.com.bluetoothscales.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kitnew.ble.QNData;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import myapplication.com.bluetoothscales.CustomViewPager;
import myapplication.com.bluetoothscales.OnItemViewClickListener;
import myapplication.com.bluetoothscales.R;
import myapplication.com.bluetoothscales.adapter.MyPagerAdapter;
import myapplication.com.bluetoothscales.app.MyApplication;
import myapplication.com.bluetoothscales.base.BaseFragment;
import myapplication.com.bluetoothscales.utils.FragmentEvent;
import myapplication.com.bluetoothscales.utils.SpUtils;

import static com.kitnew.ble.QNData.TYPE_BMI;
import static myapplication.com.bluetoothscales.utils.Constant.ACTION_BLE_NOTIFY_DATA;

public class TrendFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.trend_iv)
    ImageView trendIv;
    @BindView(R.id.baby_cb)
    CheckBox babyCb;
    @BindView(R.id.preg_cb)
    CheckBox pregCb;
    @BindView(R.id.trend_preg_ll)
    LinearLayout trendPregLl;
    @BindView(R.id.preg_ll)
    LinearLayout trendPregLl2;
    @BindView(R.id.trend_preg_ll2)
    LinearLayout trendBabyLl;
    @BindView(R.id.trend_ll)
    LinearLayout trendLl;
    @BindView(R.id.trend_preg_ll3)
    LinearLayout trend_preg_ll3;

    @BindView(R.id.msg)
    TextView msg;
    @BindView(R.id.preg_pager)
    CustomViewPager pregPager;
    @BindView(R.id.work_trend)
    TextView workTrend;
    @BindView(R.id.work_trend2)
    TextView workTrend2;
    @BindView(R.id.baby_pager)
    CustomViewPager babyPager;
    @BindView(R.id.iv)
    ImageView iv;

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_BLE_NOTIFY_DATA);
        getActivity().registerReceiver(notifyReceiver, intentFilter);
        if (SpUtils.getInt("type", 1) == 1) {
            title.setText("Workout Trend");
            trendIv.setBackgroundResource(R.drawable.work);
            initWork();
            trend_preg_ll3.setVisibility(View.VISIBLE);
        } else if (SpUtils.getInt("type", 1) == 2) {
            title.setText("Pregnancy Mode");
            setView(MyApplication.newInstance().getQnData());
            trendPregLl.setVisibility(View.VISIBLE);
        } else if (SpUtils.getInt("type", 1) == 3) {
            title.setText("Baby Trend");
            trendBabyLl.setVisibility(View.VISIBLE);
            if (SpUtils.getString("sex", "Boy").equals("Boy")) {
                trendLl.setBackgroundResource(R.drawable.main_home);
                trendIv.setBackgroundResource(R.drawable.baby_boy);
            } else {
                trendIv.setBackgroundResource(R.drawable.baby);
                trendLl.setBackgroundResource(R.drawable.main_home2);
            }

        }

        pregCb.setOnCheckedChangeListener(this);
        babyCb.setOnCheckedChangeListener(this);
        initPregList();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_trend;
    }

    @SuppressLint("StringFormatMatches")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(FragmentEvent event) {
        if (event.getDistance() == 1) {
            title.setText("Workout Trend");
            trendLl.setBackgroundResource(R.drawable.main_home);
            trendIv.setBackgroundResource(R.drawable.work);
            initWork();
        } else if (event.getDistance() == 2) {
            title.setText("Pregnancy Mode");
            trendLl.setBackgroundResource(R.drawable.main_home);
            setView(MyApplication.newInstance().getQnData());
            initList();
        } else if (event.getDistance() == 3) {
            title.setText("Baby Trend");
            if (SpUtils.getString("sex", "Boy").equals("Boy")) {
                trendLl.setBackgroundResource(R.drawable.main_home);
                trendIv.setBackgroundResource(R.drawable.baby_boy);
            } else {
                trendLl.setBackgroundResource(R.drawable.main_home2);
                trendIv.setBackgroundResource(R.drawable.baby);
            }
        }
        trendPregLl.setVisibility(event.getDistance() == 2 ? View.VISIBLE : View.GONE);
        trendBabyLl.setVisibility(event.getDistance() == 3 ? View.VISIBLE : View.GONE);
        trend_preg_ll3.setVisibility(event.getDistance() == 1 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (SpUtils.getInt("type", 1) == 3) {
            if (SpUtils.getString("sex", "Boy").equals("Boy")) {
                trendLl.setBackgroundResource(R.drawable.main_home);
                trendIv.setBackgroundResource(R.drawable.baby_boy);
            } else {
                trendIv.setBackgroundResource(R.drawable.baby);
                trendLl.setBackgroundResource(R.drawable.main_home2);
            }

        } else if (SpUtils.getInt("type", 1) == 2) {
            setView(MyApplication.newInstance().getQnData());
            initList();
        } else if (SpUtils.getInt("type", 1) == 1) {

        }
        trendPregLl.setVisibility(SpUtils.getInt("type", 1) == 2 ? View.VISIBLE : View.GONE);
        trendBabyLl.setVisibility(SpUtils.getInt("type", 1) == 3 ? View.VISIBLE : View.GONE);
        trend_preg_ll3.setVisibility(SpUtils.getInt("type", 1) == 1 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);//反注册EventBus
        getActivity().unregisterReceiver(notifyReceiver);

    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.preg_cb:
                trendPregLl2.setVisibility(b ? View.VISIBLE : View.GONE);
                break;
            case R.id.baby_cb:
                babyPager.setVisibility(b ? View.VISIBLE : View.GONE);
                break;
        }
    }

    private void setView(QNData qnData) {
        if (qnData != null) {
            if (qnData.getFloatValue(TYPE_BMI) < 18.5) {
                trendIv.setBackgroundResource(R.drawable.bmi1);
                msg.setText(R.string.preg1);
                iv.setImageResource(R.drawable.ku);
            } else if (qnData.getFloatValue(TYPE_BMI) < 25) {
                trendIv.setBackgroundResource(R.drawable.bmi2);
                msg.setText(R.string.preg2);
                iv.setImageResource(R.drawable.wu);
            } else if (qnData.getFloatValue(TYPE_BMI) >= 25) {
                trendIv.setBackgroundResource(R.drawable.bmi3);
                msg.setText(R.string.preg3);
                iv.setImageResource(R.drawable.xiao);
            }
        } else {
            trendIv.setBackgroundResource(R.drawable.bmi1);
            iv.setImageResource(R.drawable.ku);
        }
    }

    private void initWork() {
     /*   if (!SpUtils.getString("weight", "").equals("") && !SpUtils.getString("workTarget", "").equals("")) {
            double weiht = parseDouble(SpUtils.getString("weight", ""));
            double workTarget = Double.parseDouble(SpUtils.getString("workTarget", ""));
            if (MyApplication.newInstance().getQnData() != null) {
                workTrend.setText(String.format(getString(R.string.preg4), String.valueOf((weiht - MyApplication.newInstance().getQnData().getWeight()) + SpUtils.getString("unit", "LBS"))));
                workTrend2.setText(String.format(getString(R.string.preg5), String.valueOf((workTarget - MyApplication.newInstance().getQnData().getWeight()) + SpUtils.getString("unit", "LBS"))));
            } else {
                workTrend.setText(String.format(getString(R.string.preg4), "--" + SpUtils.getString("unit", "LBS")));
                workTrend2.setText(String.format(getString(R.string.preg5), String.valueOf((workTarget - weiht) + SpUtils.getString("unit", "LBS"))));
            }
        } else {
            workTrend.setText(String.format(getString(R.string.preg4), "--" + SpUtils.getString("unit", "LBS")));
            workTrend2.setText(String.format(getString(R.string.preg5), "--" + SpUtils.getString("unit", "LBS")));
        }*/
        workTrend.setText(String.format(getString(R.string.preg4), "50" + SpUtils.getString("unit", "LBS")));
        workTrend2.setText(String.format(getString(R.string.preg5), "50" + SpUtils.getString("unit", "LBS")));
    }

    private BroadcastReceiver notifyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Log.e("BLEService收到设备信息广播", intent.getAction());
            if (ACTION_BLE_NOTIFY_DATA.equals(intent.getAction())) {
                QNData qnData = MyApplication.newInstance().getQnData();
                setView(qnData);
            }
        }
    };


    private void initPregList() {
        List<String> title = new ArrayList<>();
        List<String> msg = new ArrayList<>();
        title.add(getString(R.string.baby_title));
        title.add(getString(R.string.baby_title2));
        title.add(getString(R.string.baby_title3));
        title.add(getString(R.string.baby_title3));
        title.add(getString(R.string.baby_title4));
        title.add(getString(R.string.baby_title4));
        msg.add(getString(R.string.baby_msg));
        msg.add(getString(R.string.baby_msg2));
        msg.add(getString(R.string.baby_msg3));
        msg.add(getString(R.string.baby_msg31));
        msg.add(getString(R.string.baby_msg4));
        msg.add(getString(R.string.baby_msg41));
        int[] id = {R.drawable.baby2, R.drawable.baby3, R.drawable.baby4, R.drawable.baby4, R.drawable.baby5, R.drawable.baby5};
        babyPager.setOffscreenPageLimit(5);
        MyPagerAdapter adapter = new MyPagerAdapter(title, msg, getActivity());
        adapter.setId(id);
        babyPager.setAdapter(adapter);
        adapter.setOnItemViewClickListener(new OnItemViewClickListener() {
            @Override
            public void OnItemView(int position, View view, boolean is) {
                if (is)
                    babyPager.setCurrentItem(position - 1);
                else
                    babyPager.setCurrentItem(position + 1);
            }
        });

    }

    private void initList() {
        List<String> title = new ArrayList<>();
        List<String> msg = new ArrayList<>();
        title.add(getString(R.string.trend_preg1_title));
        title.add(getString(R.string.trend_preg1_title2));
        title.add(getString(R.string.trend_preg1_title3));
        title.add(getString(R.string.trend_preg1_title4));
        title.add(getString(R.string.trend_preg1_title5));
        title.add(getString(R.string.trend_preg1_title6));
        title.add(getString(R.string.trend_preg1_title7));
        title.add(getString(R.string.trend_preg1_title8));
        title.add(getString(R.string.trend_preg1_title9));

        msg.add(getString(R.string.trend_preg1));
        msg.add(getString(R.string.trend_preg2));
        msg.add(getString(R.string.trend_preg3));
        msg.add(getString(R.string.trend_preg4));
        msg.add(getString(R.string.trend_preg5));
        msg.add(getString(R.string.trend_preg6));
        msg.add(getString(R.string.trend_preg7));
        msg.add(getString(R.string.trend_preg8));
        msg.add(getString(R.string.trend_preg9));

        int[] id = {R.drawable.putao, R.drawable.jvzi, R.drawable.wandou, R.drawable.ninmeng, R.drawable.xihongshi, R.drawable.mianhua, R.drawable.qiezi
                , R.drawable.baocai, R.drawable.xigua};
        int[] id2 = {R.drawable.yunfu1, R.drawable.yunfu2, R.drawable.yunfu3, R.drawable.yunfu4, R.drawable.yunfu5, R.drawable.yunfu6, R.drawable.yunfu7
                , R.drawable.yunfu8, R.drawable.yunfu9};
        pregPager.setOffscreenPageLimit(5);
        MyPagerAdapter adapter = new MyPagerAdapter(title, msg, getActivity());
        adapter.setId(id);
        adapter.setId2(id2);
        pregPager.setAdapter(adapter);
        adapter.setOnItemViewClickListener(new OnItemViewClickListener() {
            @Override
            public void OnItemView(int position, View view, boolean is) {
                if (is)
                    pregPager.setCurrentItem(position - 1);
                else
                    pregPager.setCurrentItem(position + 1);
            }
        });

    }
}

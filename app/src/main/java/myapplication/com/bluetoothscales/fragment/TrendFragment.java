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

import butterknife.BindView;
import myapplication.com.bluetoothscales.R;
import myapplication.com.bluetoothscales.app.MyApplication;
import myapplication.com.bluetoothscales.base.BaseFragment;
import myapplication.com.bluetoothscales.utils.FragmentEvent;
import myapplication.com.bluetoothscales.utils.SpUtils;

import static com.kitnew.ble.QNData.TYPE_BMI;
import static myapplication.com.bluetoothscales.utils.Constant.ACTION_BLE_NOTIFY_DATA;
import static myapplication.com.bluetoothscales.utils.DateUtil.dayDiffCurr;

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
    @BindView(R.id.baby_tv)
    TextView babyTv;
    @BindView(R.id.msg)
    TextView msg;
    @BindView(R.id.yunfu)
    ImageView yunFu;
    @BindView(R.id.shiwu)
    ImageView shiWu;
    @BindView(R.id.trend_msg)
    TextView trendMsg;

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_BLE_NOTIFY_DATA);
        getActivity().registerReceiver(notifyReceiver, intentFilter);
        if (SpUtils.getInt("type", 1) == 1) {
            title.setText("Workout Trend");
            trendIv.setBackgroundResource(R.drawable.work);
        } else if (SpUtils.getInt("type", 1) == 2) {
            title.setText("Pregnancy Mode");
            setView(MyApplication.newInstance().getQnData());
            trendPregLl.setVisibility(View.VISIBLE);
        } else if (SpUtils.getInt("type", 1) == 3) {
            title.setText("Baby Trend");
            trendBabyLl.setVisibility(View.VISIBLE);
            if (SpUtils.getString("sex", "boy").equals("boy")) {
                trendLl.setBackgroundResource(R.drawable.main_home);
                trendIv.setBackgroundResource(R.drawable.baby_boy);
            } else {
                trendIv.setBackgroundResource(R.drawable.baby);
                trendLl.setBackgroundResource(R.drawable.main_home2);
            }

        }

        pregCb.setOnCheckedChangeListener(this);
        babyCb.setOnCheckedChangeListener(this);
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

        } else if (event.getDistance() == 2) {
            title.setText("Pregnancy Mode");
            trendLl.setBackgroundResource(R.drawable.main_home);
            setView(MyApplication.newInstance().getQnData());
            setPreg();
        } else if (event.getDistance() == 3) {
            title.setText("Baby Trend");
            if (SpUtils.getString("sex", "boy").equals("boy")) {
                trendLl.setBackgroundResource(R.drawable.main_home);
                trendIv.setBackgroundResource(R.drawable.baby_boy);
            } else {
                trendLl.setBackgroundResource(R.drawable.main_home2);
                trendIv.setBackgroundResource(R.drawable.baby);
            }
        }
        trendPregLl.setVisibility(event.getDistance() == 2 ? View.VISIBLE : View.GONE);
        trendBabyLl.setVisibility(event.getDistance() == 3 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (SpUtils.getInt("type", 1) == 3) {
            if (SpUtils.getString("sex", "boy").equals("boy")) {
                trendLl.setBackgroundResource(R.drawable.main_home);
                trendIv.setBackgroundResource(R.drawable.baby_boy);
            } else {
                trendIv.setBackgroundResource(R.drawable.baby);
                trendLl.setBackgroundResource(R.drawable.main_home2);
            }

        } else if (SpUtils.getInt("type", 1) == 2) {
            setView(MyApplication.newInstance().getQnData());
            setPreg();
        }else if (SpUtils.getInt("type", 1) == 1){

        }
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
                babyTv.setVisibility(b ? View.VISIBLE : View.GONE);
                break;
        }
    }

    private void setView(QNData qnData) {
        if (qnData != null) {
            if (qnData.getFloatValue(TYPE_BMI) < 18.5) {
                trendIv.setBackgroundResource(R.drawable.bmi1);
                msg.setText(R.string.preg1);
            } else if (qnData.getFloatValue(TYPE_BMI) < 25) {
                trendIv.setBackgroundResource(R.drawable.bmi2);
                msg.setText(R.string.preg2);
            } else if (qnData.getFloatValue(TYPE_BMI) >= 25) {
                trendIv.setBackgroundResource(R.drawable.bmi3);
                msg.setText(R.string.preg3);
            }
        } else {
            trendIv.setBackgroundResource(R.drawable.bmi1);
        }
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
    private void setPreg(){
        setyunfu((int) (dayDiffCurr(SpUtils.getString("pregancyTime", "2017-01-01")) / 7));
    }
    private void setyunfu(int week) {
        if (week <= 3) {
            yunFu.setImageResource(R.drawable.yunfu1);
            shiWu.setImageResource(R.drawable.putao);
            trendMsg.setText(R.string.trend_preg1);
        } else if (week <= 12) {
            yunFu.setImageResource(R.drawable.yunfu2);
            shiWu.setImageResource(R.drawable.jvzi);
            trendMsg.setText(R.string.trend_preg2);
        } else if (week <= 13) {
            yunFu.setImageResource(R.drawable.yunfu3);
            shiWu.setImageResource(R.drawable.wandou);
            trendMsg.setText(R.string.trend_preg3);
        } else if (week <= 14) {
            yunFu.setImageResource(R.drawable.yunfu4);
            shiWu.setImageResource(R.drawable.ninmeng);
            trendMsg.setText(R.string.trend_preg4);
        } else if (week <= 19) {
            yunFu.setImageResource(R.drawable.yunfu5);
            shiWu.setImageResource(R.drawable.xihongshi);
            trendMsg.setText(R.string.trend_preg5);
        } else if (week <= 27) {
            yunFu.setImageResource(R.drawable.yunfu6);
            shiWu.setImageResource(R.drawable.mianhua);
            trendMsg.setText(R.string.trend_preg6);
        } else if (week <= 28) {
            yunFu.setImageResource(R.drawable.yunfu7);
            shiWu.setImageResource(R.drawable.qiezi);
            trendMsg.setText(R.string.trend_preg7);
        } else if (week <= 30) {
            yunFu.setImageResource(R.drawable.yunfu8);
            shiWu.setImageResource(R.drawable.baocai);
            trendMsg.setText(R.string.trend_preg8);
        } else if (week <= 39) {
            yunFu.setImageResource(R.drawable.yunfu9);
            shiWu.setImageResource(R.drawable.xigua);
            trendMsg.setText(R.string.trend_preg9);
        } else {
            yunFu.setImageResource(R.drawable.yunfu9);
            shiWu.setImageResource(R.drawable.xigua);
            trendMsg.setText(R.string.trend_preg9);
        }
    }
}

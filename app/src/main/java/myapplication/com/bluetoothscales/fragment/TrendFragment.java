package myapplication.com.bluetoothscales.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import myapplication.com.bluetoothscales.R;
import myapplication.com.bluetoothscales.base.BaseFragment;
import myapplication.com.bluetoothscales.utils.FragmentEvent;
import myapplication.com.bluetoothscales.utils.SpUtils;

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

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        if (SpUtils.getInt("type", 1) == 1) {
            title.setText("Workout Trend");
            trendIv.setBackgroundResource(R.drawable.work);

        } else if (SpUtils.getInt("type", 1) == 2) {
            title.setText("Pregnancy Mode");
            trendIv.setBackgroundResource(R.drawable.preg);
            trendPregLl.setVisibility( View.VISIBLE);
        } else if (SpUtils.getInt("type", 1) == 3) {
            title.setText("Baby Trend");
            trendBabyLl.setVisibility( View.VISIBLE);
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
            trendIv.setBackgroundResource(R.drawable.preg);
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
        trendPregLl.setVisibility(event.getDistance() == 2? View.VISIBLE:View.GONE);
        trendBabyLl.setVisibility(event.getDistance() == 3? View.VISIBLE:View.GONE);
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
                trendLl.setBackgroundResource(R.drawable.main_home2);
            }

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);//反注册EventBus


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
}

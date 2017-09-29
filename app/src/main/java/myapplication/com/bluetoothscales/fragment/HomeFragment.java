package myapplication.com.bluetoothscales.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kitnew.ble.QNData;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import myapplication.com.bluetoothscales.R;
import myapplication.com.bluetoothscales.activity.BabyActivity;
import myapplication.com.bluetoothscales.activity.PregActivity;
import myapplication.com.bluetoothscales.activity.WorkoutActivity;
import myapplication.com.bluetoothscales.app.MyApplication;
import myapplication.com.bluetoothscales.base.BaseFragment;
import myapplication.com.bluetoothscales.utils.FragmentEvent;
import myapplication.com.bluetoothscales.utils.SpUtils;

import static com.kitnew.ble.QNData.TYPE_BMI;
import static com.kitnew.ble.QNData.TYPE_BODYFAT;
import static com.kitnew.ble.QNData.TYPE_BONE;
import static com.kitnew.ble.QNData.TYPE_SKELETAL_MUSCLE;


public class HomeFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.home_weight)
    TextView homeWeight;
    @BindView(R.id.home_lbs)
    TextView homeLbs;
    @BindView(R.id.home_bmi)
    TextView homeBmi;
    @BindView(R.id.home_rou)
    TextView homeRou;
    @BindView(R.id.home_jirou)
    TextView homeJirou;
    @BindView(R.id.home_guge)
    TextView homeGuge;
    @BindView(R.id.home_mode)
    TextView homeMode;
    @BindView(R.id.home_ll)
    LinearLayout homeLl;
    @BindView(R.id.home_time)
    TextView homeTime;
    PopupWindow window;
    PopupWindow window2;

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        final View popupView = getActivity().getLayoutInflater().inflate(R.layout.popupwindow, null);
        final View popupView2 = getActivity().getLayoutInflater().inflate(R.layout.popupwindow2, null);
        popupView.findViewById(R.id.preg_pop).setOnClickListener(this);
        popupView.findViewById(R.id.baby_pop).setOnClickListener(this);
        popupView.findViewById(R.id.work_pop).setOnClickListener(this);
        popupView2.findViewById(R.id.preg_lbs).setOnClickListener(this);
        popupView2.findViewById(R.id.preg_kg).setOnClickListener(this);
        popupView2.findViewById(R.id.preg_st).setOnClickListener(this);
        if (SpUtils.getInt("type", 0) == 1)
            homeMode.setText("Workout Mode");
        else if (SpUtils.getInt("type", 0) == 2)
            homeMode.setText("Pregnancy Mode");
        else if (SpUtils.getInt("type", 0) == 3)
            homeMode.setText("Baby Mode");
        else
            homeMode.setText("Select Mode");
        homeLl.post(new Runnable() {
            @Override
            public void run() {
                window = new PopupWindow(popupView, homeLl.getWidth(), 350);
                window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6E199782")));
                window.setFocusable(true);
                window.setOutsideTouchable(true);
                window.update();
                window2 = new PopupWindow(popupView2, homeLbs.getWidth(), 350);
                window2.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6E199782")));
                window2.setFocusable(true);
                window2.setOutsideTouchable(true);
                window2.update();
            }
        });
        homeLbs.setText(SpUtils.getString("unit", "LBS"));

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }


    @OnClick(value = {R.id.home_mode, R.id.home_lbs})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_mode:
                window.showAsDropDown(homeLl, 0, 20);
                break;
            case R.id.home_lbs:
                window2.showAsDropDown(homeLbs, 0, 20);
                break;
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.baby_pop:
                homeMode.setText("Baby Mode");
                EventBus.getDefault().post(new FragmentEvent(3));
                SpUtils.putInt("type", 3);
                startActivity(new Intent(getActivity(), BabyActivity.class));
                break;
            case R.id.work_pop:
                homeMode.setText("Workout Mode");
                EventBus.getDefault().post(new FragmentEvent(1));
                SpUtils.putInt("type", 1);
                startActivity(new Intent(getActivity(), WorkoutActivity.class));
                break;
            case R.id.preg_pop:
                homeMode.setText("Pregnancy Mode");
                EventBus.getDefault().post(new FragmentEvent(2));
                SpUtils.putInt("type", 2);
                startActivity(new Intent(getActivity(), PregActivity.class));
                break;
            case R.id.preg_lbs:
                homeLbs.setText("LBS");
                SpUtils.putString("unit", "LBS");
                break;
            case R.id.preg_kg:
                homeLbs.setText("Kg");
                SpUtils.putString("unit", "Kg");
                break;
            case R.id.preg_st:
                homeLbs.setText("ST");
                SpUtils.putString("unit", "ST");
                break;
        }
        window.dismiss();
        window2.dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.newInstance().isMeasure && SpUtils.getInt("type", 0) != 3) {
            QNData qnData = MyApplication.newInstance().getQnData();
            homeWeight.setText(String.valueOf(qnData.getWeight()));
            homeBmi.setText(String.valueOf(qnData.getFloatValue(TYPE_BMI)));
            homeRou.setText(String.valueOf(qnData.getFloatValue(TYPE_BODYFAT)));
            homeJirou.setText(String.valueOf(qnData.getFloatValue(TYPE_SKELETAL_MUSCLE)));
            homeGuge.setText(String.valueOf(qnData.getFloatValue(TYPE_BONE)));
        }
        homeTime.setText("Detection time: " + SpUtils.getString("HomeTime", "00:00 01/01/2017"));
    }
}

package myapplication.com.bluetoothscales.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import myapplication.com.bluetoothscales.R;
import myapplication.com.bluetoothscales.activity.BabyActivity;
import myapplication.com.bluetoothscales.activity.PregActivity;
import myapplication.com.bluetoothscales.activity.WorkoutActivity;
import myapplication.com.bluetoothscales.base.BaseFragment;
import myapplication.com.bluetoothscales.utils.FragmentEvent;
import myapplication.com.bluetoothscales.utils.SpUtils;


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
    PopupWindow window;

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        View popupView = getActivity().getLayoutInflater().inflate(R.layout.popupwindow, null);
        popupView.findViewById(R.id.preg_pop).setOnClickListener(this);
        popupView.findViewById(R.id.baby_pop).setOnClickListener(this);
        popupView.findViewById(R.id.work_pop).setOnClickListener(this);
        window = new PopupWindow(popupView, 300, 350);
        window.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#6E199782")));
        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.update();
        if (SpUtils.getInt("type", 1) == 1)
            homeMode.setText("Workout Mode");
        else if (SpUtils.getInt("type", 1) == 2)
            homeMode.setText("Pregnancy Mode");
        else if (SpUtils.getInt("type", 1) == 3)
            homeMode.setText("Baby Mode");
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }


    @OnClick(value = {R.id.home_mode, R.id.home_main})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_mode:
                window.showAsDropDown(homeMode, 0, 20);
                break;
            case R.id.home_main:
                if (SpUtils.getInt("type", 1) == 1)
                    startActivity(new Intent(getActivity(), WorkoutActivity.class));
                else if (SpUtils.getInt("type", 1) == 2)
                    startActivity(new Intent(getActivity(), PregActivity.class));
                else if (SpUtils.getInt("type", 1) == 3)
                    startActivity(new Intent(getActivity(), BabyActivity.class));
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
                break;
            case R.id.work_pop:
                homeMode.setText("Workout Mode");
                EventBus.getDefault().post(new FragmentEvent(1));
                SpUtils.putInt("type", 1);
                break;
            case R.id.preg_pop:
                homeMode.setText("Pregnancy Mode");
                EventBus.getDefault().post(new FragmentEvent(2));
                SpUtils.putInt("type", 2);
                break;
        }
        window.dismiss();
    }
}

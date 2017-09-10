package myapplication.com.bluetoothscales.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import myapplication.com.bluetoothscales.R;
import myapplication.com.bluetoothscales.activity.WorkoutActivity;
import myapplication.com.bluetoothscales.base.BaseFragment;


public class HomeFragment extends BaseFragment {

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


    @Override
    protected void initData(View layout, Bundle savedInstanceState) {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }


    @OnClick(R.id.home_mode)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), WorkoutActivity.class));
    }
}

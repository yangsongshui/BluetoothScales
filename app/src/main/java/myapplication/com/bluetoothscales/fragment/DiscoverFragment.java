package myapplication.com.bluetoothscales.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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


public class DiscoverFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.novice_bt)
    CheckBox noviceBt;
    @BindView(R.id.novice_msg)
    LinearLayout noviceMsg;
    @BindView(R.id.muscle_bt)
    CheckBox muscleBt;
    @BindView(R.id.muscle_msg)
    LinearLayout muscleMsg;
    @BindView(R.id.exercise_bt)
    CheckBox exerciseBt;
    @BindView(R.id.exercise_msg)
    LinearLayout exerciseMsg;
    @BindView(R.id.tips_bt)
    CheckBox tipsBt;
    @BindView(R.id.discover_tips)
    LinearLayout discoverTips;
    @BindView(R.id.discover_tips2)
    LinearLayout discoverTips2;
    @BindView(R.id.discover_tips3)
    LinearLayout discoverTips3;
    @BindView(R.id.list_bt)
    CheckBox listBt;
    @BindView(R.id.discover_list)
    LinearLayout discoverList;
    @BindView(R.id.discover_list2)
    LinearLayout discoverList2;
    @BindView(R.id.discover_ll)
    LinearLayout discoverLl;
    @BindView(R.id.discover_title)
    TextView discoverTitle;
    int mode = 0;
    boolean sex = false;

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        mode = SpUtils.getInt("type", 1);
        EventBus.getDefault().register(this);
        noviceBt.setOnCheckedChangeListener(this);
        listBt.setOnCheckedChangeListener(this);
        tipsBt.setOnCheckedChangeListener(this);
        exerciseBt.setOnCheckedChangeListener(this);
        muscleBt.setOnCheckedChangeListener(this);
        initView();

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_discover;
    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.exercise_bt:
                exerciseMsg.setVisibility(b ? View.VISIBLE : View.GONE);
                break;
            case R.id.muscle_bt:
                muscleMsg.setVisibility(b ? View.VISIBLE : View.GONE);
                break;
            case R.id.novice_bt:
                noviceMsg.setVisibility(b ? View.VISIBLE : View.GONE);
                break;
            case R.id.tips_bt:
                if (mode == 2) {
                    discoverTips.setVisibility(b ? View.VISIBLE : View.GONE);
                } else if (mode == 3) {
                    discoverTips2.setVisibility(b ? View.VISIBLE : View.GONE);
                } else if (mode == 1) {
                    discoverTips3.setVisibility(b ? View.VISIBLE : View.GONE);
                }
                break;
            case R.id.list_bt:
                if (mode == 2) {
                    discoverList.setVisibility(b ? View.VISIBLE : View.GONE);
                } else if (mode == 3) {
                    discoverList2.setVisibility(b ? View.VISIBLE : View.GONE);
                }
                break;
        }
    }

    private void initView() {

        noviceBt.setVisibility(mode == 1 ? View.VISIBLE : View.GONE);
        muscleBt.setVisibility(mode == 1 ? View.VISIBLE : View.GONE);
        exerciseBt.setVisibility(mode == 1 ? View.VISIBLE : View.GONE);
        listBt.setVisibility(mode == 1 ? View.GONE : View.VISIBLE);
        if (mode == 3) {
            tipsBt.setText("Diet tips（for baby）");
            listBt.setText("Parenting tips");
            discoverTitle.setText("Baby Discover");
        } else if (mode == 2) {
            tipsBt.setText("Diet tips");
            listBt.setText("Preparation list ");
            discoverTitle.setText("Pregnancy Discover");
        }else if (mode == 1){
            discoverTitle.setText("Workout Discover");
        }
        discoverLl.setBackground(SpUtils.getString("sex", "boy").equals("girl") ? getResources().getDrawable(R.drawable.main_home2) : getResources().getDrawable(R.drawable.main_home));
    }

    @SuppressLint("StringFormatMatches")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(FragmentEvent event) {
        mode =event.getDistance();
        initView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }
}

package myapplication.com.bluetoothscales.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import butterknife.BindView;
import myapplication.com.bluetoothscales.R;
import myapplication.com.bluetoothscales.base.BaseFragment;


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
    int mode = 0;
    boolean sex = false;

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
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
                if (mode == 0) {
                    discoverTips.setVisibility(b ? View.VISIBLE : View.GONE);
                } else if (mode == 1) {
                    discoverTips2.setVisibility(b ? View.VISIBLE : View.GONE);
                } else if (mode == 3) {
                    discoverTips3.setVisibility(b ? View.VISIBLE : View.GONE);
                }
                break;
            case R.id.list_bt:
                if (mode == 0) {
                    discoverList.setVisibility(b ? View.VISIBLE : View.GONE);
                } else if (mode == 1) {
                    discoverList2.setVisibility(b ? View.VISIBLE : View.GONE);
                }
                break;
        }
    }

    private void initView() {
        noviceBt.setVisibility(mode == 3 ? View.VISIBLE : View.GONE);
        muscleBt.setVisibility(mode == 3 ? View.VISIBLE : View.GONE);
        exerciseBt.setVisibility(mode == 3 ? View.VISIBLE : View.GONE);
        listBt.setVisibility(mode == 3 ? View.GONE : View.VISIBLE);
        if (mode == 2) {
            tipsBt.setText("Diet tips（for baby）");
            listBt.setText("Parenting tips");
        } else if (mode == 1 || mode == 3) {
            tipsBt.setText("Diet tips");
            listBt.setText("Preparation list ");
        }
        discoverLl.setBackground(sex?getResources().getDrawable(R.drawable.main_home2):getResources().getDrawable(R.drawable.main_home));
    }
}

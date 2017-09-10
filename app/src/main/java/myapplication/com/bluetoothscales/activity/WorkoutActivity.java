package myapplication.com.bluetoothscales.activity;

import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import myapplication.com.bluetoothscales.R;
import myapplication.com.bluetoothscales.base.BaseActivity;
import myapplication.com.bluetoothscales.utils.Toastor;

public class WorkoutActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.work_edit)
    TextView workEdit;
    @BindView(R.id.work_msg)
    TextView workMsg;
    @BindView(R.id.work_weight)
    TextView workWeight;
    @BindView(R.id.work_weight_ll)
    LinearLayout workWeightLl;
    @BindView(R.id.work_msg2)
    TextView workMsg2;
    @BindView(R.id.work_height)
    TextView workHeight;
    @BindView(R.id.work_height_ll)
    LinearLayout workHeightLl;
    @BindView(R.id.work_msg3)
    TextView workMsg3;
    @BindView(R.id.work_bmi)
    TextView workBmi;
    @BindView(R.id.work_bmi_ll)
    LinearLayout workBmiLl;
    @BindView(R.id.work_msg4)
    TextView workMsg4;
    @BindView(R.id.work_mucs)
    TextView workMucs;
    @BindView(R.id.work_mucs_ll)
    LinearLayout workMucsLl;
    @BindView(R.id.work_msg5)
    TextView workMsg5;
    @BindView(R.id.work_fat)
    TextView workFat;
    @BindView(R.id.work_fat_ll)
    LinearLayout workFatLl;
    @BindView(R.id.work_et)
    EditText workEt;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.work_next)
    TextView workNext;
    @BindView(R.id.edit_ll)
    LinearLayout editLl;
    @BindView(R.id.goal_edit)
    TextView goalEdit;
    @BindView(R.id.work_msg6)
    TextView workMsg6;
    @BindView(R.id.work_target)
    TextView workTarget;
    @BindView(R.id.work_target_ll)
    LinearLayout workTargetLl;
    @BindView(R.id.work_msg7)
    TextView workMsg7;
    @BindView(R.id.work_duration)
    TextView workDuration;
    @BindView(R.id.work_duration_ll)
    LinearLayout workDurationLl;
    @BindView(R.id.work_et2)
    EditText workEt2;
    @BindView(R.id.tab_layout2)
    TabLayout tabLayout2;
    @BindView(R.id.work_next2)
    TextView workNext2;
    @BindView(R.id.edit_ll2)
    LinearLayout editLl2;
    @BindView(R.id.work_activity)
    CheckBox workActivity;
    @BindView(R.id.work_recommended)
    CheckBox workRecommended;
    @BindView(R.id.discover_list2)
    LinearLayout discoverList2;
    @BindView(R.id.setting_edit)
    TextView settingEdit;
    @BindView(R.id.work_et3)
    EditText workEt3;
    @BindView(R.id.tab_layout3)
    TabLayout tabLayout3;
    @BindView(R.id.work_next3)
    TextView workNext3;
    @BindView(R.id.edit_ll3)
    LinearLayout editLl3;
    @BindView(R.id.activity_workout)
    LinearLayout activityWorkout;
    Toastor toastor;
    int postion = 0;
    int indext = 1;

    @Override
    protected int getContentView() {
        return R.layout.activity_workout;
    }

    @Override
    protected void init() {
        toastor = new Toastor(this);
        tabLayout.addTab(tabLayout.newTab().setText("Weight"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (postion < 3)
                    postion = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout2.addTab(tabLayout2.newTab().setText("Weight"));
        tabLayout2.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (postion < 3)
                    postion = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout3.addTab(tabLayout3.newTab().setText("hour"));
        tabLayout3.addTab(tabLayout3.newTab().setText("min"));
        tabLayout3.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (postion < 3)
                    postion = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        workActivity.setOnCheckedChangeListener(this);
        workRecommended.setOnCheckedChangeListener(this);
    }


    @OnClick({R.id.baby_back, R.id.work_edit, R.id.work_weight_ll, R.id.work_height_ll, R.id.work_bmi_ll, R.id.work_mucs_ll, R.id.work_fat_ll, R.id.work_next,
            R.id.goal_edit, R.id.work_target_ll, R.id.work_duration_ll, R.id.work_next2, R.id.discover_list2, R.id.work_next3, R.id.setting_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.baby_back:
                break;
            case R.id.work_edit:
                editLl.setVisibility(View.VISIBLE);
                workEdit.setVisibility(View.GONE);
                break;
            case R.id.work_weight_ll:
                break;
            case R.id.work_height_ll:
                break;
            case R.id.work_bmi_ll:
                break;
            case R.id.work_mucs_ll:
                break;
            case R.id.work_fat_ll:
                break;
            case R.id.work_next:
                break;
            case R.id.goal_edit:
                editLl2.setVisibility(View.VISIBLE);
                goalEdit.setVisibility(View.GONE);
                break;
            case R.id.work_target_ll:
                break;
            case R.id.work_duration_ll:
                break;
            case R.id.work_next2:
                break;
            case R.id.discover_list2:
                break;
            case R.id.work_next3:
                break;
            case R.id.setting_edit:
                editLl3.setVisibility(View.VISIBLE);
                settingEdit.setVisibility(View.GONE);
                break;

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.work_recommended:
                discoverList2.setVisibility(b ? View.VISIBLE : View.GONE);
                break;
            case R.id.work_activity:

                break;
        }
    }
}

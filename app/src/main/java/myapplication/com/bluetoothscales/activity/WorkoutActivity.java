package myapplication.com.bluetoothscales.activity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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
    @BindView(R.id.work_time)
    TextView workTiem;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    Toastor toastor;
    int postion = 0;
    int indext = 0;
    int place = 0;

    @Override
    protected int getContentView() {
        return R.layout.activity_workout;
    }

    @Override
    protected void init() {
        toastor = new Toastor(this);
        tabLayout.addTab(tabLayout.newTab().setText("Weight"));
        tabLayout2.addTab(tabLayout2.newTab().setText("Weight"));

        tabLayout3.addTab(tabLayout3.newTab().setText("hour"));
        tabLayout3.addTab(tabLayout3.newTab().setText("min"));
        tabLayout3.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                place = tab.getPosition();
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
            R.id.goal_edit, R.id.work_target_ll, R.id.work_duration_ll, R.id.work_next2, R.id.work_next3, R.id.setting_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.baby_back:
                finish();
                break;
            case R.id.work_edit:
                indext = 1;
                editLl.setVisibility(View.VISIBLE);
                workEdit.setVisibility(View.GONE);
                setText();
                setData();
                break;
            case R.id.work_weight_ll:
                if (indext != 0) {
                    indext = 1;
                    setText();
                    setData();
                }
                break;
            case R.id.work_height_ll:
                if (indext != 0) {
                    indext = 2;
                    setText();
                    setData();
                }
                break;
            case R.id.work_bmi_ll:
                if (indext != 0) {
                    indext = 3;
                    setText();
                    setData();
                }
                break;
            case R.id.work_mucs_ll:
                if (indext != 0) {
                    indext = 4;
                    setText();
                    setData();
                }
                break;
            case R.id.work_fat_ll:
                if (indext != 0) {
                    indext = 5;
                    setText();
                    setData();
                }
                break;
            case R.id.work_next:
                if (indext < 5) {
                    setView();
                } else {
                    String msg = workEt.getText().toString().trim();
                    if (!msg.equals("")) {
                        workFat.setText(msg);
                        editLl.setVisibility(View.GONE);
                        workEdit.setVisibility(View.VISIBLE);
                        indext = 0;
                        workEt.setText("");
                        setText();
                    } else {
                        toastor.showSingletonToast("输入内容不能为空");
                    }

                }
                break;
            case R.id.goal_edit:
                postion = 1;
                editLl2.setVisibility(View.VISIBLE);
                goalEdit.setVisibility(View.GONE);
                setText2();
                workNext2.setText("Next");
                break;
            case R.id.work_target_ll:
                if (postion != 0) {
                    postion = 1;
                    setText2();
                    workNext2.setText("Next");
                    workEt2.setText("");
                }
                break;
            case R.id.work_duration_ll:
                if (postion != 0) {
                    postion = 2;
                    setText2();
                    workNext2.setText("Commit");
                    workEt2.setText("");
                }
                break;
            case R.id.work_next2:
                String msg = workEt2.getText().toString().trim();
                if (!msg.equals("")) {
                    if (Integer.parseInt(msg) <= 250) {
                        if (postion == 1) {
                            workTarget.setText(msg + "kg");
                            postion++;
                            setText2();
                            workNext2.setText("Commit");
                        } else {
                            postion = 0;
                            workDuration.setText(msg + "Weeks");
                            setText2();
                            editLl2.setVisibility(View.GONE);
                            goalEdit.setVisibility(View.VISIBLE);
                        }

                        workEt2.setText("");
                    } else
                        toastor.showSingletonToast("请输入正确体重");
                } else
                    toastor.showSingletonToast("输入内容不能为空");
                break;

            case R.id.work_next3:
                String msg2 = workEt3.getText().toString().trim();
                StringBuilder time = new StringBuilder(workTiem.getText().toString().trim());
                if (!msg2.equals("")) {
                    if (place == 0) {
                        if (Integer.parseInt(msg2) <= 24) {
                            place++;
                            tabLayout3.getTabAt(place).select();
                            workTiem.setText(time.replace(0, 2, msg2));
                            workEt3.setText("");
                        } else {
                            toastor.showSingletonToast("请输入正确小时");
                        }
                    } else if (place == 1) {
                        if (Integer.parseInt(msg2) <= 60) {
                            place = 0;
                            workTiem.setText(time.replace(3, 5, msg2));
                            editLl3.setVisibility(View.GONE);
                            settingEdit.setVisibility(View.VISIBLE);
                            workTiem.setVisibility(View.GONE);
                            workEt3.setText("");
                        } else {
                            toastor.showSingletonToast("请输入正确分钟");
                        }
                    }
                } else {
                    toastor.showSingletonToast("输入内容不能为空");
                }


                break;
            case R.id.setting_edit:
                place = 0;
                editLl3.setVisibility(View.VISIBLE);
                settingEdit.setVisibility(View.GONE);
                workTiem.setVisibility(View.VISIBLE);
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
                break;

        }
    }

    private void setData() {
        tabLayout.removeAllTabs();
        switch (indext) {
            case 1:
                tabLayout.addTab(tabLayout.newTab().setText("Weight"));

                break;
            case 2:

                tabLayout.addTab(tabLayout.newTab().setText("Height"));
                break;
            case 3:
                tabLayout.removeAllTabs();
                tabLayout.addTab(tabLayout.newTab().setText("BMI"));
                break;
            case 4:

                tabLayout.addTab(tabLayout.newTab().setText("Muscle"));
                break;
            case 5:

                tabLayout.addTab(tabLayout.newTab().setText("Fat"));

                break;

        }
        workNext.setText(indext == 5 ? "Commit" : "Next");
    }

    private void setView() {
        String msg = workEt.getText().toString().trim();

        if (!msg.equals("")) {
            switch (indext) {
                case 1:
                    if (Integer.parseInt(msg) <= 250)
                        workWeight.setText(msg + "kg");
                    else {
                        toastor.showSingletonToast("请输入正确体重");
                        return;
                    }
                    break;
                case 2:
                    if (Integer.parseInt(msg) <= 250)
                        workHeight.setText(msg + "cm");
                    else {
                        toastor.showSingletonToast("请输入正确身高");
                        return;
                    }
                    break;
                case 3:
                    workBmi.setText(msg);
                    break;
                case 4:
                    workMucs.setText(msg);
                    break;


            }
            indext++;
            setText();
            setData();

        } else {
            toastor.showSingletonToast("输入内容不能为空");
        }
        workEt.setText("");
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

    @SuppressLint("NewApi")
    private void setText() {
        workMsg.setTextColor(getColor(indext == 1 ? R.color.grey : R.color.white));
        workWeight.setTextColor(getColor(indext == 1 ? R.color.grey : R.color.white));
        workMsg2.setTextColor(getColor(indext == 2 ? R.color.grey : R.color.white));
        workHeight.setTextColor(getColor(indext == 2 ? R.color.grey : R.color.white));
        workMsg3.setTextColor(getColor(indext == 3 ? R.color.grey : R.color.white));
        workBmi.setTextColor(getColor(indext == 3 ? R.color.grey : R.color.white));
        workMsg4.setTextColor(getColor(indext == 4 ? R.color.grey : R.color.white));
        workMucs.setTextColor(getColor(indext == 4 ? R.color.grey : R.color.white));
        workMsg5.setTextColor(getColor(indext == 5 ? R.color.grey : R.color.white));
        workFat.setTextColor(getColor(indext == 5 ? R.color.grey : R.color.white));
    }

    @SuppressLint("NewApi")
    private void setText2() {
        workMsg6.setTextColor(getColor(postion == 1 ? R.color.grey : R.color.white));
        workTarget.setTextColor(getColor(postion == 1 ? R.color.grey : R.color.white));
        workMsg7.setTextColor(getColor(postion == 2 ? R.color.grey : R.color.white));
        workDuration.setTextColor(getColor(postion == 2 ? R.color.grey : R.color.white));

    }
}

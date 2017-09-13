package myapplication.com.bluetoothscales.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kitnew.ble.QNData;

import butterknife.BindView;
import butterknife.OnClick;
import myapplication.com.bluetoothscales.R;
import myapplication.com.bluetoothscales.app.MyApplication;
import myapplication.com.bluetoothscales.base.BaseActivity;
import myapplication.com.bluetoothscales.utils.SpUtils;
import myapplication.com.bluetoothscales.utils.Toastor;

import static myapplication.com.bluetoothscales.utils.Constant.ACTION_BLE_NOTIFY_DATA;

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
    @BindView(R.id.work_rg)
    RadioGroup WorkRg;
    Toastor toastor;
    int postion = 0;
    int indext = 0;
    int place = 0;
    String unit = "";
int[] id={R.id.work_xingzou,R.id.work_qixing,R.id.work_paobu,R.id.work_youyong,R.id.work_yangwoqizuo,
        R.id.work_jvzhong,R.id.work_fuwocheng,R.id.work_shendun,R.id.work_pashan,R.id.work_ticoa};
    @Override
    protected int getContentView() {
        return R.layout.activity_workout;
    }

    @Override
    protected void init() {
        unit = SpUtils.getString("unit", "LBS");
        toastor = new Toastor(this);
        workHeight.setText(SpUtils.getString("workHeight", "--") + "cm");
        workTarget.setText(SpUtils.getString("workTarget", "--") + unit);
        workDuration.setText(SpUtils.getString("workDuration", "--") + "weeks");
        workTiem.setText(SpUtils.getString("workTiem", "00:00"));
        WorkRg.check( id[SpUtils.getInt("workType",0)]);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_BLE_NOTIFY_DATA);
        registerReceiver(notifyReceiver, intentFilter);
        tabLayout.addTab(tabLayout.newTab().setText("Height"));
        tabLayout2.addTab(tabLayout2.newTab().setText("Weight"));

        tabLayout3.addTab(tabLayout3.newTab().setText("hour"));
        tabLayout3.addTab(tabLayout3.newTab().setText("min"));
        workNext.setText("Commit");
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
        WorkRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.work_xingzou:
                        SpUtils.putInt("workType",0);
                        break;
                    case R.id.work_qixing:
                        SpUtils.putInt("workType",1);
                        break;
                    case R.id.work_paobu:
                        SpUtils.putInt("workType",2);
                        break;
                    case R.id.work_youyong:
                        SpUtils.putInt("workType",3);
                        break;
                    case R.id.work_yangwoqizuo:
                        SpUtils.putInt("workType",4);
                        break;
                    case R.id.work_jvzhong:
                        SpUtils.putInt("workType",5);
                        break;
                    case R.id.work_fuwocheng:
                        SpUtils.putInt("workType",6);
                        break;
                    case R.id.work_shendun:
                        SpUtils.putInt("workType",7);
                        break;
                    case R.id.work_pashan:
                        SpUtils.putInt("workType",8);
                        break;
                    case R.id.work_ticoa:
                        SpUtils.putInt("workType",9);
                        break;
                }
            }
        });
    }

    boolean isData = false;

    @OnClick({R.id.baby_back, R.id.work_edit, R.id.work_next,
            R.id.goal_edit, R.id.work_target_ll, R.id.work_duration_ll,
            R.id.work_next2, R.id.work_next3, R.id.setting_edit, R.id.work_measure})
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

                break;

            case R.id.work_next:

                setView();

                break;
            case R.id.work_measure:

                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Hint");
                dialog.setMessage("Please stand on the electronic scale");
                dialog.show();
                isData = true;
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
                            workTarget.setText(msg + unit);
                            postion++;
                            setText2();
                            SpUtils.putString("workTarget", msg);
                            workNext2.setText("Commit");
                        } else {
                            postion = 0;
                            workDuration.setText(msg + "weeks");
                            SpUtils.putString("workDuration", msg);
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
                            SpUtils.putString("workTiem", workTiem.getText().toString());
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


    private void setView() {
        String msg = workEt.getText().toString().trim();

        if (!msg.equals("")) {
            if (Integer.parseInt(msg) <= 250) {
                editLl.setVisibility(View.GONE);
                workEdit.setVisibility(View.VISIBLE);
                workHeight.setText(msg + "cm");
                indext = 0;
                setText();
                SpUtils.putString("workHeight", msg);
            } else {
                toastor.showSingletonToast("请输入正确身高");
                return;
            }

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
                WorkRg.setVisibility(b?View.VISIBLE:View.GONE);
                break;
        }
    }

    private void setText() {
        workMsg2.setTextColor(getResources().getColor(indext == 1 ? R.color.grey : R.color.white));
        workHeight.setTextColor(getResources().getColor(indext == 1 ? R.color.grey : R.color.white));

    }

    private void setText2() {
        workMsg6.setTextColor(getResources().getColor(postion == 1 ? R.color.grey : R.color.white));
        workTarget.setTextColor(getResources().getColor(postion == 1 ? R.color.grey : R.color.white));
        workMsg7.setTextColor(getResources().getColor(postion == 2 ? R.color.grey : R.color.white));
        workDuration.setTextColor(getResources().getColor(postion == 2 ? R.color.grey : R.color.white));

    }

    private BroadcastReceiver notifyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Log.e("BLEService收到设备信息广播", intent.getAction());
            if (ACTION_BLE_NOTIFY_DATA.equals(intent.getAction())) {
                if (isData) {
                    QNData qnData = MyApplication.newInstance().getQnData();
                    workWeight.setText(String.valueOf(qnData.getWeight() + unit));
                    workBmi.setText(String.valueOf(qnData.getFloatValue(QNData.TYPE_BMI)));
                    workMucs.setText(String.valueOf(qnData.getFloatValue(QNData.TYPE_SKELETAL_MUSCLE)));
                    workFat.setText(String.valueOf(qnData.getFloatValue(QNData.TYPE_BODYFAT)));
                    isData = false;
                    MyApplication.newInstance().isMeasure = true;
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(notifyReceiver);
    }
}

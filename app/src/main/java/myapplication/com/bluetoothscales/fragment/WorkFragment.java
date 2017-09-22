package myapplication.com.bluetoothscales.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
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
import myapplication.com.bluetoothscales.base.BaseFragment;
import myapplication.com.bluetoothscales.utils.SpUtils;
import myapplication.com.bluetoothscales.utils.Toastor;

import static myapplication.com.bluetoothscales.utils.Constant.ACTION_BLE_NOTIFY_DATA;


public class WorkFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {
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
    @BindView(R.id.goal_edit)
    TextView goalEdit;
    @BindView(R.id.work_msg6)
    TextView workMsg6;
    @BindView(R.id.work_target)
    TextView workTarget;;
    @BindView(R.id.work_msg7)
    TextView workMsg7;
    @BindView(R.id.work_duration)
    TextView workDuration;

    @BindView(R.id.work_activity)
    CheckBox workActivity;
    @BindView(R.id.discover_list2)
    TextView discoverList2;
    @BindView(R.id.setting_edit)
    TextView settingEdit;
    @BindView(R.id.edit_ll3)
    LinearLayout editLl3;
    @BindView(R.id.work_time)
    TextView workTiem;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.work_rg)
    RadioGroup WorkRg;
    @BindView(R.id.work_year)
    EditText workYear;
    @BindView(R.id.work_month)
    EditText workMonth;
    Toastor toastor;
    int postion = 0;
    int indext = 0;
    String unit = "";
    AlertDialog dialog;
    int[] id = {R.id.work_xingzou, R.id.work_qixing, R.id.work_paobu, R.id.work_youyong, R.id.work_yangwoqizuo,
            R.id.work_jvzhong, R.id.work_fuwocheng, R.id.work_shendun, R.id.work_pashan, R.id.work_ticoa};

    int[] sid = {R.string.work_msg1, R.string.work_msg2, R.string.work_msg3, R.string.work_msg4, R.string.work_msg5,
            R.string.work_msg6, R.string.work_msg6, R.string.work_msg7, R.string.work_msg8, R.string.work_msg9,};

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        unit = SpUtils.getString("unit", "LBS");
        toastor = new Toastor(getActivity());
        workHeight.setText(SpUtils.getString("workHeight", "--") + "cm");
        workTarget.setText(SpUtils.getString("workTarget", "--") + unit);
        workDuration.setText(SpUtils.getString("workDuration", "--") + "weeks");
        workTiem.setText(SpUtils.getString("workTiem", "00:00"));
        WorkRg.check(id[SpUtils.getInt("workType", 0)]);
        discoverList2.setText(sid[SpUtils.getInt("workType", 0)]);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_BLE_NOTIFY_DATA);
        getActivity().registerReceiver(notifyReceiver, intentFilter);

        workActivity.setOnCheckedChangeListener(this);

        WorkRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.work_xingzou:
                        SpUtils.putInt("workType", 0);
                        discoverList2.setText(getString(R.string.work_msg1));
                        break;
                    case R.id.work_qixing:
                        SpUtils.putInt("workType", 1);
                        discoverList2.setText(getString(R.string.work_msg2));
                        break;
                    case R.id.work_paobu:
                        SpUtils.putInt("workType", 2);
                        discoverList2.setText(getString(R.string.work_msg3));
                        break;
                    case R.id.work_youyong:
                        SpUtils.putInt("workType", 3);
                        discoverList2.setText(getString(R.string.work_msg4));
                        break;
                    case R.id.work_yangwoqizuo:
                        SpUtils.putInt("workType", 4);
                        discoverList2.setText(getString(R.string.work_msg5));
                        break;
                    case R.id.work_jvzhong:
                        SpUtils.putInt("workType", 5);
                        break;
                    case R.id.work_fuwocheng:
                        SpUtils.putInt("workType", 6);
                        discoverList2.setText(getString(R.string.work_msg6));
                        break;
                    case R.id.work_shendun:
                        SpUtils.putInt("workType", 7);
                        discoverList2.setText(getString(R.string.work_msg7));
                        break;
                    case R.id.work_pashan:
                        SpUtils.putInt("workType", 8);
                        discoverList2.setText(getString(R.string.work_msg8));
                        break;
                    case R.id.work_ticoa:
                        SpUtils.putInt("workType", 9);
                        discoverList2.setText(getString(R.string.work_msg9));
                        break;
                }
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Hint");
        builder.setMessage("Please stand on the electronic scale");
        dialog = builder.create();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_work;
    }

    boolean isData = false;

    @OnClick({R.id.baby_back, R.id.work_edit,
            R.id.goal_edit, R.id.work_target_ll, R.id.work_duration_ll,
             R.id.work_next3, R.id.setting_edit, R.id.work_measure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.baby_back:
                getActivity().finish();
                break;
            case R.id.work_edit:
                indext = 1;
                //editLl.setVisibility(View.VISIBLE);
                workEdit.setVisibility(View.GONE);
                setText();
                break;
            case R.id.work_measure:

                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Hint");
                dialog.setMessage("Please stand on the electronic scale");
                dialog.show();
                isData = true;
                break;

            case R.id.goal_edit:
                postion = 1;
                //editLl2.setVisibility(View.VISIBLE);
                goalEdit.setVisibility(View.GONE);
                setText2();

                break;
            case R.id.work_target_ll:
                if (postion != 0) {
                    postion = 1;
                    setText2();

                }
                break;
            case R.id.work_duration_ll:
                if (postion != 0) {
                    postion = 2;
                    setText2();

                }
                break;

            case R.id.work_next3:
                String hour = workYear.getText().toString().trim();
                String min = workMonth.getText().toString().trim();
                if (!hour.equals("") && !min.equals("")) {
                    if (Integer.parseInt(hour) <= 24 && Integer.parseInt(min) <= 60) {
                        workTiem.setText(hour + ":" + min);
                        editLl3.setVisibility(View.GONE);
                        settingEdit.setVisibility(View.VISIBLE);
                        workTiem.setVisibility(View.GONE);
                        SpUtils.putString("workTiem", workTiem.getText().toString());
                    } else {
                        toastor.showSingletonToast("请输入合法时间");
                    }

                } else {
                    toastor.showSingletonToast("输入内容不能为空");
                }


                break;
            case R.id.setting_edit:
                String time = SpUtils.getString("workTiem", "00:00");
                workYear.setText(time.substring(0, 2));
                workMonth.setText(time.substring(3, 5));

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

/*
    private void setView() {
        String msg = workEt.getText().toString().trim();

        if (!msg.equals("")) {
            if (Integer.parseInt(msg) <= 250) {
                //editLl.setVisibility(View.GONE);
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
    }*/

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.work_activity:
                WorkRg.setVisibility(b ? View.VISIBLE : View.GONE);
                discoverList2.setVisibility(b ? View.VISIBLE : View.GONE);
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
                    dialog.dismiss();
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
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(notifyReceiver);
    }
}

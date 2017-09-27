package myapplication.com.bluetoothscales.fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kitnew.ble.QNData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DoublePicker;
import cn.qqtheme.framework.picker.NumberPicker;
import myapplication.com.bluetoothscales.R;
import myapplication.com.bluetoothscales.app.MyApplication;
import myapplication.com.bluetoothscales.base.BaseFragment;
import myapplication.com.bluetoothscales.utils.SpUtils;
import myapplication.com.bluetoothscales.utils.Toastor;

import static myapplication.com.bluetoothscales.utils.Constant.ACTION_BLE_NOTIFY_DATA;
import static myapplication.com.bluetoothscales.utils.DateUtil.currDay;
import static myapplication.com.bluetoothscales.utils.DateUtil.dayDiffCurr;


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
    TextView workTarget;
    @BindView(R.id.edit_ll)
    LinearLayout editLl;
    @BindView(R.id.edit_ll2)
    LinearLayout editLl2;
    @BindView(R.id.work_msg7)
    TextView workMsg7;
    @BindView(R.id.work_duration)
    TextView workDuration;
    @BindView(R.id.work_activity)
    CheckBox workActivity;
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

    @BindView(R.id.msg1)
    TextView msg1;
    @BindView(R.id.msg2)
    TextView msg2;
    @BindView(R.id.msg3)
    TextView msg3;
    @BindView(R.id.msg4)
    TextView msg4;
    @BindView(R.id.msg5)
    TextView msg5;
    @BindView(R.id.msg6)
    TextView msg6;
    @BindView(R.id.msg7)
    TextView msg7;
    @BindView(R.id.msg8)
    TextView msg8;
    @BindView(R.id.msg9)
    TextView msg9;
    @BindView(R.id.work_tv2)
    TextView workTv2;
    @BindView(R.id.wheelview_container)
    LinearLayout wheelview_container;
    @BindView(R.id.wheelview_container2)
    LinearLayout wheelview_container2;
    @BindView(R.id.wheelview_container3)
    LinearLayout wheelview_container3;
    @BindView(R.id.week1)
    CheckBox week1;
    @BindView(R.id.week2)
    CheckBox week2;
    @BindView(R.id.week3)
    CheckBox week3;
    @BindView(R.id.week4)
    CheckBox week4;
    @BindView(R.id.week5)
    CheckBox week5;
    @BindView(R.id.week6)
    CheckBox week6;
    @BindView(R.id.week7)
    CheckBox week7;
    @BindView(R.id.intensity)
    TextView intensity;
    Toastor toastor;
    int postion = 0;
    int indext = 0;
    String unit = "";
    ProgressDialog progressDialog;
    int[] id = {R.id.work_xingzou, R.id.work_qixing, R.id.work_paobu, R.id.work_youyong, R.id.work_yangwoqizuo
            , R.id.work_fuwocheng, R.id.work_shendun, R.id.work_pashan, R.id.work_ticoa};

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        unit = SpUtils.getString("unit", "LBS");
        toastor = new Toastor(getActivity());
        workHeight.setText(SpUtils.getString("workHeight", "--") + "cm");
        workTarget.setText(SpUtils.getString("workTarget", "--") + unit);
        workDuration.setText(SpUtils.getString("workDuration", "--") + "weeks");
        workTiem.setText(SpUtils.getString("workTime", "00:00"));

        setTv(SpUtils.getInt("workType", -1));
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_BLE_NOTIFY_DATA);
        getActivity().registerReceiver(notifyReceiver, intentFilter);
        iniCb();
        initWheel();
        initTarget();
        workActivity.setOnCheckedChangeListener(this);
        week1.setOnCheckedChangeListener(this);
        week2.setOnCheckedChangeListener(this);
        week3.setOnCheckedChangeListener(this);
        week4.setOnCheckedChangeListener(this);
        week5.setOnCheckedChangeListener(this);
        week6.setOnCheckedChangeListener(this);
        week7.setOnCheckedChangeListener(this);
        if (SpUtils.getInt("workType", -1) != -1)
            WorkRg.check(id[SpUtils.getInt("workType", -1)]);
        WorkRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int inten=0;
                int target = (int) (Double.parseDouble(SpUtils.getString("workTarget", "0")) * 7700);
                int week = Integer.parseInt(SpUtils.getString("workDuration", "0"));
                switch (checkedId) {
                    case R.id.work_xingzou:
                        SpUtils.putInt("workType", 0);
                         inten=(target / 72) / week;
                        intensity.setText(inten+"");
                        break;
                    case R.id.work_qixing:
                        SpUtils.putInt("workType", 1);
                         inten=(target / 330) / week;
                        intensity.setText(inten+"");
                        break;
                    case R.id.work_paobu:
                        SpUtils.putInt("workType", 2);
                        inten=(target / 300) / week;
                        intensity.setText(inten+"");
                        break;
                    case R.id.work_youyong:
                        SpUtils.putInt("workType", 3);
                        inten=(target / 175) / week;
                        intensity.setText(inten+"");
                        break;
                    case R.id.work_yangwoqizuo:
                        SpUtils.putInt("workType", 4);
                        inten=(target / 432) / week;
                        intensity.setText(inten+"");
                        break;
                    case R.id.work_fuwocheng:
                        SpUtils.putInt("workType", 5);
                        inten=(target / 1968) / week;
                        intensity.setText(inten+"");
                        break;
                    case R.id.work_shendun:
                        SpUtils.putInt("workType", 6);
                        inten=(target / 150) / week;
                        intensity.setText(inten+"");
                        break;
                    case R.id.work_pashan:
                        SpUtils.putInt("workType", 7);
                        inten=(target / 210) / week;
                        intensity.setText(inten+"");
                        break;
                    case R.id.work_ticoa:
                        SpUtils.putInt("workType", 8);
                        inten=(target / 150) / week;
                        intensity.setText(inten+"");
                        break;
                }
                setTv(SpUtils.getInt("workType", -1));
            }
        });
  /*      AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Hint");
        builder.setMessage("Please stand on the electronic scale");
        dialog = builder.create();*/
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loding...");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_work;
    }

    boolean isData = false;

    @OnClick({R.id.baby_back, R.id.work_edit, R.id.work_next, R.id.work_next2,
            R.id.goal_edit, R.id.work_target_ll, R.id.work_duration_ll,
            R.id.work_next3, R.id.setting_edit, R.id.work_measure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.baby_back:
                getActivity().finish();
                break;
            case R.id.work_edit:
                indext = 1;
                editLl.setVisibility(View.VISIBLE);
                workEdit.setVisibility(View.GONE);
                setText();
                break;
            case R.id.work_measure:
                if (MyApplication.newInstance().isLink) {
                    progressDialog.show();
                    isData = true;
                } else {
                    toastor.showSingletonToast("Unconnected equipment");
                }
                break;

            case R.id.goal_edit:
                postion = 1;
                editLl2.setVisibility(View.VISIBLE);
                goalEdit.setVisibility(View.GONE);
                setText2();

                break;
            case R.id.work_target_ll:
                if (postion != 0) {
                    postion = 1;
                    setText2();
                    wheelview_container2.removeAllViews();
                    initTarget();
                }
                break;
            case R.id.work_duration_ll:
                if (postion != 0) {
                    postion = 2;
                    setText2();
                    wheelview_container2.removeAllViews();
                    initWeek();
                }
                break;
            case R.id.work_next:
                indext = 0;
                SpUtils.putString("workHeight", picker.getSelectedItem() + "");
                workHeight.setText(picker.getSelectedItem() + "cm");
                editLl.setVisibility(View.GONE);
                workEdit.setVisibility(View.VISIBLE);
                setText();
                break;
            case R.id.work_next2:
                setView();

                break;
            case R.id.work_next3:
                String h = doublePicker2.getSelectedFirstItem().length() == 1 ? "0" + doublePicker2.getSelectedFirstItem() : doublePicker2.getSelectedFirstItem();
                String m = doublePicker2.getSelectedSecondItem().length() == 1 ? "0" + doublePicker2.getSelectedSecondItem() : doublePicker2.getSelectedSecondItem();
                String msg = h + ":" + m;
                workTiem.setText(msg);
                editLl3.setVisibility(View.GONE);
                settingEdit.setVisibility(View.VISIBLE);
                workTiem.setVisibility(View.GONE);
                SpUtils.putString("workTime", workTiem.getText().toString());
                break;
            case R.id.setting_edit:
                initTime();
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
        if (postion == 1) {
            String msg = doublePicker.getSelectedFirstItem() + "." + doublePicker.getSelectedSecondItem();

            workTarget.setText(msg + unit);
            SpUtils.putString("workTarget", msg);
            postion = 2;
            setText2();
            wheelview_container2.removeAllViews();
            initWeek();
        } else if (postion == 2) {
            String msg = picker2.getSelectedItem() + "";
            workDuration.setText(msg + "weeks");
            SpUtils.putString("workDuration", msg);
            postion = 0;
            setText2();
            wheelview_container2.removeAllViews();
            initTarget();
            editLl2.setVisibility(View.GONE);
            goalEdit.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.work_activity:
                WorkRg.setVisibility(b ? View.VISIBLE : View.GONE);
                break;
            case R.id.week1:
                SpUtils.putBoolean("week1", b);
                break;
            case R.id.week2:
                SpUtils.putBoolean("week2", b);
                break;
            case R.id.week3:
                SpUtils.putBoolean("week3", b);
                break;
            case R.id.week4:
                SpUtils.putBoolean("week4", b);
                break;
            case R.id.week5:
                SpUtils.putBoolean("week5", b);
                break;
            case R.id.week6:
                SpUtils.putBoolean("week6", b);
                break;
            case R.id.week7:
                SpUtils.putBoolean("week7", b);
                break;

        }
    }

    private void iniCb() {
        week1.setChecked(SpUtils.getBoolean("week1", false));
        week2.setChecked(SpUtils.getBoolean("week2", false));
        week3.setChecked(SpUtils.getBoolean("week3", false));
        week4.setChecked(SpUtils.getBoolean("week4", false));
        week5.setChecked(SpUtils.getBoolean("week5", false));
        week6.setChecked(SpUtils.getBoolean("week6", false));
        week7.setChecked(SpUtils.getBoolean("week7", false));
    }

    private void setText() {
        workMsg2.setTextColor(getResources().getColor(indext == 1 ? R.color.maya_blue2 : R.color.tv));
        workHeight.setTextColor(getResources().getColor(indext == 1 ? R.color.maya_blue2 : R.color.tv));

    }

    private void setText2() {
        workMsg6.setTextColor(getResources().getColor(postion == 1 ? R.color.maya_blue2 : R.color.tv));
        workTarget.setTextColor(getResources().getColor(postion == 1 ? R.color.maya_blue2 : R.color.tv));
        workMsg7.setTextColor(getResources().getColor(postion == 2 ? R.color.maya_blue2 : R.color.tv));
        workDuration.setTextColor(getResources().getColor(postion == 2 ? R.color.maya_blue2 : R.color.tv));
        workTv2.setText(postion == 2 ? "Weeks" : "Kg");

    }

    private BroadcastReceiver notifyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Log.e("BLEService收到设备信息广播", intent.getAction());
            if (ACTION_BLE_NOTIFY_DATA.equals(intent.getAction())) {
                if (isData) {
                    progressDialog.dismiss();
                    QNData qnData = MyApplication.newInstance().getQnData();
                    workWeight.setText(String.valueOf(qnData.getWeight() + unit));
                    workBmi.setText(String.valueOf(qnData.getFloatValue(QNData.TYPE_BMI)));
                    workMucs.setText(String.valueOf(qnData.getFloatValue(QNData.TYPE_SKELETAL_MUSCLE)));
                    workFat.setText(String.valueOf(qnData.getFloatValue(QNData.TYPE_BODYFAT)));
                    if (!SpUtils.getString("time", "").equals("")) {
                        if (dayDiffCurr(SpUtils.getString("time", "2017-1-1")) / 7 >= 1) {
                            SpUtils.putString("weight", qnData.getWeight() + "");
                            SpUtils.putString("time", currDay());
                        }
                    } else {
                        SpUtils.putString("weight", qnData.getWeight() + "");
                        SpUtils.putString("time", currDay());
                    }


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

    private void setTv(int indext) {
        msg1.setVisibility(indext == 0 ? View.VISIBLE : View.GONE);
        msg2.setVisibility(indext == 1 ? View.VISIBLE : View.GONE);
        msg3.setVisibility(indext == 2 ? View.VISIBLE : View.GONE);
        msg4.setVisibility(indext == 3 ? View.VISIBLE : View.GONE);
        msg5.setVisibility(indext == 4 ? View.VISIBLE : View.GONE);
        msg6.setVisibility(indext == 5 ? View.VISIBLE : View.GONE);
        msg7.setVisibility(indext == 6 ? View.VISIBLE : View.GONE);
        msg8.setVisibility(indext == 7 ? View.VISIBLE : View.GONE);
        msg9.setVisibility(indext == 8 ? View.VISIBLE : View.GONE);
    }

    NumberPicker picker;

    private void initWheel() {
        picker = new NumberPicker(getActivity());
        picker.setWidth(picker.getScreenWidthPixels() / 2);
        picker.setCycleDisable(true);
        picker.setDividerVisible(false);
        picker.setOffset(1);
        picker.setLineSpaceMultiplier(3);
        picker.setTextColor(Color.rgb(255, 255, 255));
        picker.setRange(100, 230, 1);//数字范围
        if (!SpUtils.getString("workHeight", "").equals(""))
            picker.setSelectedItem(Integer.parseInt(SpUtils.getString("workHeight", "")));
        picker.setLabel("");
        picker.setDividerVisible(false);
        View pickerContentView = picker.getContentView();
        wheelview_container.addView(pickerContentView);
    }

    DoublePicker doublePicker;

    private void initTarget() {
        final ArrayList<String> firstData = new ArrayList<>();

        for (int i = 5; i <= 120; i++) {
            firstData.add(i + "");
        }
        final ArrayList<String> secondData = new ArrayList<>();
        for (int i = 0; i <= 99; i++) {
            secondData.add(i + "");
        }
        doublePicker = new DoublePicker(getActivity(), firstData, secondData);
        doublePicker.setCycleDisable(true);
        doublePicker.setDividerVisible(false);
        doublePicker.setOffset(1);
        doublePicker.setTextColor(Color.rgb(255, 255, 255));
        doublePicker.setLabelTextColor(Color.rgb(255, 255, 255));
        doublePicker.setLineSpaceMultiplier(3);
        String msg = SpUtils.getString("workTarget", "");
        if (!msg.equals("")) {
            int indext = Integer.parseInt(msg.substring(0, msg.indexOf("."))) - 5;
            int indext2 = Integer.parseInt(msg.substring(msg.indexOf(".") + 1, msg.length()));
            doublePicker.setSelectedIndex(indext, indext2);
            //doublePicker.setSelectedItem(Integer.parseInt(SpUtils.getString("workHeight", "")));
        }

        doublePicker.setFirstLabel(null, ".");

        doublePicker.setDividerVisible(false);

        View pickerContentView2 = doublePicker.getContentView();
        wheelview_container2.addView(pickerContentView2);
    }

    NumberPicker picker2;

    private void initWeek() {
        picker2 = new NumberPicker(getActivity());
        picker2.setWidth(picker2.getScreenWidthPixels() / 2);
        picker2.setCycleDisable(true);
        picker2.setDividerVisible(false);
        picker2.setOffset(1);
        picker2.setTextColor(Color.rgb(255, 255, 255));
        picker2.setRange(1, 50, 1);//数字范围
        picker2.setLineSpaceMultiplier(3);
        if (!SpUtils.getString("workDuration", "").equals(""))
            picker2.setSelectedItem(Integer.parseInt(SpUtils.getString("workDuration", "")));
        picker2.setLabel("");
        picker2.setDividerVisible(false);
        View pickerContentView = picker2.getContentView();
        wheelview_container2.addView(pickerContentView);
    }

    DoublePicker doublePicker2;

    private void initTime() {
        final ArrayList<String> firstData = new ArrayList<>();

        for (int i = 0; i <= 23; i++) {
            firstData.add(i + "");
        }
        final ArrayList<String> secondData = new ArrayList<>();
        for (int i = 0; i <= 59; i++) {
            secondData.add(i + "");
        }
        doublePicker2 = new DoublePicker(getActivity(), firstData, secondData);
        doublePicker2.setCycleDisable(true);
        doublePicker2.setDividerVisible(false);
        doublePicker2.setOffset(1);
        doublePicker2.setTextColor(Color.rgb(255, 255, 255));
        doublePicker2.setLabelTextColor(Color.rgb(255, 255, 255));
        doublePicker2.setLineSpaceMultiplier(3);
        String msg = SpUtils.getString("workTime", "");
        if (!msg.equals("")) {
            int indext = Integer.parseInt(msg.substring(0, msg.indexOf(":")));
            int indext2 = Integer.parseInt(msg.substring(msg.indexOf(":") + 1, msg.length()));
            doublePicker2.setSelectedIndex(indext, indext2);

        }

        doublePicker2.setFirstLabel(null, ":");

        doublePicker2.setDividerVisible(false);

        View pickerContentView2 = doublePicker2.getContentView();
        wheelview_container3.addView(pickerContentView2);
    }
}

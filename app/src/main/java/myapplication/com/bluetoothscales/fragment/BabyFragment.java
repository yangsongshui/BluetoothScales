package myapplication.com.bluetoothscales.fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kitnew.ble.QNData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.DoublePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.util.ConvertUtils;
import myapplication.com.bluetoothscales.R;
import myapplication.com.bluetoothscales.app.MyApplication;
import myapplication.com.bluetoothscales.base.BaseFragment;
import myapplication.com.bluetoothscales.utils.SpUtils;
import myapplication.com.bluetoothscales.utils.Toastor;

import static myapplication.com.bluetoothscales.utils.Constant.ACTION_BLE_NOTIFY_DATA;
import static myapplication.com.bluetoothscales.utils.DateUtil.dayDiffCurr;


public class BabyFragment extends BaseFragment {
    @BindView(R.id.baby_edit)
    TextView babyEdit;
    @BindView(R.id.edit_ll)
    LinearLayout edit_ll;
    @BindView(R.id.baby_msg)
    TextView babyMsg;
    @BindView(R.id.baby_time)
    TextView babyTime;
    @BindView(R.id.baby_msg2)
    TextView babyMsg2;
    @BindView(R.id.baby_weight)
    TextView babyWeight;
    @BindView(R.id.baby_msg3)
    TextView babyMsg3;
    @BindView(R.id.baby_sex)
    TextView babySex;
    @BindView(R.id.mom_weight)
    TextView momWeight;
    @BindView(R.id.baby_mom_weight)
    TextView babyMomWeight;
    @BindView(R.id.current_weight)
    TextView currentWeight;
    @BindView(R.id.current_time)
    TextView currentTime;
    @BindView(R.id.wheelview_container)
    LinearLayout wheelview_container;
    @BindView(R.id.weight_et)
    LinearLayout weightEt;
    @BindView(R.id.wheelview_container2)
    LinearLayout wheelview_container2;
    @BindView(R.id.wheelview_container3)
    LinearLayout wheelview_container3;
    @BindView(R.id.year)
    TextView year;
    @BindView(R.id.month)
    TextView month;
    @BindView(R.id.day)
    TextView day;
    Toastor toastor;
    int indext = 0;
    String unit = "";
    String weight = "";

    ProgressDialog progressDialog;

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        unit = SpUtils.getString("unit", "LBS");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_BLE_NOTIFY_DATA);
        getActivity().registerReceiver(notifyReceiver, intentFilter);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loding...");
        progressDialog.setCanceledOnTouchOutside(false);
        toastor = new Toastor(getActivity());
        babySex.setText(SpUtils.getString("sex", "Boy/Girl"));
        babyTime.setText(SpUtils.getString("BabyData", "2017-01-01"));
        babyWeight.setText(SpUtils.getString("BabyWeight", "--") + unit);
        if (!babyWeight.getText().equals("--"))
            currentTime.setText(String.valueOf((dayDiffCurr(babyTime.getText().toString()) / 7) + " Weeks"));
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_baby;
    }

    @OnClick({R.id.baby_back, R.id.baby_edit, R.id.baby_measure, R.id.baby_measure2, R.id.baby_next, R.id.baby_birth, R.id.baby_weight_ll, R.id.baby_sex_ll})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.baby_back:
                getActivity().finish();
                break;
            case R.id.baby_measure:
                if (MyApplication.newInstance().isLink){
                    progressDialog.show();
                    isMom = true;
                }else {
                    toastor.showSingletonToast("Unconnected equipment");
                }

                break;
            case R.id.baby_measure2:
                if (MyApplication.newInstance().isLink){
                    progressDialog.show();
                    isBaby = true;
                }else {
                    toastor.showSingletonToast("Unconnected equipment");
                }
                break;
            case R.id.baby_edit:
                indext = 1;
                babyEdit.setVisibility(View.GONE);
                edit_ll.setVisibility(View.VISIBLE);
                String time = babyTime.getText().toString();
                initWheel(Integer.parseInt(time.substring(0, 4)), Integer.parseInt(time.substring(6, 7)), Integer.parseInt(time.substring(8, 10)));
                setText();
                break;
            case R.id.baby_birth:
                if (indext != 0) {
                    indext = 1;
                    String time2 = babyTime.getText().toString();
                    initWheel(Integer.parseInt(time2.substring(0, 4)), Integer.parseInt(time2.substring(6, 7)), Integer.parseInt(time2.substring(8, 10)));
                    setText();
                }
                break;
            case R.id.baby_weight_ll:
                if (indext != 0) {
                    indext = 2;
                    setText();
                    initTarget();
                }
                break;
            case R.id.baby_sex_ll:
                if (indext != 0) {
                    indext = 3;
                    initSex();
                    setText();
                }
                break;
            case R.id.baby_next:
                setView();
                break;
        }
    }

    private void setText() {
        babyMsg.setTextColor(getResources().getColor(indext == 1 ? R.color.maya_blue2 : R.color.tv));
        babyTime.setTextColor(getResources().getColor(indext == 1 ? R.color.maya_blue2 : R.color.tv));
        babyMsg2.setTextColor(getResources().getColor(indext == 2 ? R.color.maya_blue2 : R.color.tv));
        babyWeight.setTextColor(getResources().getColor(indext == 2 ? R.color.maya_blue2 : R.color.tv));
        babyMsg3.setTextColor(getResources().getColor(indext == 3 ? R.color.maya_blue2 : R.color.tv));
        babySex.setTextColor(getResources().getColor(indext == 3 ? R.color.maya_blue2 : R.color.tv));
        wheelview_container.setVisibility(indext == 1 ? View.VISIBLE : View.GONE);
        weightEt.setVisibility(indext == 2 ? View.VISIBLE : View.GONE);
        wheelview_container3.setVisibility(indext == 3 ? View.VISIBLE : View.GONE);
        day.setVisibility(indext == 1 ? View.VISIBLE : View.GONE);
        year.setVisibility(indext == 1 ? View.VISIBLE : View.GONE);
        if (indext == 1) {
            month.setText("Month");
        } else if (indext == 2) {
            month.setText("Kg");
        } else if (indext == 3) {
            month.setText("Gender");
        }

    }


    boolean isMom = false;
    boolean isBaby = false;
    private BroadcastReceiver notifyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Log.e("BLEService收到设备信息广播", intent.getAction());
            if (ACTION_BLE_NOTIFY_DATA.equals(intent.getAction())) {
                if (isMom) {
                    QNData qnData = MyApplication.newInstance().getQnData();
                    momWeight.setText(String.valueOf(qnData.getWeight() + unit));
                    isMom = false;
                    progressDialog.dismiss();
                }
                if (isBaby) {
                    QNData qnData = MyApplication.newInstance().getQnData();
                    babyMomWeight.setText(String.valueOf(qnData.getWeight() + unit));
                    isMom = false;
                    double weight = Double.parseDouble(momWeight.getText().toString().replace(unit, ""));
                    currentWeight.setText(String.format("%.2f", qnData.getWeight() - weight) + unit);
                    MyApplication.newInstance().isMeasure = true;
                    progressDialog.dismiss();
                }

            }
        }
    };

    private void setView() {
        if (indext == 1) {
            babyTime.setText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            indext = 2;
            SpUtils.putString("BabyData", babyTime.getText().toString());
            currentTime.setText(String.valueOf((dayDiffCurr(babyTime.getText().toString()) / 7) + " Weeks"));
            initTarget();
        } else if (indext == 2) {
            String weight = doublePicker.getSelectedFirstItem() + "." + doublePicker.getSelectedSecondItem();
            babyWeight.setText(weight + unit);
            indext = 3;
            SpUtils.putString("BabyWeight", weight);
            initSex();
        } else if (indext == 3) {
            babySex.setText(wheelView.getSelectedIndex() == 0 ? "Boy" : "Girl");
            indext = 0;
            edit_ll.setVisibility(View.GONE);
            babyEdit.setVisibility(View.VISIBLE);
            SpUtils.putString("sex", babySex.getText().toString());
        }
        setText();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(notifyReceiver);
    }

    DatePicker picker;

    private void initWheel(int year, int month, int day) {
        wheelview_container.removeAllViews();
        picker = new DatePicker(getActivity());
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setOffset(1);
        picker.setTopPadding(ConvertUtils.toPx(getActivity(), 10));
        picker.setRangeEnd(2111, 1, 11);
        picker.setSelectedItem(year, month, day);
        picker.setTextColor(Color.rgb(255, 255, 255));
        picker.setResetWhileWheel(false);
        picker.setLabel("", "", "");

        picker.setLineSpaceMultiplier(3);
        picker.setDividerVisible(false);
        View pickerContentView = picker.getContentView();
        wheelview_container.addView(pickerContentView);
    }

    DoublePicker doublePicker;

    private void initTarget() {
        wheelview_container2.removeAllViews();
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
        String msg = SpUtils.getString("BabyWeight", "");
        if (!msg.equals("")) {
            int indext = Integer.parseInt(msg.substring(0, msg.indexOf("."))) - 5;
            int indext2 = Integer.parseInt(msg.substring(msg.indexOf(".") + 1, msg.length()));
            doublePicker.setSelectedIndex(indext, indext2);
        }
        doublePicker.setFirstLabel(null, ".");
        View pickerContentView = doublePicker.getContentView();
        wheelview_container2.addView(pickerContentView);
    }

    OptionPicker wheelView;

    private void initSex() {
        wheelview_container3.removeAllViews();
        wheelView = new OptionPicker(getActivity(), new String[]{"Boy", "Girl"});
        wheelView.setOffset(1);
        wheelView.setCycleDisable(true);
        wheelView.setDividerVisible(false);
        wheelView.setTextColor(Color.rgb(255, 255, 255));
        wheelView.setLineSpaceMultiplier(3);
        if (!SpUtils.getString("sex", "").equals("")) {
            wheelView.setSelectedIndex(SpUtils.getString("sex", "Boy").equals("Boy") ? 0 : 1);
        }
        View pickerContentView = wheelView.getContentView();
        wheelview_container3.addView(pickerContentView);
    }
}

package myapplication.com.bluetoothscales.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
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
import static myapplication.com.bluetoothscales.utils.DateUtil.dayDiffCurr;


public class BabyFragment extends BaseFragment {
    @BindView(R.id.baby_edit)
    TextView babyEdit;
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

    Toastor toastor;
    int indext = 0;
    String unit = "";
    String weight = "";
    AlertDialog dialog;

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        unit = SpUtils.getString("unit", "LBS");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_BLE_NOTIFY_DATA);
        getActivity().registerReceiver(notifyReceiver, intentFilter);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Hint");
        builder.setMessage("Please stand on the electronic scale");
        dialog = builder.create();
        toastor = new Toastor(getActivity());
        babySex.setText(SpUtils.getString("sex", "boy"));
        babyTime.setText(SpUtils.getString("BabyData", "2017-01-01"));
        babyWeight.setText(SpUtils.getString("BabyWeight", "--") + unit);
        babySex.setText(SpUtils.getString("sex", "boy"));
        if (!babyWeight.getText().equals("--"))
            currentTime.setText(String.valueOf((dayDiffCurr(babyTime.getText().toString()) / 7) + " Weeks"));
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_baby;
    }

    @OnClick({R.id.baby_back, R.id.baby_edit, R.id.baby_measure, R.id.baby_measure2, R.id.baby_birth, R.id.baby_weight_ll, R.id.baby_sex_ll})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.baby_back:
                getActivity().finish();
                break;
            case R.id.baby_measure:
                dialog.show();
                isMom = true;
                break;
            case R.id.baby_measure2:
                dialog.show();
                isBaby = true;
                break;
            case R.id.baby_edit:
                indext = 1;
                babyEdit.setVisibility(View.GONE);
                // editLl.setVisibility(View.VISIBLE);
                setText();
                break;
            case R.id.baby_birth:
                if (indext != 0) {
                    indext = 1;
                    setText();
                }
                break;
            case R.id.baby_weight_ll:
                if (indext != 0) {
                    indext = 2;
                    setText();
                }
                break;
            case R.id.baby_sex_ll:
                if (indext != 0) {
                    indext = 3;
                    setText();
                }
                break;
        }
    }

    private void setText() {
        babyMsg.setTextColor(getResources().getColor(indext == 1 ? R.color.grey : R.color.white));
        babyTime.setTextColor(getResources().getColor(indext == 1 ? R.color.grey : R.color.white));
        babyMsg2.setTextColor(getResources().getColor(indext == 2 ? R.color.grey : R.color.white));
        babyWeight.setTextColor(getResources().getColor(indext == 2 ? R.color.grey : R.color.white));
        babyMsg3.setTextColor(getResources().getColor(indext == 3 ? R.color.grey : R.color.white));
        babySex.setTextColor(getResources().getColor(indext == 3 ? R.color.grey : R.color.white));

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
                    dialog.dismiss();
                }
                if (isBaby) {
                    QNData qnData = MyApplication.newInstance().getQnData();
                    babyMomWeight.setText(String.valueOf(qnData.getWeight() + unit));
                    isMom = false;
                    double weight = Double.parseDouble(momWeight.getText().toString().replace(unit, ""));
                    currentWeight.setText(String.format("%.2f", qnData.getWeight() - weight) + unit);
                    MyApplication.newInstance().isMeasure = true;
                    dialog.dismiss();
                }

            }
        }
    };
   /* private void setView() {
        String year = babyYear.getText().toString().trim();
        String month = babyMonth.getText().toString().trim();
        String day = babyDay.getText().toString().trim();
        if (!year.trim().equals("") && !month.trim().equals("") && !day.trim().equals("")) {
            if (indext == 1) {
                if (year.length() == 4 && Integer.parseInt(year) <= 2100 && Integer.parseInt(year) >= 2016) {
                    if (Integer.parseInt(month) <= 12 && Integer.parseInt(month) > 0) {
                        if (Integer.parseInt(day) <= DateUtil.getDaysOfMonth(Integer.parseInt(year), Integer.parseInt(month))) {
                            month = month.length() == 1 ? "0" + month : month;
                            day = day.length() == 1 ? "0" + day : month;
                            babyTime.setText(year + "-" + month + "-" + day);
                            indext = 2;
                            SpUtils.putString("BabyData", babyTime.getText().toString());
                            currentTime.setText(String.valueOf((dayDiffCurr(babyTime.getText().toString()) / 7) + " Weeks"));

                        } else {
                            toastor.showSingletonToast("请输入正确日期");
                            return;
                        }
                    } else {
                        toastor.showSingletonToast("请输入正确月份");
                        return;
                    }
                } else {
                    toastor.showSingletonToast("请输入正确年份");
                    return;
                }
            } else if (indext == 2) {
                if (Integer.parseInt(month) <= 250&&Integer.parseInt(month)>= 5) {
                    babyWeight.setText(month + unit);
                    indext = 3;
                    SpUtils.putString("BabyWeight", month);
                } else {
                    toastor.showSingletonToast("输入正常体重");
                    return;
                }
            } else if (indext == 3) {
                if (month.trim().toLowerCase().equals("boy") || month.trim().toLowerCase().equals("girl")) {
                    babySex.setText(month);
                    indext = 0;
                    //editLl.setVisibility(View.GONE);
                    babyEdit.setVisibility(View.VISIBLE);
                    SpUtils.putString("sex", babySex.getText().toString());
                } else {
                    toastor.showSingletonToast("请输入正确性别");
                    return;
                }

            }
            setText();
        } else {
            toastor.showSingletonToast("输入内容不能为空");
        }
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(notifyReceiver);
    }
}
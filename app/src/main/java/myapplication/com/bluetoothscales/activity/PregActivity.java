package myapplication.com.bluetoothscales.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kitnew.ble.QNData;

import butterknife.BindView;
import butterknife.OnClick;
import myapplication.com.bluetoothscales.R;
import myapplication.com.bluetoothscales.app.MyApplication;
import myapplication.com.bluetoothscales.base.BaseActivity;
import myapplication.com.bluetoothscales.utils.DateUtil;
import myapplication.com.bluetoothscales.utils.SpUtils;
import myapplication.com.bluetoothscales.utils.Toastor;

import static myapplication.com.bluetoothscales.utils.Constant.ACTION_BLE_NOTIFY_DATA;
import static myapplication.com.bluetoothscales.utils.DateUtil.dayDiffCurr;

public class PregActivity extends BaseActivity {
    @BindView(R.id.preg_next)
    TextView pregNext;
    int postion = 0;
    @BindView(R.id.preg_edit)
    TextView pregEdit;
    @BindView(R.id.edit_ll)
    LinearLayout editLl;

    @BindView(R.id.preg_msg)
    TextView pregMsg;
    @BindView(R.id.pregancy_time)
    TextView pregancyTime;
    @BindView(R.id.preg_msg2)
    TextView pregMsg2;
    @BindView(R.id.expecting_time)
    TextView expectingTime;
    @BindView(R.id.preg_msg3)
    TextView pregMsg3;
    @BindView(R.id.weight_tv)
    TextView weightTv;
    @BindView(R.id.current_weight)
    TextView currentWeight;
    @BindView(R.id.current_time)
    TextView currentTime;
    @BindView(R.id.daily_msg)
    LinearLayout daily_msg;
    @BindView(R.id.preg_daily)
    CheckBox pregDaily;
    Toastor toastor;
    String unit = "";
    @BindView(R.id.preg_year)
    EditText pregYear;
    @BindView(R.id.preg_month)
    EditText pregMonth;
    @BindView(R.id.preg_day)
    EditText pregDay;
    @BindView(R.id.preg_year_tv)
    TextView pregYearTv;
    @BindView(R.id.preg_month_tv)
    TextView pregMonthTv;
    @BindView(R.id.preg_day_tv)
    TextView pregDayTv;
    @BindView(R.id.preg_yunfu)
    ImageView pregYunfu;
    AlertDialog dialog;

    @Override
    protected int getContentView() {
        return R.layout.activity_preg;
    }

    @Override
    protected void init() {
        unit = SpUtils.getString("unit", "LBS");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_BLE_NOTIFY_DATA);
        registerReceiver(notifyReceiver, intentFilter);
        expectingTime.setText(SpUtils.getString("expectingTime", "2017-01-01"));
        pregancyTime.setText(SpUtils.getString("pregancyTime", "2017-01-01"));
        weightTv.setText(SpUtils.getString("pregWeight", "--") + unit);
        toastor = new Toastor(this);
        pregDaily.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                daily_msg.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });
        if (!expectingTime.getText().equals("--")) {
            currentTime.setText(String.valueOf((dayDiffCurr(pregancyTime.getText().toString()) / 7) + " Weeks"));
            setyunfu((int) (dayDiffCurr(pregancyTime.getText().toString()) / 7));
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Hint");
        builder.setMessage("Please stand on the electronic scale");
        dialog = builder.create();
    }

    int indext = 0;

    @OnClick({R.id.preg_back, R.id.preg_next, R.id.preg_measure, R.id.preg_edit, R.id.preg_pregancy, R.id.preg_expecting, R.id.preg_weight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.preg_back:
                finish();
                break;
            case R.id.preg_measure:
                dialog.show();
                isData = true;
                break;
            case R.id.preg_edit:
                indext = 1;
                pregEdit.setVisibility(View.GONE);
                editLl.setVisibility(View.VISIBLE);
                setText();
                break;
            case R.id.preg_next:
                setView();

                break;
            case R.id.preg_pregancy:
                if (indext != 0) {
                    indext = 1;
                    setText();
                }
                break;
            case R.id.preg_expecting:
                if (indext != 0) {
                    indext = 2;
                    setText();
                }
                break;
            case R.id.preg_weight:
                if (indext != 0) {
                    indext = 3;
                    postion = 3;
                    setText();


                }
                break;

        }
    }


    private void setText() {
        pregMsg.setTextColor(getResources().getColor(indext == 1 ? R.color.grey : R.color.white));
        pregancyTime.setTextColor(getResources().getColor(indext == 1 ? R.color.grey : R.color.white));
        pregMsg2.setTextColor(getResources().getColor(indext == 2 ? R.color.grey : R.color.white));
        expectingTime.setTextColor(getResources().getColor(indext == 2 ? R.color.grey : R.color.white));
        pregMsg3.setTextColor(getResources().getColor(indext == 3 ? R.color.grey : R.color.white));
        weightTv.setTextColor(getResources().getColor(indext == 3 ? R.color.grey : R.color.white));
        if (indext == 1) {
            pregYear.setText(pregancyTime.getText().toString().substring(0, 5));
            pregMonth.setText(pregancyTime.getText().toString().substring(5, 7));
            pregDay.setText(pregancyTime.getText().toString().substring(8, 10));
        } else if (indext == 2) {
            pregYear.setText(expectingTime.getText().toString().substring(0, 5));
            pregMonth.setText(expectingTime.getText().toString().substring(5, 7));
            pregDay.setText(expectingTime.getText().toString().substring(8, 10));
        } else if (indext == 3) {
            pregMonth.setText(SpUtils.getString("pregWeight", ""));

        } else {
            pregYear.setText("");
            pregMonth.setText("");
            pregDay.setText("");
        }

        pregYear.setVisibility(indext == 3 ? View.GONE : View.VISIBLE);
        pregYearTv.setVisibility(indext == 3 ? View.GONE : View.VISIBLE);
        pregDay.setVisibility(indext == 3 ? View.GONE : View.VISIBLE);
        pregDayTv.setVisibility(indext == 3 ? View.GONE : View.VISIBLE);
        pregMonthTv.setText(indext == 3 ? "Weight" : "month");
    }


    private void setView() {
        String year = pregYear.getText().toString().trim();
        String month = pregMonth.getText().toString().trim();
        String day = pregDay.getText().toString().trim();
        if (indext == 1) {
            if (year.length() == 4 && Integer.parseInt(year) <= 2100 && Integer.parseInt(year) >= 2016) {
                if (Integer.parseInt(month) <= 12 && Integer.parseInt(month) > 0) {
                    if (Integer.parseInt(day) <= DateUtil.getDaysOfMonth(Integer.parseInt(year), Integer.parseInt(month))) {
                        month = month.length() == 1 ? "0" + month : month;
                        day = day.length() == 1 ? "0" + day : month;
                        pregancyTime.setText(year + "-" + month + "-" + day);
                        indext = 2;
                        SpUtils.putString("pregancyTime", pregancyTime.getText().toString());
                        currentTime.setText(String.valueOf((dayDiffCurr(pregancyTime.getText().toString()) / 7) + " Weeks"));
                        setyunfu((int) (dayDiffCurr(pregancyTime.getText().toString()) / 7));
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
            if (year.length() == 4 && Integer.parseInt(year) <= 2100 && Integer.parseInt(year) >= 2016) {
                if (Integer.parseInt(month) <= 12 && Integer.parseInt(month) > 0) {
                    if (Integer.parseInt(day) <= DateUtil.getDaysOfMonth(Integer.parseInt(year), Integer.parseInt(month))) {
                        month = month.length() == 1 ? "0" + month : month;
                        day = day.length() == 1 ? "0" + day : month;
                        expectingTime.setText(year + "-" + month + "-" + day);
                        indext = 3;
                        SpUtils.putString("expectingTime", expectingTime.getText().toString());
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
        } else if (indext == 3) {
            if (Integer.parseInt(month) <= 200) {
                weightTv.setText(month + unit);
                indext = 0;
                SpUtils.putString("pregWeight", month);
                editLl.setVisibility(View.GONE);
            } else {
                toastor.showSingletonToast("输入正常体重");
                return;
            }
        }
        setText();

    }

    boolean isData = false;
    private BroadcastReceiver notifyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Log.e("BLEService收到设备信息广播", intent.getAction());
            if (ACTION_BLE_NOTIFY_DATA.equals(intent.getAction())) {
                if (isData) {
                    dialog.dismiss();
                    QNData qnData = MyApplication.newInstance().getQnData();
                    currentWeight.setText(String.valueOf(qnData.getWeight() + unit));
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

    private void setyunfu(int week) {
        if (week <= 3) {
            pregYunfu.setImageResource(R.drawable.yunfu1);
        } else if (week <= 12) {
            pregYunfu.setImageResource(R.drawable.yunfu2);
        } else if (week <= 13) {
            pregYunfu.setImageResource(R.drawable.yunfu3);
        } else if (week <= 14) {
            pregYunfu.setImageResource(R.drawable.yunfu4);
        } else if (week <= 19) {
            pregYunfu.setImageResource(R.drawable.yunfu5);
        } else if (week <= 27) {
            pregYunfu.setImageResource(R.drawable.yunfu6);
        } else if (week <= 28) {
            pregYunfu.setImageResource(R.drawable.yunfu7);
        } else if (week <= 30) {
            pregYunfu.setImageResource(R.drawable.yunfu8);
        } else if (week <= 39) {
            pregYunfu.setImageResource(R.drawable.yunfu9);
        } else {
            pregYunfu.setImageResource(R.drawable.yunfu9);
        }
    }
}

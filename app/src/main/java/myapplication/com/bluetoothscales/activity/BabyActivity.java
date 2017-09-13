package myapplication.com.bluetoothscales.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
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

public class BabyActivity extends BaseActivity {

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

    @BindView(R.id.baby_et)
    EditText babyEt;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.preg_next)
    TextView pregNext;
    @BindView(R.id.edit_ll)
    LinearLayout editLl;
    @BindView(R.id.mom_weight)
    TextView momWeight;
    @BindView(R.id.baby_mom_weight)
    TextView babyMomWeight;
    @BindView(R.id.current_weight)
    TextView currentWeight;
    @BindView(R.id.current_time)
    TextView currentTime;
    Toastor toastor;
    int postion = 0;
    int indext = 0;
    String unit = "";
    String weight = "";

    @Override
    protected int getContentView() {
        return R.layout.activity_baby;
    }

    @Override
    protected void init() {
        unit = SpUtils.getString("unit", "LBS");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_BLE_NOTIFY_DATA);
        registerReceiver(notifyReceiver, intentFilter);
        toastor = new Toastor(this);
        babySex.setText(SpUtils.getString("sex", "boy"));
        tabLayout.addTab(tabLayout.newTab().setText("Year"));
        tabLayout.addTab(tabLayout.newTab().setText("Month"));
        tabLayout.addTab(tabLayout.newTab().setText("Day"));
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
        babyTime.setText(SpUtils.getString("BabyData", "2017-01-01"));
        babyWeight.setText(SpUtils.getString("BabyWeight", "--"));
        babySex.setText(SpUtils.getString("sex", "boy"));
        if (!babyWeight.getText().equals("--"))
            currentTime.setText(String.valueOf((dayDiffCurr(babyTime.getText().toString())  /7) + " Weeks"));
    }


    @OnClick({R.id.baby_back, R.id.baby_edit, R.id.baby_measure, R.id.baby_measure2, R.id.preg_next, R.id.baby_birth, R.id.baby_weight_ll, R.id.baby_sex_ll})
    public void onViewClicked(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Hint");
        dialog.setMessage("Please stand on the electronic scale");
        switch (view.getId()) {
            case R.id.baby_back:
                finish();
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
                editLl.setVisibility(View.VISIBLE);
                setText();
                break;
            case R.id.preg_next:
                setView();
                break;
            case R.id.baby_birth:
                if (indext != 0) {
                    indext = 1;
                    setText();
                    pregNext.setText("Next");
                    babyEt.setText("");
                    initTab();
                }
                break;
            case R.id.baby_weight_ll:
                if (indext != 0) {
                    indext = 2;
                    postion = 2;
                    babyEt.setText("");
                    setText();
                    pregNext.setText("Next");
                    tabLayout.removeAllTabs();
                    tabLayout.addTab(tabLayout.newTab().setText("Weight"));
                }
                break;
            case R.id.baby_sex_ll:
                if (indext != 0) {
                    indext = 3;
                    setText();
                    tabLayout.removeAllTabs();
                    tabLayout.addTab(tabLayout.newTab().setText("Sex"));
                    pregNext.setText("Commit");
                    postion = 3;
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
        babyEt.setInputType(indext == 3 ? InputType.TYPE_CLASS_TEXT : InputType.TYPE_CLASS_NUMBER);
    }

    private void setView() {
        String msg = babyEt.getText().toString();
        StringBuilder time = new StringBuilder(babyTime.getText().toString().trim());
        if (!msg.trim().equals("")) {
            switch (postion) {
                case 0:
                    if (indext == 1) {
                        if (msg.trim().length() == 4 && Integer.parseInt(msg) <= 2100) {
                            babyTime.setText(time.replace(0, 4, msg));
                            babyEt.setText("");
                            setData();
                        } else
                            toastor.showSingletonToast("请输入合法年份");
                    } else if (indext == 2) {
                        if (msg.trim().length() <= 3 && Integer.parseInt(msg) <= 250) {
                            postion = 2;
                            weight = msg;
                            babyWeight.setText(msg + unit);
                            babyEt.setText("");
                            setData();
                        }
                    }
                    break;
                case 1:
                    if (indext == 1) {
                        if (msg.trim().length() < 3 && Integer.parseInt(msg) <= 12) {
                            babyTime.setText(time.replace(5, 7, msg.length() == 2 ? msg : "0" + msg));
                            babyEt.setText("");
                            setData();
                        } else
                            toastor.showSingletonToast("请输入合法月份");
                    } else if (indext == 2) {

                    }
                    break;
                case 2:
                    if (msg.trim().length() < 3 &&
                            Integer.parseInt(msg) <= DateUtil.getDaysOfMonth(time.substring(0, 4), time.substring(5, 7))) {
                        if (indext == 1) {

                            babyTime.setText(time.replace(8, 10, msg.length() == 2 ? msg : "0" + msg));
                        }
                        babyEt.setText("");
                        setData();
                    } else
                        toastor.showSingletonToast("请输入合法日期");
                    break;
                case 3:
                    if (msg.trim().toLowerCase().equals("boy") || msg.trim().toLowerCase().equals("girl")) {
                        babySex.setText(msg);
                        babyEt.setText("");
                        setData();

                    } else
                        toastor.showSingletonToast("请输入正确性别");
                    break;
            }


        } else {
            toastor.showSingletonToast("输入内容不能为空");
        }
    }

    private void setData() {
        if (postion == 2) {
            if (indext == 1) {
                tabLayout.removeAllTabs();
                tabLayout.addTab(tabLayout.newTab().setText("Weight"));
                indext = 2;
                setText();

            } else if (indext == 2) {
                indext = 3;
                setText();
                tabLayout.removeAllTabs();
                tabLayout.addTab(tabLayout.newTab().setText("Sex"));
                pregNext.setText("Commit");
                postion = 3;
            }
        } else if (postion == 3) {
            //保存用户数据
            babyEdit.setVisibility(View.VISIBLE);
            pregNext.setText("Next");
            editLl.setVisibility(View.GONE);
            postion = 0;
            indext = 0;
            initTab();
            setText();
            SpUtils.putString("BabyData", babyTime.getText().toString());
            SpUtils.putString("BabyWeight", weight);
            SpUtils.putString("sex", babySex.getText().toString());
            currentTime.setText(String.valueOf(-(dayDiffCurr(babyTime.getText().toString()) / 7) + " Weeks"));
        } else {
            postion++;
            tabLayout.getTabAt(postion).select();
        }
    }

    private void initTab() {
        postion = 0;
        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab().setText("Year"));
        tabLayout.addTab(tabLayout.newTab().setText("Month"));
        tabLayout.addTab(tabLayout.newTab().setText("Day"));

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
                }
                if (isBaby) {
                    QNData qnData = MyApplication.newInstance().getQnData();
                    babyMomWeight.setText(String.valueOf(qnData.getWeight() + unit));
                    isMom = false;
                    double weight = Double.parseDouble(momWeight.getText().toString().replace(unit, ""));
                    currentWeight.setText(String .format("%.2f",qnData.getWeight() - weight) + unit);
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
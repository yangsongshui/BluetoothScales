package myapplication.com.bluetoothscales.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kitnew.ble.QNData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.util.ConvertUtils;
import myapplication.com.bluetoothscales.OnItemViewClickListener;
import myapplication.com.bluetoothscales.R;
import myapplication.com.bluetoothscales.adapter.MyPagerAdapter;
import myapplication.com.bluetoothscales.app.MyApplication;
import myapplication.com.bluetoothscales.base.BaseFragment;
import myapplication.com.bluetoothscales.utils.SpUtils;
import myapplication.com.bluetoothscales.utils.Toastor;

import static myapplication.com.bluetoothscales.utils.Constant.ACTION_BLE_NOTIFY_DATA;
import static myapplication.com.bluetoothscales.utils.DateUtil.dayDiffCurr;


public class PregFragment extends BaseFragment implements OnItemViewClickListener {

    @BindView(R.id.preg_edit)
    TextView pregEdit;
    @BindView(R.id.pager)
    ViewPager pager;
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
    @BindView(R.id.preg_yunfu)
    ImageView pregYunfu;
    @BindView(R.id.wheelview_container)
    LinearLayout wheelview_container;
    @BindView(R.id.edit_ll)
    LinearLayout editLl;
    @BindView(R.id.weight_et)
    EditText weightEt;
    @BindView(R.id.year)
    TextView year;
    @BindView(R.id.month)
    TextView month;
    @BindView(R.id.day)
    TextView day;
    AlertDialog dialog;
    MyPagerAdapter adapter;
    List<String> title;
    List<String> msg;
    Toastor toastor;
    String unit = "";

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        unit = SpUtils.getString("unit", "LBS");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_BLE_NOTIFY_DATA);
        getActivity().registerReceiver(notifyReceiver, intentFilter);
        msg = new ArrayList<>();
        title = new ArrayList<>();
        title.add(getString(R.string.msgtitle));
        title.add(getString(R.string.msgtitle2));
        title.add(getString(R.string.msgtitle3));
        title.add(getString(R.string.msgtitle4));
        msg.add(getString(R.string.msg));
        msg.add(getString(R.string.msg2));
        msg.add(getString(R.string.msg3));
        msg.add(getString(R.string.msg4));
        adapter = new MyPagerAdapter(title, msg, getActivity());
        pager.setAdapter(adapter);
        expectingTime.setText(SpUtils.getString("expectingTime", "2017-01-01"));
        pregancyTime.setText(SpUtils.getString("pregancyTime", "2017-01-01"));
        weightTv.setText(SpUtils.getString("pregWeight", "--") + unit);
        toastor = new Toastor(getActivity());
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Hint");
        builder.setMessage("Please stand on the electronic scale");
        dialog = builder.create();

        String time = pregancyTime.getText().toString();
        initWheel(Integer.parseInt(time.substring(0, 4)), Integer.parseInt(time.substring(6, 7)), Integer.parseInt(time.substring(8, 10)));
        adapter.setOnItemViewClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_preg;
    }

    int indext = 0;

    @OnClick({R.id.preg_back, R.id.preg_measure, R.id.preg_edit, R.id.preg_next, R.id.preg_pregancy, R.id.preg_expecting, R.id.preg_weight})
    public void onViewClicked(View view) {
        String time;
        switch (view.getId()) {
            case R.id.preg_back:
                getActivity().finish();
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
                wheelview_container.removeAllViews();
                time = pregancyTime.getText().toString();
                initWheel(Integer.parseInt(time.substring(0, 4)), Integer.parseInt(time.substring(6, 7)), Integer.parseInt(time.substring(8, 10)));
                break;

            case R.id.preg_pregancy:
                if (indext != 0) {
                    indext = 1;
                    setText();
                    wheelview_container.removeAllViews();
                    time = pregancyTime.getText().toString();
                    initWheel(Integer.parseInt(time.substring(0, 4)), Integer.parseInt(time.substring(6, 7)), Integer.parseInt(time.substring(8, 10)));
                }
                break;
            case R.id.preg_expecting:
                if (indext != 0) {
                    indext = 2;
                    setText();
                    wheelview_container.removeAllViews();
                    time = expectingTime.getText().toString();
                    initWheel(Integer.parseInt(time.substring(0, 4)), Integer.parseInt(time.substring(6, 7)), Integer.parseInt(time.substring(8, 10)));

                }
                break;
            case R.id.preg_weight:
                if (indext != 0) {
                    indext = 3;
                    setText();
                    weightEt.setText(SpUtils.getString("pregWeight", "0"));
                }
                break;
            case R.id.preg_next:
                setView();
                break;
        }
    }

    private void setText() {
        pregMsg.setTextColor(getResources().getColor(indext == 1 ? R.color.maya_blue2 : R.color.tv));
        pregancyTime.setTextColor(getResources().getColor(indext == 1 ? R.color.maya_blue2 : R.color.tv));
        pregMsg2.setTextColor(getResources().getColor(indext == 2 ? R.color.maya_blue2 : R.color.tv));
        expectingTime.setTextColor(getResources().getColor(indext == 2 ? R.color.maya_blue2 : R.color.tv));
        pregMsg3.setTextColor(getResources().getColor(indext == 3 ? R.color.maya_blue2 : R.color.tv));
        weightTv.setTextColor(getResources().getColor(indext == 3 ? R.color.maya_blue2 : R.color.tv));

        day.setVisibility(indext == 3 ? View.GONE : View.VISIBLE);
        wheelview_container.setVisibility(indext == 3 ? View.GONE : View.VISIBLE);
        weightEt.setVisibility(indext == 3 ? View.VISIBLE : View.GONE);
        year.setVisibility(indext == 3 ? View.GONE : View.VISIBLE);
        month.setText(indext == 3 ? "Kg" : "month");
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
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().unregisterReceiver(notifyReceiver);
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

    private void setView() {
        if (indext == 1) {
            pregancyTime.setText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            indext = 2;
            SpUtils.putString("pregancyTime", pregancyTime.getText().toString());
            currentTime.setText(String.valueOf((dayDiffCurr(pregancyTime.getText().toString()) / 7) + " Weeks"));
            setyunfu((int) (dayDiffCurr(pregancyTime.getText().toString()) / 7));
            wheelview_container.removeAllViews();
            String time = expectingTime.getText().toString();
            initWheel(Integer.parseInt(time.substring(0, 4)), Integer.parseInt(time.substring(6, 7)), Integer.parseInt(time.substring(8, 10)));
        } else if (indext == 2) {
            expectingTime.setText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            indext = 3;
            SpUtils.putString("expectingTime", expectingTime.getText().toString());
            weightEt.setText(SpUtils.getString("pregWeight", "0"));
        } else if (indext == 3) {
            String weight = weightEt.getText().toString().trim();
            if (!weight.equals("") && Integer.parseInt(weight) <= 200) {
                weightTv.setText(weight + unit);
                indext = 0;
                SpUtils.putString("pregWeight", weight);
                editLl.setVisibility(View.GONE);
                pregEdit.setVisibility(View.VISIBLE);
            } else {
                toastor.showSingletonToast("输入正常体重");
                return;
            }
        }
        setText();

    }

    DatePicker picker;

    private void initWheel(int year, int month, int day) {
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
        picker.setDividerVisible(false);
        View pickerContentView = picker.getContentView();
        wheelview_container.addView(pickerContentView);
    }

    @Override
    public void OnItemView(int position, View view, boolean is) {
        if (is)
            pager.setCurrentItem(position - 1);
        else
            pager.setCurrentItem(position + 1);
    }
}

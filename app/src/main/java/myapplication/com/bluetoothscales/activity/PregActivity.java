package myapplication.com.bluetoothscales.activity;

import android.annotation.SuppressLint;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import myapplication.com.bluetoothscales.R;
import myapplication.com.bluetoothscales.base.BaseActivity;
import myapplication.com.bluetoothscales.utils.DateUtil;
import myapplication.com.bluetoothscales.utils.Toastor;

public class PregActivity extends BaseActivity {


    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.preg_next)
    TextView pregNext;
    int postion = 0;
    @BindView(R.id.preg_edit)
    TextView pregEdit;
    @BindView(R.id.edit_ll)
    LinearLayout editLl;
    @BindView(R.id.preg_et)
    EditText preg_et;
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
    Toastor toastor;

    @Override
    protected int getContentView() {
        return R.layout.activity_preg;
    }

    @Override
    protected void init() {
        toastor = new Toastor(this);
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
    }

    int indext = 0;

    @SuppressLint("NewApi")
    @OnClick({R.id.preg_back, R.id.preg_next, R.id.preg_measure, R.id.preg_edit, R.id.preg_pregancy, R.id.preg_expecting, R.id.preg_weight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.preg_back:
                finish();
                break;
            case R.id.preg_measure:
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Hint");
                dialog.setMessage("Please stand on the electronic scale");
                dialog.show();
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
                    preg_et.setText("");
                    initTab();
                    setText();
                }
                break;
            case R.id.preg_expecting:
                if (indext != 0) {
                    indext = 2;
                    setText();
                    preg_et.setText("");
                    initTab();
                }
                break;
            case R.id.preg_weight:
                if (indext != 0) {
                    indext = 3;
                    postion = 3;
                    preg_et.setText("");
                    setText();
                    tabLayout.removeAllTabs();
                    tabLayout.addTab(tabLayout.newTab().setText("Weight"));
                }
                break;
            case R.id.preg_daily:
                break;
        }
    }

    private void initTab() {
        postion = 0;
        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab().setText("Year"));
        tabLayout.addTab(tabLayout.newTab().setText("Month"));
        tabLayout.addTab(tabLayout.newTab().setText("Day"));

    }

    @SuppressLint("NewApi")
    private void setText() {
        pregMsg.setTextColor(getColor(indext == 1 ? R.color.grey : R.color.white));
        pregancyTime.setTextColor(getColor(indext == 1 ? R.color.grey : R.color.white));
        pregMsg2.setTextColor(getColor(indext == 2 ? R.color.grey : R.color.white));
        expectingTime.setTextColor(getColor(indext == 2 ? R.color.grey : R.color.white));
        pregMsg3.setTextColor(getColor(indext == 3 ? R.color.grey : R.color.white));
        weightTv.setTextColor(getColor(indext == 3 ? R.color.grey : R.color.white));
    }

    private void setData() {
        if (postion == 2) {
            if (indext == 1) {
                initTab();
                indext = 2;
                setText();
            } else if (indext == 2) {
                indext = 3;
                setText();
                tabLayout.removeAllTabs();
                tabLayout.addTab(tabLayout.newTab().setText("Weight"));
                pregNext.setText("Commit");
                postion = 3;
            }
        } else if (postion == 3) {
            //保存用户数据
            pregEdit.setVisibility(View.VISIBLE);
            pregNext.setText("Next");
            editLl.setVisibility(View.GONE);
            postion = 0;
            indext = 0;
            initTab();
            setText();

        } else {
            postion++;
            tabLayout.getTabAt(postion).select();
        }
    }

    private void setView() {
        String msg = preg_et.getText().toString();
        StringBuilder time = new StringBuilder(pregancyTime.getText().toString().trim());
        StringBuilder time2 = new StringBuilder(expectingTime.getText().toString().trim());
        if (!msg.trim().equals("")) {
            switch (postion) {
                case 0:
                    if (msg.trim().length() == 4 && Integer.parseInt(msg) <= 2100) {
                        if (indext == 1) {
                            pregancyTime.setText(time.replace(0, 4, msg));
                        } else if (indext == 2) {
                            expectingTime.setText(time2.replace(0, 4, msg));
                        }
                        preg_et.setText("");
                        setData();
                    } else
                        toastor.showSingletonToast("请输入合法年份");

                    break;
                case 1:
                    if (msg.trim().length() < 3 && Integer.parseInt(msg) <= 12) {
                        if (indext == 1) {
                            pregancyTime.setText(time.replace(5, 7, msg.length() == 2 ? msg : "0" + msg));
                        } else if (indext == 2) {
                            expectingTime.setText(time2.replace(5, 7, msg.length() == 2 ? msg : "0" + msg));
                        }
                        preg_et.setText("");
                        setData();
                    } else
                        toastor.showSingletonToast("请输入合法月份");
                    break;
                case 2:
                    if (msg.trim().length() < 3 &&
                            Integer.parseInt(msg) <= DateUtil.getDaysOfMonth(indext == 1 ? time.substring(0, 4) : time2.substring(0, 4),
                                    indext == 1 ? time.substring(5, 7) : time2.substring(5, 7))) {
                        if (indext == 1) {

                            pregancyTime.setText(time.replace(8, 10, msg.length() == 2 ? msg : "0" + msg));
                        } else if (indext == 2) {
                            expectingTime.setText(time2.replace(8, 10, msg.length() == 2 ? msg : "0" + msg));
                        }
                        preg_et.setText("");
                        setData();
                    } else
                        toastor.showSingletonToast("请输入合法日期");
                    break;
                case 3:
                    if (msg.trim().length() <= 3 && Integer.parseInt(msg) <= 250) {
                        weightTv.setText(msg + " lbs");
                        preg_et.setText("");
                        setData();
                    } else
                        toastor.showSingletonToast("请输入正确体重");
                    break;
            }


        } else {
            toastor.showSingletonToast("输入内容不能为空");
        }
    }
}

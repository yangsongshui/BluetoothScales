package myapplication.com.bluetoothscales.activity;

import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import myapplication.com.bluetoothscales.R;
import myapplication.com.bluetoothscales.base.BaseActivity;

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

    @Override
    protected int getContentView() {
        return R.layout.activity_preg;
    }

    @Override
    protected void init() {
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


    @OnClick({R.id.preg_back, R.id.preg_next, R.id.preg_edit, R.id.preg_pregancy, R.id.preg_expecting, R.id.preg_weight})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.preg_back:
                finish();
                break;
            case R.id.preg_edit:
                pregEdit.setVisibility(View.GONE);
                editLl.setVisibility(View.VISIBLE);
                break;
            case R.id.preg_next:
                preg_et.setText("");
                if (postion == 2) {
                    tabLayout.removeAllTabs();
                    tabLayout.addTab(tabLayout.newTab().setText("Weight"));
                    pregNext.setText("Commit");
                    postion = 3;
                } else if (postion == 3) {
                    //保存用户数据
                    pregEdit.setVisibility(View.VISIBLE);
                    pregNext.setText("Next");
                    editLl.setVisibility(View.GONE);
                    postion = 0;
                    initTab();
                } else {
                    postion++;
                    tabLayout.getTabAt(postion).select();
                }

                break;
            case R.id.preg_pregancy:
                preg_et.setText("");
                initTab();
                break;
            case R.id.preg_expecting:
                preg_et.setText("");
                initTab();
                break;
            case R.id.preg_weight:
                preg_et.setText("");
                tabLayout.removeAllTabs();
                tabLayout.addTab(tabLayout.newTab().setText("Weight"));
                break;
        }
    }

    private void initTab() {
        tabLayout.removeAllTabs();
        tabLayout.addTab(tabLayout.newTab().setText("Year"));
        tabLayout.addTab(tabLayout.newTab().setText("Month"));
        tabLayout.addTab(tabLayout.newTab().setText("Day"));

    }

}

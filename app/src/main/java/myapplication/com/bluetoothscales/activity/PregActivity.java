package myapplication.com.bluetoothscales.activity;

import android.support.design.widget.TabLayout;
import android.view.View;
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


    @OnClick({R.id.preg_back, R.id.preg_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.preg_back:
                finish();
                break;
            case R.id.preg_next:
                if (postion == 3) {
                    tabLayout.removeAllTabs();
                    tabLayout.addTab(tabLayout.newTab().setText("Weight"));
                    pregNext.setText("Commit");
                    postion = 4;
                } else if (postion == 4) {
                    //保存用户数据
                }
                break;
        }
    }
}

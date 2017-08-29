package myapplication.com.bluetoothscales.activity;

import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import butterknife.BindView;
import myapplication.com.bluetoothscales.R;
import myapplication.com.bluetoothscales.base.BaseActivity;
import myapplication.com.bluetoothscales.base.BaseFragment;
import myapplication.com.bluetoothscales.fragment.DiscoverFragment;
import myapplication.com.bluetoothscales.fragment.HomeFragment;
import myapplication.com.bluetoothscales.fragment.TrendFragment;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.main_rgrpNavigation)
    RadioGroup mainRgrpNavigation;
    private Fragment[] frags = new Fragment[3];
    protected BaseFragment currentFragment;
    private HomeFragment homeFragment;
    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        initData();
        mainRgrpNavigation.setOnCheckedChangeListener(this);
        mainRgrpNavigation.check(R.id.main_home);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.main_home:
                showFragment(0);
                break;
            case R.id.main_trend:
                showFragment(1);
                break;
            case R.id.main_discover:
                showFragment(2);
                break;

        }
    }

    private void initData() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }

        if (!homeFragment.isAdded()) {

            getSupportFragmentManager().beginTransaction().add(R.id.main_frame, homeFragment).commit();

            currentFragment = homeFragment;
        }

    }
    private void showFragment(int position) {
        if (frags[position] == null) {
            frags[position] = getFrag(position);
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), frags[position]);
    }

    @Nullable
    private Fragment getFrag(int index) {
        switch (index) {
            case 0:
                return new HomeFragment();
            case 1:
                return new TrendFragment();
            case 2:
                return new DiscoverFragment();

            default:
                return null;
        }
    }

    /**
     * 添加或者显示 fragment
     *
     * @param transaction
     * @param fragment
     */
    protected void addOrShowFragment(FragmentTransaction transaction, Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment).add(R.id.main_frame, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }

        currentFragment = (BaseFragment) fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

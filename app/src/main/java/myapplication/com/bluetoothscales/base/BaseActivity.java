package myapplication.com.bluetoothscales.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.gyf.barlibrary.ImmersionBar;

import butterknife.ButterKnife;
import myapplication.com.bluetoothscales.R;
import myapplication.com.bluetoothscales.app.MyApplication;


public abstract class BaseActivity extends FragmentActivity {
    //添加到活动管理集合中
    {
        MyApplication.newInstance().addActyToList(this);
    }

    private ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getContentView());
        ButterKnife.bind(this);
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.statusBarColor(R.color.blue);
        mImmersionBar.init();
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.newInstance().removeActyFromList(this);
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }

    //注入布局
    protected abstract int getContentView();

    //初始化
    protected abstract void init();
}

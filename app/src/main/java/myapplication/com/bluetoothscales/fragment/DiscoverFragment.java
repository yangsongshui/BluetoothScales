package myapplication.com.bluetoothscales.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import myapplication.com.bluetoothscales.R;
import myapplication.com.bluetoothscales.base.BaseFragment;
import myapplication.com.bluetoothscales.utils.FragmentEvent;
import myapplication.com.bluetoothscales.utils.SpUtils;


public class DiscoverFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {


    @BindView(R.id.discover_ll)
    LinearLayout discoverLl;
    @BindView(R.id.discover_title)
    TextView discoverTitle;
    int mode = 0;
    boolean sex = false;
    @BindView(R.id.preg_check)
    CheckBox pregCheck;
    @BindView(R.id.shangyige)
    ImageView shangyige;
    @BindView(R.id.xiayige)
    ImageView xiayige;
    @BindView(R.id.preg_ll)
    LinearLayout pregLl;
    @BindView(R.id.preg_check2)
    CheckBox pregCheck2;
    @BindView(R.id.preg_tv)
    TextView pregTv;
    @BindView(R.id.preg_ll2)
    TextView pregLl2;
    int[] id = {R.string.discover_msg1, R.string.discover_msg3, R.string.discover_msg4, R.string.discover_msg5, R.string.discover_msg6, R.string.discover_msg7};

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        mode = SpUtils.getInt("type", 1);
        EventBus.getDefault().register(this);
        initView();
        pregCheck.setOnCheckedChangeListener(this);
        pregCheck2.setOnCheckedChangeListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_discover;
    }


    private void initView() {

        if (mode == 3) {

            discoverTitle.setText("Baby Discover");
        } else if (mode == 2) {

            discoverTitle.setText("Pregnancy Discover");
        } else if (mode == 1) {
            discoverTitle.setText("Workout Discover");
        }
        discoverLl.setBackground(SpUtils.getString("sex", "boy").equals("girl") ? getResources().getDrawable(R.drawable.main_home2) : getResources().getDrawable(R.drawable.main_home));
    }

    @SuppressLint("StringFormatMatches")
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(FragmentEvent event) {
        mode = event.getDistance();
        initView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);//反注册EventBus
    }


    int indext = 0;

    @OnClick({R.id.shangyige, R.id.xiayige})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shangyige:
                indext--;
                shangyige.setVisibility(indext == 0 ? View.INVISIBLE : View.VISIBLE);
                xiayige.setVisibility(View.VISIBLE);
                pregTv.setText(id[indext]);

                break;
            case R.id.xiayige:
                indext++;
                shangyige.setVisibility(View.VISIBLE);
                xiayige.setVisibility(indext == 5 ? View.INVISIBLE : View.VISIBLE);
                pregTv.setText(id[indext]);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.preg_check:
                pregLl.setVisibility(b?View.VISIBLE:View.GONE);
                break;
            case R.id.preg_check2:
                pregLl2.setVisibility(b?View.VISIBLE:View.GONE);
                break;
        }
    }
}

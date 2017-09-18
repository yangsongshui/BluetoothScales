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
    @BindView(R.id.baby_check)
    CheckBox babyCheck;
    @BindView(R.id.baby_shangyige)
    ImageView babyShangyige;
    @BindView(R.id.baby_tv)
    TextView babyTv;
    @BindView(R.id.baby_xiayige)
    ImageView babyXiayige;
    @BindView(R.id.baby_ll)
    LinearLayout babyLl;
    @BindView(R.id.baby_check2)
    CheckBox babyCheck2;
    @BindView(R.id.baby_shangyige2)
    ImageView babyShangyige2;
    @BindView(R.id.baby_tv2)
    TextView babyTv2;
    @BindView(R.id.baby_xiayige2)
    ImageView babyXiayige2;
    @BindView(R.id.baby_ll2)
    LinearLayout babyLl2;
    @BindView(R.id.discover_baby_ll)
    LinearLayout discoverBabyLl;
    @BindView(R.id.discover_preg_ll)
    LinearLayout discoverPregLl;
    @BindView(R.id.work_cb1)
    CheckBox workCb1;
    @BindView(R.id.work_cb2)
    CheckBox workCb2;
    @BindView(R.id.work_cb3)
    CheckBox workCb3;
    @BindView(R.id.work_cb4)
    CheckBox workCb4;
    @BindView(R.id.shangyige3)
    ImageView shangyige3;
    @BindView(R.id.work_tv)
    TextView workTv;
    @BindView(R.id.xiayige3)
    ImageView xiayige3;
    @BindView(R.id.wrok_ll)
    LinearLayout wrokLl;
    @BindView(R.id.discover_work_ll)
    LinearLayout discoverWorkLl;
    @BindView(R.id.cb_tv1)
    TextView cbTv1;
    @BindView(R.id.cb_tv2)
    TextView cbTv2;
    @BindView(R.id.cb_tv3)
    TextView cbTv3;
    int[] id = {R.string.discover_msg1, R.string.discover_msg3, R.string.discover_msg4, R.string.discover_msg5, R.string.discover_msg6, R.string.discover_msg7};
    int[] id2 = {R.string.discover_baby1, R.string.discover_baby2, R.string.discover_baby3};
    int[] id3 = {R.string.discover_baby21, R.string.discover_baby22, R.string.discover_baby23, R.string.discover_baby24, R.string.discover_baby25, R.string.discover_baby26};
    int[] id4 = {R.string.discover_work4, R.string.discover_work5, R.string.discover_work6, R.string.discover_work7, R.string.discover_work8};

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        mode = SpUtils.getInt("type", 1);
        EventBus.getDefault().register(this);
        initView();
        pregCheck.setOnCheckedChangeListener(this);
        pregCheck2.setOnCheckedChangeListener(this);
        babyCheck.setOnCheckedChangeListener(this);
        babyCheck2.setOnCheckedChangeListener(this);
        workCb4.setOnCheckedChangeListener(this);
        workCb3.setOnCheckedChangeListener(this);
        workCb2.setOnCheckedChangeListener(this);
        workCb1.setOnCheckedChangeListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_discover;
    }


    private void initView() {

        if (mode == 3) {
            discoverTitle.setText("Baby Discover");
            discoverLl.setBackground(SpUtils.getString("sex", "boy").equals("girl") ? getResources().getDrawable(R.drawable.main_home2) : getResources().getDrawable(R.drawable.main_home));
        } else if (mode == 2) {

            discoverTitle.setText("Pregnancy Discover");
        } else if (mode == 1) {
            discoverTitle.setText("Workout Discover");
        }
        discoverPregLl.setVisibility(mode == 2 ? View.VISIBLE : View.GONE);
        discoverWorkLl.setVisibility(mode == 1 ? View.VISIBLE : View.GONE);
        discoverBabyLl.setVisibility(mode == 3 ? View.VISIBLE : View.GONE);
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
    int indext2 = 0;
    int indext3 = 0;
    int indext4 = 0;

    @OnClick({R.id.shangyige, R.id.xiayige, R.id.shangyige3, R.id.xiayige3, R.id.baby_shangyige, R.id.baby_xiayige, R.id.baby_shangyige2, R.id.baby_xiayige2})
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
            case R.id.baby_shangyige:
                indext2--;
                babyShangyige.setVisibility(indext2 == 0 ? View.INVISIBLE : View.VISIBLE);
                babyXiayige.setVisibility(View.VISIBLE);
                babyTv.setText(id[indext2]);

                break;
            case R.id.baby_xiayige:
                indext2++;
                babyShangyige.setVisibility(View.VISIBLE);
                babyXiayige.setVisibility((indext2 == 2) ? View.INVISIBLE : View.VISIBLE);
                babyTv.setText(id2[indext2]);
                break;
            case R.id.baby_shangyige2:
                indext3--;
                babyShangyige2.setVisibility((indext3 == 0) ? View.INVISIBLE : View.VISIBLE);
                babyXiayige2.setVisibility(View.VISIBLE);
                babyTv2.setText(id3[indext3]);

                break;
            case R.id.baby_xiayige2:
                indext3++;
                babyShangyige2.setVisibility(View.VISIBLE);
                babyXiayige2.setVisibility(indext3 == 5 ? View.INVISIBLE : View.VISIBLE);
                babyTv2.setText(id3[indext3]);
                break;
            case R.id.shangyige3:
                indext4--;
                shangyige3.setVisibility(indext4 == 0 ? View.INVISIBLE : View.VISIBLE);
                xiayige3.setVisibility(View.VISIBLE);
                workTv.setText(id4[indext4]);

                break;
            case R.id.xiayige3:
                indext4++;
                shangyige3.setVisibility(View.VISIBLE);
                xiayige3.setVisibility(indext4 == 4 ? View.INVISIBLE : View.VISIBLE);
                workTv.setText(id4[indext4]);
                break;

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.preg_check:
                pregLl.setVisibility(b ? View.VISIBLE : View.GONE);
                break;
            case R.id.preg_check2:
                pregLl2.setVisibility(b ? View.VISIBLE : View.GONE);
                break;
            case R.id.work_cb1:
                cbTv1.setVisibility(b ? View.VISIBLE : View.GONE);
                break;
            case R.id.work_cb2:
                cbTv2.setVisibility(b ? View.VISIBLE : View.GONE);
                break;
            case R.id.work_cb3:
                cbTv3.setVisibility(b ? View.VISIBLE : View.GONE);
                break;
            case R.id.work_cb4:
                wrokLl.setVisibility(b ? View.VISIBLE : View.GONE);
                break;
            case R.id.baby_check:
                babyLl.setVisibility(b ? View.VISIBLE : View.GONE);
                break;
            case R.id.baby_check2:
                babyLl2.setVisibility(b ? View.VISIBLE : View.GONE);
                break;
        }
    }


}

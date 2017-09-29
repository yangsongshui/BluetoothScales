package myapplication.com.bluetoothscales.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import myapplication.com.bluetoothscales.CustomViewPager;
import myapplication.com.bluetoothscales.OnItemViewClickListener;
import myapplication.com.bluetoothscales.R;
import myapplication.com.bluetoothscales.adapter.MyPagerAdapter;
import myapplication.com.bluetoothscales.base.BaseFragment;
import myapplication.com.bluetoothscales.utils.FragmentEvent;
import myapplication.com.bluetoothscales.utils.SpUtils;


public class DiscoverFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.discover_ll)
    LinearLayout discoverLl;
    @BindView(R.id.discover_title)
    TextView discoverTitle;
    @BindView(R.id.discover_baby_ll)
    LinearLayout discoverBabyLl;
    @BindView(R.id.discover_preg_ll)
    LinearLayout discoverPregLl;
    @BindView(R.id.discover_work_ll)
    LinearLayout discoverWorkLl;
    @BindView(R.id.preg_pager)
    ViewPager pregPager;
    @BindView(R.id.preg_pager2)
    ViewPager pregPager2;
    @BindView(R.id.preg_pager3)
    ViewPager pregPager3;
    @BindView(R.id.preg_check)
    CheckBox pregChek;
    @BindView(R.id.preg_check2)
    CheckBox pregChek2;
    @BindView(R.id.preg_check3)
    CheckBox pregChek3;
    @BindView(R.id.work_cb1)
    CheckBox workCb1;
    @BindView(R.id.work_cb2)
    CheckBox workCb2;
    @BindView(R.id.work_cb3)
    CheckBox workCb3;
    @BindView(R.id.work_cb4)
    CheckBox workCb4;
    @BindView(R.id.work_cb5)
    CheckBox workCb5;
    @BindView(R.id.cb_tv3)
    LinearLayout cbTv3;
    @BindView(R.id.cb_tv2)
    LinearLayout cbTv2;
    @BindView(R.id.cb_tv1)
    LinearLayout cbTv1;
    @BindView(R.id.wrok_pager)
    CustomViewPager workLl;
    @BindView(R.id.wrok_pager2)
    CustomViewPager workLl2;
    @BindView(R.id.baby_pager)
    CustomViewPager BabyPager;
    @BindView(R.id.baby_pager2)
    CustomViewPager BabyPager2;
    @BindView(R.id.baby_check)
    CheckBox babyCheck;
    @BindView(R.id.baby_check2)
    CheckBox babyCheck2;

    int mode = 0;
    boolean sex = false;


    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        mode = SpUtils.getInt("type", 1);
        EventBus.getDefault().register(this);
        initView();


    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_discover;
    }


    private void initView() {
        if (mode == 3) {
            discoverTitle.setText("Baby Discover");
            discoverLl.setBackground(SpUtils.getString("sex", "Boy").equals("Girl") ? getResources().getDrawable(R.drawable.main_home2) : getResources().getDrawable(R.drawable.main_home));
            initBabyPregr();
            initBabyPregr2();
        } else if (mode == 2) {
            discoverTitle.setText("Pregnancy Discover");
            initPregList();
            initPregList2();
            initPregList3();
        } else if (mode == 1) {
            discoverTitle.setText("Workout Discover");
            initWrokList();
            initWrokList2();
        }
        discoverPregLl.setVisibility(mode == 2 ? View.VISIBLE : View.GONE);
        discoverWorkLl.setVisibility(mode == 1 ? View.VISIBLE : View.GONE);
        discoverBabyLl.setVisibility(mode == 3 ? View.VISIBLE : View.GONE);
        pregChek.setOnCheckedChangeListener(this);
        pregChek2.setOnCheckedChangeListener(this);
        pregChek3.setOnCheckedChangeListener(this);
        workCb1.setOnCheckedChangeListener(this);
        workCb2.setOnCheckedChangeListener(this);
        workCb3.setOnCheckedChangeListener(this);
        workCb4.setOnCheckedChangeListener(this);
        workCb5.setOnCheckedChangeListener(this);
        babyCheck.setOnCheckedChangeListener(this);
        babyCheck2.setOnCheckedChangeListener(this);
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

    private void initWrokList() {
        List<String> title = new ArrayList<>();
        List<String> msg = new ArrayList<>();
        title.add(getString(R.string.discover_title));
        title.add(getString(R.string.discover_title2));
        title.add(getString(R.string.discover_title3));
        title.add(getString(R.string.discover_title4));
        title.add(getString(R.string.discover_title5));

        msg.add(getString(R.string.discover_diet_msg));
        msg.add(getString(R.string.discover_diet_msg2));
        msg.add(getString(R.string.discover_diet_msg3));
        msg.add(getString(R.string.discover_diet_msg4));
        msg.add(getString(R.string.discover_diet_msg5));

        workLl.setOffscreenPageLimit(4);
        MyPagerAdapter adapter = new MyPagerAdapter(title, msg, getActivity());
        workLl.setAdapter(adapter);
        adapter.setOnItemViewClickListener(new OnItemViewClickListener() {
            @Override
            public void OnItemView(int position, View view, boolean is) {
                if (is)
                    workLl.setCurrentItem(position - 1);
                else
                    workLl.setCurrentItem(position + 1);
            }
        });

    }

    private void initWrokList2() {
        List<String> title = new ArrayList<>();
        List<String> msg = new ArrayList<>();

        title.add(getString(R.string.discover_title6));
        title.add(getString(R.string.discover_title7));

        msg.add(getString(R.string.discover_diet_msg6));
        msg.add(getString(R.string.discover_diet_msg7));
        workLl2.setOffscreenPageLimit(4);
        MyPagerAdapter adapter = new MyPagerAdapter(title, msg, getActivity());
        workLl2.setAdapter(adapter);
        adapter.setOnItemViewClickListener(new OnItemViewClickListener() {
            @Override
            public void OnItemView(int position, View view, boolean is) {
                if (is)
                    workLl2.setCurrentItem(position - 1);
                else
                    workLl2.setCurrentItem(position + 1);
            }
        });

    }

    private void initPregList() {
        List<String> title = new ArrayList<>();
        List<String> msg = new ArrayList<>();
        title.add(getString(R.string.preg_title));
        title.add(getString(R.string.preg_title2));
        title.add(getString(R.string.preg_title3));
        msg.add(getString(R.string.preg_msg));
        msg.add(getString(R.string.preg_msg2));
        msg.add(getString(R.string.preg_msg3));
        pregPager.setOffscreenPageLimit(3);
        MyPagerAdapter adapter = new MyPagerAdapter(title, msg, getActivity());
        pregPager.setAdapter(adapter);
        adapter.setOnItemViewClickListener(new OnItemViewClickListener() {
            @Override
            public void OnItemView(int position, View view, boolean is) {
                if (is)
                    pregPager.setCurrentItem(position - 1);
                else
                    pregPager.setCurrentItem(position + 1);
            }
        });

    }

    private void initPregList2() {
        List<String> title = new ArrayList<>();
        List<String> msg = new ArrayList<>();
        title.add(getString(R.string.preg2_title));
        title.add(getString(R.string.preg2_title2));
        title.add(getString(R.string.preg2_title3));
        title.add(getString(R.string.preg2_title4));
        title.add(getString(R.string.preg2_title5));
        title.add(getString(R.string.preg2_title6));
        msg.add(getString(R.string.preg2_msg));
        msg.add(getString(R.string.preg2_msg2));
        msg.add(getString(R.string.preg2_msg3));
        msg.add(getString(R.string.preg2_msg4));
        msg.add(getString(R.string.preg2_msg5));
        msg.add(getString(R.string.preg2_msg6));
        pregPager3.setOffscreenPageLimit(3);
        MyPagerAdapter adapter2 = new MyPagerAdapter(title, msg, getActivity());
        pregPager3.setAdapter(adapter2);
        adapter2.setOnItemViewClickListener(new OnItemViewClickListener() {
            @Override
            public void OnItemView(int position, View view, boolean is) {
                if (is)
                    pregPager3.setCurrentItem(position - 1);
                else
                    pregPager3.setCurrentItem(position + 1);
            }
        });
    }

    private void initPregList3() {
        List<String> title = new ArrayList<>();
        List<String> msg = new ArrayList<>();
        title.add(getString(R.string.preg3_title));
        title.add(getString(R.string.preg3_title2));
        title.add(getString(R.string.preg3_title3));
        title.add(getString(R.string.preg3_title4));
        msg.add(getString(R.string.preg3_msg));
        msg.add(getString(R.string.preg3_msg2));
        msg.add(getString(R.string.preg3_msg3));
        msg.add(getString(R.string.preg3_msg4));
        pregPager2.setOffscreenPageLimit(3);
        MyPagerAdapter adapter3 = new MyPagerAdapter(title, msg, getActivity());
        pregPager2.setAdapter(adapter3);
        adapter3.setOnItemViewClickListener(new OnItemViewClickListener() {
            @Override
            public void OnItemView(int position, View view, boolean is) {
                if (is)
                    pregPager2.setCurrentItem(position - 1);
                else
                    pregPager2.setCurrentItem(position + 1);
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.preg_check:
                pregPager.setVisibility(b ? View.VISIBLE : View.GONE);
                break;
            case R.id.preg_check2:
                pregPager2.setVisibility(b ? View.VISIBLE : View.GONE);
                break;
            case R.id.preg_check3:
                pregPager3.setVisibility(b ? View.VISIBLE : View.GONE);
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
                workLl.setVisibility(b ? View.VISIBLE : View.GONE);
                break;
            case R.id.baby_check:
                BabyPager.setVisibility(b ? View.VISIBLE : View.GONE);
                break;
            case R.id.baby_check2:
                BabyPager2.setVisibility(b ? View.VISIBLE : View.GONE);
                break;
        }
    }

    private void initBabyPregr() {
        List<String> title = new ArrayList<>();
        List<String> msg = new ArrayList<>();
        title.add(getString(R.string.discover_baby_title));
        title.add(getString(R.string.discover_baby_title2));
        title.add(getString(R.string.discover_baby_title3));
        title.add(getString(R.string.discover_baby_title4));
        msg.add(getString(R.string.discover_baby_msg));
        msg.add(getString(R.string.discover_baby_msg2));
        msg.add(getString(R.string.discover_baby_msg3));
        msg.add(getString(R.string.discover_baby_msg4));
        BabyPager.setOffscreenPageLimit(3);
        MyPagerAdapter adapter3 = new MyPagerAdapter(title, msg, getActivity());
        BabyPager.setAdapter(adapter3);
        adapter3.setOnItemViewClickListener(new OnItemViewClickListener() {
            @Override
            public void OnItemView(int position, View view, boolean is) {
                if (is)
                    BabyPager.setCurrentItem(position - 1);
                else
                    BabyPager.setCurrentItem(position + 1);
            }
        });
    }

    private void initBabyPregr2() {
        List<String> title = new ArrayList<>();
        List<String> msg = new ArrayList<>();
        title.add(getString(R.string.discover_baby2_title));
        title.add(getString(R.string.discover_baby2_title2));
        title.add(getString(R.string.discover_baby2_title3));
        title.add(getString(R.string.discover_baby2_title4));
        title.add(getString(R.string.discover_baby2_title5));
        msg.add(getString(R.string.discover_baby2_msg));
        msg.add(getString(R.string.discover_baby2_msg2));
        msg.add(getString(R.string.discover_baby2_msg3));
        msg.add(getString(R.string.discover_baby2_msg4));
        msg.add(getString(R.string.discover_baby2_msg5));
        BabyPager2.setOffscreenPageLimit(4);
        MyPagerAdapter adapter3 = new MyPagerAdapter(title, msg, getActivity());
        BabyPager2.setAdapter(adapter3);
        adapter3.setOnItemViewClickListener(new OnItemViewClickListener() {
            @Override
            public void OnItemView(int position, View view, boolean is) {
                if (is)
                    BabyPager2.setCurrentItem(position - 1);
                else
                    BabyPager2.setCurrentItem(position + 1);
            }
        });
    }
}

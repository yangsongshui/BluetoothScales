package myapplication.com.bluetoothscales.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import myapplication.com.bluetoothscales.R;

/**
 * Created by ys on 2017/9/22.
 */

public class MyPagerAdapter extends PagerAdapter {
    List<String> titles;
    List<String> msg;
    Context context;
    private List<View> mView = new ArrayList<View>();

    public MyPagerAdapter(List<String> titles, List<String> msg, Context context) {
        this.titles = titles;
        this.msg = msg;
        this.context = context;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.pager_item, container, false);
        container.addView(view);
        mView.add(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object)
    {
        container.removeView(mView.get(position));
    }
}

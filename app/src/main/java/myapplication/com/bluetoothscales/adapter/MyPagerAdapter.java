package myapplication.com.bluetoothscales.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import myapplication.com.bluetoothscales.OnItemViewClickListener;
import myapplication.com.bluetoothscales.R;

/**
 * Created by ys on 2017/9/22.
 */

public class MyPagerAdapter extends PagerAdapter {
    List<String> titles;
    List<String> msg;
    Context context;
    private List<View> mView = new ArrayList<View>();
    private OnItemViewClickListener onItemViewClickListener;

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
    public Object instantiateItem(ViewGroup container, final int position) {
        final View view = LayoutInflater.from(context).inflate(R.layout.pager_item, container, false);
        ((TextView) view.findViewById(R.id.title)).setText(titles.get(position));
        ((TextView) view.findViewById(R.id.msg)).setText(msg.get(position));

        view.findViewById(R.id.shangyige).setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
        view.findViewById(R.id.xiayige).setVisibility(position == (titles.size() - 1) ? View.INVISIBLE : View.VISIBLE);
        view.findViewById(R.id.shangyige).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemViewClickListener != null)
                    onItemViewClickListener.OnItemView(position, view,true);
            }
        });
        view.findViewById(R.id.xiayige).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemViewClickListener != null)
                    onItemViewClickListener.OnItemView(position, view,false);
            }
        });
        container.addView(view);
        mView.add(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        container.removeView(mView.get(position));
    }

    public void setOnItemViewClickListener(OnItemViewClickListener onItemViewClickListener) {
        this.onItemViewClickListener = onItemViewClickListener;
    }
}

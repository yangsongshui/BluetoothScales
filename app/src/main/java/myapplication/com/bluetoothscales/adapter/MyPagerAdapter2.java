package myapplication.com.bluetoothscales.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import myapplication.com.bluetoothscales.OnItemViewClickListener;
import myapplication.com.bluetoothscales.R;

/**
 * Created by ys on 2017/9/22.
 */

public class MyPagerAdapter2 extends PagerAdapter {

    int[] msg;

    Context context;
    private List<View> mView = new ArrayList<View>();
    private OnItemViewClickListener onItemViewClickListener;

    public MyPagerAdapter2(int[] msg, Context context) {

        this.msg = msg;
        this.context = context;
    }

    @Override
    public int getCount() {
        return msg.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final View view = LayoutInflater.from(context).inflate(R.layout.pager_item2, container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.pager_iv);
        imageView.setImageResource(msg[position]);
        view.findViewById(R.id.shangyige).setVisibility(position == 0 ? View.INVISIBLE : View.VISIBLE);
        view.findViewById(R.id.xiayige).setVisibility(position == (msg.length - 1) ? View.INVISIBLE : View.VISIBLE);
        view.findViewById(R.id.shangyige).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemViewClickListener != null)
                    onItemViewClickListener.OnItemView(position, view, true);
            }
        });
        view.findViewById(R.id.xiayige).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemViewClickListener != null)
                    onItemViewClickListener.OnItemView(position, view, false);
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

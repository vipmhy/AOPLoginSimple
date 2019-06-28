package com.mars.aoploginsimple.adapter;

import android.widget.TextView;

import com.mars.aoploginsimple.R;
import com.mars.aoploginsimple.bean.UserInfo;
import com.mars.ioclibrary.recyclerview.RViewAdapter;
import com.mars.ioclibrary.recyclerview.RViewHolder;

import java.util.List;

public class SampleAdapter extends RViewAdapter<UserInfo> {

    public SampleAdapter(List<UserInfo> datas) {
        super(datas);
    }

    @Override
    public int getLayoutId() {
        return R.layout.adapter_item;
    }

    @Override
    public void convert(RViewHolder holder, UserInfo info) {
        TextView textView = holder.getView(R.id.item_tv);
        textView.setText(info.toString());
    }
}

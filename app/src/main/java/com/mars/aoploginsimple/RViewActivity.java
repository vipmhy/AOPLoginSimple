package com.mars.aoploginsimple;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.mars.aoploginsimple.adapter.SampleAdapter;
import com.mars.aoploginsimple.base.BaseActivity;
import com.mars.aoploginsimple.bean.UserInfo;
import com.mars.ioclibrary.InjectManager;
import com.mars.ioclibrary.annotation.ContentView;
import com.mars.ioclibrary.annotation.InjectView;
import com.mars.ioclibrary.annotation.OnItemClick;
import com.mars.ioclibrary.annotation.OnItemLongClick;
import com.mars.ioclibrary.recyclerview.RView;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_recyclerview)
public class RViewActivity extends BaseActivity {

    @InjectView(R.id.recyclerView)
    private RView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initRView();
    }

    private List<UserInfo> initDatas() {
        List<UserInfo> datas = new ArrayList<>();
        for (int i = 0; i < 49; i++) {
            datas.add(new UserInfo("netease", "p" + i));
        }
        return datas;
    }

    private void initRView() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        SampleAdapter adapter = new SampleAdapter(initDatas());
        recyclerView.setRViewAdapter(adapter);
        recyclerView.setAdapter(adapter);
        InjectManager.injectEvents(this);
    }

    @OnItemClick(R.id.recyclerView)
    public void itemClick(View view, UserInfo info, int position) {
        Toast.makeText(this, "OnItemClick\n" + info.getPassword(), Toast.LENGTH_SHORT).show();
    }

    @OnItemLongClick(R.id.recyclerView)
    public boolean itemLongClick(View view, UserInfo info, int position) {
        Toast.makeText(this, "OnItemLongClick\n" + info.getPassword(), Toast.LENGTH_SHORT).show();
        return true;
    }
}

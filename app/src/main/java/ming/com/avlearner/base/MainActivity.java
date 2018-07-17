package ming.com.avlearner.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

import ming.com.avlearner.R;
import ming.com.avlearner.audio.AudioRecordActivity;
import ming.com.avlearner.ctest.CTestActivity;

public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    ListView listview;
    ArrayList<DemoBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initEvent();
    }

    private void initData() {
        mList = new ArrayList<>();
        addDemo();
    }

    private void initView() {
        listview = (ListView) findViewById(R.id.list);
    }

    private void initEvent() {
        MyAdapter myAdapter = new MyAdapter(mList, this);
        listview.setAdapter(myAdapter);
        listview.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Class activityClass = mList.get(position).getActivityClass();
        if (null != activityClass) {
            Intent mIntent = new Intent();
            mIntent.setClass(this, activityClass);
            startActivity(mIntent);
        } else {
            Toast.makeText(this, "跳转失败", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 添加demo
     */
    public void addDemo() {
        addNDKDemo();
        addAudioRecordDemo();
    }


    public void addNDKDemo() {
        DemoBean demoBean = new DemoBean("NDK Demo", "调试NDK", CTestActivity.class);
        mList.add(demoBean);
    }

    public void addAudioRecordDemo() {
        DemoBean demoBean = new DemoBean("AudioRecord Demo", "一个采用AudioRecord的例子", AudioRecordActivity.class);
        mList.add(demoBean);
    }

}

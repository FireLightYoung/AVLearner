package ming.com.avlearner.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import ming.com.avlearner.R;

/**
 * Created by ming on 17/9/15.
 */

public class MyAdapter extends BaseAdapter {

    ArrayList<DemoBean> mList;
    Context mContext;
    LayoutInflater inflater;

    public MyAdapter(ArrayList<DemoBean> list, Context context) {
        mList = list;
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DemoBean demoBean = mList.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_main, null);
            holder.mTextView1 = (TextView) convertView.findViewById(R.id.item_main_txt1);
            holder.mTextView2 = (TextView) convertView.findViewById(R.id.item_main_txt2);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTextView1.setText(demoBean.getDemoName());
        holder.mTextView2.setText(demoBean.getDescription());
        return convertView;
    }

    class ViewHolder {
        TextView mTextView1;
        TextView mTextView2;

    }
}

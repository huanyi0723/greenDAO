package com.example.greendaoexample.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.greendaoexample.R;
import com.example.greendaoexample.dao.StudentsInfo;

public class StudentAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<StudentsInfo> list; //数据源

	public StudentAdapter(Context context) {
		inflater = LayoutInflater.from(context);
		if (list == null) {
			list = new ArrayList<StudentsInfo>();
		}
	}
	
	//修改列表数据源的接口
	public void setData(List<StudentsInfo> list) {
		//清空列表
		if (list == null || list.size() == 0) {
			this.list.clear();
			notifyDataSetChanged();
			return;
		}
		if (this.list == null) {
			this.list = new ArrayList<StudentsInfo>();
		}
		//添加数据
		this.list.clear();
		this.list.addAll(list);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.listitem, parent, false);
			holder = new ViewHolder();
			holder.tv = (TextView) convertView.findViewById(R.id.tv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tv.setText("姓名： " + list.get(position).getName() + "      " + "年龄： " + list.get(position).getAge());
		return convertView;
	}

	class ViewHolder {
		TextView tv;
	}
}

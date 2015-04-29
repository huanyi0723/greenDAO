package com.example.greendaoexample.activity;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.example.greendaoexample.GreenDaoApplication;
import com.example.greendaoexample.R;
import com.example.greendaoexample.adapter.StudentAdapter;
import com.example.greendaoexample.dao.StudentsInfo;
import com.example.greendaoexample.util.DBHelper;

public class MainActivity extends BaseActivity implements OnClickListener, OnItemClickListener {
	
	private EditText nameEt, ageEt; //姓名 年龄编辑框
	private String name, age;
	private Button add, delete, age_search, all_search;
	private ListView mListView;
	private StudentAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public void initView() {
		nameEt = (EditText) findViewById(R.id.nameEt);
		ageEt = (EditText) findViewById(R.id.ageEt);
		add = (Button) findViewById(R.id.add);
		delete = (Button) findViewById(R.id.delete);
		age_search = (Button) findViewById(R.id.age_search);
		all_search = (Button) findViewById(R.id.all_search);
		mListView = (ListView) findViewById(R.id.listView);
	}

	@Override
	public void initData() {
		mAdapter = new StudentAdapter(this);
		mListView.setAdapter(mAdapter);
		mAdapter.setData(DBHelper.getInstance(getApplicationContext()).getStudentsInfoList());
	}

	@Override
	public void setLinstener() {
		add.setOnClickListener(this);
		delete.setOnClickListener(this);
		all_search.setOnClickListener(this);
		age_search.setOnClickListener(this);
		mListView.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.add:
			name = nameEt.getText().toString().trim();
			age = ageEt.getText().toString().trim();
			if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(age)) {
				StudentsInfo stu = new StudentsInfo();
				stu.setName(name);
				stu.setAge(age);
				DBHelper.getInstance(getApplicationContext()).addToStudentsInfoTable(stu);
				mAdapter.setData(DBHelper.getInstance(getApplicationContext()).getStudentsInfoList());
				// nameEt.setText("");
				// ageEt.setText("");
			}
			break;
		case R.id.delete:
			new AlertDialog.Builder(MainActivity.this).setTitle("提示").setMessage("确定要全部删除么？")
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					DBHelper.getInstance(getApplicationContext()).clearStudentsInfo();
					mAdapter.setData(DBHelper.getInstance(getApplicationContext()).getStudentsInfoList());
				}
			}).setNegativeButton("取消", null).show();
			break;
		case R.id.age_search:
			age = ageEt.getText().toString().trim();
			if (!TextUtils.isEmpty(age)) {
				mAdapter.setData(DBHelper.getInstance(getApplicationContext()).getListByAge(age));
			}else {
				Toast.makeText(this, "请输入年龄", Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.all_search:
				mAdapter.setData(DBHelper.getInstance(getApplicationContext()).getStudentsInfo());
			break;
			
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
		new AlertDialog.Builder(MainActivity.this).setTitle("提示").setMessage("确定要删除么？")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						DBHelper.getInstance(getApplicationContext()).deleteStudentsInfoList(
								((StudentsInfo) mAdapter.getItem(position)).getId());
						mAdapter.setData(DBHelper.getInstance(getApplicationContext()).getStudentsInfoList());
					}
				}).setNegativeButton("取消", null).show();
	}
}

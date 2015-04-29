package com.example.greendaotest;

import java.util.List;

import com.cn.speedchat.greendao.SessionEntity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	private DBHelper dBManager; // 定义一个DBHelper对象，用他来对数据库进行增删改查

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dBManager = DBHelper.getInstance(this); // 得到DBHelper对象
		SessionEntity entity = new SessionEntity(); // 创建一个SessionEntity实体对象，并赋值
		entity.setFrom("A");
		entity.setGossip("大家好吗？我来了...");
		entity.setGossipid(10);
		entity.setSessionid("abcdefg");
		entity.setSessiontype(1);
		entity.setTo("B");
		// 下面这一行就把entity对象存数据库了，然后我们新建一个SessionEntity列表再读一下
		dBManager.saveSession(entity); // 保存到数据库
		// 下面这个方法是查询Session表里面的所有数据返回一个数据列表，相当于select * from table，然后扫描打印出来
		List<SessionEntity> listentity = dBManager.loadAllSession();
		for (int i = 0; i < listentity.size(); i++) {
			SessionEntity tmpEntity = listentity.get(i);
			Log.v("tmpEntity.getFrom()", tmpEntity.getFrom());
			Log.v("tmpEntity.getGossip()", tmpEntity.getGossip());
			Log.v("tmpEntity.getGossipid()", tmpEntity.getGossipid() + "");
			Log.v("tmpEntity.getSessionid()", tmpEntity.getSessionid());
			Log.v("tmpEntity.getSessiontype()", tmpEntity.getSessiontype() + "");
			Log.v("tmpEntity.getTo()", tmpEntity.getTo());
		}
	}

}

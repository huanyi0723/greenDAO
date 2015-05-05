package com.example.greendaotest;

import java.util.List;

import com.cn.speedchat.greendao.SessionEntity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	private DBHelper dBManager; // ����һ��DBHelper���������������ݿ������ɾ�Ĳ�

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dBManager = DBHelper.getInstance(this); // �õ�DBHelper����
		SessionEntity entity = new SessionEntity(); // ����һ��SessionEntityʵ����󣬲���ֵ
		entity.setFrom("A");
		entity.setGossip("��Һ���������...");
		entity.setGossipid(10);
		entity.setSessionid("abcdefg");
		entity.setSessiontype(1);
		entity.setTo("B");
		// ������һ�оͰ�entity��������ݿ��ˣ�Ȼ�������½�һ��SessionEntity�б��ٶ�һ��
		dBManager.saveSession(entity); // ���浽���ݿ�
		// ������������ǲ�ѯSession��������������ݷ���һ�������б����൱��select * from table��Ȼ��ɨ���ӡ����
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
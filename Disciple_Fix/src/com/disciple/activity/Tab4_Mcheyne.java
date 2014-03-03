package com.disciple.activity;

import java.util.ArrayList;
import java.util.List;

import org.disciple.db.ReadDB;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.disciple.model.McheyneVo;

public class Tab4_Mcheyne extends Activity {
	int month;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab4_mcheyne);
		
		month=9;
		showMcheyneListView(month);
		
	}
	
	public void showMcheyneListView(int month) {
		ReadDB db = new ReadDB();
		List<McheyneVo> voList = db.selectMcheyneByMonth(this, month);
		Tab4_McheyneAdapter adapter = new Tab4_McheyneAdapter(this, R.layout.tab4_rows, voList);
		ListView mcheyneListView = (ListView) findViewById(R.id.mcheyneListView);
		mcheyneListView.setAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "������");
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			dialogSelectOption();
			break;
		}
		return false;
	}
	
	public void dialogSelectOption() {
		final String items[] = { "1��", "2��","3��", "4��","5��", "6��","7��", "8��","9��", "10��","11��", "12��"};
		AlertDialog.Builder ab = new AlertDialog.Builder(Tab4_Mcheyne.this);
		ab.setTitle("�� ����");
		ab.setSingleChoiceItems(items, 0,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						month = whichButton + 1; // ����Ʈ ���ý� �� �ٲ��ְ� �̵��ϴ°� ������ �ǰٻ�.
					}
				})
				.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Ȯ�� ��ư Ŭ����
						showMcheyneListView(month);
					}
				})
				.setNegativeButton("���", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// ��ҹ�ư Ŭ����
					}
				});
		ab.show();
	}
}

package com.disciple.activity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;

import org.disciple.db.LoadDB;

import com.disciple.presenter.McheyneClick;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Tab4_Mac extends Activity {
	int month;
	TableLayout tl;
	TextView tv;	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab4_mac);

		int curMonth, curDay;
		
		Calendar c;
		c = Calendar.getInstance();
		curMonth = c.get(Calendar.MONTH) + 1;
		curDay = c.get(Calendar.DAY_OF_MONTH);

		printMonthWords(curMonth);
	}
	
	public void printMonthWords(int month) {
		LoadDB loadDB = new LoadDB();
		String[][] words;
		int[][] reads;
		boolean flag = false;
		int iDay;
		
		Calendar c;
		c = Calendar.getInstance();
		int curMonth = c.get(Calendar.MONTH) + 1;
		int curDay = c.get(Calendar.DAY_OF_MONTH);
		
		tl = (TableLayout) findViewById(R.id.addeditemlayout);
		TextView tv_month = (TextView) findViewById(R.id.month);
		tv_month.setText(month + "��");
		
		words = loadDB.select(month);
		reads = loadDB.select_read(month);
		if(tl.getChildCount()>0) {
			Log.d("childCount", "if" + month);
			tl.removeAllViews();
			tl = (TableLayout) findViewById(R.id.addeditemlayout);
		} else {
			Log.d("childCount", "else"+ month);
		}
		for (int i = 0; i < words.length; i++) {
			TableRow tr = (TableRow) View.inflate(getApplicationContext(), R.layout.tab4_sub, null);
			
			for (int j = 0; j < 5; j++) {
				tv = (TextView) (tr.getChildAt(j));
				tv.setText(words[i][j]);
				iDay = Integer.parseInt(words[i][0]);
				
				if (curMonth == month) {
					if (curDay == i + 1) { // ���� ��¥�� ���� ���, �ؽ�Ʈ bold
						tv.setBackgroundColor(Color.CYAN);
						tv.setPaintFlags(tv.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
					}
				}
				if(j==0) { // day�� ���� ���, �ؽ�Ʈ bold
					tv.setBackgroundColor(Color.LTGRAY);
					tv.setPaintFlags(tv.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
				}else  {   // �����Ŵ� ��ĥ�ϰ�,�������Ŵ� �Ͼ��
					if (reads[i][j]==1) tv.setBackgroundColor(Color.parseColor("#E6E6FA"));
					else if (reads[i][j]==0) tv.setBackgroundColor(Color.parseColor("#F5F5F5"));
				}
				tv.setOnClickListener(new McheyneClick(tr, tv, j, reads[i][j], month, iDay));
			}	
			tl.addView(tr);
		}
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
		AlertDialog.Builder ab = new AlertDialog.Builder(Tab4_Mac.this);
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
						if(month==0) month++;
						printMonthWords(month);
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

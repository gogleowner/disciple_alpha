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
		tv_month.setText(month + "월");
		
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
					if (curDay == i + 1) { // 현재 날짜는 진한 배경, 텍스트 bold
						tv.setBackgroundColor(Color.CYAN);
						tv.setPaintFlags(tv.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
					}
				}
				if(j==0) { // day는 진한 배경, 텍스트 bold
					tv.setBackgroundColor(Color.LTGRAY);
					tv.setPaintFlags(tv.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
				}else  {   // 읽은거는 색칠하고,안읽은거는 하얀색
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
		menu.add(0, 1, 0, "월선택");
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
		final String items[] = { "1월", "2월","3월", "4월","5월", "6월","7월", "8월","9월", "10월","11월", "12월"};
		AlertDialog.Builder ab = new AlertDialog.Builder(Tab4_Mac.this);
		ab.setTitle("월 선택");
		
		ab.setSingleChoiceItems(items, 0,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						month = whichButton + 1; // 리스트 선택시 값 바꿔주고 이동하는거 넣으면 되겟삼.
					}
				})
				.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// 확인 버튼 클릭시
						if(month==0) month++;
						printMonthWords(month);
					}
				})
				.setNegativeButton("취소", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// 취소버튼 클릭시
					}
				});
		ab.show();
	}
}

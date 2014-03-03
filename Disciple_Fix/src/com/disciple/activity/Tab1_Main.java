package com.disciple.activity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.disciple.db.DataConstants;
import org.disciple.db.LoadDB;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.disciple.presenter.GetQT;
import com.disciple.presenter.McheyneClick;
import com.disciple.util.GetCalendar;

public class Tab1_Main extends Activity {

	int[] homework;
	TableLayout tl;
	TextView tv;
	GetQT getQt;
	GetCalendar cal;
	String strQTcontent ;
	String strQTaddr ;
	String qtText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab1_main);
		
		try {
			boolean bResult = isCheckDB(this);
			Log.d("DBCheck", "DB check = " + bResult);
			if(!bResult) copyDB(this);
			
		} catch (Exception e) {
			Log.d("DBCheck", "error !!!!! " + e + "");
		}
		
		new GetQTAnsycTask().execute();
	}
	
	private class GetQTAnsycTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... arg0) {
			getQt = new GetQT();
			try {
				qtText = getQt.loadURL();
			} catch (Exception e) {
				qtText = "" + e;
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void result) {
			cal = new GetCalendar();
			
			TextView tv_today = (TextView) findViewById(R.id.tvtoday);
			TextView tv_qtContent = (TextView) findViewById(R.id.tvqt_content);
			TextView tv_qtAddr = (TextView) findViewById(R.id.tvqt_addr);
			tv_qtAddr.setText("");
			tv_today.setText(cal.getCurMonth() + "월 " + cal.getCurDay() + "일");
			tv_qtContent.setText(qtText);
			
			tl = (TableLayout) findViewById(R.id.mainmacchain);
		    printMonthWords(cal.getCurMonth(), cal.getCurDay());
		    select_home();
		}
	}
	public boolean isCheckDB(Context mContext){
	      String filePath = "/data/data/" + DataConstants.PACKAGE_NAME + "/databases/" + DataConstants.DB_NAME;
	      File file = new File(filePath);
	       if (file.exists()) {
	           return true;
	       }
	       return false;
	}
	
	// DB를 복사하기
	// assets의 /db/xxxx.db 파일을 설치된 프로그램의 내부 DB공간으로 복사하기
	public void copyDB(Context mContext){
	      Log.d("DBCheck", "copyDB");
	      AssetManager manager = mContext.getAssets();
	      String folderPath = "/data/data/" + DataConstants.PACKAGE_NAME + "/databases";
	      String filePath = "/data/data/" + DataConstants.PACKAGE_NAME + "/databases/" + DataConstants.DB_NAME;
	      File folder = new File(folderPath);
	      File file = new File(filePath);
	       
	      FileOutputStream fos = null;
	      BufferedOutputStream bos = null;
	      try {
	       InputStream is = manager.open(DataConstants.DB_NAME);
	       BufferedInputStream bis = new BufferedInputStream(is);
	 
	           if (folder.exists()) {
	           }else{
	               folder.mkdirs();
	           }
	           if (file.exists()) {
	               file.delete();
	               file.createNewFile();
	           }
	            
	           fos = new FileOutputStream(file);
	           bos = new BufferedOutputStream(fos);
	           int read = -1;
	           byte[] buffer = new byte[1024];
	           while ((read = bis.read(buffer, 0, 1024)) != -1) {
	            bos.write(buffer, 0, read);
	           }
	 
	           bos.flush();
	          
	           bos.close();
	           fos.close();
	           bis.close();
	           is.close();
	 
	       } catch (IOException e) {
	       Log.e("ErrorMessage : ", e.getMessage());
	      } 
	}
	
	public void printMonthWords(int month, int day) {
		LoadDB loadDB = new LoadDB();
		String[] words;
		int[] reads;
		words = loadDB.select_today(month, day);
		reads = loadDB.select_read_today(month, day);

		TableRow tr = (TableRow) View.inflate(this, R.layout.tab4_sub, null);
			for (int j = 0; j < 5; j++) {
				tv = (TextView) (tr.getChildAt(j));
				tv.setText(words[j]);
				
				if(j==0) { // day는 진한 배경, 텍스트 bold
					tv.setBackgroundColor(Color.LTGRAY);
					tv.setPaintFlags(tv.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
				}else  {   // 읽은거는 색칠하고,안읽은거는 하얀색
					if (reads[j]==1) tv.setBackgroundColor(Color.parseColor("#E6E6FA"));
					else if (reads[j]==0) tv.setBackgroundColor(Color.parseColor("#F5F5F5"));
				}
				tv.setOnClickListener(new McheyneClick(tr, tv, j, reads[j], month, day));
			}	
		tl.addView(tr);
	}
	
	public void select_home() {
		LoadDB loadDB = new LoadDB();
		// TODO Auto-generated method stub
		homework = loadDB.select_homework();
		
		
		// 초기화 할 때, DB로부터 Check 여부 확인하여, 1이면 체크된체로, 0이면 체크 안된채로 표시.
		final CheckBox cb1 = (CheckBox)findViewById(R.id.checkBox1);
		final CheckBox cb2 = (CheckBox)findViewById(R.id.checkBox2);
		final CheckBox cb3 = (CheckBox)findViewById(R.id.checkBox3);
		final CheckBox cb4 = (CheckBox)findViewById(R.id.checkBox4);
		final CheckBox cb5 = (CheckBox)findViewById(R.id.checkBox5);
		final CheckBox cb6 = (CheckBox)findViewById(R.id.checkBox6);
		final CheckBox cb7 = (CheckBox)findViewById(R.id.checkBox7);
		Button bt_update =(Button)findViewById(R.id.button_update); 
		cb1.setText("생활점검표");
		cb2.setText("묵상");
		cb3.setText("D형큐티");
		cb4.setText("영적일기");
		cb5.setText("설교요약 및 적용");
		cb6.setText("교재예습");
		cb7.setText("독서감상문");
		bt_update.setText("업데이트");
		
		if(homework[0] == 0) cb1.setChecked(false);
		else if (homework[0] == 1) cb1.setChecked(true);
		
		if(homework[1] == 0) cb2.setChecked(false);
		else if (homework[1] == 1) cb2.setChecked(true);
		
		if(homework[2] == 0) cb3.setChecked(false);
		else if (homework[2] == 1) cb3.setChecked(true);
		
		if(homework[3] == 0) cb4.setChecked(false);
		else if (homework[3] == 1) cb4.setChecked(true);
		
		if(homework[4] == 0) cb5.setChecked(false);
		else if (homework[4] == 1) cb5.setChecked(true);
		
		if(homework[5] == 0) cb6.setChecked(false);
		else if (homework[5] == 1) cb6.setChecked(true);
		
		if(homework[6] == 0) cb7.setChecked(false);
		else if (homework[6] == 1) cb7.setChecked(true);
		
		int vv;
		
		bt_update.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//클릭을 하면, Check 여부를 읽어서 db에 업데이트.
				//int 변수 하나 둬가지구, 배열로. 0,1,1,1,0,0 이게 업데이트. 이런식으로.
				int[] check = new int[7];
				if(cb1.isChecked()) check[0] = 1;
				if(cb2.isChecked()) check[1] = 1;
				if(cb3.isChecked()) check[2] = 1;
				if(cb4.isChecked()) check[3] = 1;
				if(cb5.isChecked()) check[4] = 1;
				if(cb6.isChecked()) check[5] = 1;
				if(cb7.isChecked()) check[6] = 1;
				LoadDB loadDB = new LoadDB();
				loadDB.update_homework(check);
				// check 변수를 loaddb의 메서드로 넘긴다.	
				
				Toast.makeText(Tab1_Main.this, "업데이트 :)", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	
}


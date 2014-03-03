package com.disciple.activity;

import org.disciple.db.LoadDB;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.disciple.model.ReciteWords;
import com.disciple.model.ReciteWordsVo;

public class Tab2_Recite extends Activity {
	LinearLayout lyt_jindo, lyt_contents;
	int semester;
	boolean isJindoVisible = true;
	LoadDB db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab2_recite);
		db = new LoadDB();
		lyt_jindo = (LinearLayout) findViewById(R.id.lyt_jindo);
		lyt_contents = (LinearLayout) findViewById(R.id.lyt_contents);
		lyt_contents.setVisibility(View.INVISIBLE);
		int chooseSemester = db.select_semester(); // 나중에는 db에서 불러옵시다.
		listViewShow(chooseSemester);
	}
	
	public void listViewShow(final int chooseSemester) {
		ListView wordListView = (ListView) findViewById(R.id.wordsListView);
		TextView tv_semester = (TextView) findViewById(R.id.tv_semester);
		tv_semester.setText((chooseSemester+1) + "학기 커리큘럼");
		ArrayAdapter<CharSequence> adapter;
		int arraySemester = 0;
		switch(chooseSemester) {
		case 0 : //1학기
			arraySemester = R.array.semester1; break;
		case 1 : //2학기
			arraySemester = R.array.semester2; break;
		}
		adapter = ArrayAdapter.createFromResource(this, arraySemester, android.R.layout.simple_list_item_1);
		wordListView.setAdapter(adapter);
		wordListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				lyt_jindo.setVisibility(View.INVISIBLE);
				lyt_contents.setVisibility(View.VISIBLE);
				isJindoVisible = false;
				wordContentView(position, chooseSemester);
			}
		});
	}
	
	public void wordContentView(int position, int chooseSemester) {
		TextView tv1_addr = (TextView) findViewById(R.id.tv_1content);
		TextView tv2_addr = (TextView) findViewById(R.id.tv_2content);
		TextView tv1_print = (TextView) findViewById(R.id.tv_1word);
		TextView tv2_print = (TextView) findViewById(R.id.tv_2word);
		TextView tv3_books = (TextView) findViewById(R.id.tv_3book);
		tv1_print.setMovementMethod(new ScrollingMovementMethod());
		tv2_print.setMovementMethod(new ScrollingMovementMethod());
		ReciteWords content = new ReciteWords();
		ReciteWordsVo reciteWordVo = null;
		switch(chooseSemester) {
		case 0 : //1학기
			reciteWordVo = content.getContents_1(position); 			break;
		case 1 : //2학기
			reciteWordVo = content.getContents_2(position);			break;
		}
		tv1_addr.setText(reciteWordVo.getWord1_addr());
		tv2_addr.setText(reciteWordVo.getWord2_addr());
		tv1_print.setText(reciteWordVo.getWord1());
		tv2_print.setText(reciteWordVo.getWord2());
		tv3_books.setText(reciteWordVo.getBook());
	}
	
	@Override
	public void onBackPressed() {
		if (!isJindoVisible) {
			isJindoVisible = true;
			lyt_jindo.setVisibility(View.VISIBLE);
			lyt_contents.setVisibility(View.INVISIBLE);
		} else {
			finish();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "학기선택");
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
		final String items[] = { "1학기", "2학기" };
		AlertDialog.Builder ab = new AlertDialog.Builder(Tab2_Recite.this);
		ab.setTitle("학기 선택");
		ab.setSingleChoiceItems(items, 0,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						semester = whichButton; // 리스트 선택시 값 바꿔주고 이동하는거 넣으면 되겟삼.
					}
				})
				.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// 확인 버튼 클릭시
						db.update_semester(semester); // db에 학기 정보를 업데이트
						listViewShow(semester);	
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

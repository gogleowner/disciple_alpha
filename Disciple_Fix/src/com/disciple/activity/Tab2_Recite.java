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
		int chooseSemester = db.select_semester(); // ���߿��� db���� �ҷ��ɽô�.
		listViewShow(chooseSemester);
	}
	
	public void listViewShow(final int chooseSemester) {
		ListView wordListView = (ListView) findViewById(R.id.wordsListView);
		TextView tv_semester = (TextView) findViewById(R.id.tv_semester);
		tv_semester.setText((chooseSemester+1) + "�б� Ŀ��ŧ��");
		ArrayAdapter<CharSequence> adapter;
		int arraySemester = 0;
		switch(chooseSemester) {
		case 0 : //1�б�
			arraySemester = R.array.semester1; break;
		case 1 : //2�б�
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
		case 0 : //1�б�
			reciteWordVo = content.getContents_1(position); 			break;
		case 1 : //2�б�
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
		menu.add(0, 1, 0, "�б⼱��");
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
		final String items[] = { "1�б�", "2�б�" };
		AlertDialog.Builder ab = new AlertDialog.Builder(Tab2_Recite.this);
		ab.setTitle("�б� ����");
		ab.setSingleChoiceItems(items, 0,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						semester = whichButton; // ����Ʈ ���ý� �� �ٲ��ְ� �̵��ϴ°� ������ �ǰٻ�.
					}
				})
				.setPositiveButton("Ȯ��", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// Ȯ�� ��ư Ŭ����
						db.update_semester(semester); // db�� �б� ������ ������Ʈ
						listViewShow(semester);	
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

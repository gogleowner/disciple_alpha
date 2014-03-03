package com.disciple.activity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Resources res = getResources();
		
		TabHost mTabHost;
		mTabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;
		
		//new DBandTabTask().execute();

		intent = new Intent().setClass(this, Tab1_Main.class);
		spec = mTabHost
				.newTabSpec("tab1_main")
				.setIndicator("과제목록",
						res.getDrawable(R.drawable.ic_action_search))
				.setContent(intent);
		mTabHost.addTab(spec);

		intent = new Intent().setClass(this, Tab2_Recite.class);
		spec = mTabHost
				.newTabSpec("tab2_recite")
				.setIndicator("암송",
						res.getDrawable(R.drawable.ic_action_search))
				.setContent(intent);
		mTabHost.addTab(spec);

		intent = new Intent().setClass(this, Tab3_Dqt.class);
		spec = mTabHost
				.newTabSpec("tab3_dqt")
				.setIndicator("성경연구",
						res.getDrawable(R.drawable.ic_action_search))
				.setContent(intent);
		mTabHost.addTab(spec);

		intent = new Intent().setClass(this, Tab4_Mac.class);
		spec = mTabHost
				.newTabSpec("Tab4_Mac")
				.setIndicator("맥체인",
						res.getDrawable(R.drawable.ic_action_search))
				.setContent(intent);
		mTabHost.addTab(spec);

		mTabHost.setCurrentTab(0);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}

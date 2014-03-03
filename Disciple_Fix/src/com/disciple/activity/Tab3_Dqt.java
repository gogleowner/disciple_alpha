package com.disciple.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Tab3_Dqt extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab3_dqt);
	    ImageView iv = (ImageView)findViewById(R.id.image);
	    iv.setImageResource(R.drawable.dqt);
	}
}

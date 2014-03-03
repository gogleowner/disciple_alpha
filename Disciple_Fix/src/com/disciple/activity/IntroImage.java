package com.disciple.activity;

import java.security.Timestamp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;
import android.view.Window;
import android.widget.ImageView;

public class IntroImage extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intro_image);
		 ImageView iv = (ImageView)findViewById(R.id.bgd);
		 iv.setImageResource(R.drawable.dreamee);
		
		sleep(1000);
		Intent intent = new Intent(IntroImage.this, MainActivity.class);
		startActivity(intent);
		finish();
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	public void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
		}
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		// h.removeCallbacks(run);
	};
}

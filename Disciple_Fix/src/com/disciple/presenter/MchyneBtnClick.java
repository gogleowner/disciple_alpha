package com.disciple.presenter;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MchyneBtnClick implements OnClickListener{
	private View convertView;
	private Button button;
	private int month;
	private int day;
	
	public MchyneBtnClick(Button button, int month, int day) {
	
		this.button = button;
		this.month = month;
		this.day = day;
	}
	
	@Override
	public void onClick(View v) {
		Log.d("color change test", "plz... "  + button.getText());
		button.setBackgroundColor(Color.parseColor("#E6E6FA"));
	}

}

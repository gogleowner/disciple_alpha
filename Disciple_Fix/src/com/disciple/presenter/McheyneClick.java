package com.disciple.presenter;

import org.disciple.db.LoadDB;

import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableRow;
import android.widget.TextView;

public class McheyneClick implements OnClickListener {
	
	TableRow tr;
	TextView tv;
	boolean flag;
	int ox;
	int sequence;
	int month;
	int day;
	int read;
	
	public McheyneClick(TableRow tr, TextView tv, int sequence, int ox, int month, int day) {
		this.tr = tr;
		this.tv = tv;
		this.sequence = sequence;
		this.ox = ox; // boolean if ���ᰡ����, o�̸�,true, x�̸� false �ٲ������. 
		this.month = month;
		this.day = day;
	}

	public void onClick(View v) {
		LoadDB loaddb = new LoadDB();

		// sequence�� 0�̸�, �� ���� �غ� ������ �� �ٲ�����.
		if (sequence == 0) {
			read = loaddb.select_sumreadcolor(month, day);
			if (read <= 3) {
				for (int i = 1; i < 5; i++)
					tr.getChildAt(i).setBackgroundColor(Color.parseColor("#E6E6FA"));
				flag = true;
			} else if (read == 4) {
				for (int i = 1; i < 5; i++)
					tr.getChildAt(i).setBackgroundColor(Color.parseColor("#F5F5F5"));
				flag = false;
			}
			loaddb.update_allday(month, day, flag);	
		} else {// ���͵� ������ ��,
			//�̰� ���� �˾ƺ�.
			read = loaddb.select_readcolor(month, day, sequence);
			if (read==0) {
				tv.setBackgroundColor(Color.parseColor("#E6E6FA"));
				flag = true;
			} else if(read==1) {
				tv.setBackgroundColor(Color.parseColor("#F5F5F5"));
				flag = false;
			}
			loaddb.update_day(month, sequence, day, flag);
		}
	}
}

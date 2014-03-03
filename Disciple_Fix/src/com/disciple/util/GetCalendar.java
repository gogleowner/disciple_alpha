package com.disciple.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class GetCalendar {

	private int curDay;
	private int curMonth;
	private int curYear;
	private String curYearMonthDay;
	
	public GetCalendar() {
	    Calendar c;
	    c = Calendar.getInstance();
	    curMonth = c.get(Calendar.MONTH) + 1;
	    curDay = c.get(Calendar.DAY_OF_MONTH);
	    curYear = c.get(Calendar.YEAR);
	    
	    java.util.Date today = Calendar.getInstance().getTime();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
		curYearMonthDay= formatter.format(today);    
	}

	public int getCurDay() {
		return curDay;
	}

	public int getCurMonth() {
		return curMonth;
	}

	public int getCurYear() {
		return curYear;
	}

	public String getCurYearMonthDay() {
		return curYearMonthDay;
	}
	
	
}

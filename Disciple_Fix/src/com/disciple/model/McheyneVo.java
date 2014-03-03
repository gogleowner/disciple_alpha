package com.disciple.model;

public class McheyneVo {

	/*
	 * create table  words  (
		 * _id integer primary key autoincrement, 
		 * month INTEGER NOT NULL, 
		 * day INTEGER NOT NULL, 
		 * word1 TEXT, word2 TEXT, word3 TEXT, word4 TEXT, 
		 * read1 INTEGER, read2 INTEGER, read3 INTEGER, read4 INTEGER
	 * )
	 */
	private int month;
	private int day;
	private String word1;
	private String word2;
	private String word3;
	private String word4;
	private String read1;
	private String read2;
	private String read3;
	private String read4;
	
	public McheyneVo() { /*Default Constructor*/	}
	
	public McheyneVo(int month, int day, String word1, String word2,
			String word3, String word4, String read1, String read2,
			String read3, String read4) {
		super();
		this.month = month;
		this.day = day;
		this.word1 = word1;
		this.word2 = word2;
		this.word3 = word3;
		this.word4 = word4;
		this.read1 = read1;
		this.read2 = read2;
		this.read3 = read3;
		this.read4 = read4;
	}

	public McheyneVo(int day, String word1, String word2, String word3,
			String word4) {
		super();
		this.day = day;
		this.word1 = word1;
		this.word2 = word2;
		this.word3 = word3;
		this.word4 = word4;
	}
	
	public void setMonth(int month) {
		this.month = month;
	}


	public void setDay(int day) {
		this.day = day;
	}


	public void setWord1(String word1) {
		this.word1 = word1;
	}


	public void setWord2(String word2) {
		this.word2 = word2;
	}


	public void setWord3(String word3) {
		this.word3 = word3;
	}


	public void setWord4(String word4) {
		this.word4 = word4;
	}


	public void setRead1(String read1) {
		this.read1 = read1;
	}


	public void setRead2(String read2) {
		this.read2 = read2;
	}


	public void setRead3(String read3) {
		this.read3 = read3;
	}


	public void setRead4(String read4) {
		this.read4 = read4;
	}


	public int getMonth() {
		return month;
	}

	public int getDay() {
		return day;
	}

	public String getWord1() {
		return word1;
	}

	public String getWord2() {
		return word2;
	}

	public String getWord3() {
		return word3;
	}

	public String getWord4() {
		return word4;
	}

	public String getRead1() {
		return read1;
	}

	public String getRead2() {
		return read2;
	}

	public String getRead3() {
		return read3;
	}

	public String getRead4() {
		return read4;
	}
	
		
	
}

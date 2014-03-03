package org.disciple.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class LoadDB{

	public SQLiteDatabase db;
	public static final String DB_FNAME = DataConstants.DB_NAME;
	public static final String ROOT_DIR = "data/data/" + DataConstants.PACKAGE_NAME + "/databases/ " + DB_FNAME;
	
	public LoadDB() {
		try {
			db = SQLiteDatabase.openDatabase("data/data/com.disciple.activity/databases/mcheyne.db", null, SQLiteDatabase.CREATE_IF_NECESSARY);			
		} catch (Exception e) {
		}		
	}

	public void insertSomeDBData() {
		// TODO Auto-generated method stub
		Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name = 'words'", null);
		if (c.getCount() == 0) { // ���̺��� �����ϸ� CREATE, INSERT ���� �ʴ´�.
			// db.execSQL("drop table  words");
			db.execSQL("create table  words  (_id integer primary key autoincrement, month INTEGER NOT NULL, day INTEGER NOT NULL, word1 TEXT, word2 TEXT, word3 TEXT, word4 TEXT, read1 INTEGER, read2 INTEGER, read3 INTEGER, read4 INTEGER)");
			db.execSQL("create table  homework  (_id integer primary key autoincrement, num1 INTEGER NOT NULL, num2 INTEGER NOT NULL, num3 INTEGER NOT NULL, num4 INTEGER NOT NULL, num5 INTEGER NOT NULL, num6 INTEGER NOT NULL, num7 INTEGER NOT NULL)");
			db.execSQL("create table  semester  (semester_num INTEGER NOT NULL);");
			insert_homework();
			insert_semester();
			january();
			fabuary();
			march();
			april();
			may();
			june();
			july();
			august();
			september();
			october();
			november();
			december();
		}
	}


	public String[][] select(int month) {
		String[][] columns;
		int i = 0;
		Cursor c =  db.rawQuery("select day, word1, word2, word3, word4 from words where month = " + month, null);
		if (c.moveToFirst()) {
			columns = new String[c.getCount()][5];
			do {
				for (int j = 0; j < 5; j++) {
					columns[i][j] = c.getString(j);
				}
				i++;
			} while (c.moveToNext());
		} else {
			columns = null;
		}
		c.close();
		return columns;
	}
	
	public int[][] select_read(int month) {
		int[][] columns;
		int i = 0;
		Cursor c =  db.rawQuery("select day, read1, read2, read3, read4 from words where month = " + month, null);
		if (c.moveToFirst()) {
			columns = new int[c.getCount()][5];
			do {
				for (int j = 0; j < 5; j++) {
					columns[i][j] = c.getInt(j);
				}
				i++;
			} while (c.moveToNext());
		} else {
			columns = null;
		}
		c.close();
		return columns;
	}
	
	public int select_sumreadcolor(int month, int day) {
		int[] columns;
		int result=0;
		Cursor c =  db.rawQuery("select read1, read2, read3, read4 from words where month = " + month + " and day = " + day, null);
		if (c.moveToFirst()) {
			columns = new int[4];
			for (int i = 0; i < 4; i++) {
				columns[i] = c.getInt(i);
			}
		} else {
			columns = null;
		}
		c.close();
		for (int j = 0; j < columns.length; j++) {
			result += columns[j];
		}
		return result;
	}

	public int select_readcolor(int month, int day, int sequence) {
		int columns=0;
		int i = 0;

		Cursor c =  db.rawQuery("select read" + sequence+ " from words where month = " + month + " and day = " + day, null);
		if (c.moveToFirst()) {
			for (int j = 0; j < 1; j++) {
				columns = c.getInt(j);
			}
		} else {
			columns = 0;
		}
		c.close();
		return columns;
	}

	
	public String[] select_today(int month, int day) {
		String[] columns;
		int i = 0;
		Cursor c = db.rawQuery(
				"select day, word1, word2, word3, word4 from words where month = "
						+ month + " and day = " + day, null);
		if (c.moveToFirst()) {
			columns = new String[5];
			for (int j = 0; j < 5; j++) {
				columns[j] = c.getString(j);
			}
		} else {
			columns = null;
		}
		c.close();
		return columns;
	}

	public int[] select_read_today(int month, int day) {
		int[] columns;
		int i = 0;
		Cursor c = db.rawQuery(
				"select day, read1, read2, read3, read4 from words where month = "
						+ month + " and day = " + day, null);
		if (c.moveToFirst()) {
			columns = new int[5];
			for (int j = 0; j < 5; j++) {
				columns[j] = c.getInt(j);
			}
		} else {
			columns = null;
		}
		c.close();
		return columns;
	}


	
	public void update_day(int month, int sequence, int day, boolean flag) {
		db.beginTransaction();
		if(flag) { 
			db.execSQL("UPDATE words SET read"+sequence+ " = 1" + " WHERE month = " + month + " AND day = " + day);
			db.setTransactionSuccessful();
		} else {
			db.execSQL("UPDATE words SET read"+sequence+ " = 0" + " WHERE month = " + month + " AND day = " + day);
			//db.execSQL("UPDATE words SET read = 'X' WHERE month = " + month + " AND day = " + day);
			db.setTransactionSuccessful();
		}
		db.endTransaction();
	}

	public void update_allday(int month, int day, boolean flag) {
		db.beginTransaction();
		if(flag) { 
			db.execSQL("UPDATE words SET read1=1, read2=1, read3=1, read4=1 WHERE month = " + month + " AND day = " + day);
			db.setTransactionSuccessful();
		} else {
			db.execSQL("UPDATE words SET read1=0, read2=0, read3=0, read4=0 WHERE month = " + month + " AND day = " + day);
			db.setTransactionSuccessful();
		}
		db.endTransaction();
	}
	
	public int[] select_homework() {
		int[] columns;
		Cursor c = db.rawQuery("SELECT num1, num2, num3, num4, num5, num6, num7 FROM homework",null);
		if (c.moveToFirst()) {
			columns = new int[7];
			for (int j = 0; j < 7; j++) {
				columns[j] = c.getInt(j);
			}
		} else {
			columns = null;
		}
		c.close();
		return columns;
	}
	
	private void insert_homework() {
		db.execSQL("INSERT INTO homework(num1, num2, num3, num4, num5, num6, num7) VALUES (0, 0, 0, 0, 0, 0, 0)");
	}
	
	public void update_homework(int[] check) {
		db.execSQL("UPDATE homework SET num1 = " + check[0] + ", num2 =" + check[1] + ", num3 =" + check[2] + ", num4 =" + check[3] + ", num5 =" + check[4] + ", num6 =" + check[5] + ", num7 =" + check[6]);
	}
	
	public void insert_semester() {
		db.execSQL("INSERT INTO semester(semester_num) VALUES (0)");
	}

	public int select_semester() {
		// TODO Auto-generated method stub
		int columns=0;
		Cursor c =  db.rawQuery("SELECT semester_num FROM semester", null);
		if (c.moveToFirst()) {
			for (int j = 0; j < 1; j++) {
				columns = c.getInt(j);
			}
		} else {
			columns = 0;
		}
		c.close();
		return columns;
	}

	public void update_semester(int value) {
		// TODO Auto-generated method stub
		db.execSQL("UPDATE semester SET semester_num = " + value);
	}

	//���� ��ü�� data insert
	private void january() {
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 1, 'â1', '��1', '��1', '��1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 2, 'â2', '��2', '��2', '��2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 3, 'â3', '��3', '��3', '��3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 4, 'â4', '��4', '��4', '��4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 5, 'â5', '��5', '��5', '��5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 6, 'â6', '��6', '��6', '��6', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 7, 'â7', '��7', '��7', '��7', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 8, 'â8', '��8', '��8', '��8', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 9, 'â9~10', '��9', '��9', '��9', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 10, 'â11', '��10', '��10', '��10', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 11, 'â12', '��11', '��1', '��11', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 12, 'â13', '��12', '��2', '��12', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 13, 'â14', '��13', '��3', '��13', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 14, 'â15', '��14', '��4', '��14', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 15, 'â16', '��15', '��5', '��15', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 16, 'â17', '��16', '��6', '��16', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 17, 'â18', '��17', '��7', '��17', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 18, 'â19', '��18', '��8', '��18', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 19, 'â20', '��19', '��9', '��19', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 20, 'â21', '��20', '��10', '��20', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 21, 'â22', '��21', '��11', '��21', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 22, 'â23', '��22', '��12', '��22', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 23, 'â24', '��23', '��13', '��23', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 24, 'â25', '��24', '��1', '��24', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 25, 'â26', '��25', '��2', '��25', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 26, 'â27', '��26', '��3', '��26', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 27, 'â28', '��27', '��4', '��27', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 28, 'â29', '��28', '��5', '��28', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 29, 'â30', '��1', '��6', '��1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 30, 'â31', '��2', '��7', '��2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 31, 'â32', '��3', '��8', '��3', 0, 0, 0, 0)");
		//db.setTransactionSuccessful();
	}
	private void fabuary() {
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 1, 'â33', '��4', '��9~20', '��4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 2, 'â34', '��5', '��1', '��5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 3, 'â35~36', '��6', '��2', '��6', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 4, 'â37', '��7', '��3', '��7', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 5, 'â38', '��8', '��4', '��8', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 6, 'â39', '��9', '��5', '��9', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 7, 'â40', '��10', '��6', '��10', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 8, 'â41', '��11', '��7', '��11', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 9, 'â42', '��12', '��8', '��12', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 10, 'â43', '��13', '��9', '��13', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 11, 'â44', '��14', '��10', '��14', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 12, 'â45', '��15', '��11', '��15', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 13, 'â46', '��16', '��12', '��16', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 14, 'â47', '��1:1~38', '��13', '����1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 15, 'â48', '��1:39~80', '��14', '����2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 16, 'â49', '��2', '��15', '����3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 17, 'â50', '��3', '��16~17', '����4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 18, '��1', '��4', '��18', '����5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 19, '��2', '��5', '��19', '����6', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 20, '��3', '��6', '��20', '����7', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 21, '��4', '��7', '��21', '����8', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 22, '��5', '��8', '��22', '����9', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 23, '��6', '��9', '��23', '����10', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 24, '��7', '��10', '��24', '����11', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 25, '��8', '��11', '��25~26', '����12', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 26, '��9', '��12', '��27', '����13', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 27, '��10', '��13', '��28', '����14', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 28, '��11:1~', '��14', '��29', '����15', 0, 0, 0, 0)");
		//db.setTransactionSuccessful();
	}
	private void march() {// 3��
		// TODO Auto-generated method stub
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 1 , '��12:22','��15', '��30', '����16', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 2 , '��13','��16','��31','����1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 3 , '��14','��17','��32','����2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 4 , '��15','��18','��33','����3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 5 , '��16','��19','��34','����4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 6 , '��17','��20','��35','����5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 7 , '��18','��21','��36','����6', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 8 , '��19','��22','��37','����7', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 9 , '��20','��23','��38','����8', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 10, '��21','��24','��39','����9', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 11, '��22','��1','��40','����10', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 12, '��23','��2','��41','����11', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 13, '��24','��3','��42','����12', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 14, '��25','��4','��1','����13', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 15, '��26','��5','��2','��1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 16, '��27','��6','��3','��2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 17, '��28','��7','��4','��3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 18, '��29','��8','��5','��4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 19, '��30','��9','��6','��5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 20, '��31','��10','��7','��6', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 21, '��32','��11','��8','��1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 22, '��33','��12','��9','��2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 23, '��34','��13','��10','��3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 24, '��35','��14','��11','��4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 25, '��36','��15','��12','��5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 26, '��37','��16','��13','��6', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 27, '��38','��17','��14','��1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 28, '��39','��18','��15','��2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 29, '��40','��19','��16','��3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 30, '��1','��20','��17','��4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 31, '��2,3','��21','��18','��1', 0, 0, 0, 0)");
		//db.setTransactionSuccessful();
	}
	private void april() {		//4��
		// TODO Auto-generated method stub
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 1 , '��4','��1,2','��19','��2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 2 , '��5','��3,4','��20','��3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 3 , '��6','��5,6','��21','��4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 4 , '��7','��7,8','��22','����1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 5 , '��8','��9','��23','����2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 6 , '��9','��10','��24','����3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 7 , '��10','��11,12','��25','����4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 8 , '��11,12','��13,14','��26','����5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 9 , '��13','��15,16','��27','����1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 10, '��14','��17','��28','����2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 11, '��15','��18','��29','����3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 12, '��16','��19','��30','����1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 13, '��17','��20,21','��31','����2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 14, '��18','��22','��1','����3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 15, '��19','��23,24','��2','����4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 16, '��20','��25','��3','����5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 17, '��21','��26,27','��4','����6', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 18, '��22','��28,29','��5','����1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 19, '��23','��30','��6','����2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 20, '��24','��31','��7','����3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 21, '��25','��32','��8','����4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 22, '��26','��33','��9','��1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 23, '��27','��34','��10','��2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 24, '��1','��35','��11','��3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 25, '��2','��36','��12','��1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 26, '��3','��37','��1','��1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 27, '��4','��38','��2','��2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 28, '��5','��39','��3,','��3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 29, '��6','��40,41','��4','��4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 30, '��7','��42,43','��5','��5', 0, 0, 0, 0)");
		//db.setTransactionSuccessful();
	}
	private void may() { 
		// TODO Auto-generated method stub
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 1 , '��8','��44','��6','��6', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 2 , '��9','��45','��7','��7', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 3 , '��10','��46,47','��8','��8', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 4 , '��11','��48','��1','��9', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 5 , '��12,13','��49','��2','��10', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 6 , '��14','��50','��3,4','��11', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 7 , '��15','��51','��5','��12', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 8 , '��16','��52,53,54','��6','��13', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 9 , '��17,18','��55','��7','��1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 10, '��19','��56,57','��8,9:1-7','��2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 11, '��20','��58,59','��9:8-10:4','��3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 12, '��21','��60,61','��10:5-34','��4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 13, '��22','��62,63','��11,12','��5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 14, '��23','��64,65','��13','����1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 15, '��24','��66,67','��14','����2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 16, '��25','��68','��15','����3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 17, '��26','��69','��16','����4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 18, '��27','��70,71','��17,18','����5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 19, '��28','��72','��19,20','����1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 20, '��29','��73','��21','����2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 21, '��30','��74','��22','����3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 22, '��31','��75,76','��23','����1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 23, '��32','��77','��24','����2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 24, '��33','��78:1-37','��25','����3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 25, '��34','��78:38-72','��26','����4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 26, '��35','��79','��27','����5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 27, '��36','��80','��28','����1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 28, '��1','��81,82','��29','���1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 29, '��2','��83,84','��30','��1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 30, '��3','��85','��31','��1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 31, '��4','��86,87','��32','��2', 0, 0, 0, 0)");
		//db.setTransactionSuccessful();
	}
	private void june(){
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 1, '��5', '��88', '��33', '��3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 2, '��6', '��89', '��34', '��4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 3, '��7', '��90', '��35', '��5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 4, '��8', '��91', '��36', '��6', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 5, '��9', '��92-93', '��37', '��7', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 6, '��10', '��94', '��38', '��8', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 7, '��11', '��95-96', '��39', '��9', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 8, '��12', '��97-98', '��40', '��10', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 9, '��13-14', '��99-101', '��41', '��11', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 10, '��15', '��102', '��42', '��12', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 11, '��16', '��103', '��43', '��13', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 12, '��17', '��104', '��44', '��14', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 13, '��18', '��105', '��45', '��15', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 14, '��19', '��106', '��46', '��16', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 15, '��20', '��107', '��47', '��17', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 16, '��21', '��108-109', '��48', '��18', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 17, '��22', '��110-111', '��49', '��19', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 18, '��23', '��112-113', '��50', '��20', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 19, '��24', '��114-115', '��51', '��21', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 20, '��25', '��116', '��52', '��22', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 21, '��26', '��117-118', '��53', '��1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 22, '��27:1-28:19', '��119:1-24', '��54', '��2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 23, '��28:20-68', '��119:25-48', '��55', '��3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 24, '��29', '��119:49-72', '��56', '��4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 25, '��30', '��119:73-96', '��57', '��5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 26, '��31', '��119:97-120', '��58', '��6', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 27, '��32', '��119:121-144', '��59', '��7', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 28, '��33-34', '��119:145-176', '��60', '��8', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 29, '��1', '��120-122', '��61', '��9', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 30, '��2', '��123-125', '��62', '��10', 0, 0, 0, 0)");
		//db.setTransactionSuccessful();
	}
	private void july(){
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 1, '��3', '��126-128', '��63', '��11', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 2, '��4', '��129-131', '��64', '��12', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 3, '��5:1-6:5', '��132-134', '��65', '��13', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 4, '��6:6-27', '��135-136', '��66', '��14', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 5, '��7', '��137-138', '��1', '��15', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 6, '��8', '��139', '��2', '��16', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 7, '��9', '��140-141', '��3', '��17', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 8, '��10', '��142-143', '��4', '��18', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 9, '��11', '��144', '��5', '��19', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 10, '��12-13', '��145', '��6', '��20', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 11, '��14-15', '��146-147', '��7', '��21', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 12, '��16-17', '��148', '��8', '��22', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 13, '��18-19', '��149-150', '��9', '��23', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 14, '��20-21', '��1', '��10', '��24', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 15, '��22', '��2', '��11', '��25', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 16, '��23', '��3', '��12', '��26', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 17, '��24', '��4', '��13', '��27', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 18, '��1', '��5', '��14', '��28', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 19, '��2', '��6', '��15', '��1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 20, '��3', '��7', '��16', '��2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 21, '��4', '��8', '��17', '��3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 22, '��5', '��9', '��18', '��4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 23, '��6', '��10', '��19', '��5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 24, '��7', '��11', '��20', '��6', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 25, '��8', '��12', '��21', '��7', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 26, '��9', '��13', '��22', '��8', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 27, '��10:1-11:11', '��14', '��23', '��9', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 28, '��11:12-40', '��15', '��24', '��10', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 29, '��12', '��16', '��25', '��11', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 30, '��13', '��17', '��26', '��12', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 31, '��14', '��18', '��27', '��13', 0, 0, 0, 0)");
	}
	private void august(){
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 1, '��15', '��19', '��28', '��14', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 2, '��16', '��20', '��29', '��15', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 3, '��17', '��21', '��30-31', '��16', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 4, '��18', '��22', '��32', '��1-2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 5, '��19', '��23', '��33', '��3-4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 6, '��20', '��24', '��34', '��5-6', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 7, '��21', '��25', '��35', '��7-8', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 8, '��1', '��26', '��36-37', '��9', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 9, '��2', '��27', '��38', '��10', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 10, '��3-4', '��28', '��39', '��11-12', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 11, '���1', '��1', '��40', '��13-14', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 12, '���2', '��2', '��41', '��15-16', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 13, '���3', '��3', '��42', '��17', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 14, '���4', '��4', '��43', '��18', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 15, '���5-6', '��5', '��44', '��19', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 16, '���7-8', '��6', '��45', '��20-21', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 17, '���9', '��7', '��46', '��22', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 18, '���10', '��8', '��47', '��23-24', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 19, '���11', '��9', '��48', '��25', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 20, '���12', '��10', '��49', '��26-27', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 21, '���13', '��11', '��50', '��28-29', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 22, '���14', '��12', '��51', '��30', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 23, '���15', '��13', '��52', '��31', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 24, '���16', '��14', '��1', '��32', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 25, '���17', '��15', '��2', '��33', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 26, '���18', '��16', '��3', '��34', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 27, '���19', '����1', '��4', '��35', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 28, '���20', '����2', '��5', '��36', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 29, '���21-22', '����3', '��1', '��37', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 30, '���23', '����4', '��2', '��38', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 31, '���24', '����5', '��3', '��39', 0, 0, 0, 0)");
	}
	private void september(){
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 1, '���25', '����6', '��4', '��40-41', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 2, '���26', '����7', '��5', '��42-43', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 3, '���27', '����8', '��6', '��44', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 4, '���28', '����9', '��7', '��45-46', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 5, '���29-30', '����10', '��8', '��47', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 6, '���31', '����11', '��9', '��48', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 7, '����1', '����12', '��10', '��49', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 8, '����2', '����13', '��11', '��50', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 9, '����3', '����14', '��12', '��51', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 10, '����4-5', '����15', '��13', '��52-54', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 11, '����6', '����16', '��14', '��55', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 12, '����7', '����1', '��15', '��56-57', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 13, '����8-9', '����2', '��16', '��58-59', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 14, '����10', '����3', '��17', '��60-61', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 15, '����11', '����4', '��18', '��62-63', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 16, '����12', '����5', '��19', '��64-65', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 17, '����13', '����6', '��20', '��66-67', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 18, '����14', '����7', '��21', '��68', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 19, '����15', '����8', '��22', '��69', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 20, '����16', '����9', '��23', '��70-71', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 21, '����17', '����10', '��24', '��72', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 22, '����18', '����11', '��25', '��73', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 23, '����19', '����12', '��26', '��74', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 24, '����20', '����13', '��27', '��75-76', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 25, '����21', '��1', '��28', '��77', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 26, '����22', '��2', '��29', '��78:1-37', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 27, '����23', '��3', '��30', '��78:38-72', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 28, '����24', '��4', '��31', '��79', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 29, '�ջ�1', '��5', '��32', '��80', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 30, '�ջ�2', '��6', '��33', '��81-82', 0, 0, 0, 0)");
	}
	private void october(){
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 1, '�ջ�3', '��1', '��34', '��83-84', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 2, '�ջ�4-5', '��2', '��35', '��85', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 3, '�ջ�6', '��3', '��36', '��86', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 4, '�ջ�7', '��4', '��37', '��87-88', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 5, '�ջ�8', '��5', '��38', '��89', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 6, '�ջ�9', '��6', '��39', '��90', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 7, '�ջ�10', '��1', '��40', '��91', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 8, '�ջ�11', '��2', '��41', '��92-93', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 9, '�ջ�12', '��3', '��42', '��94', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 10, '�ջ�13', '��4', '��43', '��95-96', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 11, '�ջ�14', '��1', '��44', '��97-98', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 12, '�ջ�15', '��2', '��45', '��99-101', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 13, '�ջ�16', '��3', '��46', '��102', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 14, '�ջ�17', '��4', '��47', '��103', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 15, '�ջ�18', '����1', '��48', '��104', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 16, '�ջ�19', '����2', '��1', '��105', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 17, '�ջ�20', '����3', '��2', '��106', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 18, '�ջ�21', '����4', '��3', '��107', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 19, '�ջ�22', '����5', '��4', '��108-109', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 20, '����1', '����1', '��5', '��110-111', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 21, '����2', '����2', '��6', '��112-113', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 22, '����3', '����3', '��7', '��114-115', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 23, '����4', '����1', '��8', '��116', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 24, '����5', '����2', '��9', '��117-118', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 25, '����6', '����3', '��10', '��119:1-24', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 26, '����7', '����4', '��11', '��119:25-48', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 27, '����8', '����5', '��12', '��119:49-72', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 28, '����9', '����6', 'ȣ1', '��119:73-96', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 29, '����10', '����1', 'ȣ2', '��119:97-120', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 30, '����11-12', '����2', 'ȣ3-4', '��119:121-144', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 31, '����13', '����3', 'ȣ5-6', '��119:145-176', 0, 0, 0, 0)");
	}
	private void november(){
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 1, '����14', '����4', 'ȣ7', '��120-122', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 2, '����15', '��1', 'ȣ8', '��123-125', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 3, '����16', '��2', 'ȣ9', '��126-128', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 4, '����17', '��3', 'ȣ10', '��129-131', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 5, '����18', '��1', 'ȣ11', '��132-134', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 6, '����19', '��1', 'ȣ12', '��135-136', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 7, '����20', '��2', 'ȣ13', '��137-138', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 8, '����21', '��3', 'ȣ14', '��139', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 9, '����22', '��4', '��1', '��140-141', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 10, '����23', '��5', '��2', '��142', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 11, '����24', '��6', '��3', '��143', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 12, '����25', '��7', '��1', '��144', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 13, '���1-2', '��8', '��2', '��145', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 14, '���3-4', '��9', '��3', '��146-147', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 15, '���5-6', '��10', '��4', '��148-150', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 16, '���7-8', '��11', '��5', '��1:1-38', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 17, '���9-10', '��12', '��6', '��1:39-80', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 18, '���11-12', '��13', '��7', '��2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 19, '���13-14', '��1', '��8', '��3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 20, '���15', '��2', '��9', '��4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 21, '���16', '��3', '��1', '��5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 22, '���17', '��4', '��1', '��6', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 23, '���18', '��5', '��2', '��7', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 24, '���19-20', '����1', '��3', '��8', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 25, '���21', '����2', '��4', '��9', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 26, '���22', '����3', '��1', '��10', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 27, '���23', '����4', '��2', '��11', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 28, '���24-25', '����5', '��3', '��12', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 29, '���26-27', '����1', '��4', '��13', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 30, '���28', '����2', '��5', '��14', 0, 0, 0, 0)");
	}
	private void december(){
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 1, '���29', '����3', '��6', '��15', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 2, '����1', '����1', '��7', '��16', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 3, '����2', '����2', '��1', '��17', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 4, '����3-4', '����3', '��2', '��18', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 5, '����5:1-6:11', '����4', '��3', '��19', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 6, '����6:12-42', '����5', '��1', '��20', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 7, '����7', '����1', '��2', '��21', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 8, '����8', '���1', '��3', '��22', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 9, '����9', '��1', '��1', '��23', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 10, '����10', '��1', '��2', '��24', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 11, '����11-12', '��2', '��3', '��1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 12, '����13', '��3', '��1', '��2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 13, '����14-15', '��4', '��2', '��3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 14, '����16', '��5', '��1', '��4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 15, '����17', '��6', '��2', '��5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 16, '����18', '��7', '��3', '��6', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 17, '����19-20', '��8', '��4', '��7', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 18, '����21', '��9', '��5', '��8', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 19, '����22-23', '��10', '��6', '��9', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 20, '����24', '��11', '��7', '��10', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 21, '����25', '��12', '��8', '��11', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 22, '����26', '��13', '��9', '��12', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 23, '����27-28', '��14', '��10', '��13', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 24, '����29', '��15', '��11', '��14', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 25, '����30', '��16', '��12:1-13:1', '��15', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 26, '����31', '��17', '��13:2-9', '��16', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 27, '����32', '��18', '��14', '��17', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 28, '����33', '��19', '��1', '��18', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 29, '����34', '��20', '��2', '��19', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 30, '����35', '��21', '��3', '��20', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 31, '����36', '��22', '��4', '��21', 0, 0, 0, 0)");
	}
}

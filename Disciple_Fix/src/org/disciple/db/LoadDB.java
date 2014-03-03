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
		if (c.getCount() == 0) { // Å×ÀÌºíÀÌ Á¸ÀçÇÏ¸é CREATE, INSERT ÇÏÁö ¾Ê´Â´Ù.
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

	//¿ùº° ¸ÆÃ¼ÀÎ data insert
	private void january() {
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 1, 'Ã¢1', '¸¶1', '½º1', 'Çà1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 2, 'Ã¢2', '¸¶2', '½º2', 'Çà2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 3, 'Ã¢3', '¸¶3', '½º3', 'Çà3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 4, 'Ã¢4', '¸¶4', '½º4', 'Çà4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 5, 'Ã¢5', '¸¶5', '½º5', 'Çà5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 6, 'Ã¢6', '¸¶6', '½º6', 'Çà6', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 7, 'Ã¢7', '¸¶7', '½º7', 'Çà7', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 8, 'Ã¢8', '¸¶8', '½º8', 'Çà8', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 9, 'Ã¢9~10', '¸¶9', '½º9', 'Çà9', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 10, 'Ã¢11', '¸¶10', '½º10', 'Çà10', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 11, 'Ã¢12', '¸¶11', '´À1', 'Çà11', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 12, 'Ã¢13', '¸¶12', '´À2', 'Çà12', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 13, 'Ã¢14', '¸¶13', '´À3', 'Çà13', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 14, 'Ã¢15', '¸¶14', '´À4', 'Çà14', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 15, 'Ã¢16', '¸¶15', '´À5', 'Çà15', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 16, 'Ã¢17', '¸¶16', '´À6', 'Çà16', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 17, 'Ã¢18', '¸¶17', '´À7', 'Çà17', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 18, 'Ã¢19', '¸¶18', '´À8', 'Çà18', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 19, 'Ã¢20', '¸¶19', '´À9', 'Çà19', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 20, 'Ã¢21', '¸¶20', '´À10', 'Çà20', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 21, 'Ã¢22', '¸¶21', '´À11', 'Çà21', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 22, 'Ã¢23', '¸¶22', '´À12', 'Çà22', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 23, 'Ã¢24', '¸¶23', '´À13', 'Çà23', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 24, 'Ã¢25', '¸¶24', '¿¡1', 'Çà24', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 25, 'Ã¢26', '¸¶25', '¿¡2', 'Çà25', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 26, 'Ã¢27', '¸¶26', '¿¡3', 'Çà26', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 27, 'Ã¢28', '¸¶27', '¿¡4', 'Çà27', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 28, 'Ã¢29', '¸¶28', '¿¡5', 'Çà28', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 29, 'Ã¢30', '¸·1', '¿¡6', '·Ò1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 30, 'Ã¢31', '¸·2', '¿¡7', '·Ò2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (1, 31, 'Ã¢32', '¸·3', '¿¡8', '·Ò3', 0, 0, 0, 0)");
		//db.setTransactionSuccessful();
	}
	private void fabuary() {
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 1, 'Ã¢33', '¸·4', '¿¡9~20', '·Ò4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 2, 'Ã¢34', '¸·5', '¿é1', '·Ò5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 3, 'Ã¢35~36', '¸·6', '¿é2', '·Ò6', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 4, 'Ã¢37', '¸·7', '¿é3', '·Ò7', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 5, 'Ã¢38', '¸·8', '¿é4', '·Ò8', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 6, 'Ã¢39', '¸·9', '¿é5', '·Ò9', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 7, 'Ã¢40', '¸·10', '¿é6', '·Ò10', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 8, 'Ã¢41', '¸·11', '¿é7', '·Ò11', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 9, 'Ã¢42', '¸·12', '¿é8', '·Ò12', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 10, 'Ã¢43', '¸·13', '¿é9', '·Ò13', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 11, 'Ã¢44', '¸·14', '¿é10', '·Ò14', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 12, 'Ã¢45', '¸·15', '¿é11', '·Ò15', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 13, 'Ã¢46', '¸·16', '¿é12', '·Ò16', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 14, 'Ã¢47', '´ª1:1~38', '¿é13', '°íÀü1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 15, 'Ã¢48', '´ª1:39~80', '¿é14', '°íÀü2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 16, 'Ã¢49', '´ª2', '¿é15', '°íÀü3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 17, 'Ã¢50', '´ª3', '¿é16~17', '°íÀü4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 18, 'Ãâ1', '´ª4', '¿é18', '°íÀü5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 19, 'Ãâ2', '´ª5', '¿é19', '°íÀü6', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 20, 'Ãâ3', '´ª6', '¿é20', '°íÀü7', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 21, 'Ãâ4', '´ª7', '¿é21', '°íÀü8', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 22, 'Ãâ5', '´ª8', '¿é22', '°íÀü9', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 23, 'Ãâ6', '´ª9', '¿é23', '°íÀü10', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 24, 'Ãâ7', '´ª10', '¿é24', '°íÀü11', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 25, 'Ãâ8', '´ª11', '¿é25~26', '°íÀü12', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 26, 'Ãâ9', '´ª12', '¿é27', '°íÀü13', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 27, 'Ãâ10', '´ª13', '¿é28', '°íÀü14', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (2, 28, 'Ãâ11:1~', '´ª14', '¿é29', '°íÀü15', 0, 0, 0, 0)");
		//db.setTransactionSuccessful();
	}
	private void march() {// 3¿ù
		// TODO Auto-generated method stub
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 1 , 'Ãâ12:22','´ª15', '¿é30', '°íÀü16', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 2 , 'Ãâ13','´ª16','¿é31','°íÈÄ1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 3 , 'Ãâ14','´ª17','¿é32','°íÈÄ2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 4 , 'Ãâ15','´ª18','¿é33','°íÈÄ3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 5 , 'Ãâ16','´ª19','¿é34','°íÈÄ4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 6 , 'Ãâ17','´ª20','¿é35','°íÈÄ5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 7 , 'Ãâ18','´ª21','¿é36','°íÈÄ6', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 8 , 'Ãâ19','´ª22','¿é37','°íÈÄ7', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 9 , 'Ãâ20','´ª23','¿é38','°íÈÄ8', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 10, 'Ãâ21','´ª24','¿é39','°íÈÄ9', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 11, 'Ãâ22','¿ä1','¿é40','°íÈÄ10', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 12, 'Ãâ23','¿ä2','¿é41','°íÈÄ11', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 13, 'Ãâ24','¿ä3','¿é42','°íÈÄ12', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 14, 'Ãâ25','¿ä4','Àá1','°íÈÄ13', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 15, 'Ãâ26','¿ä5','Àá2','°¥1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 16, 'Ãâ27','¿ä6','Àá3','°¥2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 17, 'Ãâ28','¿ä7','Àá4','°¥3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 18, 'Ãâ29','¿ä8','Àá5','°¥4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 19, 'Ãâ30','¿ä9','Àá6','°¥5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 20, 'Ãâ31','¿ä10','Àá7','°¥6', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 21, 'Ãâ32','¿ä11','Àá8','¿¦1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 22, 'Ãâ33','¿ä12','Àá9','¿¦2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 23, 'Ãâ34','¿ä13','Àá10','¿¦3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 24, 'Ãâ35','¿ä14','Àá11','¿¦4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 25, 'Ãâ36','¿ä15','Àá12','¿¦5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 26, 'Ãâ37','¿ä16','Àá13','¿¦6', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 27, 'Ãâ38','¿ä17','Àá14','ºô1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 28, 'Ãâ39','¿ä18','Àá15','ºô2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 29, 'Ãâ40','¿ä19','Àá16','ºô3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 30, '·¹1','¿ä20','Àá17','ºô4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (3, 31, '·¹2,3','¿ä21','Àá18','°ñ1', 0, 0, 0, 0)");
		//db.setTransactionSuccessful();
	}
	private void april() {		//4¿ù
		// TODO Auto-generated method stub
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 1 , '·¹4','½Ã1,2','Àá19','°ñ2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 2 , '·¹5','½Ã3,4','Àá20','°ñ3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 3 , '·¹6','½Ã5,6','Àá21','°ñ4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 4 , '·¹7','½Ã7,8','Àá22','»ìÀü1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 5 , '·¹8','½Ã9','Àá23','»ìÀü2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 6 , '·¹9','½Ã10','Àá24','»ìÀü3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 7 , '·¹10','½Ã11,12','Àá25','»ìÀü4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 8 , '·¹11,12','½Ã13,14','Àá26','»ìÀü5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 9 , '·¹13','½Ã15,16','Àá27','»ìÈÄ1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 10, '·¹14','½Ã17','Àá28','»ìÈÄ2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 11, '·¹15','½Ã18','Àá29','»ìÈÄ3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 12, '·¹16','½Ã19','Àá30','µõÀü1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 13, '·¹17','½Ã20,21','Àá31','µõÀü2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 14, '·¹18','½Ã22','Àü1','µõÀü3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 15, '·¹19','½Ã23,24','Àü2','µõÀü4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 16, '·¹20','½Ã25','Àü3','µõÀü5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 17, '·¹21','½Ã26,27','Àü4','µõÀü6', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 18, '·¹22','½Ã28,29','Àü5','µõÈÄ1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 19, '·¹23','½Ã30','Àü6','µõÈÄ2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 20, '·¹24','½Ã31','Àü7','µõÈÄ3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 21, '·¹25','½Ã32','Àü8','µõÈÄ4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 22, '·¹26','½Ã33','Àü9','µó1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 23, '·¹27','½Ã34','Àü10','µó2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 24, '¹Î1','½Ã35','Àü11','µó3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 25, '¹Î2','½Ã36','Àü12','¸ó1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 26, '¹Î3','½Ã37','¾Æ1','È÷1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 27, '¹Î4','½Ã38','¾Æ2','È÷2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 28, '¹Î5','½Ã39','¾Æ3,','È÷3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 29, '¹Î6','½Ã40,41','¾Æ4','È÷4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (4, 30, '¹Î7','½Ã42,43','¾Æ5','È÷5', 0, 0, 0, 0)");
		//db.setTransactionSuccessful();
	}
	private void may() { 
		// TODO Auto-generated method stub
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 1 , '¹Î8','½Ã44','¾Æ6','È÷6', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 2 , '¹Î9','½Ã45','¾Æ7','È÷7', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 3 , '¹Î10','½Ã46,47','¾Æ8','È÷8', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 4 , '¹Î11','½Ã48','»ç1','È÷9', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 5 , '¹Î12,13','½Ã49','»ç2','È÷10', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 6 , '¹Î14','½Ã50','»ç3,4','È÷11', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 7 , '¹Î15','½Ã51','»ç5','È÷12', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 8 , '¹Î16','½Ã52,53,54','»ç6','È÷13', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 9 , '¹Î17,18','½Ã55','»ç7','¾à1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 10, '¹Î19','½Ã56,57','»ç8,9:1-7','¾à2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 11, '¹Î20','½Ã58,59','»ç9:8-10:4','¾à3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 12, '¹Î21','½Ã60,61','»ç10:5-34','¾à4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 13, '¹Î22','½Ã62,63','»ç11,12','¾à5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 14, '¹Î23','½Ã64,65','»ç13','º¦Àü1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 15, '¹Î24','½Ã66,67','»ç14','º¦Àü2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 16, '¹Î25','½Ã68','»ç15','º¦Àü3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 17, '¹Î26','½Ã69','»ç16','º¦Àü4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 18, '¹Î27','½Ã70,71','»ç17,18','º¦Àü5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 19, '¹Î28','½Ã72','»ç19,20','º¦ÈÄ1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 20, '¹Î29','½Ã73','»ç21','º¦ÈÄ2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 21, '¹Î30','½Ã74','»ç22','º¦ÈÄ3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 22, '¹Î31','½Ã75,76','»ç23','¿äÀÏ1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 23, '¹Î32','½Ã77','»ç24','¿äÀÏ2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 24, '¹Î33','½Ã78:1-37','»ç25','¿äÀÏ3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 25, '¹Î34','½Ã78:38-72','»ç26','¿äÀÏ4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 26, '¹Î35','½Ã79','»ç27','¿äÀÏ5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 27, '¹Î36','½Ã80','»ç28','¿äÀÌ1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 28, '½Å1','½Ã81,82','»ç29','¿ä»ï1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 29, '½Å2','½Ã83,84','»ç30','À¯1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 30, '½Å3','½Ã85','»ç31','°è1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (5, 31, '½Å4','½Ã86,87','»ç32','°è2', 0, 0, 0, 0)");
		//db.setTransactionSuccessful();
	}
	private void june(){
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 1, '½Å5', '½Ã88', '»ç33', '°è3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 2, '½Å6', '½Ã89', '»ç34', '°è4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 3, '½Å7', '½Ã90', '»ç35', '°è5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 4, '½Å8', '½Ã91', '»ç36', '°è6', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 5, '½Å9', '½Ã92-93', '»ç37', '°è7', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 6, '½Å10', '½Ã94', '»ç38', '°è8', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 7, '½Å11', '½Ã95-96', '»ç39', '°è9', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 8, '½Å12', '½Ã97-98', '»ç40', '°è10', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 9, '½Å13-14', '½Ã99-101', '»ç41', '°è11', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 10, '½Å15', '½Ã102', '»ç42', '°è12', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 11, '½Å16', '½Ã103', '»ç43', '°è13', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 12, '½Å17', '½Ã104', '»ç44', '°è14', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 13, '½Å18', '½Ã105', '»ç45', '°è15', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 14, '½Å19', '½Ã106', '»ç46', '°è16', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 15, '½Å20', '½Ã107', '»ç47', '°è17', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 16, '½Å21', '½Ã108-109', '»ç48', '°è18', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 17, '½Å22', '½Ã110-111', '»ç49', '°è19', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 18, '½Å23', '½Ã112-113', '»ç50', '°è20', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 19, '½Å24', '½Ã114-115', '»ç51', '°è21', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 20, '½Å25', '½Ã116', '»ç52', '°è22', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 21, '½Å26', '½Ã117-118', '»ç53', '¸¶1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 22, '½Å27:1-28:19', '½Ã119:1-24', '»ç54', '¸¶2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 23, '½Å28:20-68', '½Ã119:25-48', '»ç55', '¸¶3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 24, '½Å29', '½Ã119:49-72', '»ç56', '¸¶4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 25, '½Å30', '½Ã119:73-96', '»ç57', '¸¶5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 26, '½Å31', '½Ã119:97-120', '»ç58', '¸¶6', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 27, '½Å32', '½Ã119:121-144', '»ç59', '¸¶7', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 28, '½Å33-34', '½Ã119:145-176', '»ç60', '¸¶8', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 29, '¼ö1', '½Ã120-122', '»ç61', '¸¶9', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (6, 30, '¼ö2', '½Ã123-125', '»ç62', '¸¶10', 0, 0, 0, 0)");
		//db.setTransactionSuccessful();
	}
	private void july(){
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 1, '¼ö3', '½Ã126-128', '»ç63', '¸¶11', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 2, '¼ö4', '½Ã129-131', '»ç64', '¸¶12', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 3, '¼ö5:1-6:5', '½Ã132-134', '»ç65', '¸¶13', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 4, '¼ö6:6-27', '½Ã135-136', '»ç66', '¸¶14', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 5, '¼ö7', '½Ã137-138', '·½1', '¸¶15', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 6, '¼ö8', '½Ã139', '·½2', '¸¶16', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 7, '¼ö9', '½Ã140-141', '·½3', '¸¶17', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 8, '¼ö10', '½Ã142-143', '·½4', '¸¶18', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 9, '¼ö11', '½Ã144', '·½5', '¸¶19', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 10, '¼ö12-13', '½Ã145', '·½6', '¸¶20', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 11, '¼ö14-15', '½Ã146-147', '·½7', '¸¶21', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 12, '¼ö16-17', '½Ã148', '·½8', '¸¶22', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 13, '¼ö18-19', '½Ã149-150', '·½9', '¸¶23', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 14, '¼ö20-21', 'Çà1', '·½10', '¸¶24', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 15, '¼ö22', 'Çà2', '·½11', '¸¶25', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 16, '¼ö23', 'Çà3', '·½12', '¸¶26', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 17, '¼ö24', 'Çà4', '·½13', '¸¶27', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 18, '»ñ1', 'Çà5', '·½14', '¸¶28', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 19, '»ñ2', 'Çà6', '·½15', '¸·1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 20, '»ñ3', 'Çà7', '·½16', '¸·2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 21, '»ñ4', 'Çà8', '·½17', '¸·3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 22, '»ñ5', 'Çà9', '·½18', '¸·4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 23, '»ñ6', 'Çà10', '·½19', '¸·5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 24, '»ñ7', 'Çà11', '·½20', '¸·6', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 25, '»ñ8', 'Çà12', '·½21', '¸·7', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 26, '»ñ9', 'Çà13', '·½22', '¸·8', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 27, '»ñ10:1-11:11', 'Çà14', '·½23', '¸·9', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 28, '»ñ11:12-40', 'Çà15', '·½24', '¸·10', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 29, '»ñ12', 'Çà16', '·½25', '¸·11', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 30, '»ñ13', 'Çà17', '·½26', '¸·12', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (7, 31, '»ñ14', 'Çà18', '·½27', '¸·13', 0, 0, 0, 0)");
	}
	private void august(){
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 1, '»ñ15', 'Çà19', '·½28', '¸·14', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 2, '»ñ16', 'Çà20', '·½29', '¸·15', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 3, '»ñ17', 'Çà21', '·½30-31', '¸·16', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 4, '»ñ18', 'Çà22', '·½32', '½Ã1-2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 5, '»ñ19', 'Çà23', '·½33', '½Ã3-4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 6, '»ñ20', 'Çà24', '·½34', '½Ã5-6', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 7, '»ñ21', 'Çà25', '·½35', '½Ã7-8', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 8, '·í1', 'Çà26', '·½36-37', '½Ã9', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 9, '·í2', 'Çà27', '·½38', '½Ã10', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 10, '·í3-4', 'Çà28', '·½39', '½Ã11-12', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 11, '»ï»ó1', '·Ò1', '·½40', '½Ã13-14', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 12, '»ï»ó2', '·Ò2', '·½41', '½Ã15-16', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 13, '»ï»ó3', '·Ò3', '·½42', '½Ã17', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 14, '»ï»ó4', '·Ò4', '·½43', '½Ã18', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 15, '»ï»ó5-6', '·Ò5', '·½44', '½Ã19', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 16, '»ï»ó7-8', '·Ò6', '·½45', '½Ã20-21', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 17, '»ï»ó9', '·Ò7', '·½46', '½Ã22', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 18, '»ï»ó10', '·Ò8', '·½47', '½Ã23-24', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 19, '»ï»ó11', '·Ò9', '·½48', '½Ã25', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 20, '»ï»ó12', '·Ò10', '·½49', '½Ã26-27', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 21, '»ï»ó13', '·Ò11', '·½50', '½Ã28-29', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 22, '»ï»ó14', '·Ò12', '·½51', '½Ã30', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 23, '»ï»ó15', '·Ò13', '·½52', '½Ã31', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 24, '»ï»ó16', '·Ò14', '¾Ö1', '½Ã32', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 25, '»ï»ó17', '·Ò15', '¾Ö2', '½Ã33', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 26, '»ï»ó18', '·Ò16', '¾Ö3', '½Ã34', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 27, '»ï»ó19', '°íÀü1', '¾Ö4', '½Ã35', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 28, '»ï»ó20', '°íÀü2', '¾Ö5', '½Ã36', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 29, '»ï»ó21-22', '°íÀü3', '°Ö1', '½Ã37', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 30, '»ï»ó23', '°íÀü4', '°Ö2', '½Ã38', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (8, 31, '»ï»ó24', '°íÀü5', '°Ö3', '½Ã39', 0, 0, 0, 0)");
	}
	private void september(){
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 1, '»ï»ó25', '°íÀü6', '°Ö4', '½Ã40-41', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 2, '»ï»ó26', '°íÀü7', '°Ö5', '½Ã42-43', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 3, '»ï»ó27', '°íÀü8', '°Ö6', '½Ã44', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 4, '»ï»ó28', '°íÀü9', '°Ö7', '½Ã45-46', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 5, '»ï»ó29-30', '°íÀü10', '°Ö8', '½Ã47', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 6, '»ï»ó31', '°íÀü11', '°Ö9', '½Ã48', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 7, '»ïÇÏ1', '°íÀü12', '°Ö10', '½Ã49', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 8, '»ïÇÏ2', '°íÀü13', '°Ö11', '½Ã50', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 9, '»ïÇÏ3', '°íÀü14', '°Ö12', '½Ã51', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 10, '»ïÇÏ4-5', '°íÀü15', '°Ö13', '½Ã52-54', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 11, '»ïÇÏ6', '°íÀü16', '°Ö14', '½Ã55', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 12, '»ïÇÏ7', '°íÈÄ1', '°Ö15', '½Ã56-57', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 13, '»ïÇÏ8-9', '°íÈÄ2', '°Ö16', '½Ã58-59', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 14, '»ïÇÏ10', '°íÈÄ3', '°Ö17', '½Ã60-61', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 15, '»ïÇÏ11', '°íÈÄ4', '°Ö18', '½Ã62-63', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 16, '»ïÇÏ12', '°íÈÄ5', '°Ö19', '½Ã64-65', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 17, '»ïÇÏ13', '°íÈÄ6', '°Ö20', '½Ã66-67', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 18, '»ïÇÏ14', '°íÈÄ7', '°Ö21', '½Ã68', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 19, '»ïÇÏ15', '°íÈÄ8', '°Ö22', '½Ã69', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 20, '»ïÇÏ16', '°íÈÄ9', '°Ö23', '½Ã70-71', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 21, '»ïÇÏ17', '°íÈÄ10', '°Ö24', '½Ã72', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 22, '»ïÇÏ18', '°íÈÄ11', '°Ö25', '½Ã73', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 23, '»ïÇÏ19', '°íÈÄ12', '°Ö26', '½Ã74', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 24, '»ïÇÏ20', '°íÈÄ13', '°Ö27', '½Ã75-76', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 25, '»ïÇÏ21', '°¥1', '°Ö28', '½Ã77', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 26, '»ïÇÏ22', '°¥2', '°Ö29', '½Ã78:1-37', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 27, '»ïÇÏ23', '°¥3', '°Ö30', '½Ã78:38-72', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 28, '»ïÇÏ24', '°¥4', '°Ö31', '½Ã79', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 29, '¿Õ»ó1', '°¥5', '°Ö32', '½Ã80', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (9, 30, '¿Õ»ó2', '°¥6', '°Ö33', '½Ã81-82', 0, 0, 0, 0)");
	}
	private void october(){
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 1, '¿Õ»ó3', '¿¦1', '°Ö34', '½Ã83-84', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 2, '¿Õ»ó4-5', '¿¦2', '°Ö35', '½Ã85', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 3, '¿Õ»ó6', '¿¦3', '°Ö36', '½Ã86', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 4, '¿Õ»ó7', '¿¦4', '°Ö37', '½Ã87-88', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 5, '¿Õ»ó8', '¿¦5', '°Ö38', '½Ã89', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 6, '¿Õ»ó9', '¿¦6', '°Ö39', '½Ã90', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 7, '¿Õ»ó10', 'ºô1', '°Ö40', '½Ã91', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 8, '¿Õ»ó11', 'ºô2', '°Ö41', '½Ã92-93', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 9, '¿Õ»ó12', 'ºô3', '°Ö42', '½Ã94', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 10, '¿Õ»ó13', 'ºô4', '°Ö43', '½Ã95-96', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 11, '¿Õ»ó14', '°ñ1', '°Ö44', '½Ã97-98', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 12, '¿Õ»ó15', '°ñ2', '°Ö45', '½Ã99-101', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 13, '¿Õ»ó16', '°ñ3', '°Ö46', '½Ã102', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 14, '¿Õ»ó17', '°ñ4', '°Ö47', '½Ã103', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 15, '¿Õ»ó18', '»ìÀü1', '°Ö48', '½Ã104', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 16, '¿Õ»ó19', '»ìÀü2', '´Ü1', '½Ã105', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 17, '¿Õ»ó20', '»ìÀü3', '´Ü2', '½Ã106', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 18, '¿Õ»ó21', '»ìÀü4', '´Ü3', '½Ã107', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 19, '¿Õ»ó22', '»ìÀü5', '´Ü4', '½Ã108-109', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 20, '¿ÕÇÏ1', '»ìÈÄ1', '´Ü5', '½Ã110-111', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 21, '¿ÕÇÏ2', '»ìÈÄ2', '´Ü6', '½Ã112-113', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 22, '¿ÕÇÏ3', '»ìÈÄ3', '´Ü7', '½Ã114-115', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 23, '¿ÕÇÏ4', 'µõÀü1', '´Ü8', '½Ã116', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 24, '¿ÕÇÏ5', 'µõÀü2', '´Ü9', '½Ã117-118', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 25, '¿ÕÇÏ6', 'µõÀü3', '´Ü10', '½Ã119:1-24', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 26, '¿ÕÇÏ7', 'µõÀü4', '´Ü11', '½Ã119:25-48', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 27, '¿ÕÇÏ8', 'µõÀü5', '´Ü12', '½Ã119:49-72', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 28, '¿ÕÇÏ9', 'µõÀü6', 'È£1', '½Ã119:73-96', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 29, '¿ÕÇÏ10', 'µõÈÄ1', 'È£2', '½Ã119:97-120', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 30, '¿ÕÇÏ11-12', 'µõÈÄ2', 'È£3-4', '½Ã119:121-144', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (10, 31, '¿ÕÇÏ13', 'µõÈÄ3', 'È£5-6', '½Ã119:145-176', 0, 0, 0, 0)");
	}
	private void november(){
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 1, '¿ÕÇÏ14', 'µõÈÄ4', 'È£7', '½Ã120-122', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 2, '¿ÕÇÏ15', 'µó1', 'È£8', '½Ã123-125', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 3, '¿ÕÇÏ16', 'µó2', 'È£9', '½Ã126-128', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 4, '¿ÕÇÏ17', 'µó3', 'È£10', '½Ã129-131', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 5, '¿ÕÇÏ18', '¸ó1', 'È£11', '½Ã132-134', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 6, '¿ÕÇÏ19', 'È÷1', 'È£12', '½Ã135-136', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 7, '¿ÕÇÏ20', 'È÷2', 'È£13', '½Ã137-138', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 8, '¿ÕÇÏ21', 'È÷3', 'È£14', '½Ã139', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 9, '¿ÕÇÏ22', 'È÷4', '¿ç1', '½Ã140-141', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 10, '¿ÕÇÏ23', 'È÷5', '¿ç2', '½Ã142', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 11, '¿ÕÇÏ24', 'È÷6', '¿ç3', '½Ã143', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 12, '¿ÕÇÏ25', 'È÷7', '¾Ï1', '½Ã144', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 13, '´ë»ó1-2', 'È÷8', '¾Ï2', '½Ã145', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 14, '´ë»ó3-4', 'È÷9', '¾Ï3', '½Ã146-147', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 15, '´ë»ó5-6', 'È÷10', '¾Ï4', '½Ã148-150', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 16, '´ë»ó7-8', 'È÷11', '¾Ï5', '´ª1:1-38', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 17, '´ë»ó9-10', 'È÷12', '¾Ï6', '´ª1:39-80', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 18, '´ë»ó11-12', 'È÷13', '¾Ï7', '´ª2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 19, '´ë»ó13-14', '¾à1', '¾Ï8', '´ª3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 20, '´ë»ó15', '¾à2', '¾Ï9', '´ª4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 21, '´ë»ó16', '¾à3', '¿É1', '´ª5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 22, '´ë»ó17', '¾à4', '¿æ1', '´ª6', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 23, '´ë»ó18', '¾à5', '¿æ2', '´ª7', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 24, '´ë»ó19-20', 'º¦Àü1', '¿æ3', '´ª8', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 25, '´ë»ó21', 'º¦Àü2', '¿æ4', '´ª9', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 26, '´ë»ó22', 'º¦Àü3', '¹Ì1', '´ª10', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 27, '´ë»ó23', 'º¦Àü4', '¹Ì2', '´ª11', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 28, '´ë»ó24-25', 'º¦Àü5', '¹Ì3', '´ª12', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 29, '´ë»ó26-27', 'º¦ÈÄ1', '¹Ì4', '´ª13', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (11, 30, '´ë»ó28', 'º¦ÈÄ2', '¹Ì5', '´ª14', 0, 0, 0, 0)");
	}
	private void december(){
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 1, '´ë»ó29', 'º¦ÈÄ3', '¹Ì6', '´ª15', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 2, '´ëÇÏ1', '¿äÀÏ1', '¹Ì7', '´ª16', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 3, '´ëÇÏ2', '¿äÀÏ2', '³ª1', '´ª17', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 4, '´ëÇÏ3-4', '¿äÀÏ3', '³ª2', '´ª18', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 5, '´ëÇÏ5:1-6:11', '¿äÀÏ4', '³ª3', '´ª19', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 6, '´ëÇÏ6:12-42', '¿äÀÏ5', 'ÇÕ1', '´ª20', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 7, '´ëÇÏ7', '¿äÀÌ1', 'ÇÕ2', '´ª21', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 8, '´ëÇÏ8', '¿ä»ï1', 'ÇÕ3', '´ª22', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 9, '´ëÇÏ9', 'À¯1', '½À1', '´ª23', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 10, '´ëÇÏ10', '°è1', '½À2', '´ª24', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 11, '´ëÇÏ11-12', '°è2', '½À3', '¿ä1', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 12, '´ëÇÏ13', '°è3', 'ÇÐ1', '¿ä2', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 13, '´ëÇÏ14-15', '°è4', 'ÇÐ2', '¿ä3', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 14, '´ëÇÏ16', '°è5', '½»1', '¿ä4', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 15, '´ëÇÏ17', '°è6', '½»2', '¿ä5', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 16, '´ëÇÏ18', '°è7', '½»3', '¿ä6', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 17, '´ëÇÏ19-20', '°è8', '½»4', '¿ä7', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 18, '´ëÇÏ21', '°è9', '½»5', '¿ä8', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 19, '´ëÇÏ22-23', '°è10', '½»6', '¿ä9', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 20, '´ëÇÏ24', '°è11', '½»7', '¿ä10', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 21, '´ëÇÏ25', '°è12', '½»8', '¿ä11', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 22, '´ëÇÏ26', '°è13', '½»9', '¿ä12', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 23, '´ëÇÏ27-28', '°è14', '½»10', '¿ä13', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 24, '´ëÇÏ29', '°è15', '½»11', '¿ä14', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 25, '´ëÇÏ30', '°è16', '½»12:1-13:1', '¿ä15', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 26, '´ëÇÏ31', '°è17', '½»13:2-9', '¿ä16', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 27, '´ëÇÏ32', '°è18', '½»14', '¿ä17', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 28, '´ëÇÏ33', '°è19', '¸»1', '¿ä18', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 29, '´ëÇÏ34', '°è20', '¸»2', '¿ä19', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 30, '´ëÇÏ35', '°è21', '¸»3', '¿ä20', 0, 0, 0, 0)");
		db.execSQL("INSERT INTO words(month, day, word1, word2, word3, word4, read1, read2, read3, read4) VALUES (12, 31, '´ëÇÏ36', '°è22', '¸»4', '¿ä21', 0, 0, 0, 0)");
	}
}

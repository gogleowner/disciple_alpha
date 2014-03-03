package com.disciple.presenter;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;

public class GetQT {
	String selectday;
	String malthmQT;
	String strhtml;
	String subject;
	
	public String loadURL() throws Exception {
		String strUrl = "http://365qt.com/TodaysQT.asp";
		StringBuilder html = new StringBuilder();
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "euc-kr"));
			while (true) {
				String line = br.readLine();
				if (line == null) break;
				html.append(line + "\n");
			}
			br.close();
			conn.disconnect();
		strhtml = html.toString();
		
		String[] allHtml = strhtml.split("\n");
		String realText = allHtml[279].replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
		
		return realText;
	}
}

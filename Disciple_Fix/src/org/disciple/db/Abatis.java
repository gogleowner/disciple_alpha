/*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.disciple.db;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 
 * @author sonix - http://www.sonix.asia
 * @since JDK1.5 Android Level 3
 *
 */
public class Abatis extends SQLiteOpenHelper {
    /**
     * Debug TAG
     */
    private static final String TAG = "aBatis";
    
    /**
     * DB
     */
    private static final String INIT_CREATE_SQL = "abatis_init";
    
    /**
     * abatis version
     */
    private static final String VERSION_FOR_UPGRADE = "abatis_version";
    
    /**
     * Default DB file name
     */
    private static final String DB_FILE_NAME = DataConstants.DB_NAME;
    
    /**
     * SQLiteDatabase object
     */
    private SQLiteDatabase dbObj;
    
    /**
     * Context object
     */
    private Context context;
    
    /**
     * Upgrade Flag
     */
    private boolean isUpgrade = false;
    
    /**
     * Default DB file name constructor
     *
     * @param context
     *
     */
    public Abatis(Context context) {
        super(context, DB_FILE_NAME, null, Integer.parseInt(context.getResources().getString(context.getResources().getIdentifier(VERSION_FOR_UPGRADE, "string", context.getPackageName()))));
        this.context = context;
    }
    
    /**
     *
     * @param context
     * @param dbName
     *
     */
    public Abatis(Context context, String dbName) {
        super(context, dbName.concat(".db") , null, Integer.parseInt(context.getResources().getString(context.getResources().getIdentifier(VERSION_FOR_UPGRADE, "string", context.getPackageName()))));
        this.context = context;
    }
    
    /**
     * DB connector
     * @param db SQLiteDatabase object
     *
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        int pointer = context.getResources().getIdentifier(INIT_CREATE_SQL, "string", context.getPackageName());
        if (pointer == 0) {
            Log.e(TAG, "undefined sql id - initialize");
        } else {
            String[] createTabelSqls = context.getResources().getString(pointer).split(";");
            for (String sql : createTabelSqls) {
                db.execSQL(sql + ";");
            }
        }
    }
    
    /**
     * for upgrade
     *
     * @param db SQLiteDatabase object
     * @param oldVersion old version value
     * @param newVersion new version value
     * 
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        isUpgrade = true;
    }
    
    /**
     * @param sqlId SQLID
     * @param bindParams sql parameter
     * 
     * @return Map<String, Object> result
     */
    public Map<String, Object> executeForMap(String sqlId, Map<String, Object> bindParams) {
        getDbObject();
        int pointer = context.getResources().getIdentifier(sqlId, "string", context.getPackageName());
        if (pointer == 0) {
            Log.e(TAG, "undefined sql id");
            return null;
        }
        String sql = context.getResources().getString(pointer);
        if (bindParams != null) {
            Iterator<String> mapIterator = bindParams.keySet().iterator();
            while (mapIterator.hasNext()) {
                String key = mapIterator.next();
                Object value = bindParams.get(key);
                sql = sql.replaceAll("#" + key.toLowerCase() + "#", value == null ? null : "'" + value.toString() + "'");
            }
        }
        if (sql.indexOf('#') != -1) {
            Log.e(TAG, "undefined parameter");
            return null;
        }
        Cursor cursor = dbObj.rawQuery(sql, null);
        List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
        if (cursor == null) {
            return null;
        }
        String[] columnNames = cursor.getColumnNames();
        while(cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<String, Object>();
            int i = 0;
            for (String columnName : columnNames) {
                map.put(columnName, cursor.getString(i));
                i++;
            }
            mapList.add(map);
        }
        if (mapList.size() <= 0) {
            return null;
        }
        cursor.close();
        dbObj.close();
        return mapList.get(0);
    }
    
    /**
     *
     * @param sqlId SQLID
     * @param bindParams sql parameter
     * 
     * @return List<Map<String, Object>> result
     */
    public List<Map<String, Object>> executeForMapList(String sqlId, Map<String, Object> bindParams) {
        getDbObject();
        int pointer = context.getResources().getIdentifier(sqlId, "string", context.getPackageName());
        if (pointer == 0) {
            Log.e(TAG, "undefined sql id");
            return null;
        }
        String sql = context.getResources().getString(pointer);
        if (bindParams != null) {
            Iterator<String> mapIterator = bindParams.keySet().iterator();
            while (mapIterator.hasNext()) {
                String key = mapIterator.next();
                Object value = bindParams.get(key);
                sql = sql.replaceAll("#" + key.toLowerCase() + "#", value == null ? null : "'" + value.toString() + "'");
            }
        }
        if (sql.indexOf('#') != -1) {
            Log.e(TAG, "undefined parameter");
            return null;
        }
        Cursor cursor = dbObj.rawQuery(sql, null);
        List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
        if (cursor == null) {
            return null;
        }
        String[] columnNames = cursor.getColumnNames();
        while(cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<String, Object>();
            int i = 0;
            for (String columnName : columnNames) {
                map.put(columnName, cursor.getString(i));
                i++;
            }
            mapList.add(map);
        }
        cursor.close();
        dbObj.close();
        return mapList;
    }
	
    /**
     *
     * @param sqlId SQLID
     * @param bindParams sql parameter
     * @param bean bean class of result 
     * 
     * @return List<Map<String, Object>> result
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T> T executeForBean(String sqlId, Map<String, Object> bindParams, Class bean) {
        getDbObject();
        int pointer = context.getResources().getIdentifier(sqlId, "string", context.getPackageName());
        if (pointer == 0) {
            Log.e(TAG, "undefined sql id");
            return null;
        }
        String sql = context.getResources().getString(pointer);
        if (bindParams != null) {
            Iterator<String> mapIterator = bindParams.keySet().iterator();
            while (mapIterator.hasNext()) {
                String key = mapIterator.next();
                Object value = bindParams.get(key);
                sql = sql.replaceAll("#" + key.toLowerCase() + "#", value == null ? null : "'" + value.toString() + "'");
            }
        }
        if (sql.indexOf('#') != -1) {
            Log.e(TAG, "undefined parameter");
            return null;
        }
        Cursor cursor = dbObj.rawQuery(sql, null);
        List<T> objectList = new ArrayList<T>();
        if (cursor == null) {
            return null;
        }
        String[] columnNames = cursor.getColumnNames();
        List<String> dataNames = new ArrayList<String>();
        for (String columnName : columnNames) {
            dataNames.add(chgDataName(columnName));
        } 
        T beanObj = null;
        // get bean class package
        Package beanPackage = bean.getPackage();
        while(cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<String, Object>();
            int i = 0;
            for (String dataName : dataNames) {
                map.put(dataName, cursor.getString(i));
                i++;
            }
            JSONObject json = new JSONObject(map);
            try {
                beanObj = (T)parse(json.toString(), bean, beanPackage.getName());
            } catch (Exception e) {
                Log.d(TAG, e.toString());
                return null;
            } 
            objectList.add(beanObj);
        }
        if (objectList.size() <= 0) {
            return null;
        }
        cursor.close();
        dbObj.close();
        return objectList.get(0);
    }
    
    /**
     *
     * @param sqlId SQLID
     * @param bindParams sql parameter
     * @param bean bean class of result 
     * 
     * @return List<Map<String, Object>> result
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public <T>List<T> executeForBeanList(String sqlId, Map<String, Object> bindParams, Class bean) {
        getDbObject();
        int pointer = context.getResources().getIdentifier(sqlId, "string", context.getPackageName());
        if (pointer == 0) {
            Log.e(TAG, "undefined sql id");
            return null;
        }
        String sql = context.getResources().getString(pointer);
        if (bindParams != null) {
            Iterator<String> mapIterator = bindParams.keySet().iterator();
            while (mapIterator.hasNext()) {
                String key = mapIterator.next();
                Object value = bindParams.get(key);
                sql = sql.replaceAll("#" + key.toLowerCase() + "#", value == null ? null : "'" + value.toString() + "'");
            }
        }
        if (sql.indexOf('#') != -1) {
            Log.e(TAG, "undefined parameter");
            return null;
        }
        Cursor cursor = dbObj.rawQuery(sql, null);
        List<T> objectList = new ArrayList<T>();
        if (cursor == null) {
            return null;
        }
        String[] columnNames = cursor.getColumnNames();
        List<String> dataNames = new ArrayList<String>();
        for (String columnName : columnNames) {
            dataNames.add(chgDataName(columnName));
        } 
        T beanObj = null;
        // get bean class package
        Package beanPackage = bean.getPackage();
        while(cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<String, Object>();
            int i = 0;
            for (String dataName : dataNames) {
                map.put(dataName, cursor.getString(i));
                i++;
            }
            JSONObject json = new JSONObject(map);
            try {
                beanObj = (T)parse(json.toString(), bean, beanPackage.getName());
            } catch (Exception e) {
                 Log.d(TAG, e.toString());
                return null;
            } 
            objectList.add(beanObj);
        }
        cursor.close();
        dbObj.close();
        return objectList;
    }
    
    /**
     *
     * @param sqlId SQLiteDatabase object
     * @param bindParams old version value
     * 
     * @return int
     */
    public int execute(String sqlId, Map<String, Object> bindParams) {
        getDbObject();
        int row = 0;
        int pointer = context.getResources().getIdentifier(sqlId, "string", context.getPackageName());
        if (pointer == 0) {
            Log.e(TAG, "undefined sql id");
            return row;
        }
        String sql = context.getResources().getString(pointer);
        if (bindParams != null) {
            Iterator<String> mapIterator = bindParams.keySet().iterator();
            while (mapIterator.hasNext()) {
                String key = mapIterator.next();
                Object value = bindParams.get(key);
                sql = sql.replaceAll("#" + key.toLowerCase() + "#", value == null ? null : "'" + value.toString() + "'");
            }
        }
        if (sql.indexOf('#') != -1) {
            Log.e(TAG, "undefined parameter");
            return row;
        }
        try {
            dbObj.execSQL(sql);
            dbObj.close();
            row += 1;
        } catch (SQLException e) {
            return row;
        }
        return row;
    }
    
    /**
     * SQLiteDatabase Object
     * 
     * @return SQLiteDatabase SQLiteDatabase Object
     */
    private SQLiteDatabase getDbObject() {
        if (dbObj == null || !dbObj.isOpen()) {
            dbObj = getWritableDatabase();
        }
        return dbObj;
    }
    
    /**
     * JsonString
     *  
     * @param jsonStr JSON String
     * @param beanClass Bean class 
     * @param basePackage Base package name which includes all Bean classes 
     * @return Object Bean
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private Object parse(String jsonStr, Class beanClass, String basePackage) throws Exception {
        Object obj = null;
        JSONObject jsonObj = new JSONObject(jsonStr);
        // Check bean object
        if(beanClass == null){
            Log.d(TAG, "Bean class is null");
            return null;
        }
        // Read Class member fields
        Field[] props = beanClass.getDeclaredFields();
        if(props == null || props.length == 0) {
            Log.d(TAG, "Class"+ beanClass.getName() +" has no fields");
            return null;
        }
        // Create instance of this Bean class
        obj = beanClass.newInstance();
        // Set value of each member variable of this object
        for (int i=0; i<props.length; i++) {
            String fieldName = props[i].getName();
            // Skip public and static fields
            if (props[i].getModifiers() == (Modifier.PUBLIC | Modifier.STATIC)) {
                continue;
            }
            // Date Type of Field 
            Class type = props[i].getType();
            String typeName = type.getName();
            // Check for Custom type
            if (typeName.equals("int")) {
                Class[] parms = {type};
                Method m = beanClass.getDeclaredMethod(getBeanMethodName(fieldName, 1), parms);
                m.setAccessible(true);
                // Set value
                try {
                    m.invoke(obj, jsonObj.getInt(fieldName));
                } catch (Exception ex) {
                    Log.d(TAG, ex.getMessage());
                }
            } else if (typeName.equals("long")) {
                Class[] parms = {type};
                Method m = beanClass.getDeclaredMethod(getBeanMethodName(fieldName, 1), parms);
                m.setAccessible(true);
                // Set value
                try {
                    m.invoke(obj, jsonObj.getLong(fieldName));
                }catch(Exception ex){
                    Log.d(TAG, ex.getMessage());
                }
            } else if (typeName.equals("java.lang.String")) {
                Class[] parms = {type};
                Method m = beanClass.getDeclaredMethod(getBeanMethodName(fieldName, 1), parms);
                m.setAccessible(true);
                // Set value
                try {
                    m.invoke(obj, jsonObj.getString(fieldName));
                } catch (Exception ex) {
                    Log.d(TAG, ex.getMessage());
                }
            } else if (typeName.equals("double")) {
                Class[] parms = {type};
                Method m = beanClass.getDeclaredMethod(getBeanMethodName(fieldName, 1), parms);
                m.setAccessible(true);
                // Set value
                try {
                    m.invoke(obj,  jsonObj.getDouble(fieldName));
                } catch(Exception ex){
                    Log.d(TAG, ex.getMessage());
                }
            } else if (type.getName().equals(List.class.getName()) || 
                    type.getName().equals(ArrayList.class.getName())) {
                // Find out the Generic
                String generic = props[i].getGenericType().toString();
                if (generic.indexOf("<") != -1) {
                    String genericType = generic.substring(generic.lastIndexOf("<") + 1, generic.lastIndexOf(">"));
                    if (genericType != null) {
                        JSONArray array = null;
                        try {
                            array = jsonObj.getJSONArray(fieldName);
                        } catch(Exception ex) {
                            Log.d(TAG, ex.getMessage());
                            array = null;
                        }
                        if (array == null) {
                            continue;
                        }
                        ArrayList arrayList = new ArrayList();
                        for (int j=0; j<array.length(); j++) {
                            arrayList.add(parse(array.getJSONObject(j).toString(), Class.forName(genericType), basePackage));
                        }
                        // Set value
                        Class[] parms = {type};
                        Method m = beanClass.getDeclaredMethod(getBeanMethodName(fieldName, 1), parms);
                        m.setAccessible(true);
                        m.invoke(obj, arrayList);
                    }
                } else {
                    // No generic defined
                    generic = null;
                }
            } else if (typeName.startsWith(basePackage)) {
                Class[] parms = {type};
                Method m = beanClass.getDeclaredMethod(getBeanMethodName(fieldName, 1), parms);
                m.setAccessible(true);
                // Set value
                try{
                    JSONObject customObj = jsonObj.getJSONObject(fieldName);
                    if (customObj != null) {
                        m.invoke(obj, parse(customObj.toString(), type, basePackage));
                    }
                } catch (JSONException ex) {
                    Log.d(TAG, ex.getMessage());
                }
            } else {
                // Skip
                Log.d(TAG, "Field " + fieldName + "#" + typeName + " is skip");
            }
        }
        return obj;
    }
    
    /**
     * BeanClass fields
     * @param fieldName
     * @param type
     * @return String MethodName
     */
    private String getBeanMethodName(String fieldName, int type){
        if(fieldName == null || fieldName == "") {
            return "";
        }
        String methodName = "";
        if(type == 0) {
            methodName = "get";
        } else {
            methodName = "set";
        }
        methodName += fieldName.substring(0, 1).toUpperCase();
        if (fieldName.length() == 1) {
            return methodName;
        }
        methodName += fieldName.substring(1);
        return methodName;
    }
    
    /**
     * Database
     * @param targetStr database
     * @return String bean data
     */
    private String chgDataName(String targetStr) {
        Pattern p = Pattern.compile("_([a-z])");
        Matcher m = p.matcher(targetStr.toLowerCase());

        StringBuffer sb = new StringBuffer(targetStr.length());
        while (m.find()) {
            m.appendReplacement(sb, m.group(1).toUpperCase());
        }
        m.appendTail(sb);
        return sb.toString();
    }
    
    /**
     * 
     * @return boolean Upgrade
     */;
    public boolean isUpgrade() {
        // log and set upgrade flag
        Log.d(TAG, String.valueOf(getDbObject().getVersion())); 
        return isUpgrade;
    }
}

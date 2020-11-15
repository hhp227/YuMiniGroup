package com.hhp227.yu_minigroup.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TimetableHelper extends SQLiteOpenHelper {
    private final static String TAG = "DB헬퍼";

    private final static String dbName = "timetable.db";

    private final String dbTableName = "schedule";

    SQLiteDatabase db;

    static String result;

    public TimetableHelper(Context context) {
        super(context, dbName, null, 1);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String sql = "create table if not exists " + dbTableName + "("
                + " _id integer PRIMARY KEY,"
                + " subject text, "
                + " classroom text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS" + dbTableName);
        onCreate(db);
    }

    // DB에 추가하는 함수
    public void add(int id, String a, String b) {
        ContentValues val = new ContentValues();
        val.put("_id", id);
        val.put("subject", a);
        val.put("classroom", b);
        db.insert(dbTableName, null, val);
        search_data();
    }

    // DB수정하는 함수
    public void update(long rawId, String a, String b) {
        ContentValues val = new ContentValues();
        val.put("_id", rawId);
        val.put("subject", a);
        val.put("classroom", b);
        db.update(dbTableName, val, "_id = "+ rawId, null);
        search_data();
    }

    // DB에 레코드 삭제하는 함수
    public void delete(long rawId) {
        //DB에 삭제하고자하는 아이디값을 넘겨 받아서 쿼리로 검색 후 해당 아이디값의 레코드 삭제
        db.delete(dbTableName, "_id = "+ rawId , null);
        search_data();
    }

    // 로그상으로 데이터를 확인하고자 만든 함수
    public void search_data() {
        String sql = "select * from "+ dbTableName;
        Cursor cur = db.rawQuery(sql, null);
        cur.moveToFirst();

        // 커서가 움직일 때에는 무조건 데이터가 마지막인지 확인을 해주어야 한다.
        // 커서가 데이터의 마지막이 아닐때까지 반복문 수행
        // (커서를 반드시 반복문안에서 moveToNext를 해주어야 다음 행 레코드를 읽을 수 있다.
        while (!cur.isAfterLast()) {
            // 해당 레코드행의 각 열의 값을 가져온다.
            // 0번 열은 아이디, 1번 열은 강의명, 2번 열은 강의실
            // 0번은 int값이기 때문에 가져올때는 cur.getInt(0); 이렇게 가져온다.
            // cursor.getInt(or getString)(열번호);
            String subject = cur.getString(1);
            String classroom = cur.getString(2);
            result = (subject + "   " + classroom);
            Log.i(TAG, result);
            cur.moveToNext();
        }
        cur.close();
    }

    // DB의 레코드들을 모두가져오는 함수
    public Cursor getAll() {
        //해당 테이블의 모든 레코드 리턴
        return db.query(dbTableName, null, null,null,null,null,null);
    }

    // 검색하고자하는 아이디값으로 아이디에 해당하는 레코드 반환
    public Cursor getId(int id) {
        Cursor cur = db.query(dbTableName , null, "_id = " + id , null,null,null,null);
        if (cur != null && cur.getCount() != 0)
            cur.moveToNext();
        return cur;
    }

    // 레코드 행의 갯수를 카운트 해줌
    public int getCounter() {
        Cursor cur = null;
        String sql = "select * from "+ dbTableName;
        cur = db.rawQuery(sql, null);
        int counter = 0;
        while (!cur.isAfterLast()) {
            cur.moveToNext();
            counter++;
        }
        return counter;
    }
}

package com.tangtisanon.pikulkaew.mynotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by k on 16/1/2559.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notes.db"; //crtl+d = Copy this line
    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME = "notes"; //class อื่นๆ เรียกได้, static เรียกใช้ได้เลยไม่ต้อง new object class.static method
    public static final String COL_ID = "_id";
    public static final String COL_NOTE = "note";
    public static final String COL_IMPORTANT = "important";

    //ระวังพิมพ์ผิดเพราะมันจะไม่แจ้งเตือน มันเป็น string ต้องเว้นวรรคหน้าและหลังคำว่า integer " INTEGER....MENT, "
    private static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_NOTE + " TEXT, "
                    + COL_IMPORTANT + " INTEGER)";


    public DatabaseHelper(Context context) {  //Constructor เมื่อ new object จะมาเรียก
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {  //call back = เรียกอัตโนมัติเพียงครั้กแรกที่ยังไม่มีฐานข้อมูล
        db.execSQL(SQL_CREATE);
        ContentValues cv = new ContentValues();
        cv.put(COL_NOTE, "HELLO"); //key = ชื่อ column ใส่ string
        cv.put(COL_IMPORTANT, 1); //key = ชื่อ column ใส่ integer ดังนั้นลำดับไม่สำคัญ
        db.insert(TABLE_NAME, null,cv);  // insert ได้ทีละแถว

        cv = new ContentValues();
        cv.put(COL_NOTE, "HELLO2"); //key = ชื่อ column ใส่ string
        cv.put(COL_IMPORTANT, 0); //key = ชื่อ column ใส่ integer ดังนั้นลำดับไม่สำคัญ
        db.insert(TABLE_NAME, null, cv);  // insert ได้ทีละแถว

        cv = new ContentValues();
        cv.put(COL_NOTE, "HELLO3"); //key = ชื่อ column ใส่ string
        cv.put(COL_IMPORTANT, 0); //key = ชื่อ column ใส่ integer ดังนั้นลำดับไม่สำคัญ
        db.insert(TABLE_NAME, null,cv);  // insert ได้ทีละแถว
    }

    @Override
    // เปลี่ยน version ของ db จะมาทำงานที่นี่ เราสามารถแก้ไขโครงสร้าง (schema) ของ db

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);  //ลบตารางเดิมทิ้ง
        onCreate(db);
    }
}

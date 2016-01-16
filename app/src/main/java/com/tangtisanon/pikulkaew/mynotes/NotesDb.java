package com.tangtisanon.pikulkaew.mynotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

//สร้างมาเพื่อใช้แทน DBHelper copy ตัว helper เดิมมาแต่แก้เป็น private เรียก inner class ในตัว main ปกติต้องรู้ว่าตารางมีคอลัมอะไรบ้าง แต่ถ้าทำแบบนี้ไม่ต้องรู้ก็ได้ มาทำตรงนี้แทน

public class NotesDb {
    private static final String DATABASE_NAME = "notes.db"; //crtl+d = Copy this line
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "notes"; //class อื่นๆ เรียกได้, static เรียกใช้ได้เลยไม่ต้อง new object class.static method
    private static final String COL_ID = "_id";
    private static final String COL_NOTE = "note";
    private static final String COL_IMPORTANT = "important";

    //ระวังพิมพ์ผิดเพราะมันจะไม่แจ้งเตือน มันเป็น string ต้องเว้นวรรคหน้าและหลังคำว่า integer " INTEGER....MENT, "
    private static final String SQL_CREATE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COL_NOTE + " TEXT, "
                    + COL_IMPORTANT + " INTEGER)";
    private static DatabaseHelper mHelper;  // เป็นสมบัติของ class มันแชร์ได้กับทุกคลาสในนี้ เช่น setting ระบบทุกหน้าใช้ setting เดียว
    private SQLiteDatabase mDatabase;

    public NotesDb(Context context) {
        if (mHelper == null) {   //check ว่าถ้ามีแล้วจะไม่สร้าง object อีกเพราะมันเปลืองเมม
            mHelper = new DatabaseHelper(context);
        }
        mDatabase = mHelper.getWritableDatabase();
    }

    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> noteArrayList = new ArrayList<>();
        Cursor cursor = mDatabase.query(
                com.tangtisanon.pikulkaew.mynotes.DatabaseHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        //movetonext คือชี้ไปที่ข้อมูลแถวแรก เพราะตอนรับมามันยังไม่ได้ชี้ไปที่ไหนเลยถ้าถึงบันทัดสุดท้ายจะ return fault แล้วหลุดลูป
        //วนลูปแสดงผลหรือใช้ listviewหรือใช้ arraylist
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String note = cursor.getString(cursor.getColumnIndex("note")); //getstring ต้องการหมายเลขลำดับของ column เราเลยต้องแปลง
            int important = cursor.getInt(cursor.getColumnIndex("important")); //databasehelper.cal_important จะไม่ hardcode

            Note n = null;
            if (important == 0) {
                n = new Note(id, note, false);
            } else {
                n = new Note(id, note, true);
            }
            noteArrayList.add(n);
            Log.i("MainActivity", "note: " + note + ", important: " + important);
        }
        return noteArrayList;

    }

    public long insertNote(Note note){
        ContentValues cv = new ContentValues();
        cv.put(COL_NOTE, note.text);
        if(note.isImportant == true)
        cv.put(COL_IMPORTANT, "1");
        else
            cv.put(COL_IMPORTANT,"0");

        long result = mDatabase.insert(TABLE_NAME, null, cv);
        return result;  // return -1 คือ error ถ้าไม่ error จะ return ID ของแถว
    }
    public long insertNote(String text, boolean isImportant){
        return insertNote(
                new Note (-1,text,isImportant)
        );
    }


    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {  //Constructor เมื่อ new object จะมาเรียก
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {  //call back = เรียกอัตโนมัติเพียงครั้กแรกที่ยังไม่มีฐานข้อมูล
            db.execSQL(SQL_CREATE);
            ContentValues cv = new ContentValues();
            cv.put(COL_NOTE, "HELLO"); //key = ชื่อ column ใส่ string
            cv.put(COL_IMPORTANT, 1); //key = ชื่อ column ใส่ integer ดังนั้นลำดับไม่สำคัญ
            db.insert(TABLE_NAME, null, cv);  // insert ได้ทีละแถว

            cv = new ContentValues();
            cv.put(COL_NOTE, "HELLO2"); //key = ชื่อ column ใส่ string
            cv.put(COL_IMPORTANT, 0); //key = ชื่อ column ใส่ integer ดังนั้นลำดับไม่สำคัญ
            db.insert(TABLE_NAME, null, cv);  // insert ได้ทีละแถว

            cv = new ContentValues();
            cv.put(COL_NOTE, "HELLO3"); //key = ชื่อ column ใส่ string
            cv.put(COL_IMPORTANT, 0); //key = ชื่อ column ใส่ integer ดังนั้นลำดับไม่สำคัญ
            db.insert(TABLE_NAME, null, cv);  // insert ได้ทีละแถว
        }

        @Override
        // เปลี่ยน version ของ db จะมาทำงานที่นี่ เราสามารถแก้ไขโครงสร้าง (schema) ของ db

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);  //ลบตารางเดิมทิ้ง
            onCreate(db);
        }
    }
}

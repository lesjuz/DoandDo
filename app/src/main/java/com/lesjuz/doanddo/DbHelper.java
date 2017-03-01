package com.lesjuz.doanddo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Lesjuz on 2/28/2017.
 */

public class DbHelper extends SQLiteOpenHelper {
    public  static final  String DATABASE_NAME = "myToDo.db";
    public  static final  String TABLE_NAME = "myList_data";
    public  static final  String COL1 = "ID";
    public  static final  String COLUMN_ITEM = "Todo";
    SQLiteDatabase db;


    public DbHelper(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable="CREATE TABLE "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT, Todo TEXT);";
        db.execSQL(createTable);
        Log.d("db", "onCreate: db created ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP IF TABLE EXISTS"+TABLE_NAME);
    }

    public long addData(Todo todo){
        db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_ITEM,todo.getItem() );
        return db.insert(TABLE_NAME,null,cv);

    }

    public String editList(int id,String item) {
        String msg = "Sorry!! Error for Updating";
        db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_ITEM, item);
            db.update(TABLE_NAME, cv," " + COL1 + "=" + "'"
                    + id + "'", null);

            msg = "Successfully Updated!";
        } catch (Exception e) {
            msg = "Sorry!! Error for Updating! ";
        }

        return msg;




    }
    public void delete(int id) {
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL1 + " = ?",
                new String[] { Integer.toString(id) });
        db.close();
    }
    public ArrayList<Todo> getAllData() {
        open();
        ArrayList<Todo> mydata = new ArrayList<>();
        try {
            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Todo todo=new Todo();
                todo.setId(Integer.parseInt(cursor.getString(0)));
                todo.setItem(cursor.getString(1));
                mydata.add(todo);
                cursor.moveToNext();
            }
            cursor.close();
        } catch (Exception e) {

        }
        close();

        return mydata;

    }
    public void open() {
        try {
            db = this.getWritableDatabase();
        } catch (SQLException s) {
            new Exception("Error with DB Open");
        }
    }
    public void close() {
        db.close();
    }

}

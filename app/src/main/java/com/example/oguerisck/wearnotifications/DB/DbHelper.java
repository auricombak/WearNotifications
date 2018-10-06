package com.example.oguerisck.wearnotifications.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.oguerisck.wearnotifications.MainActivity;
import com.example.oguerisck.wearnotifications.Model.EventModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Oguerisck on 24/09/2018.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="TODODB";

    private static final int DB_VER = 1 ;
    public static final String DB_TABLE="Task";
    public static final String DB_COLUMN_TITLE="Title";
    public static final String DB_COLUMN_DESCRIPT="Description";
    public static final String DB_COLUMN_ADRESS="Adress";
    public static final String DB_COLUMN_DATE_A="Date_A";
    public static final String DB_COLUMN_DATE_B="Date_B";


    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE %s ( ID INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s DATE NOT NULL, %s DATE NOT NULL);", DB_TABLE, DB_COLUMN_TITLE, DB_COLUMN_DESCRIPT, DB_COLUMN_ADRESS, DB_COLUMN_DATE_A, DB_COLUMN_DATE_B);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        String query = String.format("DELETE TABLE IF EXISTS %s", DB_TABLE);
        db.execSQL(query);
        onCreate(db);


    }


    public void insertNewTask(String title, String description, String address, String date_a, String date_b){


        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COLUMN_TITLE, title);
        values.put(DB_COLUMN_DESCRIPT, description);
        values.put(DB_COLUMN_ADRESS, address);
        values.put(DB_COLUMN_DATE_A, date_a);
        values.put(DB_COLUMN_DATE_B, date_b);
        db.insertWithOnConflict(DB_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE );
    }

    public void deleteTask(String task){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE, DB_COLUMN_TITLE + " - ?", new String[]{task});
        db.close();
    }

    public ArrayList<EventModel> getTaskList() throws ParseException {
        ArrayList<EventModel> taskList = new ArrayList<EventModel>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor  cursor = db.rawQuery("select * from "+DB_TABLE,null);


        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String title = cursor.getString(cursor.getColumnIndex(DB_COLUMN_TITLE));
                String description = cursor.getString(cursor.getColumnIndex(DB_COLUMN_DESCRIPT));
                String adress = cursor.getString(cursor.getColumnIndex(DB_COLUMN_ADRESS));
                String dateB = cursor.getString(cursor.getColumnIndex(DB_COLUMN_DATE_A));
                String dateD = cursor.getString(cursor.getColumnIndex(DB_COLUMN_DATE_B));
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                EventModel e = new EventModel(title, description, adress, sdf.parse(dateB), sdf.parse(dateD));
                taskList.add(e);
                cursor.moveToNext();

                Log.d("DB", dateB.toString());
            }
        }
        cursor.close();
        db.close();



        return taskList;
    }


}



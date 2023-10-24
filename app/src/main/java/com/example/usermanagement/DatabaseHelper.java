package com.example.usermanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "UserManagement.db";
    private static final String TABLE_NAME = "USER_MANAGEMENT";
    private static final String COLUMN_USER_ID = "USER_ID";
    private static final String COLUMN_USERNAME = "USERNAME";
    private static final String COLUMN_AGE = "AGE";
    private static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_AVATAR = "AVATAR";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 2);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USERNAME + " TEXT, " + COLUMN_AGE + " INTEGER, " + COLUMN_EMAIL + " TEXT, " + COLUMN_AVATAR + " BLOB)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addUser(UserModel user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERNAME, user.getName());
        cv.put(COLUMN_AGE, String.valueOf(user.getAge()));
        cv.put(COLUMN_EMAIL, user.getEmail());
        cv.put(COLUMN_AVATAR, user.getAvatar());


        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            Toast.makeText(context, "Added successfully", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public ArrayList<UserModel> getAll() {
        ArrayList <UserModel> returnList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int age = Integer.parseInt(cursor.getString(2));
                String email = cursor.getString(3);
                byte[] avatar = cursor.getBlob(4);

                UserModel newUser = new UserModel(id, name, age, email, avatar);
                returnList.add(newUser);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return returnList;
    }
}

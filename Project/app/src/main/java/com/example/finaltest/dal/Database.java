package com.example.finaltest.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.finaltest.model.Target;
import com.example.finaltest.model.User;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="finalTest.db";
    private static int DATABASE_VERSION = 1;
    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String userTbl = "CREATE TABLE IF NOT EXISTS users (" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  username TEXT UNIQUE," +
                "  password TEXT," +
                "  fullname TEXT" +
                ");";

        db.execSQL(userTbl);

        String foodTbl = "CREATE TABLE IF NOT EXISTS foods (" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  name TEXT," +
                "  calories TEXT," +
                "  protein TEXT," +
                "  carbs TEXT," +
                "  fat TEXT" +
                ");";
        db.execSQL(foodTbl);

        String foodDaliTbl = "CREATE TABLE IF NOT EXISTS DailyFoods (" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  userId INTEGER," +
                "  foodId INTEGER," +
                "  date TEXT," +
                "  meal TEXT," +
                "  FOREIGN KEY (userId) REFERENCES users(id)," +
                "  FOREIGN KEY (foodId) REFERENCES foods(id)" +
                ");";
        db.execSQL(foodDaliTbl);

        String targetTbl = "CREATE TABLE IF NOT EXISTS Targets(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userId INTEGER," +
                "height TEXT, " +
                "weight TEXT, " +
                "weightTarget TEXT," +
                "age TEXT," +
                "r TEXT," +
                "sex TEXT," +
                "date TEXT," +
                "FOREIGN KEY (userId) REFERENCES users(id));";

        db.execSQL(targetTbl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    // thực hiện thao tác với bảng users
    public User createUser(User user){
        User existingUser = existUserByUsername(user.getUsername());

        if(existingUser == null){
            ContentValues values=new ContentValues();
            values.put("username", user.getUsername());
            values.put("password", user.getPassword());
            values.put("fullname", user.getFullname());
            SQLiteDatabase sqLiteDatabase=getWritableDatabase();
            sqLiteDatabase.insert("users",null,values);
            return existUserByUsername(user.getUsername());
        }
        return null;
    }

    public User login(User user){
        User existingUser = existUserByUsername(user.getUsername());
        if(existingUser != null && user.getPassword().trim().equalsIgnoreCase(existingUser.getPassword().trim())){
            return existingUser;
        }
        return null;
    }

    public User existUserByUsername(String username){
        User user = null;
        String whereClause = "username = ?";
        String[] whereArgs = {username};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("users", null, whereClause, whereArgs, null, null, null);

        while (st != null && rs.moveToNext()){
            int id =rs.getInt(0);
            String u = rs.getString(1);
            String p = rs.getString(2);
            String f = rs.getString(3);
            user = new User(id, u, p, f);
        }
        return user;
    }

    // thao tác với bảng targets
    public Target existsTarget(int userId){
        Target latestTarget = null;
        String whereClause = "userId = ?";
        String[] whereArgs = {String.valueOf(userId)};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("Targets", null, whereClause, whereArgs,
                null, null, "date DESC", "1");

        while (st != null && rs.moveToNext()){
            int id = rs.getInt(0);
            double height = Double.parseDouble(rs.getString(2).trim());
            double weight = Double.parseDouble(rs.getString(3).trim());
            double weightTarget = Double.parseDouble(rs.getString(4).trim());
            int age = Integer.parseInt(rs.getString(5).trim());
            double r = Double.parseDouble(rs.getString(6).trim());
            int sex = Integer.parseInt(rs.getString(7));
            String date = rs.getString(8);

            latestTarget = new Target(id, userId, height, weight, weightTarget, age, r, sex, date);
        }
        return latestTarget;
    }


    // thao tác với bảng foods
//    public
}

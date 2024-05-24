package com.example.finaltest.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.finaltest.model.Food;
import com.example.finaltest.model.FoodDaily;
import com.example.finaltest.model.Target;
import com.example.finaltest.model.User;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="finalTest1.db";
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

        String foodDailyTbl = "CREATE TABLE IF NOT EXISTS DailyFoods (" +
                "  id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "  userId INTEGER," +
                "  foodId INTEGER," +
                "  weight TEXT, " +
                "  totalKCal TEXT," +
                "  date TEXT," +
                "  meal TEXT," +
                "  FOREIGN KEY (userId) REFERENCES users(id)," +
                "  FOREIGN KEY (foodId) REFERENCES foods(id)" +
                ");";
        db.execSQL(foodDailyTbl);

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
    public long createTarget(Target newTarget){

        ContentValues values=new ContentValues();
        values.put("userId", newTarget.getUserId() +"");
        values.put("height", newTarget.getHeight() +"");
        values.put("weight", newTarget.getWeight() +"");
        values.put("weightTarget", newTarget.getWeightTarget() + "");
        values.put("age", newTarget.getAge()+"");
        values.put("r", newTarget.getR()+"");
        values.put("sex", newTarget.getSex());
        values.put("date", newTarget.getDate());

        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        return sqLiteDatabase.insert("Targets",null,values);
    }

    public int updateTarget(Target newTarget){
        ContentValues values=new ContentValues();
        values.put("userId", newTarget.getUserId() +"");
        values.put("height", newTarget.getHeight() +"");
        values.put("weight", newTarget.getWeight() +"");
        values.put("weightTarget", newTarget.getWeightTarget() + "");
        values.put("age", newTarget.getAge()+"");
        values.put("r", newTarget.getR()+"");
        values.put("sex", newTarget.getSex());
        values.put("date", newTarget.getDate());
        SQLiteDatabase db = getWritableDatabase();
        String whareClause = "id = ?";
        String[] whereArgs = {Integer.toString(newTarget.getId())};

        return db.update("Targets", values, whareClause, whereArgs);
    }
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
    public Long createFood(Food food){
        ContentValues values=new ContentValues();
        values.put("name", food.getName()+"");
        values.put("calories", food.getCalories()+"");
        values.put("protein", food.getProtein()+"");
        values.put("carbs", food.getCarbs()+"");
        values.put("fat", food.getFat()+"");

        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        return sqLiteDatabase.insert("foods",null,values);
    }

    public int deleteFood(int id){
        String whareClause = "id = ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase db = getWritableDatabase();

        return db.delete("foods", whareClause, whereArgs);
    }

    public List<Food> getAllFoods(){
        List<Food> list = new ArrayList<>();

        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("foods", null, null,
                null, null, null, null);

        while (rs != null && rs.moveToNext()){
            int id = rs.getInt(0);
            String name = rs.getString(1);
            double calories = Double.parseDouble(rs.getString(2));
            double protein = Double.parseDouble(rs.getString(3));
            double carbs = Double.parseDouble(rs.getString(4));
            double fat = Double.parseDouble(rs.getString(5));
            list.add(new Food(id, name, calories, protein, carbs, fat));
        }
        return list;
    }

    public Food getFoodById(int foodId){
        Food food = null;
        String whereClause = "id = ?";
        String[] whereArgs = {foodId+""};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("foods", null, whereClause, whereArgs, null, null, null);

        while (rs != null && rs.moveToNext()){
            int id = rs.getInt(0);
            String name = rs.getString(1);
            double calories = Double.parseDouble(rs.getString(2));
            double protein = Double.parseDouble(rs.getString(3));
            double carbs = Double.parseDouble(rs.getString(4));
            double fat = Double.parseDouble(rs.getString(5));
            food =  new Food(id, name, calories, protein, carbs, fat);
        }
        return food;
    }

    public List<Food> searchByName(String key){
        List<Food> list = new ArrayList<>();
        String whereClause = "name like ?";
        String[] whereArgs = {"%" + key + "%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("foods", null, whereClause, whereArgs, null, null, null);
        while (st != null && rs.moveToNext()){
            int id = rs.getInt(0);
            String name = rs.getString(1);
            double calories = Double.parseDouble(rs.getString(2));
            double protein = Double.parseDouble(rs.getString(3));
            double carbs = Double.parseDouble(rs.getString(4));
            double fat = Double.parseDouble(rs.getString(5));
            list.add(new Food(id, name, calories, protein, carbs, fat));
        }
        return list;
    }
    // thao tac voi fooddailys
    public Long createFoodDaily(FoodDaily foodDaily){
        ContentValues values=new ContentValues();
        values.put("userId", foodDaily.getUserId()+"");
        values.put("foodId", foodDaily.getFood().getId()+"");
        values.put("weight", foodDaily.getWeight()+"");
        values.put("totalKCal", foodDaily.getTotalKCal()+"");
        values.put("date", foodDaily.getDate()+"");
        values.put("meal", foodDaily.getMeal()+"");

        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        return sqLiteDatabase.insert("DailyFoods",null,values);
    }

    public int updateFoodDaily(FoodDaily foodDaily){
        ContentValues values=new ContentValues();
        values.put("userId", foodDaily.getUserId()+"");
        values.put("foodId", foodDaily.getFood().getId()+"");
        values.put("weight", foodDaily.getWeight()+"");
        values.put("totalKCal", foodDaily.getTotalKCal()+"");
        values.put("date", foodDaily.getDate()+"");
        values.put("meal", foodDaily.getMeal()+"");
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String whereClause = "id= ?";
        String[] whereArgs = {Integer.toString(foodDaily.getId())};
        return sqLiteDatabase.update("DailyFoods",values,whereClause,whereArgs);
    }

    public int deleteFoodDaily(int id){
        String whereClause = "id= ?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        return sqLiteDatabase.delete("DailyFoods",whereClause,whereArgs);
    }

    public List<FoodDaily> getAllFoodDailyByDate(String currentDate, int idUser){
        List<FoodDaily> list = new ArrayList<>();
        String whereClause = "userId = ? AND date like ?";
        String[] whereArgs = {idUser + "", "%" + currentDate.trim() + "%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("DailyFoods", null, whereClause, whereArgs, null, null, null);

        while(rs != null && rs.moveToNext()){
            int id = rs.getInt(0);
            int userId = rs.getInt(1);
            Food food = getFoodById(rs.getInt(2));
            int weight = Integer.parseInt(rs.getString(3));
            double totalKCal = Double.parseDouble(rs.getString(4));
            String date = rs.getString(5);
            String meal = rs.getString(6);
            list.add(new FoodDaily(id, userId, food,
                    weight, totalKCal, date, meal));
        }

        return list;
    }

}

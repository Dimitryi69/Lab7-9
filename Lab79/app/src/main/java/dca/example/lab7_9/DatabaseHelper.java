package dca.example.lab7_9;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    static String DATABASE_NAME = "Lab8_BD.db";
    static int SCHEMA = 1;
    public static String TABLE_RECIPE_NAME = "Recipe";
//    static String TABLE_FAVORITE_NAME = "Favorite";

    static String RECIPE_COLUMN_ID = "ID";
    static String RECIPE_COLUMN_TITLE = "Title";
    static String RECIPE_COLUMN_TYPE = "Type";
    static String RECIPE_COLUMN_DESCRIPTION = "Description";
    static String RECIPE_COLUMN_RECIPE = "Recipe";
    static String RECIPE_COLUMN_INGREDIENTS = "Ingredients";
    static String RECIPE_COLUMN_TIME = "Time";
    static String RECIPE_COLUMN_PHOTO = "Photo";
    static String RECIPE_COLUMN_FAVORITE ="Favorite";

    static String FAVORITE_COLUMN_ID = "ID";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        createTableRecipe(db);
//        createTableFavorite(db);
    }

    private void createTableRecipe(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_RECIPE_NAME+ " ("
                + RECIPE_COLUMN_ID + " INTEGER PRIMARY KEY,"
                + RECIPE_COLUMN_TITLE + " TEXT, "
                + RECIPE_COLUMN_TYPE + " TEXT,"
                + RECIPE_COLUMN_DESCRIPTION + " TEXT,"
                + RECIPE_COLUMN_RECIPE + " TEXT,"
                + RECIPE_COLUMN_INGREDIENTS + " TEXT,"
                + RECIPE_COLUMN_TIME + " INTEGER,"
                + RECIPE_COLUMN_PHOTO + " TEXT,"
                + RECIPE_COLUMN_FAVORITE + " INTEGER"
                +");");
        db.execSQL("INSERT INTO " + TABLE_RECIPE_NAME+
                " VALUES (0, 'Name', 'FirstType', 'FirstDesc', 'FirstRecipie', 'FirstIngridient\n', 15, '/data/data/dca.example.lab7_9/files/Rex.png',0);");
    }

//    private void createTableFavorite(SQLiteDatabase db) {
//        db.execSQL("CREATE TABLE " + TABLE_FAVORITE_NAME + " ("
//                + FAVORITE_COLUMN_ID + " INTEGER PRIMARY KEY"
//                +");");
//    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void dropTableRecipe(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_NAME);
        createTableRecipe(db);
    }

//    public void dropTableFavorite(SQLiteDatabase db) {
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVORITE_NAME);
//        createTableFavorite(db);
//    }

    public void insertRecipe(SQLiteDatabase db,int ID, String Title, String Type, String D, String Re, String I, int Time, String F,int favorite) {
        db.execSQL("INSERT INTO " + TABLE_RECIPE_NAME +
                " VALUES ("+ID+", '"+Title+"', '"+Type+"', '"+D+"', '"+Re+"', '"+I+"', "+Time+", '"+F+"',"+favorite+");");
    }
    public Cursor selectRecipe(SQLiteDatabase db, int id){
        return db.rawQuery("select * from " + TABLE_RECIPE_NAME+" where ID=?", new String[]{String.valueOf(id)});
    }

    public void deleteRecipe(SQLiteDatabase db, int id){
        db.execSQL("Delete FROM " + TABLE_RECIPE_NAME +
                " Where ID="+id+";");
    }
//
//    public void insertFavorite(SQLiteDatabase db,int id) {
//        db.execSQL("INSERT INTO " + TABLE_FAVORITE_NAME +
//                " VALUES ("+id+");");
//    }
//    public void deleteFavorite(SQLiteDatabase db,int id){
//        db.execSQL("Delete FROM " + TABLE_FAVORITE_NAME +
//                " Where ID="+id+");");
//    }
}

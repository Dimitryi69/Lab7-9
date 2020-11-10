package dca.example.bd10;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseS extends SQLiteOpenHelper
{
    public DataBaseS(Context context)
    {
        super(context, "STUDENTSDB", null, 1);
    }
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table GROUPS (" +
                "IDGROUP  integer primary key autoincrement," +
                "COURSE integer," +
                "NAME text unique," +
                "HEAD text);");
        db.execSQL("create table STUDENTS (" +
                "IDGROUP integer," +
                "IDSTUDENT integer primary key autoincrement," +
                "NAME text unique," +
                "FOREIGN KEY(IDGROUP) REFERENCES GROUPS(IDGROUP));");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(" drop table if exists GROUPS");
        db.execSQL(" drop table if exists STUDENTS");
        db.execSQL("create table GROUPS (" +
                "IDGROUP  integer primary key autoincrement," +
                "IDGROUP text," +
                "COURSE integer," +
                "NAME text," +
                "HEAD text);");
        db.execSQL("create table STUDENTS (" +
                "IDGROUP integer," +
                "IDSTUDENT integer primary key autoincrement," +
                "NAME text," +
                "FOREIGN KEY(IDGROUP) REFERENCES GROUPS(IDGROUP));");
    }
}

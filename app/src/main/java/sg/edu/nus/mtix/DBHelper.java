package sg.edu.nus.mtix;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int databaseVersion = 1;
    public static final String databaseName = "custDB";

    //table names
    public static final String QRTable = "QRCode";
    public static final String myPostTable = "post";
    public static final String allPostTable = "allPost";


    //qr table column names
    public static final String columnName1 = "_idQR";
    public static final String columnName2 = "img";

    //mypost table column names
    public static final String columnName3 = "_idMyPost";
    public static final String columnName4 = "title";
    public static final String columnName5 = "description";
    public static final String columnName6 = "image";
    public static final String columnName12 = "name";

    //all post table column names
    public static final String columnName7 = "_idAllPost";
    public static final String columnName8 = "allTitle";
    public static final String columnName9 = "allDescription";
    public static final String columnName10 = "allImage";
    public static final String columnName11 = "allName";

    //create table
    //qr table create statement
    private static final String SQLite_CREATE_QR =
            "CREATE TABLE " + QRTable + "(" + columnName1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + columnName2 + " BLOB);";

    //mypost table create statement
    private static final String SQLite_CREATE_MyPost =
            "CREATE TABLE " + myPostTable + "(" + columnName3 + " INTEGER PRIMARY KEY AUTOINCREMENT," + columnName4 + " TEXT NOT NULL," + columnName5 + " TEXT NOT NULL," + columnName6 + " BLOB," + columnName12 + " TEXT NOT NULL);";

    //allpost table create statement
    private static final String SQLite_CREATE_AllPost =
            "CREATE TABLE " + allPostTable + "(" + columnName7 + " INTEGER PRIMARY KEY AUTOINCREMENT," + columnName8 + " TEXT NOT NULL," + columnName9 + " TEXT NOT NULL," + columnName10 + " BLOB," + columnName11 + " TEXT NOT NULL);";

    private static final String SQLite_DELETE_QR = "DROP TABLE IF EXISTS" + QRTable;
    private static final String SQLite_DELETE_MyPost = "DROP TABLE IF EXISTS" + myPostTable;
    private static final String SQLite_DELETE_AllPost = "DROP TABLE IF EXISTS" + allPostTable;

    public DBHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLite_CREATE_QR);
        db.execSQL(SQLite_CREATE_MyPost);
        db.execSQL(SQLite_CREATE_AllPost);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQLite_DELETE_QR);
        db.execSQL(SQLite_DELETE_MyPost);
        db.execSQL(SQLite_DELETE_AllPost);
        onCreate(db);
    }
}

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


    //qr table column names
    public static final String columnName1 = "_idQR";
    public static final String columnName2 = "img";

    //mypost table column names
    public static final String columnName3 = "_idMyPost";
    public static final String columnName4 = "title";
    public static final String columnName5 = "description";
    public static final String columnName6 = "image";

    //create table
    //qr table create statement
    private static final String SQLite_CREATE_QR =
            "CREATE TABLE " + QRTable + "(" + columnName1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + columnName2 + " BLOB);";

    //mycontent table create statement
    private static final String SQLite_CREATE_MyPost =
            "CREATE TABLE " + myPostTable + "(" + columnName3 + " INTEGER PRIMARY KEY AUTOINCREMENT," + columnName4 + " TEXT NOT NULL," + columnName5 + " TEXT NOT NULL," + columnName6 + " BLOB);";


    private static final String SQLite_DELETE_QR = "DROP TABLE IF EXISTS" + QRTable;
    private static final String SQLite_DELETE_MyPost = "DROP TABLE IF EXISTS" + myPostTable;


    public DBHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLite_CREATE_QR);
        db.execSQL(SQLite_CREATE_MyPost);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQLite_DELETE_QR);
        db.execSQL(SQLite_DELETE_MyPost);
        onCreate(db);
    }
}

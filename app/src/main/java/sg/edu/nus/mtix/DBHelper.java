package sg.edu.nus.mtix;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final int databaseVersion = 1;
    public static final String databaseName = "custDB";

    //table names
    public static final String QRTable = "QRCode";

    //qr table column names
    public static final String columnName1 = "_idQR";
    public static final String columnName2 = "img";

    //create table
    //qr table create statement
    private static final String SQLite_CREATE_QR =
            "CREATE TABLE " + QRTable + "(" + columnName1 + " INTEGER PRIMARY KEY AUTOINCREMENT," + columnName2 + " BLOB);";

    private static final String SQLite_DELETE_QR = "DROP TABLE IF EXISTS" + QRTable;

    public DBHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLite_CREATE_QR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQLite_DELETE_QR);
        onCreate(db);
    }
}

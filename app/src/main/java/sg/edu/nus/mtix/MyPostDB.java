package sg.edu.nus.mtix;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyPostDB {

    DBHelper DBHelper;
    SQLiteDatabase db;
    final Context context;

    public MyPostDB(Context ctx) {
        this.context = ctx;
        DBHelper = new DBHelper(this.context);
    }

    public MyPostDB open() {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }

    public long insertRecord(String title, String content, byte[] data) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(DBHelper.columnName4, title);
        initialValues.put(DBHelper.columnName5, content);
        initialValues.put(DBHelper.columnName6, data);

        return db.insert(DBHelper.myPostTable, null, initialValues);
    }

    public Cursor getAllRecords() {
        return db.query(DBHelper.myPostTable, new String[] {DBHelper.columnName4, DBHelper.columnName5, DBHelper.columnName6},
                null, null, null, null, null);
    }
}

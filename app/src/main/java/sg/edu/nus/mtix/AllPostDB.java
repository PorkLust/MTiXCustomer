package sg.edu.nus.mtix;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AllPostDB {

    DBHelper DBHelper;
    SQLiteDatabase db;
    final Context context;

    public AllPostDB(Context ctx) {
        this.context = ctx;
        DBHelper = new DBHelper(this.context);
    }

    public AllPostDB open() {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }

    public long insertRecord(String title, String content, byte[] data) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(DBHelper.columnName8, title);
        initialValues.put(DBHelper.columnName9, content);
        initialValues.put(DBHelper.columnName10, data);

        return db.insert(DBHelper.allPostTable, null, initialValues);
    }

    public Cursor getAllRecords() {
        return db.query(DBHelper.allPostTable, new String[] {DBHelper.columnName8, DBHelper.columnName9, DBHelper.columnName10},
                null, null, null, null, null);
    }
}

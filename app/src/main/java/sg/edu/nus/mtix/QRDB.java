package sg.edu.nus.mtix;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class QRDB {

    DBHelper DBHelper;
    SQLiteDatabase db;
    final Context context;

    public QRDB(Context ctx) {
        this.context = ctx;
        DBHelper = new DBHelper(this.context);
    }

    public QRDB open() {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }

    public long insertRecord(byte[] qrcode) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(DBHelper.columnName2, qrcode);

        return db.insert(DBHelper.QRTable, null, initialValues);
    }

    public Cursor getRecord() {
        return db.query(DBHelper.QRTable, new String[] {DBHelper.columnName1, DBHelper.columnName2},
                null, null, null, null, null);
    }
}

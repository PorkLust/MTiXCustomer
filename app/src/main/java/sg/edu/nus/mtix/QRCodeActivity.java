package sg.edu.nus.mtix;

import android.graphics.BitmapFactory;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;

public class QRCodeActivity extends AppCompatActivity {

    private ImageView imageView;
    QRDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);

        imageView = (ImageView) findViewById(R.id.imageView);
        db = new QRDB(this);

        addQRCode();
        //imageView.setImageDrawable(getResources().getDrawable(R.drawable.code1));
    }

    //convert from bitmap to byte array to string and store in datebase
    public void addQRCode() {
        //convert from bitmap to byte array
        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.oct_29_2016);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, outputStream);
        byte[] data = outputStream.toByteArray();

        //insert to database
        db.open();
        db.insertRecord(data);
        db.close();

        getQRCode();
    }

    //get blob from database and convert from byte array to bitmap
    public void getQRCode() {
        db.open();
        Cursor c = db.getRecord();
        byte[] qrCode = new byte[]{};

        while (c.moveToNext()) {
            byte[] image = c.getBlob(1);
            qrCode = image;
        }

        db.close();
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(qrCode, 0, qrCode.length));

        /*
        ArrayList<String[]> records = new ArrayList<String[]>();

        if (c.moveToFirst()) {
            do {
                String[] record = {c.getString(0), c.getString(1)};
                records.add(record);
            } while (c.moveToNext());
        }
        db.close();

        //convert string to byte array
        byte[] bytes = myQRCode[1].getBytes();

        //convert from byte array to bitmap
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length)); */
    }

}

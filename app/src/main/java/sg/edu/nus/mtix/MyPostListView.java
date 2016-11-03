package sg.edu.nus.mtix;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyPostListView extends Activity implements AdapterView.OnItemClickListener {

    public static String[] titles;
    public static String[] descriptions;
    public static ArrayList<byte[]> images = new ArrayList<byte[]>();

    MyPostDB db;

    ListView listview;
    List<RowItem> rowItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_post_list_view);

        listview = (ListView) findViewById(R.id.listview);
        rowItems = new ArrayList<RowItem>();

        db = new MyPostDB(this);

        initialise(); //initialise title, description, images

        for (int i = titles.length-1; i >= 0; i--) {
            //for (int i = 0; i < titles.length; i++) {
            RowItem item = new RowItem(titles[i], descriptions[i], images.get(i));
            rowItems.add(item);
        }

        CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.activity_custom_list_view_adapter, rowItems);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast toast = Toast.makeText(getApplicationContext(), "Post " + (position + 1) + "  |  " + rowItems.get(position), Toast.LENGTH_SHORT);
        toast.show();
    }

    //initialise title, description, image
    public void initialise() {
        db.open();

        Cursor c = db.getAllRecords();
        ArrayList<String> atitles = new ArrayList<String>();
        ArrayList<String> adescriptions = new ArrayList<String>();

        if (c.moveToFirst()) {
            do {
                String[] record = {c.getString(0), c.getString(1)}; //title, description
                atitles.add(record[0]);
                adescriptions.add(record[1]);
                images.add(c.getBlob(2));
            } while (c.moveToNext());
        }
        db.close();

        //convert arraylist to string[] for titles and descriptions
        titles = new String[atitles.size()];
        titles = atitles.toArray(titles);

        descriptions = new String[adescriptions.size()];
        descriptions = adescriptions.toArray(descriptions);

    }
}

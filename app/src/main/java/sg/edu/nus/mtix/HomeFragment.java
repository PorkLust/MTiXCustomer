package sg.edu.nus.mtix;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    public static String[] titles;
    public static String[] descriptions;
    public static ArrayList<byte[]> images = new ArrayList<byte[]>();

    AllPostDB db;
    ListView listview;
    List<RowItem> rowItems;

    private OnFragmentInteractionListener listener;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity)getActivity()).setActionBarTitle("Home");
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        listview = (ListView) view.findViewById(R.id.listview1);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {

                //String value = (String)adapter.getItemAtPosition(position);
                // assuming string and if you want to get the value on click of list item
                // do what you intend to do on click of listview row
                RowItem row = rowItems.get(position);
            }
        });
        rowItems = new ArrayList<RowItem>();

        db = new AllPostDB(getActivity());

        //addDummyRecord();

        initialise(); //initialise title, description, images

        for (int i = titles.length-1; i >= 0; i--) {
            //for (int i = 0; i < titles.length; i++) {
            RowItem item = new RowItem(titles[i], descriptions[i], images.get(i));
            rowItems.add(item);
        }

        CustomListViewAdapter adapter = new CustomListViewAdapter(getActivity(), R.layout.fragment_home, rowItems);
        listview.setAdapter(adapter);

        return view;
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }


    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String str);
    }

    public void addDummyRecord() {
        String content1 = "Just ytd, I attended the Jay Chou concert tour 2016! Certaintly left me deep memories as he sang some of the 2000s' songs that i have been listening since young! Such nostalgic moments! I had a really wonderful time then and i would defintely go for the next one in future!";
        Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getApplicationContext().getResources(), R.drawable.jaychou);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0, os);
        byte[] data = os.toByteArray();

        String content2 = "JJ LIN CONCERT ROCKS! :D AN AVID FAN OF HIS AND HIS CONCERT NV SEEMS TO FAIL ME!:)) HOPE THAT HIS FAN BASE IS ALWAYS INCREASING! WILL SUPPORT HIM FOREVER!:')";
        Bitmap bitmap1 = BitmapFactory.decodeResource(getActivity().getApplicationContext().getResources(), R.drawable.jjlin);
        ByteArrayOutputStream os1 = new ByteArrayOutputStream();
        bitmap1.compress(Bitmap.CompressFormat.JPEG, 0, os1);
        byte[] data1 = os1.toByteArray();

        db.open();
        db.insertRecord("Jay Chou 2016 World Tour", content1, data);
        db.insertRecord("JJ LIN CONCERT", content2, data1);
        db.close();
    }
}


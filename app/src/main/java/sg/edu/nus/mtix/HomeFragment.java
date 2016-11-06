package sg.edu.nus.mtix;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public static String[] names;

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

        ((MainActivity)getActivity()).setActionBarTitle("Feed");
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        listview = (ListView) view.findViewById(R.id.listview1);

        rowItems = new ArrayList<RowItem>();

        db = new AllPostDB(getActivity());

        addDummyRecord();

        initialise(); //initialise title, description, images, names

        for (int i = titles.length-1; i >= 0; i--) {
            RowItem item = new RowItem(titles[i], descriptions[i], images.get(i), names[i]);
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
        ArrayList<String> anames = new ArrayList<String>();
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String key = pref.getString("name", null);


        if (c.moveToFirst()) {
            do {
                String[] record = {c.getString(0), c.getString(1), c.getString(3)}; //title, description, name
                atitles.add(record[0]);
                adescriptions.add(record[1]);
                images.add(c.getBlob(2));
                anames.add(record[2]);
            } while (c.moveToNext());
        }
        db.close();

        //convert arraylist to string[] for titles and descriptions
        titles = new String[atitles.size()];
        titles = atitles.toArray(titles);

        descriptions = new String[adescriptions.size()];
        descriptions = adescriptions.toArray(descriptions);

        names = new String[anames.size()];
        names = anames.toArray(names);
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
        db.insertRecord("Jay Chou 2016 World Tour", content1, data, "Dan");
        db.insertRecord("JJ LIN CONCERT", content2, data1, "Crystal");
        db.close();
    }
}


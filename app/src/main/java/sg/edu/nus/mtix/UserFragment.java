package sg.edu.nus.mtix;


import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {
    public static String[] titles;
    public static String[] descriptions;
    public static String[] names;
    public static ArrayList<byte[]> images = new ArrayList<byte[]>();

    MyPostDB db;

    ListView listview;
    List<RowItem> rowItems;

    private OnFragmentInteractionListener listener;

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((MainActivity)getActivity()).setActionBarTitle("Profile");
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        listview = (ListView) view.findViewById(R.id.listview);
        rowItems = new ArrayList<RowItem>();

        db = new MyPostDB(getActivity());

        initialise(); //initialise title, description, images

        for (int i = titles.length-1; i >= 0; i--) {
            //for (int i = 0; i < titles.length; i++) {
            RowItem item = new RowItem(titles[i], descriptions[i], images.get(i), names[i]);
            rowItems.add(item);
        }

        CustomListViewAdapter adapter = new CustomListViewAdapter(getActivity(), R.layout.fragment_user, rowItems);
        listview.setAdapter(adapter);

        return view;
    }

    /*
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast toast = Toast.makeText(getActivity(), "Post " + (position + 1) + "  |  " + rowItems.get(position), Toast.LENGTH_SHORT);
        toast.show();
    } */

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
                String[] record = {c.getString(0), c.getString(1)}; //title, description
                atitles.add(record[0]);
                adescriptions.add(record[1]);
                images.add(c.getBlob(2));
                anames.add(key);
            } while (c.moveToNext());
        }
        db.close();

        //convert arraylist to string[] for titles and descriptions
        titles = new String[atitles.size()];
        titles = atitles.toArray(titles);

        descriptions = new String[adescriptions.size()];
        descriptions = adescriptions.toArray(descriptions);

        names = new String[atitles.size()];
        names = adescriptions.toArray(names);
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
}

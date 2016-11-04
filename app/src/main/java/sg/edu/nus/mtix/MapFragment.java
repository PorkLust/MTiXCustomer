package sg.edu.nus.mtix;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment  {

    TextView tvStarTheatre, tvStarArena, tvStarAtria;
    ImageView imageView1, imageView2, imageView3;

    private OnFragmentInteractionListener listener;

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public MapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        tvStarTheatre = (TextView) view.findViewById(R.id.textView1);
        tvStarArena = (TextView) view.findViewById(R.id.textView2);
        tvStarAtria = (TextView) view.findViewById(R.id.textView3);
        imageView1 = (ImageView) view.findViewById(R.id.imageView1);
        imageView2 = (ImageView) view.findViewById(R.id.imageView2);
        imageView3 = (ImageView) view.findViewById(R.id.imageView3);

        imageView1.setImageResource(R.drawable.startheatre);
        imageView2.setImageResource(R.drawable.arena);
        imageView3.setImageResource(R.drawable.atria);

        tvStarTheatre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getActivity(), MapsTheatreActivity.class);
                startActivity(myIntent);
            }
        });

        tvStarArena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getActivity(), MapsArenaActivity.class);
                startActivity(myIntent);
            }
        });

        tvStarAtria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getActivity(), MapsAtriaActivity.class);
                startActivity(myIntent);
            }
        });

        return view;
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

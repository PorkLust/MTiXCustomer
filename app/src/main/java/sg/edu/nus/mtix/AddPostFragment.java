package sg.edu.nus.mtix;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddPostFragment extends DialogFragment {

    private EditText title;
    private EditText content;
    private ImageView imageView;
    String imgDecodableString = "";
    Toolbar toolbar;
    Button postBtn;
    ImageButton closeBtn;
    MyPostDB db;
    AllPostDB db1;

    private OnFragmentInteractionListener listener;

    public static AddPostFragment newInstance() {
        return new AddPostFragment();
    }

    private static int RESULT_LOAD_IMG = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(AddPostFragment.STYLE_NO_TITLE, R.style.DialogSlideAnim);

        setHasOptionsMenu(true);

        db = new MyPostDB(getActivity());
        db1 = new AllPostDB(getActivity());

    }

    public AddPostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_post, container, false);
        title = (EditText) view.findViewById(R.id.titleInput);
        content = (EditText) view.findViewById(R.id.contentInput);
        imageView = (ImageView) view.findViewById(R.id.imageViewDesc);

        TextView newPostTitle = (TextView) view.findViewById(R.id.newPostTitle);
        Typeface custom_font = Typeface.createFromAsset(getContext().getApplicationContext().getAssets(),  "fonts/ProximaNovaSoft-Bold.otf");
        newPostTitle.setTypeface(custom_font);

        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        postBtn = (Button) view.findViewById(R.id.btn_post);
        closeBtn = (ImageButton)view.findViewById(R.id.btn_back);
        postBtn.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String titleTyped = title.getText().toString();
                String contentTyped = content.getText().toString();
                Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream);
                byte[] data = outputStream.toByteArray();

                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                String key = pref.getString("name", null);

                System.out.println("Testing database1");
                db.open();
                db.insertRecord(titleTyped, contentTyped, data, key);
                db.close();

                System.out.println("Testing database2");

                db1.open();
                db1.insertRecord(titleTyped, contentTyped, data, key);
                db1.close();

                System.out.println("Ended database");

                Toast.makeText(getActivity(), "Post loaded successfully.", Toast.LENGTH_SHORT).show();

                Fragment fragment = UserFragment.newInstance();

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();

                dismiss();
            }
        });

        closeBtn.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction =  getActivity().getSupportFragmentManager().beginTransaction();
                Fragment homeFragment = new HomeFragment();
                fragmentTransaction.replace(R.id.frameLayout, homeFragment, "homeFragment");
                fragmentTransaction.addToBackStack("homeFragment");
                fragmentTransaction.commit();
                dismiss();
            }
        });


        Button uploadImageBtn = (Button) view.findViewById(R.id.button2);
        uploadImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(myIntent, RESULT_LOAD_IMG);
            }
        });

        // Inflate the layout for this fragment
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            //when image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == getActivity().RESULT_OK && null != data) {
                //get image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                //get cursor
                Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);

                //move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                //set image in imageview after decoding the string

                Bitmap bitmapImage = BitmapFactory.decodeFile(imgDecodableString);

                int nh = (int) ( bitmapImage.getHeight() * (512.0 / bitmapImage.getWidth()) );
                Bitmap scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true);
                imageView.setImageBitmap(scaled);
            }
        } catch (Exception e) {
            System.out.println("Failed to upload image");
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        //inflater.inflate(R.menu.activity_main_actions, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String str);
    }
}

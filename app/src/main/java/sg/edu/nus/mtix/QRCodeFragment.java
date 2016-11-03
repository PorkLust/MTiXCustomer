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
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class QRCodeFragment extends Fragment {

    private ImageView imageView;
    QRDB db;

    private OnFragmentInteractionListener listener;

    public static QRCodeFragment newInstance() {
        return new QRCodeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        db = new QRDB(getActivity());

        //addQRCode();
    }

    //convert from bitmap to byte array to string and store in datebase
    public void addQRCode() {
        //convert from bitmap to byte array
        Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.oct_29_2016);
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
    }

    public QRCodeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qrcode, container, false);
        // Inflate the layout for this fragment
        imageView = (ImageView) view.findViewById(R.id.imageView_QRCode);
        getQRCode();
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

package sg.edu.nus.mtix;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentInteractionListener,
        QRCodeFragment.OnFragmentInteractionListener, MapFragment.OnFragmentInteractionListener, UserFragment.OnFragmentInteractionListener
        ,AddPostFragment.OnFragmentInteractionListener{

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar);

        setContentView(R.layout.activity_main);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, HomeFragment.newInstance());
        fragmentTransaction.commit();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        fragment = HomeFragment.newInstance();
                        break;
                    case R.id.menu_qrcode:
                        fragment = QRCodeFragment.newInstance();
                        break;
                    case R.id.menu_map:
                        fragment = MapFragment.newInstance();
                        break;
                    case R.id.menu_user:
                        fragment = UserFragment.newInstance();
                        break;
                    case R.id.menu_addpost:
                        FragmentManager fm = getSupportFragmentManager();
                        AddPostFragment postFragment = new AddPostFragment ();
                        postFragment.show(fm, "Sample Fragment");
                        //fragment = AddPostFragment.newInstance();
                        break;
                }
                if (fragment != null) {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.frameLayout, fragment);
                    fragmentTransaction.commit();
                }
                return true;
            }
        });
    }


    public void setActionBarTitle(String title) {
        View v = getSupportActionBar().getCustomView();
        TextView titleTxtView = (TextView) v.findViewById(R.id.actionBarTitle);
        titleTxtView.setText(title);
    }

    public void onFragmentInteraction(String str){

    }
}

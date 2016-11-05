package sg.edu.nus.mtix;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {

            //showcase company logo
            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent myIntent = new Intent(SplashActivity.this, WelcomeActivity.class);
                startActivity(myIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}

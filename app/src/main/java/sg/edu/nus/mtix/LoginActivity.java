package sg.edu.nus.mtix;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.Header;

public class LoginActivity extends AppCompatActivity {
    EditText editText_username;
    EditText editText_password;
    ProgressDialog prgDialog;
    TextView errorMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        editText_username = (EditText)findViewById(R.id.editText_username);
        editText_password = (EditText)findViewById(R.id.editText_password);

        prgDialog = new ProgressDialog(this);
        prgDialog.setMessage("Please wait...");
        prgDialog.setCancelable(false);
    }

    public void onClick_Login(View view){
        //  Intent myIntent = new Intent (this,MainActivity.class);
        // startActivity(myIntent);
        String username = editText_username.getText().toString();
        String password = editText_password.getText().toString();
        errorMsg = (TextView)findViewById(R.id.login_error);
        RequestParams params = new RequestParams();

        if(Utility.isNotNull(username) && Utility.isNotNull(password)){
            // When Email entered is Valid
            if(Utility.validate(username)){
                params.put("username", username);
                params.put("password", password);
                invokeWS(params);
            }
            // When Email is invalid
            else{
                Toast.makeText(getApplicationContext(), "Please enter valid email", Toast.LENGTH_LONG).show();
            }
        } else{
            Toast.makeText(getApplicationContext(), "Please fill the form, don't leave any field blank", Toast.LENGTH_LONG).show();
        }


        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = pref.edit();
        String[] arr = username.split("@");
        //editor.putString("name","Ryan");
        editor.putString("name", arr[0]);
        editor.commit(); //save changes in SharedPreferences

    }


    public void invokeWS(RequestParams params){
        // Show Progress Dialog
        prgDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object


        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://10.0.2.2:8080/MTiXBackend/webresources/mtixwebservice/login",params ,new AsyncHttpResponseHandler() {
            //client.post("http://172.25.101.71:8080/MTiXBackend/webresources/mtixwebservice/login",params ,new AsyncHttpResponseHandler() {
        //client.post("http://192.168.1.2:8080/MTiXBackend/webresources/mtixwebservice/login",params ,new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                prgDialog.hide();
                try {
                    // JSON Object
                    //JSONObject obj = response;
                    // When the JSON response has status boolean value assigned with true
                    String result = new String(response, "UTF-8");
                    if(result.equals("true")){

                        // one min.
                        Toast.makeText(getApplicationContext(), "You are successfully logged in!", Toast.LENGTH_LONG).show();
                        // Navigate to Home screen
                        navigateToHomeActivity();
                    }
                    // Else display error message
                    else{
                        errorMsg.setText("Invalid");
                        // Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] response, Throwable throwable) {
                // Hide Progress Dialog
                prgDialog.hide();
                // When Http response code is '404'
                if(statusCode == 404){
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else{
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void navigateToHomeActivity(){
        Intent homeIntent = new Intent(getApplicationContext(),MainActivity.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}

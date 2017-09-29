package cardscaner.cfcs.com.cardscanner.MainClass;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cardscaner.cfcs.com.cardscanner.MainClass.HomeActivity;
import cardscaner.cfcs.com.cardscanner.R;
import cardscaner.cfcs.com.cardscanner.source.AppController;
import cardscaner.cfcs.com.cardscanner.source.ConnectionDetector;
import cardscaner.cfcs.com.cardscanner.source.SettingConstant;
import cardscaner.cfcs.com.cardscanner.source.SharedPrefs;
import cardscaner.cfcs.com.cardscanner.source.UtilsMethods;

public class LoginActivity extends AppCompatActivity {

    public Button loginBtn;
    public TextView forgetPassBtn ;
    public EditText userNmatTxt, passwordTxt;
    public String loginUrl = SettingConstant.BASEURL_FOR_LOGIN + "loginservice.asmx/AppUserLogin";
    public ConnectionDetector conn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        }


        loginBtn = (Button)findViewById(R.id.loginbtn);
        forgetPassBtn = (TextView)findViewById(R.id.forgetpass);
        userNmatTxt = (EditText)findViewById(R.id.usernmaetxt);
        passwordTxt = (EditText)findViewById(R.id.passwordtxt);

        conn = new ConnectionDetector(LoginActivity.this);

        forgetPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), ForgetPasswordActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
                finish();

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("checking the url is", loginUrl);


                String AuthCode = "";
                // Device model
                String PhoneModel = android.os.Build.MODEL;

                // Android version
                String AndroidVersion = android.os.Build.VERSION.RELEASE;

                long randomNumber = (long) ((Math.random() * 9000000) + 1000000);
                AuthCode = String.valueOf(randomNumber);

                if (userNmatTxt.getText().toString().equalsIgnoreCase(""))
                {
                    userNmatTxt.setError("Please Enter Valid User Name");
                }else if (passwordTxt.getText().toString().equalsIgnoreCase(""))
                {
                    passwordTxt.setError("Please Enter Valid Password");
                }else
                    {

                        if (conn.getConnectivityStatus() > 0) {
                            Login_Api(userNmatTxt.getText().toString(), passwordTxt.getText().toString(),
                                    AuthCode, PhoneModel, AndroidVersion);
                        }else
                            {
                                conn.showNoInternetAlret();
                            }
                    }

               /* Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
                finish();*/
            }
        });


        // CHECKED PERMISSION
        if (Build.VERSION.SDK_INT == 16 || Build.VERSION.SDK_INT == 17 ||
                Build.VERSION.SDK_INT == 18 || Build.VERSION.SDK_INT == 19)
        {

            loginBtn.setBackgroundColor(getResources().getColor(R.color.white));
        }
        else
        {
            loginBtn.setBackgroundResource(R.drawable.rippileefact);
        }
    }

    public void Login_Api(final String UserName  , final String Password, final String AuthCode ,
                          final String ClientName , final String ClientVersion) {
        final ProgressDialog pDialog = new ProgressDialog(LoginActivity.this,R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest historyInquiry = new StringRequest(
                Request.Method.POST, loginUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("Login", response);
                    JSONArray jsonArray = new JSONArray(response.substring(response.indexOf("["),response.lastIndexOf("]") +1 ));

                    for (int i=0 ; i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                       // String status = jsonObject.getString("status");
                        if (jsonObject.has("MsgNotification"))
                        {
                            String MsgNotification = jsonObject.getString("MsgNotification");
                            Toast.makeText(LoginActivity.this, MsgNotification, Toast.LENGTH_SHORT).show();

                        }else
                        {

                            String UserID = jsonObject.getString("UserID");
                            String Name = jsonObject.getString("Name");
                            String RoleName = jsonObject.getString("RoleName");
                            String EmailID = jsonObject.getString("EmailID");
                            String MobileNo = jsonObject.getString("MobileNo");
                            String ZoneID = jsonObject.getString("ZoneID");
                            String ZoneName = jsonObject.getString("ZoneName");
                            String AuthCode = jsonObject.getString("AuthCode");




                            Intent ik = new Intent(getApplicationContext(),HomeActivity.class);
                            startActivity(ik);
                            overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
                            finish();


                            UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.setStatus(LoginActivity.this,
                                    "1")));
                            UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.setUserId(LoginActivity.this,
                                    UserID)));
                            UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.setAuthCode(LoginActivity.this, AuthCode)));

                            UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.setZoneId(LoginActivity.this, ZoneID)));

                        }


                    }

                    pDialog.dismiss();

                } catch (JSONException e) {
                    Log.e("checking json excption" , e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Login", "Error: " + error.getMessage());
                // Log.e("checking now ",error.getMessage());

                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();


            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("UserName", UserName);
                params.put("Password",Password);
                params.put("AuthCode",AuthCode);
                params.put("ClientName",ClientName);
                params.put("ClientVersion",ClientVersion);

                Log.e("Parms", params.toString());
                return params;
            }

        };
        historyInquiry.setRetryPolicy(new DefaultRetryPolicy(SettingConstant.Retry_Time,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(historyInquiry, "Login");

    }
}
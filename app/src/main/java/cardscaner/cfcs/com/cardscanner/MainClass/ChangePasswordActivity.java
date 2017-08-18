package cardscaner.cfcs.com.cardscanner.MainClass;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cardscaner.cfcs.com.cardscanner.R;
import cardscaner.cfcs.com.cardscanner.source.AppController;
import cardscaner.cfcs.com.cardscanner.source.ConnectionDetector;
import cardscaner.cfcs.com.cardscanner.source.SettingConstant;
import cardscaner.cfcs.com.cardscanner.source.SharedPrefs;
import cardscaner.cfcs.com.cardscanner.source.UtilsMethods;

public class ChangePasswordActivity extends AppCompatActivity {

    public ImageView backbtnImg;
    public EditText currentPasswordtxt, newpasswordTxt, confirmnewPassTxt;
    public Button changePassBtn;
    public String userId="", authcode = "";
    public ConnectionDetector conn;
    public String changePassUrl = SettingConstant.BASEURL_FOR_LOGIN + "loginservice.asmx/AppUserChangePassword";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbottomnavigation);
        setSupportActionBar(toolbar);
        backbtnImg = (ImageView)toolbar.findViewById(R.id.backbtn);

        backbtnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
                finish();
            }
        });

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        conn = new ConnectionDetector(ChangePasswordActivity.this);

        userId = UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getUserId(ChangePasswordActivity.this)));
        authcode = UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAuthCode(ChangePasswordActivity.this)));

        currentPasswordtxt = (EditText)findViewById(R.id.currentpass);
        newpasswordTxt = (EditText) findViewById(R.id.newpassword);
        confirmnewPassTxt = (EditText) findViewById(R.id.confirmnewpassword);
        changePassBtn = (Button) findViewById(R.id.chagepasswordbtn);

        changePassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentPasswordtxt.getText().toString().equalsIgnoreCase(""))
                {
                    currentPasswordtxt.setError("Please Enter Valid Current Password");
                }else if (newpasswordTxt.getText().toString().equalsIgnoreCase(""))
                {
                    newpasswordTxt.setError("Please Enter Valid New Password");
                }else if (!newpasswordTxt.getText().toString().equalsIgnoreCase(confirmnewPassTxt.getText().toString()))
                {
                    confirmnewPassTxt.setError("New Password and Confirm Password is not match");
                }else {

                    if (conn.getConnectivityStatus()>0) {
                        chnagePasswordMethod(userId, currentPasswordtxt.getText().toString(), newpasswordTxt.getText().toString(), authcode);
                    }else
                        {
                            conn.showNoInternetAlret();
                        }
                }
            }
        });
    }

    //Api Work
    public void chnagePasswordMethod(final String userId, final String currentPass, final String newPass, final String authCode ) {

        final ProgressDialog pDialog = new ProgressDialog(ChangePasswordActivity.this,R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest historyInquiry = new StringRequest(
                Request.Method.POST, changePassUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("Login", response);
                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"),response.lastIndexOf("}") +1 ));

                   /* for (int i=0 ; i<jsonArray.length();i++)
                    {*/
                    // JSONObject jsonObject = jsonArray.getJSONObject(i);
                    // String status = jsonObject.getString("status");
                    if (jsonObject.has("MsgNotification"))
                    {
                        String MsgNotification = jsonObject.getString("MsgNotification");
                        Toast.makeText(ChangePasswordActivity.this, MsgNotification, Toast.LENGTH_SHORT).show();

                    }


                    //}

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

                Toast.makeText(ChangePasswordActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();


            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("UserID", userId);
                params.put("OldPassword", currentPass);
                params.put("NewPassword", newPass);
                params.put("AuthCode", authCode);

                // Log.e("Parms", params.toString());
                return params;
            }

        };
        historyInquiry.setRetryPolicy(new DefaultRetryPolicy(SettingConstant.Retry_Time,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(historyInquiry, "Login");

    }

}

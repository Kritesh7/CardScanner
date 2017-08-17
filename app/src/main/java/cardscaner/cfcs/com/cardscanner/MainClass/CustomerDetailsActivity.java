package cardscaner.cfcs.com.cardscanner.MainClass;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
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

import cardscaner.cfcs.com.cardscanner.Model.CardListModel;
import cardscaner.cfcs.com.cardscanner.R;
import cardscaner.cfcs.com.cardscanner.source.AppController;
import cardscaner.cfcs.com.cardscanner.source.SettingConstant;
import cardscaner.cfcs.com.cardscanner.source.SharedPrefs;
import cardscaner.cfcs.com.cardscanner.source.UtilsMethods;

public class CustomerDetailsActivity extends AppCompatActivity {

    public ImageView backbtnImg;
    public TextView nameTxt, designationtxt,companynameTxt,zoneNameTxt,mangementLevelTxt, businessverticalTxt, industryTypetxt,
    principleTypeTxt, industryTypeSegmentTxt,emailIdtxt,phonenumbertxt,websitetxt;
    public String userId = "", authCode = "",customerId = "";
    public String getCustomerDetailsUrl = SettingConstant.BASEURL_FOR_LOGIN + "DigiCardScannerService.asmx/AppCustomerDetail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbottomnavigation);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if (intent != null)
        {
            customerId = intent.getStringExtra("CustomerId");
        }

        Log.e("Customer id is", customerId);

        backbtnImg = (ImageView)toolbar.findViewById(R.id.backbtn);
        nameTxt = (TextView)findViewById(R.id.nametxt);
        designationtxt = (TextView)findViewById(R.id.designationtxt);
        companynameTxt = (TextView)findViewById(R.id.companytxt);
        zoneNameTxt = (TextView)findViewById(R.id.zonenametxt);
        mangementLevelTxt = (TextView)findViewById(R.id.mangementleveltxt);
        businessverticalTxt = (TextView)findViewById(R.id.businessverticaltxt);
        industryTypetxt = (TextView)findViewById(R.id.induserytypenametxt);
        principleTypeTxt = (TextView)findViewById(R.id.principletxt);
        industryTypeSegmentTxt = (TextView)findViewById(R.id.industrysegmetnttxt);
        emailIdtxt = (TextView)findViewById(R.id.emailidtxt);
        phonenumbertxt = (TextView)findViewById(R.id.phonetxt);
        websitetxt = (TextView)findViewById(R.id.websitetxt);

        userId = UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getUserId(CustomerDetailsActivity.this)));
        authCode = UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAuthCode(CustomerDetailsActivity.this)));

        backbtnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
                finish();
            }
        });

        getSupportActionBar().setDisplayShowTitleEnabled(false);


      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        getCustomerList(authCode,userId,customerId);
    }

    //Api Work
    public void getCustomerList(final String authCode, final String userId, final String CustomerID) {
        final ProgressDialog pDialog = new ProgressDialog(CustomerDetailsActivity.this,R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest historyInquiry = new StringRequest(
                Request.Method.POST, getCustomerDetailsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("AppDdlList", response);
                    JSONArray jsonArray = new JSONArray(response.substring(response.indexOf("["),response.lastIndexOf("]") +1 ));


                    for (int i=0; i<jsonArray.length(); i++)
                    {
                        JSONObject object = jsonArray.getJSONObject(i);
                        String Name =object.getString("Name");
                        String Designation = object.getString("Designation");
                        String Company = object.getString("Company");
                        String ZoneName = object.getString("ZoneName");
                        String EmailID = object.getString("EmailID");
                        String Number = object.getString("Number");
                        String OfficeAddress = object.getString("OfficeAddress");
                        String PrincipleName = object.getString("PrincipleName");
                        String BusinessVerticalName = object.getString("BusinessVerticalName");
                        String IndustryTypeName = object.getString("IndustryTypeName");
                        String IndustrySegmentName = object.getString("IndustrySegmentName");
                        String Website = object.getString("Website");
                        String ManagementLevel = object.getString("ManagementLevel");

                        nameTxt.setText(Name);
                        designationtxt.setText(Designation);
                        companynameTxt.setText(Company);
                        zoneNameTxt.setText(ZoneName);
                        emailIdtxt.setText(EmailID);
                        phonenumbertxt.setText(Number);
                        mangementLevelTxt.setText(ManagementLevel);
                        principleTypeTxt.setText(PrincipleName);
                        businessverticalTxt.setText(BusinessVerticalName);
                        industryTypetxt.setText(IndustryTypeName);
                        industryTypeSegmentTxt.setText(IndustrySegmentName);
                        websitetxt.setText(Website);




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

                Toast.makeText(CustomerDetailsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();


            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("AuthCode", authCode);
                params.put("UserID", userId);
                params.put("CustomerID", CustomerID);
                // Log.e("Parms", params.toString());
                return params;
            }

        };
        historyInquiry.setRetryPolicy(new DefaultRetryPolicy(SettingConstant.Retry_Time,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(historyInquiry, "AddDdlList");

    }


}

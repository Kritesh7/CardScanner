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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    principleTypeTxt, industryTypeSegmentTxt,emailIdtxt,phonenumbertxt,phonenumbertxt2,phonenumbertxt3,phonenumbertxt4,
            phonenumbertxt5,websitetxt,addressTxt,residentalAddTxt,officeaddTxt,phoneType,phoneType2,phoneType3,
            phoneType4,phoneType5;
    public String userId = "", authCode = "",customerId = "";
    public Button editBtn;
    public LinearLayout phLay, phLay2,phLay3,phLay4,phLay5,busLay,indLay,indsegLay,priLay;
    public String getCustomerDetailsUrl = SettingConstant.BASEURL_FOR_LOGIN + "DigiCardScannerService.asmx/AppCustomerDetail";
    public String Name = "",Designation ="",Company="",ZoneName = "",EmailID = "",OfficeAddress = "",PrincipleName = "",
            BusinessVerticalName="",IndustryTypeName="",IndustrySegmentName="",Website="",ManagementLevel="",FactoryAddress="",
            ResidenceAddress = "",EmailID2 = "",NumberType = "", NumberType2 = "",NumberType3="",NumberType4="",
            NumberType5 = "",Number = "",Number2="",Number3="",Number4="",Number5 = "", customerIdGet = "",ZoneID = "",
            CardFrontImage = "",CardBackImage = "",UserActionMode= "";

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
        phonenumbertxt2 = (TextView)findViewById(R.id.phonetxt2);
        phonenumbertxt3 = (TextView)findViewById(R.id.phonetxt3);
        phonenumbertxt4 = (TextView)findViewById(R.id.phonetxt4);
        phonenumbertxt5 = (TextView)findViewById(R.id.phonetxt5);
        websitetxt = (TextView)findViewById(R.id.websitetxt);
        editBtn = (Button)findViewById(R.id.editbtn);
        addressTxt = (TextView)findViewById(R.id.adresstxt);
        residentalAddTxt = (TextView)findViewById(R.id.resaddadresstxt);
        officeaddTxt = (TextView)findViewById(R.id.officeadresstxt);
        phoneType = (TextView)findViewById(R.id.type1);
        phoneType2 = (TextView)findViewById(R.id.type2);
        phoneType3 = (TextView)findViewById(R.id.type3);
        phoneType4 = (TextView)findViewById(R.id.type4);
        phoneType5 = (TextView)findViewById(R.id.type5);
        phLay = (LinearLayout)findViewById(R.id.phnlay);
        phLay2 = (LinearLayout)findViewById(R.id.phnlay2);
        phLay3 = (LinearLayout)findViewById(R.id.phnlay3);
        phLay4 = (LinearLayout)findViewById(R.id.phnlay4);
        phLay5 = (LinearLayout)findViewById(R.id.phnlay5);
        busLay = (LinearLayout)findViewById(R.id.buslay);
        indLay = (LinearLayout)findViewById(R.id.indlay);
        indsegLay = (LinearLayout)findViewById(R.id.indseglay);
        priLay = (LinearLayout)findViewById(R.id.prnlay);

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

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("Name",Name);
                intent.putExtra("Designation",Designation);
                intent.putExtra("Company",Company);
                intent.putExtra("ZoneID",ZoneID);
                intent.putExtra("EmailID",EmailID);
                intent.putExtra("OfficeAddress",OfficeAddress);
                intent.putExtra("PrincipleName",PrincipleName);
                intent.putExtra("BusinessVerticalName",BusinessVerticalName);
                intent.putExtra("IndustryTypeName",IndustryTypeName);
                intent.putExtra("IndustrySegmentName",IndustrySegmentName);
                intent.putExtra("Website",Website);
                intent.putExtra("ManagementLevel",ManagementLevel);
                intent.putExtra("FactoryAddress",FactoryAddress);
                intent.putExtra("ResidenceAddress",ResidenceAddress);
                intent.putExtra("EmailID2",EmailID2);
                intent.putExtra("NumberType",NumberType);
                intent.putExtra("NumberType2",NumberType2);
                intent.putExtra("NumberType3",NumberType3);
                intent.putExtra("NumberType4",NumberType4);
                intent.putExtra("NumberType5",NumberType5);
                intent.putExtra("Number",Number);
                intent.putExtra("Number2",Number2);
                intent.putExtra("Number3",Number3);
                intent.putExtra("Number4",Number4);
                intent.putExtra("Number5",Number5);
                intent.putExtra("customerIdGet",customerIdGet);
                intent.putExtra("CardFrontImage",CardFrontImage);
                intent.putExtra("CardBackImage",CardBackImage);
                intent.putExtra("UserActionMode","EditMode");
                startActivity(intent);
                finish();
            }
        });

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
                        Name =object.getString("Name");
                        Designation = object.getString("Designation");
                        Company = object.getString("Company");
                        ZoneName = object.getString("ZoneName");
                        EmailID = object.getString("EmailID");
                        OfficeAddress = object.getString("OfficeAddress");
                        PrincipleName = object.getString("PrincipleName");
                        BusinessVerticalName = object.getString("BusinessVerticalName");
                        IndustryTypeName = object.getString("IndustryTypeName");
                        IndustrySegmentName = object.getString("IndustrySegmentName");
                        Website = object.getString("Website");
                        ManagementLevel = object.getString("ManagementLevel");
                        FactoryAddress = object.getString("FactoryAddress");
                        ResidenceAddress = object.getString("ResidenceAddress");
                        EmailID2 = object.getString("EmailID2");
                        NumberType = object.getString("NumberType");
                        NumberType2 = object.getString("NumberType2");
                        NumberType3 = object.getString("NumberType3");
                        NumberType4 = object.getString("NumberType4");
                        NumberType5 = object.getString("NumberType5");
                        Number = object.getString("Number");
                        Number2 = object.getString("Number2");
                        Number3 = object.getString("Number3");
                        Number4 = object.getString("Number");
                        Number5 = object.getString("Number5");
                        customerIdGet = object.getString("CustomerID");
                        ZoneID = object.getString("ZoneID");
                        CardBackImage = object.getString("CardBackImage");
                        CardFrontImage = object.getString("CardFrontImage");



                        nameTxt.setText(Name);
                        designationtxt.setText(Designation);
                        companynameTxt.setText(Company);
                        zoneNameTxt.setText(ZoneName);
                        emailIdtxt.setText(EmailID);
                        mangementLevelTxt.setText(ManagementLevel);
                        principleTypeTxt.setText(PrincipleName);
                        businessverticalTxt.setText(BusinessVerticalName);
                        industryTypetxt.setText(IndustryTypeName);
                        industryTypeSegmentTxt.setText(IndustrySegmentName);
                        websitetxt.setText(Website);
                        addressTxt.setText(FactoryAddress);
                        officeaddTxt.setText(OfficeAddress);
                        residentalAddTxt.setText(ResidenceAddress);
                        phonenumbertxt.setText(Number);
                        phonenumbertxt2.setText(Number2);
                        phonenumbertxt3.setText(Number3);
                        phonenumbertxt4.setText(Number4);
                        phonenumbertxt5.setText(Number5);


                        if (Number.equalsIgnoreCase(""))
                        {
                            phLay.setVisibility(View.GONE);
                        }

                        if (Number2.equalsIgnoreCase(""))
                        {
                            phLay2.setVisibility(View.GONE);
                        }

                        if (Number3.equalsIgnoreCase(""))
                        {
                            phLay3.setVisibility(View.GONE);
                        }

                        if (Number4.equalsIgnoreCase(""))
                        {
                            phLay4.setVisibility(View.GONE);
                        }

                        if (Number5.equalsIgnoreCase(""))
                        {
                            phLay5.setVisibility(View.GONE);
                        }

                        if (NumberType.equalsIgnoreCase(""))
                        {
                            phoneType.setText("Phone No.("+"No Type"+")");
                        }else
                            {
                                phoneType.setText("Phone No.("+NumberType+")");
                            }

                        if (NumberType2.equalsIgnoreCase(""))
                        {
                            phoneType2.setText("Phone No.("+"No Type"+")");
                        }else
                        {
                            phoneType2.setText("Phone No.("+NumberType2+")");
                        }

                        if (NumberType3.equalsIgnoreCase(""))
                        {
                            phoneType3.setText("Phone No.("+"No Type"+")");
                        }else
                        {
                            phoneType3.setText("Phone No.("+NumberType3+")");
                        }

                        if (NumberType4.equalsIgnoreCase(""))
                        {
                            phoneType4.setText("Phone No.("+"No Type"+")");
                        }else
                        {
                            phoneType4.setText("Phone No.("+NumberType4+")");
                        }

                        if (NumberType5.equalsIgnoreCase(""))
                        {
                            phoneType5.setText("Phone No.("+"No Type"+")");
                        }else
                        {
                            phoneType5.setText("Phone No.("+NumberType5+")");
                        }

                        if (BusinessVerticalName.equalsIgnoreCase("null") || BusinessVerticalName.equalsIgnoreCase(""))
                        {
                             busLay.setVisibility(View.GONE);
                        }

                        if (IndustryTypeName.equalsIgnoreCase("null") || IndustryTypeName.equalsIgnoreCase(""))
                        {

                            indLay.setVisibility(View.GONE);
                        }

                        if (IndustrySegmentName.equalsIgnoreCase("null") || IndustrySegmentName.equalsIgnoreCase(""))
                        {

                            indsegLay.setVisibility(View.GONE);
                        }

                        if (PrincipleName.equalsIgnoreCase("null") || PrincipleName.equalsIgnoreCase(""))
                        {

                            priLay.setVisibility(View.GONE);
                        }

                        /*phoneType2.setText("Phone No.("+NumberType2+")");
                        phoneType3.setText("Phone No.("+NumberType3+")");
                        phoneType4.setText("Phone No.("+NumberType4+")");
                        phoneType5.setText("Phone No.("+NumberType5+")");*/

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

package cardscaner.cfcs.com.cardscanner.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cardscaner.cfcs.com.cardscanner.Adapter.BusinessVerticalAdapter;
import cardscaner.cfcs.com.cardscanner.Adapter.CardListAdapter;
import cardscaner.cfcs.com.cardscanner.Adapter.DemoCustomeAdapter;
import cardscaner.cfcs.com.cardscanner.Adapter.IndusteryAdapter;
import cardscaner.cfcs.com.cardscanner.Adapter.PrincipalTypeAdapter;
import cardscaner.cfcs.com.cardscanner.Database.BusinessVerticalMasterTable;
import cardscaner.cfcs.com.cardscanner.Database.IndustrySegmentMasterTable;
import cardscaner.cfcs.com.cardscanner.Database.IndustryTypeMasterTable;
import cardscaner.cfcs.com.cardscanner.Database.MasterDatabase;
import cardscaner.cfcs.com.cardscanner.Database.NumberTypeMasterTable;
import cardscaner.cfcs.com.cardscanner.Database.PrincipleMasterTable;
import cardscaner.cfcs.com.cardscanner.Interface.CustomerNameInterface;
import cardscaner.cfcs.com.cardscanner.MainClass.ForgetPasswordActivity;
import cardscaner.cfcs.com.cardscanner.MainClass.LoginActivity;
import cardscaner.cfcs.com.cardscanner.Model.BusinessVerticalCheckList;
import cardscaner.cfcs.com.cardscanner.Model.CardListModel;
import cardscaner.cfcs.com.cardscanner.Model.CustomerDetailsModel;
import cardscaner.cfcs.com.cardscanner.Model.IndustryModel;
import cardscaner.cfcs.com.cardscanner.Model.PrincipalTypeModel;
import cardscaner.cfcs.com.cardscanner.R;
import cardscaner.cfcs.com.cardscanner.source.AppController;
import cardscaner.cfcs.com.cardscanner.source.ConnectionDetector;
import cardscaner.cfcs.com.cardscanner.source.EditTextMonitor;
import cardscaner.cfcs.com.cardscanner.source.MultiTouchListener;
import cardscaner.cfcs.com.cardscanner.source.SettingConstant;
import cardscaner.cfcs.com.cardscanner.source.SharedPrefs;
import cardscaner.cfcs.com.cardscanner.source.UtilsMethods;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CamFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CamFragment extends Fragment implements CustomerNameInterface{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    public android.support.design.widget.FloatingActionButton floatingActionButton;
    private static final int REQUEST_GALLERY = 0;
    private static final int REQUEST_CAMERA = 1;
    public String userId = "",authCode = "";
    public ConnectionDetector conn;

    public PopupWindow popupWindow;
    public EditText searchData;
    public ListView serchListData;
    private static final String TAG = "Cam Scanner";
    private static final int REQUEST_WRITE_PERMISSION = 20;
    public MasterDatabase masterDatabase;

    private Uri imageUri;
    int count = 0, addCnt = 0;
    private EditTextMonitor detectedTextView, emailTxt, phoneTxt, nameTxt,addresstxt,PostalCode,
            thirdTxt,designation, company_name,phoneNumberTxt,PhoneTxtthird,webUrlTxt,homeaddressFirst, homeaddressSecond
            , phoneNumerfour, phonenumerfivth;
    public TextView selectEditTxt,selectIndustrySegemnttxt, industryTypeTxt, principleTypeTxt;
    public Spinner addressOneSpinner, addressSpinersecond, addressSpinnerThirs, addressSpinnerFourth
            ,phoneSpinner,phoneSecondSpinner, phoneThirdSpinner,phoneFourthSpinner,phoneFivthSpinner;

    public ImageView cardImg, backCardImg;
    public LinearLayout backCardBtn;
    public RecyclerView businessVerticalRecycler, industeryRecycler,principalTypeRecycler ;
    public int flag = 0;

    public BusinessVerticalAdapter adapter;
    public IndusteryAdapter industeryAdapter;
    public PrincipalTypeAdapter principalTypeAdapter;
    public ArrayList<CustomerDetailsModel> principalTypeList = new ArrayList<>();
    public ArrayList<CustomerDetailsModel> industryTypeList = new ArrayList<>();
    public ArrayList<CustomerDetailsModel> industrySegMentList = new ArrayList<>();
    public ArrayList<CustomerDetailsModel> listcust = new ArrayList<>();
    public ArrayList<String> adressList = new ArrayList<>();
    public ArrayList<String> phoneList = new ArrayList<>();
    public String getDdlListUrl = SettingConstant.BASEURL_FOR_LOGIN + "DigiCardScannerService.asmx/AppddlList";



    public CamFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CamFragment newInstance(String param1, String param2) {
        CamFragment fragment = new CamFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_cam, container, false);

        floatingActionButton = (android.support.design.widget.FloatingActionButton)rootView.findViewById(R.id.fab);
        phoneTxt = (EditTextMonitor)rootView.findViewById(R.id.phone_number);
        emailTxt = (EditTextMonitor)rootView.findViewById(R.id.email_text);
        nameTxt = (EditTextMonitor)rootView.findViewById(R.id.name_txt);
        addresstxt = (EditTextMonitor)rootView.findViewById(R.id.address_txt);
        PostalCode = (EditTextMonitor)rootView.findViewById(R.id.second_name_txt);
        thirdTxt = (EditTextMonitor)rootView.findViewById(R.id.third_name_txt);
        designation = (EditTextMonitor)rootView.findViewById(R.id.designation_txt);
        cardImg = (ImageView)rootView.findViewById(R.id.cardImg);
        company_name = (EditTextMonitor)rootView.findViewById(R.id.comapnyname_text);
        detectedTextView = (EditTextMonitor)rootView.findViewById(R.id.detected_text);
        phoneNumberTxt = (EditTextMonitor)rootView.findViewById(R.id.phone_number2);
        PhoneTxtthird = (EditTextMonitor)rootView.findViewById(R.id.phone_number3);
        webUrlTxt = (EditTextMonitor)rootView.findViewById(R.id.web_url);
        businessVerticalRecycler = (RecyclerView)rootView.findViewById(R.id.business_vertical_recycler);
        industeryRecycler = (RecyclerView)rootView.findViewById(R.id.industery_recycler);
        principalTypeRecycler = (RecyclerView)rootView.findViewById(R.id.principal_type_recycler);
        homeaddressFirst = (EditTextMonitor)rootView.findViewById(R.id.home_address_txt1);
        homeaddressSecond = (EditTextMonitor)rootView.findViewById(R.id.home_address_txt2);
        phoneNumerfour = (EditTextMonitor)rootView.findViewById(R.id.phone_number4);
        phonenumerfivth = (EditTextMonitor)rootView.findViewById(R.id.phone_number5);
        addressOneSpinner = (Spinner)rootView.findViewById(R.id.spinner_address);
        addressSpinersecond = (Spinner)rootView.findViewById(R.id.spinner_address2);
        addressSpinnerThirs = (Spinner)rootView.findViewById(R.id.spinner_address3);
        addressSpinnerFourth = (Spinner)rootView.findViewById(R.id.spinner_address4);
        phoneSpinner = (Spinner)rootView.findViewById(R.id.spinner_phone);
        phoneSecondSpinner = (Spinner)rootView.findViewById(R.id.spinner_phone2);
        phoneThirdSpinner = (Spinner)rootView.findViewById(R.id.spinner_phone3);
        phoneFourthSpinner = (Spinner)rootView.findViewById(R.id.spinner_phone4);
        phoneFivthSpinner = (Spinner)rootView.findViewById(R.id.spinner_phone5);
        backCardBtn = (LinearLayout)rootView.findViewById(R.id.backcardbtn);
        backCardImg = (ImageView)rootView.findViewById(R.id.backcardImg);
        selectEditTxt = (TextView) rootView.findViewById(R.id.selecttheedittxt);
        selectIndustrySegemnttxt = (TextView)rootView.findViewById(R.id.selecttheinduserysegment);
        industryTypeTxt = (TextView)rootView.findViewById(R.id.selecttheinduserytype);
        principleTypeTxt = (TextView)rootView.findViewById(R.id.selecttheprincipletype);

        masterDatabase = new MasterDatabase(getActivity());
        conn = new ConnectionDetector(getActivity());
        userId = UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getUserId(getActivity())));
        authCode = UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAuthCode(getActivity())));

        selectIndustrySegemnttxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callPopup(industrySegMentList);
            }
        });

        selectEditTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callPopup(listcust);
            }
        });

        industryTypeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callPopup(industryTypeList);
            }
        });

        principleTypeTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callPopup(principalTypeList);
            }
        });




        // address List
        if (adressList.size()>0)
        {
            adressList.clear();
        }
        adressList.add("");
        adressList.add("Office Address");
        adressList.add("Factory Address");
        adressList.add("Residence Address");


        //spinner first address
        addressOneSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);
        ArrayAdapter<String> adressOneAdapter = new ArrayAdapter<String>(getActivity(),R.layout.customizespinner,
                adressList);
        adressOneAdapter.setDropDownViewResource(R.layout.customizespinner);
        addressOneSpinner.setAdapter(adressOneAdapter);

        //spinner second address
        addressOneSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);
        ArrayAdapter<String> adressSecondAdapter = new ArrayAdapter<String>(getActivity(),R.layout.customizespinner,
                adressList);
        adressSecondAdapter.setDropDownViewResource(R.layout.customizespinner);
        addressSpinersecond.setAdapter(adressSecondAdapter);

        //spinner third address
        addressOneSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);
        ArrayAdapter<String> adressThirdAdapter = new ArrayAdapter<String>(getActivity(),R.layout.customizespinner,
                adressList);
        adressThirdAdapter.setDropDownViewResource(R.layout.customizespinner);
        addressSpinnerThirs.setAdapter(adressThirdAdapter);

        //spinner fourth address
        addressOneSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);
        ArrayAdapter<String> adressFourthAdapter = new ArrayAdapter<String>(getActivity(),R.layout.customizespinner,
                adressList);
        adressFourthAdapter.setDropDownViewResource(R.layout.customizespinner);
        addressSpinnerFourth.setAdapter(adressFourthAdapter);


      /*  if (phoneList.size()>0)
        {
            phoneList.clear();
        }
        //Phone List
        phoneList.add("");
        phoneList.add("Home");
        phoneList.add("Work");
        phoneList.add("Mobile No.");
        phoneList.add("Main");
        phoneList.add("Home Fax");
        phoneList.add("Work Fax");
        phoneList.add("Extension");
        phoneList.add("Other");*/

        //spinner First Phone
        addressOneSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);
        ArrayAdapter<String> phoneFirstAdapter = new ArrayAdapter<String>(getActivity(),R.layout.customizespinner,
                phoneList);
        phoneFirstAdapter.setDropDownViewResource(R.layout.customizespinner);
        phoneSpinner.setAdapter(phoneFirstAdapter);

        //spinner Second Phone
        addressOneSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);
        ArrayAdapter<String> phoneSecondAdapter = new ArrayAdapter<String>(getActivity(),R.layout.customizespinner,
                phoneList);
        phoneSecondAdapter.setDropDownViewResource(R.layout.customizespinner);
        phoneSecondSpinner.setAdapter(phoneSecondAdapter);

        //spinner Third Phone
        addressOneSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);
        ArrayAdapter<String> phoneThirdAdapter = new ArrayAdapter<String>(getActivity(),R.layout.customizespinner,
                phoneList);
        phoneThirdAdapter.setDropDownViewResource(R.layout.customizespinner);
        phoneThirdSpinner.setAdapter(phoneThirdAdapter);

        //spinner Fourth Phone
        addressOneSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);
        ArrayAdapter<String> phoneFourthAdapter = new ArrayAdapter<String>(getActivity(),R.layout.customizespinner,
                phoneList);
        phoneFourthAdapter.setDropDownViewResource(R.layout.customizespinner);
        phoneFourthSpinner.setAdapter(phoneFourthAdapter);

        //spinner Fivth Phone
        addressOneSpinner.getBackground().setColorFilter(getResources().getColor(R.color.status_color), PorterDuff.Mode.SRC_ATOP);
        ArrayAdapter<String> phoneFivthAdapter = new ArrayAdapter<String>(getActivity(),R.layout.customizespinner,
                phoneList);
        phoneFivthAdapter.setDropDownViewResource(R.layout.customizespinner);
        phoneFivthSpinner.setAdapter(phoneFivthAdapter);



       /* // Business Vertical List
        adapter = new BusinessVerticalAdapter(getActivity(),list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        businessVerticalRecycler.setLayoutManager(mLayoutManager);
        businessVerticalRecycler.setItemAnimator(new DefaultItemAnimator());
        businessVerticalRecycler.setAdapter(adapter);*/



  //      prepareMemberData();

       /* //Industery List
        industeryAdapter = new IndusteryAdapter(getActivity(),indstryList);
        RecyclerView.LayoutManager industerymLayoutManager = new LinearLayoutManager(getActivity());
        industeryRecycler.setLayoutManager(industerymLayoutManager);
        industeryRecycler.setItemAnimator(new DefaultItemAnimator());
        industeryRecycler.setAdapter(industeryAdapter);*/

     //   prepareIndusteryData();

        //principal List
       /* principalTypeAdapter = new PrincipalTypeAdapter(getActivity(),principalTypeList);
        RecyclerView.LayoutManager principalmLayoutManager = new LinearLayoutManager(getActivity());
        principalTypeRecycler.setLayoutManager(principalmLayoutManager);
        principalTypeRecycler.setItemAnimator(new DefaultItemAnimator());
        principalTypeRecycler.setAdapter(principalTypeAdapter);

        preparePrincipalData();*/




        // MultiTouchListener touchListener=new MultiTouchListener(getActivity());

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                flag = 1;
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, REQUEST_WRITE_PERMISSION);


            }
        });

        //floatingActionButton.onTouchEvent(touchListener);
        //floatingActionButton.setOnTouchListener(touchListener);
        if (!isGooglePlayServicesAvailable(getActivity())) {

            Log.e("onCreate", "Google Play Services not available. Ending Test case.");
            //getActivity().finish();
        }
        else {
            Log.e("onCreate", "Google Play Services available. Continuing.");
        }


        backCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                flag = 2;
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, REQUEST_WRITE_PERMISSION);

            }
        });

        //checking the BusinessVertical data is save In local database or not!------------
        Cursor cursor = masterDatabase.getBusinessVerticalMasterTableData(userId);
        if (cursor != null && cursor.getCount()>0)
        {
             if (listcust.size()>0)
                    {
                        listcust.clear();
                    }
            if (cursor.moveToFirst())
            {
                do{

                    String businessVerticalId = cursor.getString(cursor.getColumnIndex(BusinessVerticalMasterTable.businessVerticalID));
                    String businessVerticalType = cursor.getString(cursor.getColumnIndex(BusinessVerticalMasterTable.businessVertical));



                    listcust.add(new CustomerDetailsModel(businessVerticalType,businessVerticalId));

                  }while (cursor.moveToNext());
            }

        }else
            {

                if (conn.getConnectivityStatus()>0)
                {

                    getAppDdlList(authCode,userId);
                }else
                    {
                        conn.showNoInternetAlret();
                    }

            }

       //Industry Segment local database checking
        Cursor industryCursor = masterDatabase.getIndustrySegmentMasterTable(userId);
        if (industryCursor != null && industryCursor.getCount()>0)
        {
            if (industrySegMentList.size()>0)
            {
                industrySegMentList.clear();
            }
            if (industryCursor.moveToFirst())
            {
                do{

                    String industrySegmentID = industryCursor.getString(industryCursor.getColumnIndex(IndustrySegmentMasterTable.industrySegmentID));
                    String industrySegment = industryCursor.getString(industryCursor.getColumnIndex(IndustrySegmentMasterTable.industrySegment));

                    industrySegMentList.add(new CustomerDetailsModel(industrySegment,industrySegmentID));

                }while (industryCursor.moveToNext());
            }

        }else
        {

            if (conn.getConnectivityStatus()>0)
            {

                getAppDdlList(authCode,userId);
            }else
            {
                conn.showNoInternetAlret();
            }

        }


        //Industry Type chck database in local database
        Cursor industryTypeCursor = masterDatabase.getIndustryTypeMasterTable(userId);
        if (industryTypeCursor != null && industryTypeCursor.getCount()>0)
        {
            if (industryTypeList.size()>0)
            {
                industryTypeList.clear();
            }
            if (industryTypeCursor.moveToFirst())
            {
                do{

                    String industryTypeID = industryTypeCursor.getString(industryTypeCursor.getColumnIndex(IndustryTypeMasterTable.industryTypeID));
                    String industryType = industryTypeCursor.getString(industryTypeCursor.getColumnIndex(IndustryTypeMasterTable.industryType));

                    industryTypeList.add(new CustomerDetailsModel(industryType,industryTypeID));

                }while (industryTypeCursor.moveToNext());
            }

        }else
        {

            if (conn.getConnectivityStatus()>0)
            {

                getAppDdlList(authCode,userId);
            }else
            {
                conn.showNoInternetAlret();
            }

        }

        //Checked principle type data in my local database
        Cursor principleTypeCursor = masterDatabase.getPrincipleMasterTable(userId);
        if (principleTypeCursor != null && principleTypeCursor.getCount()>0)
        {
            if (principalTypeList.size()>0)
            {
                principalTypeList.clear();
            }
            if (principleTypeCursor.moveToFirst())
            {
                do{

                    String principleTypeID = principleTypeCursor.getString(principleTypeCursor.getColumnIndex(PrincipleMasterTable.principleId));
                    String principleType = principleTypeCursor.getString(principleTypeCursor.getColumnIndex(PrincipleMasterTable.principle));

                    principalTypeList.add(new CustomerDetailsModel(principleType,principleTypeID));

                }while (principleTypeCursor.moveToNext());
            }

        }else
        {

            if (conn.getConnectivityStatus()>0)
            {

                getAppDdlList(authCode,userId);
            }else
            {
                conn.showNoInternetAlret();
            }

        }

        //checking the phone list data is my local database
        Cursor numberTypeCursor = masterDatabase.getNumberTypeMasterTable(userId);
        if (numberTypeCursor != null && numberTypeCursor.getCount()>0)
        {
            if (phoneList.size()>0)
            {
                phoneList.clear();
            }
            if (numberTypeCursor.moveToFirst())
            {
                do{

                    String numberType = numberTypeCursor.getString(numberTypeCursor.getColumnIndex(NumberTypeMasterTable.numberType));
                    //String principleType = principleTypeCursor.getString(principleTypeCursor.getColumnIndex(PrincipleMasterTable.principle));

                    phoneList.add(numberType);

                }while (numberTypeCursor.moveToNext());
            }

        }else
        {

            if (conn.getConnectivityStatus()>0)
            {

                getAppDdlList(authCode,userId);
            }else
            {
                conn.showNoInternetAlret();
            }

        }


        return rootView;
    }

    //Api Work
    public void getAppDdlList(final String authCode, final String userId ) {
        final ProgressDialog pDialog = new ProgressDialog(getActivity(),R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest historyInquiry = new StringRequest(
                Request.Method.POST, getDdlListUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("AppDdlList", response);
                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"),response.lastIndexOf("}") +1 ));

                    JSONArray numberTypeMasterArray = jsonObject.getJSONArray("NumberTypeMaster");

                    for (int i=0; i< numberTypeMasterArray.length(); i++ )
                    {
                        JSONObject numberTypeObject = numberTypeMasterArray.getJSONObject(i);
                        String NumberType = numberTypeObject.getString("NumberType");

                        //save on data in local database
                        masterDatabase.setNumberTypeMasterTable(userId,NumberType);
                        phoneList.add(NumberType);
                    }

                    JSONArray principleMasterArray = jsonObject.getJSONArray("PrincipleMaster");

                    for (int j=0; j<principleMasterArray.length(); j++)
                    {
                        JSONObject principleMasterObject = principleMasterArray.getJSONObject(j);
                        String PrincipleID = principleMasterObject.getString("PrincipleID");
                        String Principle = principleMasterObject.getString("Principle");

                        //save data on local databse
                        masterDatabase.setPrincipleMasterTable(userId,PrincipleID,Principle);
                        principalTypeList.add(new CustomerDetailsModel(Principle,PrincipleID));
                    }

                    JSONArray businessVerticalMasterArray = jsonObject.getJSONArray("BusinessVerticalMaster");

                    for (int k = 0; k<businessVerticalMasterArray.length(); k++)
                    {
                        JSONObject businessVerticalObject = businessVerticalMasterArray.getJSONObject(k);
                        String BusinessVerticalID = businessVerticalObject.getString("BusinessVerticalID");
                        String BusinessVertical = businessVerticalObject.getString("BusinessVertical");

                        //data save on locaal database
                        masterDatabase.setBusinessVerticalMasterTableData(BusinessVerticalID,userId,BusinessVertical);
                        listcust.add(new CustomerDetailsModel(BusinessVertical,BusinessVerticalID));
                    }

                    JSONArray industryTypeMasterArray = jsonObject.getJSONArray("IndustryTypeMaster");

                    for (int l=0 ; l<industryTypeMasterArray.length(); l++)
                    {
                        JSONObject industeryTypeObject = industryTypeMasterArray.getJSONObject(l);
                        String IndustryTypeID = industeryTypeObject.getString("IndustryTypeID");
                        String IndustryType = industeryTypeObject.getString("IndustryType");

                        //data save on local database
                        masterDatabase.setIndustryTypeMasterTable(userId,IndustryTypeID,IndustryType);
                        industryTypeList.add(new CustomerDetailsModel(IndustryType,IndustryTypeID));
                    }

                    JSONArray industrySegmentMasterArray = jsonObject.getJSONArray("IndustrySegmentMaster");

                    for (int m=0; m<industrySegmentMasterArray.length(); m++)
                    {

                        JSONObject industerySegmentObject = industrySegmentMasterArray.getJSONObject(m);
                        String IndustrySegmentID = industerySegmentObject.getString("IndustrySegmentID");
                        String IndustrySegment = industerySegmentObject.getString("IndustrySegment");

                        //data save on local database
                        masterDatabase.setIndustrySegmentMasterTable(userId,IndustrySegmentID,IndustrySegment);
                        industrySegMentList.add(new CustomerDetailsModel(IndustrySegment,IndustrySegmentID));
                    }



                   /* for (int i=0 ; i<jsonArray.length();i++)
                    {*/
                    // JSONObject jsonObject = jsonArray.getJSONObject(i);
                    // String status = jsonObject.getString("status");
                    if (jsonObject.has("MsgNotification"))
                    {
                        String MsgNotification = jsonObject.getString("MsgNotification");
                        Toast.makeText(getActivity(), MsgNotification, Toast.LENGTH_SHORT).show();

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

                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                pDialog.dismiss();


            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("AuthCode", authCode);
                params.put("UserID", userId);

                // Log.e("Parms", params.toString());
                return params;
            }

        };
        historyInquiry.setRetryPolicy(new DefaultRetryPolicy(SettingConstant.Retry_Time,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(historyInquiry, "AddDdlList");

    }

   /* private void prepareMemberData() {
        CustomerDetailsModel model = new CustomerDetailsModel("First Check","1");
        listcust.add(model);

        model = new CustomerDetailsModel("Second Check","1");
        listcust.add(model);

        model = new CustomerDetailsModel("Third Check","1");
        listcust.add(model);
        adapter.notifyDataSetChanged();
    }*/

   /* private void prepareIndusteryData() {
        IndustryModel model = new IndustryModel("Ind First Check");
        indstryList.add(model);

        model = new IndustryModel("Ind First Check");
        indstryList.add(model);

        model = new IndustryModel("Ind First Check");
        indstryList.add(model);

        industeryAdapter.notifyDataSetChanged();
    }
*/
   /* private void preparePrincipalData() {
        PrincipalTypeModel model = new PrincipalTypeModel("Ind Principal Check");
        principalTypeList.add(model);

        model = new PrincipalTypeModel("Ind Principal Check");
        principalTypeList.add(model);

        model = new PrincipalTypeModel("Ind Principal Check");
        principalTypeList.add(model);

        principalTypeAdapter.notifyDataSetChanged();
    }*/

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_WRITE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    String filename = System.currentTimeMillis() + ".jpg";

                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, filename);
                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                    imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);



                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, REQUEST_CAMERA);


                    // cardImg.setImageURI(imageUri);
                } else {
                    Toast.makeText(getActivity(), "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    public boolean isGooglePlayServicesAvailable(Activity activity) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if(status != ConnectionResult.SUCCESS) {
            if(googleApiAvailability.isUserResolvableError(status)) {

              // googleApiAvailability.getErrorDialog(activity, status, 2404).show();
                googleApiAvailability.getErrorDialog(activity, status, 2404, new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        final String appPackageName = "com.google.android.gms";
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        }catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                    }
                }).show();
            }
            return false;
        }
        return true;
    }


    private void inspectFromBitmap(Bitmap bitmap) {
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getActivity()).build();
        try {
            if (!textRecognizer.isOperational()) {
                new AlertDialog.
                        Builder(getActivity()).
                        setMessage("Text recognizer could not be set up on your device").show();
                return;
            }

            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> origTextBlocks = textRecognizer.detect(frame);
            List<TextBlock> textBlocks = new ArrayList<>();
            for (int i = 0; i < origTextBlocks.size(); i++) {
                TextBlock textBlock = origTextBlocks.valueAt(i);
                textBlocks.add(textBlock);
            }
            Collections.sort(textBlocks, new Comparator<TextBlock>() {
                @Override
                public int compare(TextBlock o1, TextBlock o2) {
                    int diffOfTops = o1.getBoundingBox().top - o2.getBoundingBox().top;
                    int diffOfLefts = o1.getBoundingBox().left - o2.getBoundingBox().left;
                    if (diffOfTops != 0) {
                        return diffOfTops;
                    }
                    return diffOfLefts;
                }
            });

            StringBuilder detectedText = new StringBuilder();
            for (TextBlock textBlock : textBlocks) {
                if (textBlock != null && textBlock.getValue() != null) {
                    detectedText.append(textBlock.getValue());
                    detectedText.append("\n");
                }
            }

            String convertStringBuilder = String.valueOf(detectedText);
            String[] lines = convertStringBuilder.split(System.getProperty("line.separator"));

            for (int i =0; i<lines.length; i++)
            {
                String firstLine = lines[i];
                if(!Pattern.matches(".*[a-zA-Z]+.*[a-zA-Z]", firstLine))
                {

                    // String not contain only character;
                    if (firstLine.length() <= 60) {

                        //email
                        Pattern pe = Pattern.compile("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b");
                        Matcher m = pe.matcher(firstLine);
                        if (m.find()) {
                 //           emailTxt.setText(firstLine);
                        } else {

                            if (getCount(firstLine)>=6) {

                                Pattern pa = Pattern.compile("\\+?\\(?\\d{2,4}\\)?[\\d\\s-]{3,}");

                                // [a-zA-Z]+
                                Matcher ma = pa.matcher(firstLine);
                                if (ma.find()) {
                                    if(!Pattern.matches("[a-zA-Z]+",firstLine)) {

                                    }
                                }
                            }else
                            {


                            }
                        }

                        try {

                        } catch (ArrayIndexOutOfBoundsException e) {

                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            //  e.printStackTrace();
                        }

                        // check the number of numeric character
                        Pattern digitPattern = Pattern.compile("\\d");
                        Matcher digitMatcher = digitPattern.matcher(firstLine);
                        int digitCount = 0;
                        while (digitMatcher.find()) {
                            digitCount++;
                        }

                        if (digitCount >=6)
                        {

                            Pattern zipPattern = Pattern.compile("(\\d{6})");
                            Matcher zipMatcher = zipPattern.matcher(firstLine);
                            if (zipMatcher.find()) {
                                String zip = zipMatcher.group(1);
                             //   PostalCode.setText(zip);
                            }else {

                                // checking phone number
                                Pattern pa = Pattern.compile("\\+?\\(?\\d{2,4}\\)?[\\d\\s-]{3,}");
                                Matcher ma = pa.matcher(firstLine);
                                if (ma.find()) {
                                    if (!Pattern.matches("[a-zA-Z]+", firstLine)) {

                                     //   phoneTxt.setText(firstLine);
                                    } else
                                    {
                                        //    thirdTxt.setText(firstLine);

                                        try {
                                            if (!thirdTxt.getText().toString().equalsIgnoreCase(lines[i + 1])) {
                                                if (!lines[i + 1].contains(".com")) {
                                                    if (!lines[i + 1].contains("@")) {
                                                        if ( !lines[i + 1].contains("co.in")) {
                                                            if (!lines[i + 1].contains("w.")) {

                                                                char[] ch1 = lines[i + 1].toCharArray();
                                                                int letter1 = 0;
                                                                int num1 = 0;
                                                                for(int id = 0; id < lines[i + 1].length(); id++){
                                                                    if(Character.isLetter(ch1[id])){
                                                                        letter1 ++ ;
                                                                    }
                                                                    else if(Character.isDigit(ch1[id])){
                                                                        num1 ++ ;
                                                                    }
                                                                }

                                                                if (num1>=1 && num1<=12 && letter1>=5) {
                                                                    if (!designation.getText().toString().equalsIgnoreCase(lines[i + 1])) {
                                                                        if (!lines[i+1].contains("mobile") ||!lines[i+1].contains("Phone")
                                                                                || !lines[i+1].contains("Phone No.")) {
                                                                            addresstxt.setText(lines[i + 1]);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        } catch (ArrayIndexOutOfBoundsException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }


                            }
                        }else {


                            //    thirdTxt.setText(firstLine);

                            try {

                                // check the number of numeric character
                                char[] ch1 = lines[i + 1].toCharArray();
                                int letter1 = 0;
                                int num1 = 0;
                                for(int id = 0; id < lines[i + 1].length(); id++){
                                    if(Character.isLetter(ch1[id])){
                                        letter1 ++ ;
                                    }
                                    else if(Character.isDigit(ch1[id])){
                                        num1 ++ ;
                                    }
                                }

                                if (num1>=1 && num1<=12 && letter1>=5) {
                                    if (!lines[i+1].contains("mobile") ||!lines[i+1].contains("Phone")
                                            || !lines[i+1].contains("Phone No.")) {
                                        addresstxt.setText(lines[i + 1]);
                                    }
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                e.printStackTrace();
                            }
                        }


                    }

                }
                else
                {

                    if (firstLine.length() >=20)
                    {

                        //email
                        Pattern pe = Pattern.compile("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b",
                                Pattern.CASE_INSENSITIVE);
                        Matcher m = pe.matcher(firstLine);
                        if (m.find()) {
                 //           emailTxt.setText(firstLine);
                        }else
                        {
                            if (!firstLine.contains("@")) {

                                    Pattern p = Pattern.compile(".*\\d+.*");
                                    Matcher ma = p.matcher(firstLine);
                                    boolean b = ma.find();
                                    if (!b) {

                                        if (!firstLine.contains(".com")) {
                                            if (!designation.getText().toString().equalsIgnoreCase(firstLine)) {
                                                if (!addresstxt.getText().toString().equalsIgnoreCase(firstLine)) {
                                                    if (!firstLine.contains(".com")  &&
                                                            !firstLine.contains("co.in")) {
                                                        if (detectedTextView.getText().toString().equalsIgnoreCase("")) {
                                                            company_name.setText(firstLine);
                                                        }
                                                    }
                                                    }
                                            }
                                        }
                                   }
                            }
                        }

                    }else {

                        if(Pattern.matches(".*[a-zA-Z]+.*[a-zA-Z]", firstLine)) {

                            if(!firstLine.contains("."))
                            {

                                boolean contains = firstLine.matches(".*\\bram\\b.*");
                                if (!contains) {

                                    Pattern pattern = Pattern.compile("\\s");
                                    Matcher matcher = pattern.matcher(firstLine);
                                    boolean found = matcher.find();
                                    if (found) {
                                      //  nameTxt.setText(firstLine);
                                        try {

                                            //checked string is numer or not
                                            String regexStr = "^[0-9]*$";
                                            if(lines[i+1].matches(regexStr))
                                            {
                                                //write code here for success
                                            }
                                            else
                                            {
                                                // System.out.println("No Match");

                                                Pattern p = Pattern.compile("(([A-Z].*[0-9]))");
                                                Matcher m = p.matcher(lines[i +1]);
                                                boolean b = m.find();
                                                System.out.println(b);



                                                if (!b) {
                                               //if(Pattern.matches(".*[a-zA-Z]+.*[a-zA-Z]", firstLine)) {
                                                  //  designation.setText(lines[i + 1]);
                                                }
                                            }

                                        } catch (ArrayIndexOutOfBoundsException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }

                    }
                }

                // company name
                if(!firstLine.contains("@")) {

                    StringTokenizer st = new StringTokenizer(firstLine, "ltd");

                    String community = "";
                    try {
                        community = st.nextToken();
                    } catch (NoSuchElementException e) {

                        Toast.makeText(getActivity(), "Scan Failed please Try again!", Toast.LENGTH_SHORT).show();
                    }

                    //name
                    Pattern pattern = Pattern.compile("\\s");
                    Matcher matcher = pattern.matcher(firstLine);
                    boolean found = matcher.find();
                    if (found) {
                        if (Pattern.matches(".*[a-zA-Z]+.*[a-zA-Z]", firstLine)) {
                            if (!community.contains("PVT.")) {
                                if (count == 0) {

                                    char[] ch1 = firstLine.toCharArray();
                                    int letter1 = 0;
                                    int num1 = 0;
                                    for (int id = 0; id < firstLine.length(); id++) {
                                        if (Character.isLetter(ch1[id])) {
                                            letter1++;
                                        } else if (Character.isDigit(ch1[id])) {
                                            num1++;
                                        }
                                    }
                                    if (letter1 <= 20 && num1 == 0) {
                                        nameTxt.setText(firstLine);
                                        count++;

                                    }


                                    Pattern p = Pattern.compile(".*\\d+.*");
                                    Matcher m = null;
                                    try {
                                        m = p.matcher(lines[i + 1]);
                                        boolean b = m.find();
                                        if (!b) {
                                            if (Pattern.matches(".*[a-zA-Z]+.*[a-zA-Z]", firstLine)) {

                                                designation.setText(lines[i + 1]);
                                            }
                                        }
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            // break;
                        }
                    }

                    if (community.contains("PVT.")) {
                        detectedTextView.setText(firstLine);
                    }


                    // checking phone number
                    Pattern pa = Pattern.compile("\\+?\\(?\\d{2,4}\\)?[\\d\\s-]{3,}");
                    Matcher ma = pa.matcher(community);
                    if (ma.find()) {
                        if (!Pattern.matches("[a-zA-Z]+", firstLine)) {

                            if (!PhoneTxtthird.getText().toString().equalsIgnoreCase(firstLine)) {
                                if (!phoneNumberTxt.getText().toString().equalsIgnoreCase(firstLine) ) {

                                    if (!addresstxt.getText().toString().equalsIgnoreCase(firstLine) ) {

                                        if (!thirdTxt.getText().toString().equalsIgnoreCase(firstLine)) {

                                            char[] ch = firstLine.toCharArray();
                                            int letter = 0;
                                            int num = 0;
                                            for(int id = 0; id < firstLine.length(); id++){
                                                if(Character.isLetter(ch[id])){
                                                    letter ++ ;
                                                }
                                                else if(Character.isDigit(ch[id])){
                                                    num ++ ;
                                                }
                                            }

                                            if (num>=10) {
                                                String[] splitString = firstLine.split(",");
                                                if (splitString.length>0) {

                                                    phoneTxt.setText(splitString[0]);
                                                    try {
                                                        phoneNumberTxt.setText(splitString[1]);
                                                        PhoneTxtthird.setText(splitString[2]);
                                                        phoneNumerfour.setText(splitString[3]);
                                                        if (!detectedTextView.getText().toString().equalsIgnoreCase(splitString[4])) {
                                                            phonenumerfivth.setText(splitString[4]);
                                                        }

                                                    } catch (ArrayIndexOutOfBoundsException e) {
                                                        e.printStackTrace();
                                                    }
                                                }else
                                                    {

                                                        phoneTxt.setText(firstLine);
                                                    }
                                            }else
                                            {
                                                if (num>=1 && num<=12 && letter>=5)
                                                    if (!firstLine.contains("mobile") ||!firstLine.contains("Phone")
                                                            || !firstLine.contains("Phone No.")) {
                                                        addresstxt.setText(firstLine);
                                                    }
                                            }
                                        }
                                    }
                                }
                            }
                            try {

                                int numberOfDigits = lines[i-1].length();
                                if (numberOfDigits>2) {

                                   /* Pattern p = Pattern.compile("(([A-Z].*[0-9]))");
                                    Matcher m = p.matcher(lines[i - 1]);
                                    boolean b = m.find();
                                    System.out.println(b);

                                    if (b) {*/
                                        if (!PhoneTxtthird.getText().toString().equalsIgnoreCase(lines[i - 1])) {

                                            if ( !phoneTxt.getText().toString().equalsIgnoreCase(lines[i - 1])) {
                                                if (!addresstxt.getText().toString().equalsIgnoreCase(lines[i - 1])) {
                                                    if (!thirdTxt.getText().toString().equalsIgnoreCase(lines[i - 1])) {

                                                        char[] ch = lines[i - 1].toCharArray();
                                                        int letter = 0;
                                                        int num = 0;
                                                        for(int id = 0; id < lines[i - 1].length(); id++){
                                                            if(Character.isLetter(ch[id])){
                                                                letter ++ ;
                                                            }
                                                            else if(Character.isDigit(ch[id])){
                                                                num ++ ;
                                                            }
                                                        }

                                                        if (num>=10) {
                                                            //if (phoneNumberTxt.getText().toString().equalsIgnoreCase("")) {

                                                                String[] splitString = lines[i - 1].split(",");
                                                                if (splitString.length>0) {

                                                                 //   phoneTxt.setText(splitString[0]);
                                                                    try {
                                                                        if (phoneNumberTxt.getText().toString().equalsIgnoreCase("")) {
                                                                            phoneNumberTxt.setText(splitString[0]);
                                                                        }

                                                                        if (PhoneTxtthird.getText().toString().equalsIgnoreCase("")) {
                                                                            PhoneTxtthird.setText(splitString[1]);
                                                                        }

                                                                        if (phoneNumerfour.getText().toString().equalsIgnoreCase("")) {
                                                                            phoneNumerfour.setText(splitString[2]);
                                                                        }

                                                                        if (phonenumerfivth.getText().toString().equalsIgnoreCase("")) {
                                                                            if (!phoneNumerfour.getText().toString().equalsIgnoreCase(splitString[3])) {
                                                                                if (!detectedTextView.getText().toString().equalsIgnoreCase(splitString[3])) {
                                                                                    phonenumerfivth.setText(splitString[3]);
                                                                                }
                                                                            }
                                                                        }
                                                                    } catch (ArrayIndexOutOfBoundsException e) {
                                                                        e.printStackTrace();
                                                                    }


                                                                }else
                                                                {
                                                                    if (phoneNumberTxt.getText().toString().equalsIgnoreCase("")) {
                                                                        phoneNumberTxt.setText(lines[i - 1]);
                                                                    }
                                                                }


                                                           // }
                                                        }else
                                                        {
                                                            if (num>=1 && num<=12 && letter>=5) {
                                                                if (!lines[i-1].contains("mobile") ||!lines[i - 1].contains("Phone")
                                                                        || !lines[i - 1].contains("Phone No.") ) {
                                                                    addresstxt.setText(lines[i - 1]);
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                              //  }

                            } catch (ArrayIndexOutOfBoundsException e) {
                                e.printStackTrace();
                            }

                            try {

                                int numberOfDigits = lines[i-2].length();
                                if (numberOfDigits>2) {

                                   /* Pattern p = Pattern.compile("(([A-Z].*[0-9]))");
                                    Matcher m = p.matcher(lines[i - 2]);
                                    boolean b = m.find();
                                    System.out.println(b);

                                    if (b) {*/
                                        if (!phoneTxt.getText().toString().equalsIgnoreCase(lines[i - 2])) {
                                            if ( !phoneNumberTxt.getText().toString().equalsIgnoreCase(lines[i - 2])) {
                                                if ( !addresstxt.getText().toString().equalsIgnoreCase(lines[i - 2])) {
                                                    if (!thirdTxt.getText().toString().equalsIgnoreCase(lines[i - 2])) {

                                                        char[] ch = lines[i - 2].toCharArray();
                                                        int letter = 0;
                                                        int num = 0;
                                                        for(int id = 0; id < lines[i - 2].length(); id++){
                                                            if(Character.isLetter(ch[id])){
                                                                letter ++ ;
                                                            }
                                                            else if(Character.isDigit(ch[id])){
                                                                num ++ ;
                                                            }
                                                        }

                                                        if (num>=10) {
                                                            if (PhoneTxtthird.getText().toString().equalsIgnoreCase("")) {
                                                                PhoneTxtthird.setText(lines[i - 2]);
                                                            }
                                                        }else
                                                            {
                                                                if (num>=1 && num<=12 && letter>=5) {
                                                                    if (!lines[i-2].contains("mobile") ||!lines[i - 2].contains("Phone")
                                                                            || !lines[i - 2].contains("Phone No.")) {
                                                                        addresstxt.setText(lines[i - 2]);
                                                                    }
                                                                }
                                                            }
                                                    }
                                                }
                                            }
                                        }
                                    }
                              //  }


                                // fourth phone numer
                                int numberOfDigitsfourth = lines[i-3].length();
                                if (numberOfDigitsfourth>2) {
                                    if (!phoneTxt.getText().toString().equalsIgnoreCase(lines[i - 3])) {
                                        if ( !phoneNumberTxt.getText().toString().equalsIgnoreCase(lines[i - 3])) {
                                            if ( !addresstxt.getText().toString().equalsIgnoreCase(lines[i - 3])) {
                                                if (!thirdTxt.getText().toString().equalsIgnoreCase(lines[i - 3])) {
                                                    if (!PhoneTxtthird.getText().toString().equalsIgnoreCase(lines[i - 3])) {

                                                        char[] ch = lines[i - 3].toCharArray();
                                                        int letter = 0;
                                                        int num = 0;
                                                        for (int id = 0; id < lines[i - 3].length(); id++) {
                                                            if (Character.isLetter(ch[id])) {
                                                                letter++;
                                                            } else if (Character.isDigit(ch[id])) {
                                                                num++;
                                                            }
                                                        }

                                                        if (num >= 10) {
                                                            if (phoneNumerfour.getText().toString().equalsIgnoreCase("")) {
                                                                phoneNumerfour.setText(lines[i - 3]);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                // fivth number
                                int numberOfDigitsfivth = lines[i-4].length();
                                if (numberOfDigitsfivth>2) {
                                    if (!phoneTxt.getText().toString().equalsIgnoreCase(lines[i - 4])) {
                                        if ( !phoneNumberTxt.getText().toString().equalsIgnoreCase(lines[i - 4])) {
                                            if ( !addresstxt.getText().toString().equalsIgnoreCase(lines[i - 4])) {
                                                if (!thirdTxt.getText().toString().equalsIgnoreCase(lines[i - 4])) {
                                                    if (!PhoneTxtthird.getText().toString().equalsIgnoreCase(lines[i - 4])) {
                                                        if (!phoneNumerfour.getText().toString().equalsIgnoreCase(lines[i - 4])) {

                                                            char[] ch = lines[i - 4].toCharArray();
                                                            int letter = 0;
                                                            int num = 0;
                                                            for (int id = 0; id < lines[i - 4].length(); id++) {
                                                                if (Character.isLetter(ch[id])) {
                                                                    letter++;
                                                                } else if (Character.isDigit(ch[id])) {
                                                                    num++;
                                                                }
                                                            }

                                                            if (num >= 10) {

                                                                if (phonenumerfivth.getText().toString().equalsIgnoreCase("")) {
                                                                    if (!phoneNumerfour.getText().toString().equalsIgnoreCase(lines[i - 3])) {
                                                                        if (!detectedTextView.getText().toString().equalsIgnoreCase(lines[i - 3])) {
                                                                            phonenumerfivth.setText(lines[i - 3]);
                                                                        }
                                                                    }
                                                                }

                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                e.printStackTrace();
                            }

                        }

                    }

                    //postal code
                    Pattern zipPattern = Pattern.compile("[0-9]{5}(?!.*([0-9]{5}))");
                    Matcher zipMatcher = zipPattern.matcher(community);
                    if (zipMatcher.find()) {
                        String zip = zipMatcher.group(1);

                        PostalCode.setText(zip);
                    }

                    //address company
                        char[] ch = firstLine.toCharArray();
                        int letter = 0;
                        int num = 0;
                        for(int id = 0; id < firstLine.length(); id++){
                            if(Character.isLetter(ch[id])){
                                letter ++ ;
                            }
                            else if(Character.isDigit(ch[id])){
                                num ++ ;
                            }
                        }

                        Log.e("letter: " , letter + " null");
                        Log.e("number: " ,num + " null");

                    if (letter>5 && num<15 ) {

                        // first address geting
                        if (!firstLine.contains(".com")) {

                            if (!designation.getText().toString().equalsIgnoreCase(firstLine)) {

                                if (!addresstxt.getText().toString().equalsIgnoreCase(firstLine)) {
                                    if (!firstLine.contains("@")) {

                                        if (!firstLine.contains("co.in")) {
                                            if (!firstLine.contains("w.")) {
                                                if (!company_name.getText().toString().equalsIgnoreCase(firstLine)) {

                                                    char[] ch1 = firstLine.toCharArray();
                                                    int letter1 = 0;
                                                    int num1 = 0;
                                                    for(int id = 0; id < firstLine.length(); id++){
                                                        if(Character.isLetter(ch1[id])){
                                                            letter1 ++ ;
                                                        }
                                                        else if(Character.isDigit(ch1[id])){
                                                            num1 ++ ;
                                                        }
                                                    }


                                                    if (!detectedTextView.getText().toString().equalsIgnoreCase(firstLine)) {

                                                        if (!nameTxt.getText().toString().equalsIgnoreCase(firstLine)) {

                                                            if (num1>=1 && num1<=12 && letter1>=5) {


                                                                //if (thirdTxt.getText().toString().equalsIgnoreCase("")) {
                                                                  //  if (addCnt == 0) {
                                                                        thirdTxt.setText(firstLine);
                                                                    //    addCnt++;
                                                                     //   break;
                                                                 //   }
                                                              //  }
                                                            }else
                                                                {
                                                                    if (num1>=1 ) {
                                                                        if (addresstxt.getText().toString().equalsIgnoreCase("")) {
                                                                            addresstxt.setText(firstLine);
                                                                        }
                                                                    }
                                                                }
                                                        }
                                                    }

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }




                    }




                }

                //Web Url ---
                if (firstLine.contains(".com") && !firstLine.contains("@") || firstLine.contains("w.") || firstLine.contains("co.in")) {

                    webUrlTxt.setText(firstLine);
                }

                //email
                /*Pattern pe = Pattern.compile("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b",
                        Pattern.CASE_INSENSITIVE);
                Matcher matcher1 = pe.matcher(firstLine);
                while(matcher1.find()) {*/

                if (firstLine.contains("@")) {
                    emailTxt.setText(firstLine);
                }

                //second type address getting
                char[] ch1 = firstLine.toCharArray();
                int letter1 = 0;
                int num1 = 0;
                for(int id = 0; id < firstLine.length(); id++){
                    if(Character.isLetter(ch1[id])){
                        letter1 ++ ;
                    }
                    else if(Character.isDigit(ch1[id])){
                        num1 ++ ;
                    }
                }

                if (num1<10 && letter1>4)
                {

                    // space count
                    int spaceCount = 0;
                    for (char c : firstLine.toCharArray()) {
                        if (c == ' ') {
                            spaceCount++;
                        }
                    }

                    //second home address first line get with any number
                    if (!addresstxt.getText().toString().equalsIgnoreCase(firstLine))
                    {
                        if (!thirdTxt.getText().toString().equalsIgnoreCase(firstLine))
                        {
                            if (!phoneTxt.getText().toString().equalsIgnoreCase(firstLine)) {
                                if (!phoneNumberTxt.getText().toString().equalsIgnoreCase(firstLine)) {
                                    if (!firstLine.contains("@")) {
                                        if (!firstLine.contains(".com")) {
                                            if (!nameTxt.getText().toString().equalsIgnoreCase(firstLine)) {
                                                if (!detectedTextView.getText().toString().equalsIgnoreCase(firstLine)) {
                                                    if (!company_name.getText().toString().equalsIgnoreCase(firstLine)) {
                                                        homeaddressFirst.setText(firstLine);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    //second home address second line get with any number
                    if (!addresstxt.getText().toString().equalsIgnoreCase(firstLine))
                    {
                        if (!thirdTxt.getText().toString().equalsIgnoreCase(firstLine))
                        {
                            if (!homeaddressFirst.getText().toString().equalsIgnoreCase(firstLine))
                            {
                                if (!phoneTxt.getText().toString().equalsIgnoreCase(firstLine)) {
                                    if (!phoneNumberTxt.getText().toString().equalsIgnoreCase(firstLine)) {
                                        if (!firstLine.contains("@")) {
                                            if (!firstLine.contains(".com")) {
                                                if (!nameTxt.getText().toString().equalsIgnoreCase(firstLine)) {
                                                    if (!detectedTextView.getText().toString().equalsIgnoreCase(firstLine)) {
                                                        if (!company_name.getText().toString().equalsIgnoreCase(firstLine)) {
                                                            homeaddressSecond.setText(firstLine);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else
                    {
                        //second home address first line get with no number
                        if (!addresstxt.getText().toString().equalsIgnoreCase(firstLine))
                        {
                            if (!thirdTxt.getText().toString().equalsIgnoreCase(firstLine))
                            {
                                if (!homeaddressFirst.getText().toString().equalsIgnoreCase(firstLine))
                                {
                                    if (!homeaddressSecond.getText().toString().equalsIgnoreCase(firstLine)) {
                                        if (!phoneTxt.getText().toString().equalsIgnoreCase(firstLine)) {
                                            if (!phoneNumberTxt.getText().toString().equalsIgnoreCase(firstLine)) {
                                                if (!firstLine.contains(".com")) {
                                                    if (!firstLine.contains(".co.in") ||
                                                            !firstLine.contains("Ltd")) {
                                                        if (!company_name.getText().toString().equalsIgnoreCase(firstLine) ) {

                                                            if (!nameTxt.getText().toString().equalsIgnoreCase(firstLine)) {
                                                                if (!firstLine.contains("@")) {

                                                                    if (!designation.getText().toString().equalsIgnoreCase(firstLine)) {
                                                                       if (!detectedTextView.getText().toString().equalsIgnoreCase(firstLine)) {

                                                                           homeaddressFirst.setText(firstLine);

                                                                       }
                                                                   }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        //second home address second line get with no number
                        if (!addresstxt.getText().toString().equalsIgnoreCase(firstLine))
                        {
                            if (!thirdTxt.getText().toString().equalsIgnoreCase(firstLine))
                            {
                                if (!homeaddressFirst.getText().toString().equalsIgnoreCase(firstLine))
                                {
                                    if (!homeaddressSecond.getText().toString().equalsIgnoreCase(firstLine)) {
                                        if (!phoneTxt.getText().toString().equalsIgnoreCase(firstLine)) {
                                            if (!phoneNumberTxt.getText().toString().equalsIgnoreCase(firstLine)) {
                                                if (!firstLine.contains(".com")) {
                                                    if (!firstLine.contains(".co.in")) {
                                                        if (!company_name.getText().toString().equalsIgnoreCase(firstLine)) {
                                                            if (!nameTxt.getText().toString().equalsIgnoreCase(firstLine)) {
                                                                if (!firstLine.contains("@")) {
                                                                    if (!designation.getText().toString().equalsIgnoreCase(firstLine)) {
                                                                        if (!detectedTextView.getText().toString().equalsIgnoreCase(firstLine)) {
                                                                            homeaddressSecond.setText(firstLine);

                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

            }


        }
        finally {
            textRecognizer.release();
        }
    }

    //extract Url
    public static List<String> extractUrls(String text)
    {
        List<String> containedUrls = new ArrayList<String>();
        String urlRegex = "((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern pattern = Pattern.compile(urlRegex, Pattern.CASE_INSENSITIVE);
        Matcher urlMatcher = pattern.matcher(text);

        while (urlMatcher.find())
        {
            containedUrls.add(text.substring(urlMatcher.start(0),
                    urlMatcher.end(0)));
        }

        return containedUrls;
    }

    // extract the string
    public static String extractNumber(final String str) {

        if(str == null || str.isEmpty()) return "";

        StringBuilder sb = new StringBuilder();
        boolean found = false;
        for(char c : str.toCharArray()){
            if(Character.isDigit(c)){
                sb.append(c);
                found = true;
            } else if(found){
                // If we already found a digit before and this char is not a digit, stop looping
                break;
            }
        }

        return sb.toString();
    }

    public static int getCount(String number) {
        int flag = 0;
        for (int i = 0; i < number.length(); i++) {
            if (Character.isDigit(number.charAt(i))) {
                flag++;
            }
        }
        return flag;
    }

    private void inspect(Uri uri) {
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            is = getActivity().getContentResolver().openInputStream(uri);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            options.inSampleSize = 2;
            options.inScreenDensity = DisplayMetrics.DENSITY_LOW;
            bitmap = BitmapFactory.decodeStream(is, null, options);
            inspectFromBitmap(bitmap);
        } catch (FileNotFoundException e) {
            Log.w(TAG, "Failed to find the file: " + uri, e);
        } finally {
            if (bitmap != null) {
                bitmap.recycle();
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.w(TAG, "Failed to close InputStream", e);
                }
            }
        }
    }


    boolean isValidPostalCode(String code) {

        // must have 6 digits

        if(code.length() != 6)

        return false;



        // make if uppercase for not having to chack for A to Z AND a to z

        code = code.toUpperCase();

        // translate into digit

        char[] digit = code.toCharArray();

        for(int i = 0; i < 4; ++i) {

            if(digit[i] < '0' || digit[i] > '9')

            return false;

        }

        for(int i = 4; i < 6; ++i) {

            if(digit[i] < 'A' || digit[i] > 'Z')

            return false;

        }

        // sounds OK to me

        return true;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {

                if (imageUri != null) {
                    inspect(imageUri);

                    if (flag == 1) {
                        cardImg.setImageBitmap(decodeSampledBitmapFromResource(getPath(imageUri), 200, 200));
                        flag = 0;
                    }else if (flag == 2) {

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500);
                        backCardImg.setLayoutParams(layoutParams);
                        backCardImg.setImageBitmap(decodeSampledBitmapFromResource(getPath(imageUri), 200, 200));
                        flag = 0;
                    }
                   // cardImg.setImageURI(imageUri);
                }

            }
        }
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
// Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 2;

        if (height > reqHeight || width > reqWidth) {
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(String resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(resId, options);
    }

    public String getPath(Uri uri)
    {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    @Override
    public void getCustomerName(String name) {

    }

    @Override
    public void getCustomerId(String cusId) {

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    // open the popup eindow method
    //call popup
    private void callPopup(final ArrayList<CustomerDetailsModel> list) {

        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup_window_layout, null);
        Button cancel, save;
        popupWindow = new PopupWindow(popupView,
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT,
                true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.animationName);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        save = (Button) popupView.findViewById(R.id.saveBtn);
        cancel = (Button) popupView.findViewById(R.id.cancelbtutton);



        save.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                popupWindow.dismiss();

            }

        });


        cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {

                popupWindow.dismiss();
            }
        });

        searchData = (EditText) popupView.findViewById(R.id.editTextsearching);
        serchListData = (ListView) popupView.findViewById(R.id.listview_customer_list);


        final DemoCustomeAdapter demoCustomeAdapter = new DemoCustomeAdapter(getActivity(), list, this);
        serchListData.setAdapter(demoCustomeAdapter);






            serchListData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    for (int i = 0; i < serchListData.getChildCount(); i++) {
                        if (position == i) {
                            serchListData.getChildAt(i).setBackgroundColor(Color.parseColor("#e0e0e0"));
                        } else {
                            serchListData.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
                        }
                    }
                }
            });

            searchData.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    demoCustomeAdapter.getFilter().filter(s.toString());
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    demoCustomeAdapter.getFilter().filter(s);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }


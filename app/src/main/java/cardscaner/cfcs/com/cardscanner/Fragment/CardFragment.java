package cardscaner.cfcs.com.cardscanner.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cardscaner.cfcs.com.cardscanner.Adapter.CardListAdapter;
import cardscaner.cfcs.com.cardscanner.Adapter.DemoCustomeAdapter;
import cardscaner.cfcs.com.cardscanner.Adapter.ExpandableListAdapter;
import cardscaner.cfcs.com.cardscanner.Database.BusinessVerticalMasterTable;
import cardscaner.cfcs.com.cardscanner.Database.IndustrySegmentMasterTable;
import cardscaner.cfcs.com.cardscanner.Database.IndustryTypeMasterTable;
import cardscaner.cfcs.com.cardscanner.Database.MasterDatabase;
import cardscaner.cfcs.com.cardscanner.Database.NumberTypeMasterTable;
import cardscaner.cfcs.com.cardscanner.Database.PrincipleMasterTable;
import cardscaner.cfcs.com.cardscanner.Database.ZoneMasterTable;
import cardscaner.cfcs.com.cardscanner.Interface.ExpandDataisInterface;
import cardscaner.cfcs.com.cardscanner.Model.AllExapndableListModel;
import cardscaner.cfcs.com.cardscanner.Model.CardListModel;
import cardscaner.cfcs.com.cardscanner.Model.CustomerDetailsModel;
import cardscaner.cfcs.com.cardscanner.Model.ZoneListModel;
import cardscaner.cfcs.com.cardscanner.R;
import cardscaner.cfcs.com.cardscanner.source.AppController;
import cardscaner.cfcs.com.cardscanner.source.ConnectionDetector;
import cardscaner.cfcs.com.cardscanner.source.LinearLayoutManagerWithSmoothScroller;
import cardscaner.cfcs.com.cardscanner.source.SettingConstant;
import cardscaner.cfcs.com.cardscanner.source.SharedPrefs;
import cardscaner.cfcs.com.cardscanner.source.UtilsMethods;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardFragment extends Fragment implements ExpandDataisInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public RecyclerView cardRecycler;
    public ArrayList<CardListModel> list = new ArrayList<>();
    public CardListAdapter adapter;
    public String getCustomerListUrl = SettingConstant.BASEURL_FOR_LOGIN + "DigiCardScannerService.asmx/AppCustomerList";
    public String userId = "",authCode = "";
    public TextView cardTxt;
    public ConnectionDetector conn;
    public PopupWindow popupWindow;
    View view_Group = null;
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    public Button applyBtn,resetBtn;
    List<String> listDataHeader;
    HashMap<String, List<AllExapndableListModel>> listDataChild;
    public FloatingActionButton filterBtn;
    public List<AllExapndableListModel> principleList = new ArrayList<AllExapndableListModel>();
    public  List<AllExapndableListModel> businessVerticalList = new ArrayList<AllExapndableListModel>();
    public List<AllExapndableListModel> industrySegmentList = new ArrayList<AllExapndableListModel>();
    public List<AllExapndableListModel> industryTypeList = new ArrayList<AllExapndableListModel>();
    public List<AllExapndableListModel> zoneTypeList = new ArrayList<AllExapndableListModel>();
    public MasterDatabase masterdatbase;
    public String headerValueName = "",principileId = "0", businessVerticalId = "0",industrySegmentId = "0",
                  industryTypeId = "0",regionId="0";

    private OnFragmentInteractionListener mListener;

    public CardFragment() {
        // Required empty public constructor
    }

    public static CardFragment newInstance(String param1, String param2) {
        CardFragment fragment = new CardFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_card, container, false);

        cardRecycler = (RecyclerView)rootView.findViewById(R.id.card_recyceler);
        cardTxt = (TextView)rootView.findViewById(R.id.no_customer);
        filterBtn = (FloatingActionButton)rootView.findViewById(R.id.filterbtn);
        userId = UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getUserId(getActivity())));
        authCode = UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAuthCode(getActivity())));
        conn = new ConnectionDetector(getActivity());
        masterdatbase = new MasterDatabase(getActivity());

        adapter = new CardListAdapter(getActivity(),list,getActivity());
        cardRecycler.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(getActivity()));
        cardRecycler.smoothScrollToPosition(5);
        /*RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        cardRecycler.setLayoutManager(mLayoutManager);
        cardRecycler.setItemAnimator(new DefaultItemAnimator());*/
        cardRecycler.setAdapter(adapter);

       // prepareMemberData();

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callPopup();
            }
        });



        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (conn.getConnectivityStatus()>0)
        {
            getCustomerList(authCode,userId,"","0","0","0","0","0");

        }else
            {
                conn.showNoInternetAlret();
            }
    }

    /*  private void prepareMemberData() {
        CardListModel model = new CardListModel("Himanshu Dubey","Nmtronics Privat Limited");
        list.add(model);

        model = new CardListModel("Rahul","Cfcs");
        list.add(model);

        model = new CardListModel("Jaiswal Ji","Cfcs");
        list.add(model);

         adapter.notifyDataSetChanged();
    }*/

    //Api Work
    public void getCustomerList(final String authCode, final String userId, final String CustomerName,final String PrincipleID,
                                final String BusinessVerticalID, final String IndustrySegmentID, final String IndustryTypeID,
                                final String ZoneID) {
        final ProgressDialog pDialog = new ProgressDialog(getActivity(),R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest historyInquiry = new StringRequest(
                Request.Method.POST, getCustomerListUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("AppDdlList", response);
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(response.substring(response.indexOf("["),response.lastIndexOf("]") +1 ));

                        if (list.size()>0)
                        {
                            list.clear();
                        }
                        for (int i=0; i<jsonArray.length(); i++)
                        {
                            JSONObject object = jsonArray.getJSONObject(i);
                            if (object.has("status")) {
                                String status = object.getString("status");
                                if (status.equalsIgnoreCase("failed"))
                                {
                                    String MsgNotification = object.getString("MsgNotification");

                                    Toast.makeText(getActivity(), MsgNotification, Toast.LENGTH_SHORT).show();
                                    pDialog.dismiss();
                                }else
                                    {
                                        String Name =object.getString("Name");
                                        String Designation = object.getString("Designation");
                                        String Company = object.getString("Company");
                                        String CardFrontImage = object.getString("CardFrontImage");
                                        String CustomerID = object.getString("CustomerID");
                                        String Number = object.getString("Number");

                                        list.add(new CardListModel(Name,Company,CustomerID,CardFrontImage,Designation,Number));
                                    }
                            }else
                                {
                                    String Name =object.getString("Name");
                                    String Designation = object.getString("Designation");
                                    String Company = object.getString("Company");
                                    String CardFrontImage = object.getString("CardFrontImage");
                                    String CustomerID = object.getString("CustomerID");
                                    String Number = object.getString("Number");

                                    list.add(new CardListModel(Name,Company,CustomerID,CardFrontImage,Designation,Number));
                                }




                        }

                    } catch (StringIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }


                    if (list.size() == 0)
                    {
                        cardTxt.setVisibility(View.VISIBLE);
                        cardRecycler.setVisibility(View.GONE);

                    }else
                        {
                            cardTxt.setVisibility(View.GONE);
                            cardRecycler.setVisibility(View.VISIBLE);
                        }



                    adapter.notifyDataSetChanged();

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
                params.put("CustomerName", CustomerName);
                params.put("PrincipleID", PrincipleID);
                params.put("BusinessVerticalID", BusinessVerticalID);
                params.put("IndustrySegmentID", IndustrySegmentID);
                params.put("IndustryTypeID", IndustryTypeID);
                params.put("ZoneID", ZoneID);

                Log.e("Parms", params.toString());
                return params;
            }

        };
        historyInquiry.setRetryPolicy(new DefaultRetryPolicy(SettingConstant.Retry_Time,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(historyInquiry, "AddDdlList");

    }

    @Override
    public void getExpandId(String Id) {


        if (headerValueName.equalsIgnoreCase("Principle"))
        {
            principileId = Id;
            Log.e("principileId",principileId);
          //  getCustomerList(authCode,userId,"",valueId,"0","0","0","0");
           // popupWindow.dismiss();

        }else if (headerValueName.equalsIgnoreCase("Business Vertical"))
        {
            businessVerticalId = Id;
            Log.e("businessVerticalId",businessVerticalId);
            //getCustomerList(authCode,userId,"","0",valueId,"0","0","0");
         //   popupWindow.dismiss();

        }else if (headerValueName.equalsIgnoreCase("Industry Segment"))
        {
            industrySegmentId = Id;
            Log.e("industrySegmentId",industrySegmentId);
          //  getCustomerList(authCode,userId,"","0","0",valueId,"0","0");
         //   popupWindow.dismiss();

        }else if (headerValueName.equalsIgnoreCase("Industry Type"))
        {
            industryTypeId = Id;
            Log.e("industryTypeId",industryTypeId);
          //  getCustomerList(authCode,userId,"","0","0","0",valueId,"0");
          //  popupWindow.dismiss();

        }else if (headerValueName.equalsIgnoreCase("Region"))
        {
            regionId = Id;
            Log.e("regionId",regionId);
          //  getCustomerList(authCode,userId,"","0","0","0","0",valueId);
         //   popupWindow.dismiss();

        }

      //  Toast.makeText(getActivity(), valueId, Toast.LENGTH_SHORT).show();

    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    //call popup
    private void callPopup() {

        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getBaseContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.filter_layout, null);
        Button cancel, save;
        popupWindow = new PopupWindow(popupView,
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT,
                true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.animationName);
        popupWindow.showAtLocation(popupView, Gravity.CENTER_HORIZONTAL, 0, 0);

        expListView = (ExpandableListView)popupView.findViewById(R.id.lvExp);
        applyBtn = (Button)popupView.findViewById(R.id.applybtn);
        resetBtn = (Button)popupView.findViewById(R.id.resetbtn);
        prepareListData();
        listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild,this);
        expListView.setAdapter(listAdapter);

        expListView.setChoiceMode(ExpandableListView.CHOICE_MODE_SINGLE);

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {

              //  Toast.makeText(getActivity(), listDataHeader.get(i), Toast.LENGTH_SHORT).show();
                headerValueName = listDataHeader.get(i);

                Log.e("checking headervalue",headerValueName);
                return false;
            }
        });

        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getCustomerList(authCode,userId,"",principileId,businessVerticalId,industrySegmentId,industryTypeId,regionId);
                popupWindow.dismiss();
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getFragmentManager();
                // FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                Fragment frag = new CardFragment();
                // update the main content by replacing fragments
                fragmentManager.beginTransaction()
                        .replace(R.id.cardlayout, frag)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .addToBackStack(null)
                        .commit();

                popupWindow.dismiss();
            }
        });


       /* expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {

                view.setSelected(true);
                if (view_Group != null) {
                    view_Group.setBackgroundColor(Color.parseColor("#e0e0e0"));
                }else {
                    view_Group = view;
                    view_Group.setBackgroundColor(Color.parseColor("#676767"));
                }

                return true;
            }
        });
*/


    }
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<AllExapndableListModel>>();

        // Adding child data
        listDataHeader.add("Region");
        listDataHeader.add("Business Vertical");
        listDataHeader.add("Industry Segment");
        listDataHeader.add("Industry Type");
        listDataHeader.add("Principle");


        // Adding child data

        //checking zone list also
        Cursor zoneTypeCursor = masterdatbase.getZoneMasterTable(userId);
        if (zoneTypeCursor != null && zoneTypeCursor.getCount() > 0) {
            if (zoneTypeList.size() > 0) {
                zoneTypeList.clear();
            }
            if (zoneTypeCursor.moveToFirst()) {
                do {

                    String zoneId = zoneTypeCursor.getString(zoneTypeCursor.getColumnIndex(ZoneMasterTable.zoneID));
                    String zoneName = zoneTypeCursor.getString(zoneTypeCursor.getColumnIndex(ZoneMasterTable.zoneName));

                    zoneTypeList.add(new AllExapndableListModel(zoneName,zoneId));


                } while (zoneTypeCursor.moveToNext());
            }


        //checking the BusinessVertical data is save In local database or not!------------
        Cursor cursor = masterdatbase.getBusinessVerticalMasterTableData(userId);
        if (cursor != null && cursor.getCount() > 0) {
            if (businessVerticalList.size() > 0) {
                businessVerticalList.clear();
            }
            if (cursor.moveToFirst()) {
                do {

                    String businessVerticalId = cursor.getString(cursor.getColumnIndex(BusinessVerticalMasterTable.businessVerticalID));
                    String businessVerticalType = cursor.getString(cursor.getColumnIndex(BusinessVerticalMasterTable.businessVertical));


                    businessVerticalList.add(new AllExapndableListModel(businessVerticalType, businessVerticalId));

                } while (cursor.moveToNext());
            }

        }

        //Industry Segment local database checking
        Cursor industryCursor = masterdatbase.getIndustrySegmentMasterTable(userId);
        if (industryCursor != null && industryCursor.getCount() > 0) {
            if (industrySegmentList.size() > 0) {
                industrySegmentList.clear();

            }
            if (industryCursor.moveToFirst()) {
                do {

                    String industrySegmentID = industryCursor.getString(industryCursor.getColumnIndex(IndustrySegmentMasterTable.industrySegmentID));
                    String industrySegment = industryCursor.getString(industryCursor.getColumnIndex(IndustrySegmentMasterTable.industrySegment));

                    industrySegmentList.add(new AllExapndableListModel(industrySegment, industrySegmentID));

                } while (industryCursor.moveToNext());
            }

        }


        //Industry Type chck database in local database
        Cursor industryTypeCursor = masterdatbase.getIndustryTypeMasterTable(userId);
        if (industryTypeCursor != null && industryTypeCursor.getCount() > 0) {
            if (industryTypeList.size() > 0) {
                industryTypeList.clear();
            }
            if (industryTypeCursor.moveToFirst()) {
                do {

                    String industryTypeID = industryTypeCursor.getString(industryTypeCursor.getColumnIndex(IndustryTypeMasterTable.industryTypeID));
                    String industryType = industryTypeCursor.getString(industryTypeCursor.getColumnIndex(IndustryTypeMasterTable.industryType));

                    industryTypeList.add(new AllExapndableListModel(industryType, industryTypeID));

                } while (industryTypeCursor.moveToNext());
            }

        }
            //Checked principle type data in my local database
            Cursor principleTypeCursor = masterdatbase.getPrincipleMasterTable(userId);
            if (principleTypeCursor != null && principleTypeCursor.getCount() > 0) {
                if (principleList.size() > 0) {
                    principleList.clear();
                }
                if (principleTypeCursor.moveToFirst()) {
                    do {

                        String principleTypeID = principleTypeCursor.getString(principleTypeCursor.getColumnIndex(PrincipleMasterTable.principleId));
                        String principleType = principleTypeCursor.getString(principleTypeCursor.getColumnIndex(PrincipleMasterTable.principle));

                        principleList.add(new AllExapndableListModel(principleType, principleTypeID));

                    } while (principleTypeCursor.moveToNext());
                }

            }




            //   zoneIdString = zoneList.get(0).getZoneId();


        }


        listDataChild.put(listDataHeader.get(0), zoneTypeList); // Header, Child data
        listDataChild.put(listDataHeader.get(1), businessVerticalList);
        listDataChild.put(listDataHeader.get(2), industrySegmentList);
        listDataChild.put(listDataHeader.get(3), industryTypeList);
        listDataChild.put(listDataHeader.get(4), principleList);
    }

}

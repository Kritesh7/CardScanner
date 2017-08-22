package cardscaner.cfcs.com.cardscanner.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Map;

import cardscaner.cfcs.com.cardscanner.Adapter.CardListAdapter;
import cardscaner.cfcs.com.cardscanner.Model.CardListModel;
import cardscaner.cfcs.com.cardscanner.Model.CustomerDetailsModel;
import cardscaner.cfcs.com.cardscanner.Model.ZoneListModel;
import cardscaner.cfcs.com.cardscanner.R;
import cardscaner.cfcs.com.cardscanner.source.AppController;
import cardscaner.cfcs.com.cardscanner.source.LinearLayoutManagerWithSmoothScroller;
import cardscaner.cfcs.com.cardscanner.source.SettingConstant;
import cardscaner.cfcs.com.cardscanner.source.SharedPrefs;
import cardscaner.cfcs.com.cardscanner.source.UtilsMethods;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardFragment extends Fragment {
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
        userId = UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getUserId(getActivity())));
        authCode = UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAuthCode(getActivity())));

        adapter = new CardListAdapter(getActivity(),list,getActivity());
        cardRecycler.setLayoutManager(new LinearLayoutManagerWithSmoothScroller(getActivity()));
        cardRecycler.smoothScrollToPosition(5);
        /*RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        cardRecycler.setLayoutManager(mLayoutManager);
        cardRecycler.setItemAnimator(new DefaultItemAnimator());*/
        cardRecycler.setAdapter(adapter);

       // prepareMemberData();

        getCustomerList(authCode,userId,"","0","0","0","0","0");

        return rootView;
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
                                }
                            }
                            String Name =object.getString("Name");
                            String Designation = object.getString("Designation");
                            String Company = object.getString("Company");
                            String CardFrontImage = object.getString("CardFrontImage");
                            String CustomerID = object.getString("CustomerID");
                            String Number = object.getString("Number");

                            list.add(new CardListModel(Name,Company,CustomerID,CardFrontImage,Designation,Number));

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

                // Log.e("Parms", params.toString());
                return params;
            }

        };
        historyInquiry.setRetryPolicy(new DefaultRetryPolicy(SettingConstant.Retry_Time,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(historyInquiry, "AddDdlList");

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

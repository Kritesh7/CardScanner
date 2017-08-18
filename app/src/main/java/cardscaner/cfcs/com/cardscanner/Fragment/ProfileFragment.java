package cardscaner.cfcs.com.cardscanner.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    public String userId="", authCode = "";
    public String getProfileDetailsUrl = SettingConstant.BASEURL_FOR_LOGIN + "loginservice.asmx/AppUserDetail";
    public EditText nameTxt, designationTxt, phoneTxt, emailTxt;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        nameTxt = (EditText)rootView.findViewById(R.id.pronametxt);
        designationTxt = (EditText)rootView.findViewById(R.id.prodesgtxt);
        phoneTxt = (EditText)rootView.findViewById(R.id.prophonetxt);
        emailTxt = (EditText)rootView.findViewById(R.id.proemailtxt);

        userId = UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getUserId(getActivity())));
        authCode = UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getAuthCode(getActivity())));

        getProfileDetailsList(authCode,userId);

        return rootView;
    }

    //Api Work
    public void getProfileDetailsList(final String authCode, final String userId) {
        final ProgressDialog pDialog = new ProgressDialog(getActivity(),R.style.AppCompatAlertDialogStyle);
        pDialog.setMessage("Loading...");
        pDialog.show();

        StringRequest historyInquiry = new StringRequest(
                Request.Method.POST, getProfileDetailsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.e("AppDdlList", response);

                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"),response.lastIndexOf("}") +1 ));


                    if (jsonObject.has("status")) {
                        String status = jsonObject.getString("status");
                        if (status.equalsIgnoreCase("failed"))
                        {
                            String MsgNotification = jsonObject.getString("MsgNotification");

                            Toast.makeText(getActivity(), MsgNotification, Toast.LENGTH_SHORT).show();
                            pDialog.dismiss();
                        }
                    }

                    String Name = jsonObject.getString("Name");
                    String EmailID = jsonObject.getString("EmailID");
                    String MobileNo = jsonObject.getString("MobileNo");
                    String DesiName = jsonObject.getString("DesiName");

                    nameTxt.setText(Name);
                    emailTxt.setText(EmailID);
                    phoneTxt.setText(MobileNo);
                    designationTxt.setText(DesiName);


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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

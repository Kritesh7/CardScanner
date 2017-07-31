package cardscaner.cfcs.com.cardscanner.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cardscaner.cfcs.com.cardscanner.Adapter.BusinessVerticalAdapter;
import cardscaner.cfcs.com.cardscanner.Adapter.CardListAdapter;
import cardscaner.cfcs.com.cardscanner.Adapter.IndusteryAdapter;
import cardscaner.cfcs.com.cardscanner.Adapter.PrincipalTypeAdapter;
import cardscaner.cfcs.com.cardscanner.Model.BusinessVerticalCheckList;
import cardscaner.cfcs.com.cardscanner.Model.CardListModel;
import cardscaner.cfcs.com.cardscanner.Model.IndustryModel;
import cardscaner.cfcs.com.cardscanner.Model.PrincipalTypeModel;
import cardscaner.cfcs.com.cardscanner.R;
import cardscaner.cfcs.com.cardscanner.source.EditTextMonitor;
import cardscaner.cfcs.com.cardscanner.source.MultiTouchListener;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CamFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CamFragment extends Fragment {
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
    private static final String TAG = "Cam Scanner";
    private static final int REQUEST_WRITE_PERMISSION = 20;

    private Uri imageUri;
    int count = 0;
    private EditTextMonitor detectedTextView, emailTxt, phoneTxt, nameTxt,addresstxt,PostalCode,
            thirdTxt,designation, company_name,phoneNumberTxt,PhoneTxtthird,webUrlTxt;
    public ImageView cardImg;
    public RecyclerView businessVerticalRecycler, industeryRecycler,principalTypeRecycler ;
    Fragment frag;

    public BusinessVerticalAdapter adapter;
    public IndusteryAdapter industeryAdapter;
    public PrincipalTypeAdapter principalTypeAdapter;
    public ArrayList<PrincipalTypeModel> principalTypeList = new ArrayList<>();
    public ArrayList<IndustryModel> indstryList = new ArrayList<>();
    public ArrayList<BusinessVerticalCheckList> list = new ArrayList<>();

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


        // Business Vertical List
        adapter = new BusinessVerticalAdapter(getActivity(),list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        businessVerticalRecycler.setLayoutManager(mLayoutManager);
        businessVerticalRecycler.setItemAnimator(new DefaultItemAnimator());
        businessVerticalRecycler.setAdapter(adapter);

        prepareMemberData();

        //Industery List
        industeryAdapter = new IndusteryAdapter(getActivity(),indstryList);
        RecyclerView.LayoutManager industerymLayoutManager = new LinearLayoutManager(getActivity());
        industeryRecycler.setLayoutManager(industerymLayoutManager);
        industeryRecycler.setItemAnimator(new DefaultItemAnimator());
        industeryRecycler.setAdapter(industeryAdapter);

        prepareIndusteryData();

        //principal List
        principalTypeAdapter = new PrincipalTypeAdapter(getActivity(),principalTypeList);
        RecyclerView.LayoutManager principalmLayoutManager = new LinearLayoutManager(getActivity());
        principalTypeRecycler.setLayoutManager(principalmLayoutManager);
        principalTypeRecycler.setItemAnimator(new DefaultItemAnimator());
        principalTypeRecycler.setAdapter(principalTypeAdapter);

        preparePrincipalData();




        // MultiTouchListener touchListener=new MultiTouchListener(getActivity());

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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

        return rootView;
    }

    private void prepareMemberData() {
        BusinessVerticalCheckList model = new BusinessVerticalCheckList("First Check");
        list.add(model);

        model = new BusinessVerticalCheckList("Second Check");
        list.add(model);

        model = new BusinessVerticalCheckList("Third Check");
        list.add(model);
        adapter.notifyDataSetChanged();
    }

    private void prepareIndusteryData() {
        IndustryModel model = new IndustryModel("Ind First Check");
        indstryList.add(model);

        model = new IndustryModel("Ind First Check");
        indstryList.add(model);

        model = new IndustryModel("Ind First Check");
        indstryList.add(model);

        industeryAdapter.notifyDataSetChanged();
    }

    private void preparePrincipalData() {
        PrincipalTypeModel model = new PrincipalTypeModel("Ind Principal Check");
        principalTypeList.add(model);

        model = new PrincipalTypeModel("Ind Principal Check");
        principalTypeList.add(model);

        model = new PrincipalTypeModel("Ind Principal Check");
        principalTypeList.add(model);

        principalTypeAdapter.notifyDataSetChanged();
    }

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
                            emailTxt.setText(firstLine);
                        } else {

                            if (getCount(firstLine)>=6) {

                                Pattern pa = Pattern.compile("\\+?\\(?\\d{2,4}\\)?[\\d\\s-]{3,}");

                                // [a-zA-Z]+
                                Matcher ma = pa.matcher(firstLine);
                                if (ma.find()) {
                                    if(!Pattern.matches("[a-zA-Z]+",firstLine)) {

                                        // firstLine = firstLine.replaceAll("[^\\d.]", "");
                                     /*   phoneTxt.setText(firstLine);*/
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
                                                addresstxt.setText(lines[i + 1]);
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
                                Pattern digitPattern1 = Pattern.compile("\\d");
                                Matcher digitMatcher1 = digitPattern1.matcher(firstLine);
                                int digitCount1 = 0;
                                while (digitMatcher1.find()) {
                                    digitCount1++;
                                }

                                if (digitCount1 >=6) {
                                }else
                                {
                                    if (!firstLine.contains("@"))
                                        addresstxt.setText(lines[i + 1]);
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
                            emailTxt.setText(firstLine);
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
                                                    if (!firstLine.contains(".com") && !firstLine.contains("@") &&
                                                            !firstLine.contains("co.in")) {
                                                        company_name.setText(firstLine);
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
                        if (count == 0) {
                            nameTxt.setText(firstLine);
                            count++;

                            Pattern p = Pattern.compile(".*\\d+.*");
                            Matcher m = p.matcher(lines[i +1]);
                            boolean b = m.find();
                            if (!b) {
                                if(Pattern.matches(".*[a-zA-Z]+.*[a-zA-Z]", firstLine)) {

                                    designation.setText(lines[i + 1]);
                                }
                            }
                        }
                       // break;
                    }

                    if (community.contains("PVT. LT")) {
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

                                            if (letter<=12) {
                                                phoneTxt.setText(firstLine);
                                            }else
                                            {
                                                addresstxt.setText(firstLine);
                                            }
                                        }
                                    }
                                }
                            }

                            try {

                                int numberOfDigits = lines[i-1].length();
                                if (numberOfDigits>2) {

                                    Pattern p = Pattern.compile("(([A-Z].*[0-9]))");
                                    Matcher m = p.matcher(lines[i - 1]);
                                    boolean b = m.find();
                                    System.out.println(b);

                                    if (b) {
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

                                                        if (letter<=12) {
                                                            phoneNumberTxt.setText(lines[i - 1]);
                                                        }else
                                                        {
                                                            addresstxt.setText(lines[i - 1]);
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

                            try {

                                int numberOfDigits = lines[i-2].length();
                                if (numberOfDigits>2) {

                                    Pattern p = Pattern.compile("(([A-Z].*[0-9]))");
                                    Matcher m = p.matcher(lines[i - 2]);
                                    boolean b = m.find();
                                    System.out.println(b);

                                    if (b) {
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

                                                        if (letter<=12) {
                                                            PhoneTxtthird.setText(lines[i - 2]);
                                                        }else
                                                            {
                                                                addresstxt.setText(lines[i - 2]);
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
                    Pattern zipPattern = Pattern.compile("(\\d{6})");
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

                        if (!firstLine.contains(".com")) {

                            if (!designation.getText().toString().equalsIgnoreCase(firstLine)) {

                                if (!addresstxt.getText().toString().equalsIgnoreCase(firstLine)) {
                                    if (!firstLine.contains(".com") && !firstLine.contains("@") &&
                                             !firstLine.contains("co.in") && !firstLine.contains("w.")) {

                                        thirdTxt.setText(firstLine);
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
                Pattern pe = Pattern.compile("\\b[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b",
                        Pattern.CASE_INSENSITIVE);
                Matcher matcher1 = pe.matcher(firstLine);
                while(matcher1.find()) {
                    emailTxt.setText(matcher1.group());
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



   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_GALLERY:
                if (resultCode == RESULT_OK) {
                    inspect(data.getData());


                }
                break;
            case REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    if (imageUri != null) {
                        inspect(imageUri);

                      *//*  Bitmap photo = (Bitmap) data.getExtras().get("data");

                       // enhanceImage(photo,8,8);
                        cardImg.setImageBitmap( enhanceImage(photo,5,50));*//*
                        cardImg.setImageURI(imageUri);
                    }
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       /* switch (requestCode) {
            case REQUEST_GALLERY:
                if (resultCode == RESULT_OK) {
                    inspect(data.getData());
                }
                break;
            case REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    if (imageUri != null) {
                        inspect(imageUri);
                        cardImg.setImageURI(imageUri);
                    }
                }
                break;
        }*/

        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == Activity.RESULT_OK) {

          /*      Bitmap bmp = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();

                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                // convert byte array to Bitmap

                Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0,
                        byteArray.length);

                cardImg.setImageBitmap(bitmap);
               imageUri =  bitmapToUriConverter(bitmap);

                inspect(imageUri);*/
                //cardImg.setImageURI(imageUri);

                if (imageUri != null) {
                    inspect(imageUri);
                    cardImg.setImageURI(imageUri);
                }

            }
        }
    }

    public Uri bitmapToUriConverter(Bitmap mBitmap) {
        Uri uri = null;
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            // Calculate inSampleSize
            //options.inSampleSize = calculateInSampleSize(options, 100, 100);

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false;
            Bitmap newBitmap = Bitmap.createScaledBitmap(mBitmap, 200, 200,
                    true);
            File file = new File(getActivity().getFilesDir(), "Image"
                    + new Random().nextInt() + ".jpeg");
            FileOutputStream out = getActivity().openFileOutput(file.getName(),
                    Context.MODE_WORLD_READABLE);
            newBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            //get absolute path
            String realPath = file.getAbsolutePath();
            File f = new File(realPath);
            uri = Uri.fromFile(f);

        } catch (Exception e) {
            Log.e("Your Error Message", e.getMessage());
        }
        return uri;
    }


    // TODO: Rename method, update argument and hook method into UI event
    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
*/
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

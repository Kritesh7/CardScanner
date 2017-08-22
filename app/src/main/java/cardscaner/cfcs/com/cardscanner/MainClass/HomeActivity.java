package cardscaner.cfcs.com.cardscanner.MainClass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;

import cardscaner.cfcs.com.cardscanner.Fragment.CamFragment;
import cardscaner.cfcs.com.cardscanner.Fragment.CardFragment;
import cardscaner.cfcs.com.cardscanner.Fragment.MessageFragment;
import cardscaner.cfcs.com.cardscanner.Fragment.ProfileFragment;
import cardscaner.cfcs.com.cardscanner.Fragment.SosFragment;
import cardscaner.cfcs.com.cardscanner.R;
import cardscaner.cfcs.com.cardscanner.source.SharedPrefs;
import cardscaner.cfcs.com.cardscanner.source.UtilsMethods;

public class HomeActivity extends AppCompatActivity {

    private TextView mTextMessage;
    public BottomNavigationView navigation;
    public  Fragment camFragment , cardFragment , messgaeFragment,sosFragment,proFragment;
    public String checkFrag = "2";
    public String Name = "",Designation ="",Company="",ZoneName = "",EmailID = "",OfficeAddress = "",PrincipleName = "",
            BusinessVerticalName="",IndustryTypeName="",IndustrySegmentName="",Website="",ManagementLevel="",FactoryAddress="",
            ResidenceAddress = "",EmailID2 = "",NumberType = "", NumberType2 = "",NumberType3="",NumberType4="",
            NumberType5 = "",Number = "",Number2="",Number3="",Number4="",Number5 = "", customerIdGet = "",ZoneID = "",
            CardBackImage = "",CardFrontImage = "",UserActionMode = "";
    public  Bundle bundle = null;

    //

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_card:
                    cardFragment = new CardFragment();
                    replaceFragment(cardFragment);
                    checkFrag = "1";
                    return true;

                case R.id.navigation_scanner:
                    camFragment = new CamFragment();
                    replaceFragment(camFragment);
                    checkFrag = "2";
                    return true;

                case R.id.navigation_pro:
                    proFragment = new ProfileFragment();
                    replaceFragment(proFragment);
                    checkFrag = "3";
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_bootm);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        }

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbottomnavigation);
        setSupportActionBar(toolbar);

       // mTextMessage = (TextView) findViewById(R.id.message);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        Intent intent = getIntent();
        if (intent != null)
        {
            Name = intent.getStringExtra("Name");
            Designation = intent.getStringExtra("Designation");
            Company = intent.getStringExtra("Company");
            ZoneID = intent.getStringExtra("ZoneID");
            EmailID = intent.getStringExtra("EmailID");
            OfficeAddress = intent.getStringExtra("OfficeAddress");
            PrincipleName = intent.getStringExtra("PrincipleName");
            BusinessVerticalName = intent.getStringExtra("BusinessVerticalName");
            IndustryTypeName = intent.getStringExtra("IndustryTypeName");
            IndustrySegmentName = intent.getStringExtra("IndustrySegmentName");
            Website = intent.getStringExtra("Website");
            ManagementLevel = intent.getStringExtra("ManagementLevel");
            FactoryAddress = intent.getStringExtra("FactoryAddress");
            ResidenceAddress = intent.getStringExtra("ResidenceAddress");
            EmailID2 = intent.getStringExtra("EmailID2");
            NumberType = intent.getStringExtra("NumberType");
            NumberType2 = intent.getStringExtra("NumberType2");
            NumberType3 = intent.getStringExtra("NumberType3");
            NumberType4 = intent.getStringExtra("NumberType4");
            NumberType5 = intent.getStringExtra("NumberType5");
            Number = intent.getStringExtra("Number");
            Number2 = intent.getStringExtra("Number2");
            Number3 = intent.getStringExtra("Number3");
            Number4 = intent.getStringExtra("Number4");
            Number5 = intent.getStringExtra("Number5");
            customerIdGet = intent.getStringExtra("customerIdGet");
            CardFrontImage = intent.getStringExtra("CardFrontImage");
            CardFrontImage = intent.getStringExtra("CardFrontImage");
            UserActionMode = intent.getStringExtra("UserActionMode");
            ZoneName = intent.getStringExtra("ZoneName");
            ArrayList<String> BussinesList = (ArrayList<String>) getIntent().getSerializableExtra("BusinessList");
            ArrayList<String> IndustrytypeList = (ArrayList<String>) getIntent().getSerializableExtra("IndustryTypeList");
            ArrayList<String> segmentmyList = (ArrayList<String>) getIntent().getSerializableExtra("IndustrySegmentList");
            ArrayList<String> PrincipleList = (ArrayList<String>) getIntent().getSerializableExtra("PrincipleList");


            bundle = new Bundle();

            bundle.putString("Name", Name);
            bundle.putString("Designation", Designation);
            bundle.putString("Company", Company);
            bundle.putString("ZoneID", ZoneID);
            bundle.putString("EmailID", EmailID);
            bundle.putString("OfficeAddress", OfficeAddress);
            bundle.putString("PrincipleName", PrincipleName);
            bundle.putString("BusinessVerticalName", BusinessVerticalName);
            bundle.putString("IndustryTypeName", IndustryTypeName);
            bundle.putString("IndustrySegmentName", IndustrySegmentName);
            bundle.putString("Website", Website);
            bundle.putString("ManagementLevel", ManagementLevel);
            bundle.putString("FactoryAddress", FactoryAddress);
            bundle.putString("ResidenceAddress", ResidenceAddress);
            bundle.putString("EmailID2", EmailID2);
            bundle.putString("NumberType", NumberType);
            bundle.putString("NumberType2", NumberType2);
            bundle.putString("NumberType3", NumberType3);
            bundle.putString("NumberType4", NumberType4);
            bundle.putString("NumberType5", NumberType5);
            bundle.putString("Number", Number);
            bundle.putString("Number2", Number2);
            bundle.putString("Number3", Number3);
            bundle.putString("Number4", Number4);
            bundle.putString("Number5", Number5);
            bundle.putString("customerIdGet", customerIdGet);
            bundle.putString("CardFrontImage",CardFrontImage);
            bundle.putString("CardBackImage",CardBackImage);
            bundle.putString("UserActionMode",UserActionMode);
            bundle.putString("ZoneName",ZoneName);
            bundle.putStringArrayList("BusinessList", BussinesList);
            bundle.putStringArrayList("IndustryTypeList", IndustrytypeList);
            bundle.putStringArrayList("IndustrySegmentList", segmentmyList);
            bundle.putStringArrayList("PrincipleList", PrincipleList);

        }



        //selected first like home fragment
        navigation.setItemIconTintList(null);
        selectBottomNavigationBarItem(R.id.navigation_scanner);
        Fragment mFragment3 = new CamFragment();
        mFragment3.setArguments(bundle);
        replaceFragment(mFragment3);


    }

    void selectBottomNavigationBarItem(int itemId) {
        Menu menu = navigation.getMenu();
        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            boolean shouldBeChecked = item.getItemId() == itemId;
            if (shouldBeChecked) {
                item.setChecked(true);
                break;
            }
        }
    }

    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations( R.anim.push_right_in,
                R.anim.push_left_out, R.anim.push_left_in, R.anim.push_right_out);
        fragmentTransaction.replace(R.id.content, someFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {

        if (checkFrag.equalsIgnoreCase("2"))
        {
            finish();
        }else
            {
                Fragment mFragment3 = new CamFragment();
                replaceFragment(mFragment3);
                selectBottomNavigationBarItem(R.id.navigation_scanner);
                checkFrag = "2";
            }

        //  SaveImageTask.this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            //item.setTitle("Date");

            UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.setStatus(HomeActivity.this,
                    "")));

            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.push_left_in,
                    R.anim.push_right_out);
            finish();
            return true;
        }

        if (id == R.id.action_changepassword)
        {
            Intent i = new Intent(getApplicationContext(), ChangePasswordActivity.class);
            startActivity(i);
            overridePendingTransition(R.anim.push_left_in,
                    R.anim.push_right_out);
        }

        return super.onOptionsItemSelected(item);
    }

}

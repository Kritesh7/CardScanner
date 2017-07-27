package cardscaner.cfcs.com.cardscanner.MainClass;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import cardscaner.cfcs.com.cardscanner.Fragment.CamFragment;
import cardscaner.cfcs.com.cardscanner.Fragment.CardFragment;
import cardscaner.cfcs.com.cardscanner.Fragment.MessageFragment;
import cardscaner.cfcs.com.cardscanner.Fragment.ProfileFragment;
import cardscaner.cfcs.com.cardscanner.Fragment.SosFragment;
import cardscaner.cfcs.com.cardscanner.R;

public class HomeActivity extends AppCompatActivity {

    private TextView mTextMessage;
    public BottomNavigationView navigation;
    public  Fragment camFragment , cardFragment , messgaeFragment,sosFragment,proFragment;
    public String checkFrag = "1";

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
               /* case R.id.navigation_msg:
                    messgaeFragment = new MessageFragment();
                    replaceFragment(messgaeFragment);
                    checkFrag = "2";
                    return  true;*/
                case R.id.navigation_scanner:
                    camFragment = new CamFragment();
                    replaceFragment(camFragment);
                    checkFrag = "2";
                    return true;
               /* case R.id.navigation_sos:
                    sosFragment = new SosFragment();
                    replaceFragment(sosFragment);
                    checkFrag = "4";
                    return true;*/
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

       // mTextMessage = (TextView) findViewById(R.id.message);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //selected first like home fragment
        navigation.setItemIconTintList(null);
        selectBottomNavigationBarItem(R.id.navigation_scanner);
        Fragment mFragment3 = new CamFragment();
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

}

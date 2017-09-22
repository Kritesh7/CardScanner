package cardscaner.cfcs.com.cardscanner.MainClass;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import cardscaner.cfcs.com.cardscanner.MainClass.LoginActivity;
import cardscaner.cfcs.com.cardscanner.R;
import cardscaner.cfcs.com.cardscanner.source.SharedPrefs;
import cardscaner.cfcs.com.cardscanner.source.UtilsMethods;

public class SplashScreen extends AppCompatActivity {

    public com.tuyenmonkey.mkloader.MKLoader progressBar;
    private static int SPLASH_TIME_OUT = 3000;
    public String statusString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        }

        statusString = UtilsMethods.getBlankIfStringNull(String.valueOf(SharedPrefs.getStatus(SplashScreen.this)));

        progressBar = (com.tuyenmonkey.mkloader.MKLoader)findViewById(R.id.animated_progress);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity


                if (statusString.equalsIgnoreCase("1"))
                {
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
                    finish();
                }else {
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
                    finish();
                }


            }
        }, SPLASH_TIME_OUT);

    }

}
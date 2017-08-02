package cardscaner.cfcs.com.cardscanner.MainClass;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import cardscaner.cfcs.com.cardscanner.MainClass.HomeActivity;
import cardscaner.cfcs.com.cardscanner.R;

public class LoginActivity extends AppCompatActivity {

    public Button loginBtn;
    public TextView forgetPassBtn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        }


        loginBtn = (Button)findViewById(R.id.loginbtn);
        forgetPassBtn = (TextView)findViewById(R.id.forgetpass);

        forgetPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), ForgetPasswordActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
                finish();

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
                finish();
            }
        });
    }


}

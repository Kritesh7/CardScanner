package cardscaner.cfcs.com.cardscanner.MainClass;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import cardscaner.cfcs.com.cardscanner.R;

public class ChangePasswordActivity extends AppCompatActivity {

    public ImageView backbtnImg;
    public EditText currentPasswordtxt, newpasswordTxt, confirmnewPassTxt;
    public Button changePassBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.status_color));
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbottomnavigation);
        setSupportActionBar(toolbar);
        backbtnImg = (ImageView)toolbar.findViewById(R.id.backbtn);

        backbtnImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_right_out);
                finish();
            }
        });

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        currentPasswordtxt = (EditText)findViewById(R.id.currentpass);
        newpasswordTxt = (EditText) findViewById(R.id.newpassword);
        confirmnewPassTxt = (EditText) findViewById(R.id.confirmnewpassword);
        changePassBtn = (Button) findViewById(R.id.chagepasswordbtn);
    }

}

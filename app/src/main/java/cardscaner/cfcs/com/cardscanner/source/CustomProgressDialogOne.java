package cardscaner.cfcs.com.cardscanner.source;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import cardscaner.cfcs.com.cardscanner.R;

/**
 * Created by Admin on 01-12-2017.
 */

public class CustomProgressDialogOne
{
    public void showCustomDialog(Context context) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_progress_dialog_one, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        RelativeLayout mainLayout = (RelativeLayout) dialogView.findViewById(R.id.mainLayout);
        mainLayout.setBackgroundResource(R.drawable.my_progress_one);
        AnimationDrawable frameAnimation = (AnimationDrawable)mainLayout.getBackground();
        frameAnimation.start();
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void dismiss(Context context)
    {

    }


}

package cardscaner.cfcs.com.cardscanner.source;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * Created by Admin on 15-07-2017.
 */

public class EditTextMonitor extends android.support.v7.widget.AppCompatEditText {
    private final Context mcontext; // Just the constructors to create a new
    // EditText...

    public EditTextMonitor(Context context) {
        super(context);
        this.mcontext = context;
    }

    public EditTextMonitor(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mcontext = context;
    }

    public EditTextMonitor(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mcontext = context;
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        // Do your thing:
        boolean consumed = super.onTextContextMenuItem(id);
        // React:
        switch (id) {
            case android.R.id.cut:
                onTextCut();
                break;
            case android.R.id.paste:
                onTextPaste();
                break;
            case android.R.id.copy:
                onTextCopy();
        }
        return consumed;
    }

    /**
     * Text was cut from this EditText.
     */
    public void onTextCut() {
        InputMethodManager imm = (InputMethodManager) getContext()
                .getApplicationContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        Toast.makeText(mcontext, "Event of Cut!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Text was copied from this EditText.
     */
    public void onTextCopy() {
        InputMethodManager imm = (InputMethodManager) getContext()
                .getApplicationContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        Toast.makeText(mcontext, "Event of Copy!", Toast.LENGTH_SHORT).show();
    }

    /**
     * Text was pasted into the EditText.
     */
    public void onTextPaste() {
        InputMethodManager imm = (InputMethodManager) getContext()
                .getApplicationContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        Toast.makeText(mcontext, "Event of Paste!", Toast.LENGTH_SHORT).show();
    }}

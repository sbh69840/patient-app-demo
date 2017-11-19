package admin.eoe.squad.patient;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by admin on 21/03/2017.
 */

public class MyTextView extends android.support.v7.widget.AppCompatTextView {
    public MyTextView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
        init();
    }
    public MyTextView(Context context, AttributeSet attrs){
        super(context,attrs);
        init();
    }
    public MyTextView(Context context) {
        super(context);
        init();
    }
    public void init(){
        Typeface tf= Typeface.createFromAsset(getContext().getAssets(),"fonts/minimal.otf");
        setTypeface(tf,1);
    }
}

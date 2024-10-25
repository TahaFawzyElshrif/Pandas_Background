package pandas.android.pansasbackground.Classes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import pandas.android.pansasbackground.R;

public class viewHolder {
    View convertView;
    ImageView mgg;
    TextView t0;
    TextView t1;

    public viewHolder(View v) {
        convertView = v;
    }

    public TextView getT0() {
        if (t0 == null) {
            t0 = convertView.findViewById(R.id.lblitem);
        }
        return t0;
    }

    public TextView getT1() {
        if (t1 == null) {
            t1 = convertView.findViewById(R.id.lbldescrip);
        }
        return t0;
    }

    public ImageView getMgg() {
        if (mgg == null) {
            mgg = convertView.findViewById(R.id.imageDescrip);
        }
        return mgg;
    }
}
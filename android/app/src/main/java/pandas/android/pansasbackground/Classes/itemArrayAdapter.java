package pandas.android.pansasbackground.Classes;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import pandas.android.pansasbackground.R;
public class itemArrayAdapter extends ArrayAdapter {
    private ArrayList<item_back> vmyitem;
    Context _context;
    public itemArrayAdapter(@NonNull Context context, int resource, @NonNull List <item_back> items) {
        super(context, resource, items);
        vmyitem=(ArrayList<item_back>)items;
        _context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup listView) {
        View raw=convertView;
        viewHolder vvvv;

        if (raw==null){// as not exist
            LayoutInflater inflater= (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            raw=inflater.inflate(R.layout.list_oneitem_choose_exist ,listView,false);
            vvvv=new viewHolder(raw);
            raw.setTag(vvvv);//each view have tag descripe what it have
        }else{
            vvvv=(viewHolder)raw.getTag();
        }
        vvvv.getT0().setText(vmyitem.get(position).label);
        vvvv.getT1().setText(vmyitem.get(position).descripe);
        vvvv.getMgg().setImageResource(vmyitem.get(position).itemIcon);





        return raw;
    }
}

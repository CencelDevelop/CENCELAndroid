package mx.com.cencel.comercial.cencel.activities.stores;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import mx.com.cencel.comercial.cencel.R;
import mx.com.cencel.comercial.cencel.pojo.StoreSimple;

/**
 * Created by iHouse on 01/08/15.
 */
public class StoresArrayAdapter extends ArrayAdapter<StoreSimple> {
    private final Activity context;
    private List<StoreSimple> itemList;

    public StoresArrayAdapter(Activity context, List<StoreSimple> itemList){
        super(context, R.layout.stores_row, itemList);

        this.context = context;
        this.itemList = itemList;
    }

    public int getCount(){
        if(itemList != null)
            return itemList.size();
        return 0;
    }

    public StoreSimple getItem(int position){
        if(itemList != null)
            return itemList.get(position);
        return null;
    }

    public long getItemId(int position) {
        if (itemList != null)
            return itemList.get(position).hashCode();
        return 0;
    }

    public List<StoreSimple> getItemList() {
        return itemList;
    }

    public void setItemList(List<StoreSimple> itemList) {
        this.itemList = itemList;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.stores_row, null, true);
        StoreSimple store = itemList.get(position);

        TextView sucursalTitleText = (TextView) rowView.findViewById(R.id.store_name);
        TextView sucursalCodeText = (TextView) rowView.findViewById(R.id.store_code);

        sucursalCodeText.setText(store.getCode());
        sucursalTitleText.setText(store.getName());

        return rowView;
    }

}

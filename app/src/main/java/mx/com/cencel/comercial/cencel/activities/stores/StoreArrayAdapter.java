package mx.com.cencel.comercial.cencel.activities.stores;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mx.com.cencel.comercial.cencel.R;
import mx.com.cencel.comercial.cencel.pojo.StoreInformation;

/**
 * Created by iHouse on 01/08/15.
 */
public class StoreArrayAdapter extends ArrayAdapter<StoreInformation> {
    private final Activity context;
    private List<StoreInformation> itemList;

    public StoreArrayAdapter(Activity context, List<StoreInformation> itemList){
        super(context, R.layout.store_detail_row, itemList);
        this.context = context;
        this.itemList = itemList;
    }

    public int getCount(){
        if(itemList != null)
            return itemList.size();
        return 0;
    }

    public StoreInformation getItem(int position){
        if(itemList != null)
            return itemList.get(position);
        return null;
    }

    public long getItemId(int position) {
        if (itemList != null)
            return itemList.get(position).hashCode();
        return 0;
    }

    public List<StoreInformation> getItemList() {
        return itemList;
    }

    public void setItemList(List<StoreInformation> itemList){
        this.itemList = itemList;
    }

    public View getView(int position, View view, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.store_detail_row, null, true);
        StoreInformation store = itemList.get(position);

        TextView storeInfo = (TextView) rowView.findViewById(R.id.store_detail_info);
        ImageView storeIcon = (ImageView) rowView.findViewById(R.id.store_detail_icon);
        TextView storeCoordinate = (TextView) rowView.findViewById(R.id.store_detail_location);

        // pintar fila en base al indice del objeto para definir que pintar
        switch (store.getId()){
            case 0:
                // pintar el nombre de la tienda
                storeInfo.setText(store.getStoreName());
                storeIcon.setVisibility(View.GONE);
                break;
            case 1:
                // direccion de la tienda
                storeInfo.setText(store.getStoreAddress());
                storeIcon.setVisibility(View.GONE);
                break;
            case 2:
                // telefono de la tienda
                storeInfo.setText(store.getStorePhone());
                storeIcon.setImageResource(R.drawable.phone_cell);
                break;
            case 3:
                //correo de la tienda
                storeInfo.setText(store.getStoreEmail());
                storeIcon.setImageResource(R.drawable.mail_cell);
                break;
            case 4:
                // coordenada para mapa
                storeCoordinate.setText(store.getStoreCoordinate());
                storeInfo.setText(getContext().getString(R.string.store_location_text));
                storeIcon.setImageResource(R.drawable.location_cell);
                break;
        }

        return rowView;
    }
}

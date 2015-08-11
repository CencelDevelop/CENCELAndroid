package mx.com.cencel.comercial.cencel.activities.stores;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import mx.com.cencel.comercial.cencel.R;
import mx.com.cencel.comercial.cencel.pojo.StoreInformation;
import mx.com.cencel.comercial.cencel.util.CencelUtils;

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

        // obtener la celda
        LinearLayout rootLayout = (LinearLayout)rowView.findViewById(R.id.store_row_root);

        // obtener condenido de la celda
        TextView storeInfo = (TextView) rowView.findViewById(R.id.store_detail_info);
        TextView storeCoordinate = (TextView) rowView.findViewById(R.id.store_detail_location);
        TextView adicionalText = (TextView) rowView.findViewById(R.id.store_detail_adicional);

        // pintar fila en base al indice del objeto para definir que pintar
        switch (store.getId()){
            case 0:
                // pintar el nombre de la tienda y la direccion en un sola celda
                rootLayout.setBackgroundResource(R.drawable.header_tienda);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CencelUtils.ConvertToDip(250, rowView));
                rootLayout.setLayoutParams(params);

                LinearLayout.LayoutParams params5 = new LinearLayout.LayoutParams(storeInfo.getLayoutParams().width, storeInfo.getLayoutParams().height);
                params5.leftMargin = CencelUtils.ConvertToDip(35, rowView);
                params5.topMargin = CencelUtils.ConvertToDip(10, rowView);

                LinearLayout.LayoutParams params6 = new LinearLayout.LayoutParams(adicionalText.getLayoutParams().width, adicionalText.getLayoutParams().height);
                params6.leftMargin = CencelUtils.ConvertToDip(15, rowView);
                params6.topMargin = CencelUtils.ConvertToDip(40, rowView);

                storeInfo.setLayoutParams(params5);
                adicionalText.setLayoutParams(params6);

                // tienda en tono blanco
                storeInfo.setText(store.getStoreName());
                storeInfo.setTextColor(Color.parseColor("#FFFFFF"));

                // direccion de la tienda
                adicionalText.setTextColor(Color.parseColor("#FFFFFF"));
                adicionalText.setText(store.getStoreAddress());
                adicionalText.setVisibility(View.VISIBLE);
                break;
            case 1:
                // telefono de la tienda
                rootLayout.setBackgroundResource(R.drawable.llamanos_celda);
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CencelUtils.ConvertToDip(80, rowView));
                rootLayout.setLayoutParams(params2);

                LinearLayout.LayoutParams params7 = new LinearLayout.LayoutParams(adicionalText.getLayoutParams().width, adicionalText.getLayoutParams().height);
                params7.leftMargin = CencelUtils.ConvertToDip(100, rowView);
                params7.topMargin = CencelUtils.ConvertToDip(8, rowView);
                adicionalText.setLayoutParams(params7);

                adicionalText.setText(store.getStorePhone());
                storeInfo.setText(R.string.llamanos);
                adicionalText.setVisibility(View.VISIBLE);
                break;
            case 2:
                //correo de la tienda
                rootLayout.setBackgroundResource(R.drawable.escribenos_celda);
                LinearLayout.LayoutParams params3 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CencelUtils.ConvertToDip(80, rowView));
                rootLayout.setLayoutParams(params3);

                LinearLayout.LayoutParams params8 = new LinearLayout.LayoutParams(adicionalText.getLayoutParams().width, adicionalText.getLayoutParams().height);
                params8.leftMargin = CencelUtils.ConvertToDip(100, rowView);
                params8.topMargin = CencelUtils.ConvertToDip(8, rowView);
                adicionalText.setLayoutParams(params8);

                adicionalText.setText(store.getStoreEmail());
                adicionalText.setVisibility(View.VISIBLE);
                storeInfo.setText(R.string.escribenos);
                break;
            case 3:
                // coordenada para mapa
                rootLayout.setBackgroundResource(R.drawable.mapa_celda);
                LinearLayout.LayoutParams params4 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, CencelUtils.ConvertToDip(80, rowView));
                rootLayout.setLayoutParams(params4);
                storeCoordinate.setText(store.getStoreCoordinate());
                storeInfo.setText(getContext().getString(R.string.store_location_text));
                break;
        }


        return rowView;
    }
}

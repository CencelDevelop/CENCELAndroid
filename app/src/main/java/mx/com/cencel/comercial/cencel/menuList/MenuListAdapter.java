package mx.com.cencel.comercial.cencel.menuList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import mx.com.cencel.comercial.cencel.R;

/**
 * Created by iHouse on 01/08/15.
 */
public class MenuListAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] itemNames;
    private final int[] imgIds;
    private final String[] itemDescriptions;


    // constructor del custom adapter
    public MenuListAdapter(Activity context, String[] itemNames, int[] imgIds, String[] itemDescriptions){
        super(context, R.layout.rowlist, itemNames);

        this.context = context;
        this.itemNames = itemNames;
        this.imgIds = imgIds;
        this.itemDescriptions = itemDescriptions;
    }

    // pintando la fila
    public View getView(int position, View view, ViewGroup parent){
        // generando la nueva fila
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.rowlist, null, true);

        TextView textTitle = (TextView) rowView.findViewById(R.id.item_title);
        ImageView imageIcon = (ImageView) rowView.findViewById(R.id.item_icon);
        TextView descriptionText = (TextView) rowView.findViewById(R.id.item_description);

        textTitle.setText(itemNames[position]);
        descriptionText.setText(itemDescriptions[position]);
        imageIcon.setImageResource(imgIds[position]);

        return rowView;
    }
}

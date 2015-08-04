package mx.com.cencel.comercial.cencel.activities.stores;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import mx.com.cencel.comercial.cencel.R;
import mx.com.cencel.comercial.cencel.pojo.StoreSimple;
import mx.com.cencel.comercial.cencel.util.CencelUtils;

/**
 * Created by iHouse on 01/08/15.
 */
public class StoresListActivity extends Activity {

    private ListView sucursalesList;
    public StoresArrayAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.stores_main);
        sucursalesList = (ListView) findViewById(R.id.stores_list);

        // en asincrono obtener los datos del server y pintar la tabla
        adapter = new StoresArrayAdapter(this, new ArrayList<StoreSimple>());
        sucursalesList.setAdapter(adapter);
        (new AsyncListViewLoader()).execute(CencelUtils.buildUrlRequest(this, getString(R.string.getCencelStoresMethod)));

        sucursalesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // sucursal
                Intent nextActivity = new Intent(getApplicationContext(), StoreDetailActivity.class);
                nextActivity.putExtra("store_selected", adapter.getItem(position));
               // Toast.makeText(getApplicationContext(), adapter.getItem(position).getCode(), Toast.LENGTH_SHORT).show();
                startActivity(nextActivity);
            }
        });
    }

    // tarea asincrona
    // clase interna que maneja la tarea asincrona de actualiza la lista de datos
    // necesario esto para que no truene en android posterior a ICS
    // ya usa lo nuevo, no deprecado ;)
    private class AsyncListViewLoader extends AsyncTask<String, Void, List<StoreSimple>>{
        private final ProgressDialog dialog = new ProgressDialog(StoresListActivity.this);

        @Override
        protected void onPostExecute(List<StoreSimple> result)
        {
            super.onPostExecute(result);
            dialog.dismiss();
            adapter.setItemList(result);
            adapter.notifyDataSetChanged();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage(getString(R.string.dialog_message));
            dialog.show();
        }

        @Override
        protected List<StoreSimple> doInBackground(String... params) {
            List<StoreSimple> result = new ArrayList<StoreSimple>();
            try{
                // recibiendo la url
                URL url = new URL(params[0]);

                HttpURLConnection cnn = (HttpURLConnection) url.openConnection();
                cnn.setRequestMethod("POST");
                cnn.connect();
                InputStream stream = cnn.getInputStream();
                byte[] b = new byte[1024];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                while (stream.read(b) != -1)
                    baos.write(b);
                String responseJson = new String(baos.toByteArray());

                JSONObject jsonObject = new JSONObject(responseJson);
                JSONArray storesArray = jsonObject.getJSONArray("d");

                for (int indice = 0; indice < storesArray.length(); indice++) {
                    JSONObject sucursal = storesArray.getJSONObject(indice);

                    result.add(new StoreSimple(sucursal.getString("Code"), sucursal.getString("Name")));
                }

                return result;
            }
            catch (Throwable t){
                t.printStackTrace();
            }
            return result;
        }
    }
}

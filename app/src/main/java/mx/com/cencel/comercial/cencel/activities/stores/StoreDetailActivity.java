package mx.com.cencel.comercial.cencel.activities.stores;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.widget.Toast;
import org.json.JSONObject;

import mx.com.cencel.comercial.cencel.R;
import mx.com.cencel.comercial.cencel.pojo.StoreInformation;
import mx.com.cencel.comercial.cencel.pojo.StoreSimple;
import mx.com.cencel.comercial.cencel.util.CencelUtils;

/**
 * Created by iHouse on 01/08/15.
 */
public class StoreDetailActivity extends Activity {
    private ListView storeInfoList;
    private StoreArrayAdapter adapter;
    private static StoreSimple storeSelected;

    @Override
    public void onCreate(Bundle savedInstanceState){
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.store_detail_main);

            // obten la sucursal seleccionada de la actividad anterior
            Bundle extras = getIntent().getExtras();
            if(extras != null){
                storeSelected = (StoreSimple) extras.get("store_selected");
            }

            storeInfoList = (ListView) findViewById(R.id.store_detail_list);
            adapter = new StoreArrayAdapter(this, new ArrayList<StoreInformation>());
            storeInfoList.setAdapter(adapter);

            // background!!
            (new AsyncListViewLoader()).execute(CencelUtils.buildUrlRequest(this, getString(R.string.getCencelStoreMethod)));

            // click handler
            storeInfoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    try{
                        StoreInformation item = adapter.getItem(position);
                        Intent nextActivity = new Intent();
                        switch (position){
                            // en base al indice seleccionado, hace algo
                            case 0:
                                // nombre y direccion de la tienda
                                nextActivity = new Intent(getApplicationContext(), MapStore.class);
                                nextActivity.putExtra("cordenada", item.getStoreCoordinate());
                                break;
                            case 1:
                                // llamar telefono
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + item.getStorePhone().toString()));
                                startActivity(callIntent);
                                break;
                            case 2:
                                // enviar correo
                                Intent mailIntent = new Intent(Intent.ACTION_SEND);
                                mailIntent.setType("text/plain");
                                mailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mailSubject));
                                mailIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.mailMessage));
                                mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{ item.getStoreEmail().toString() });
                                startActivity(Intent.createChooser(mailIntent, getString(R.string.mailMessageSendig)));
                                break;
                            case 3:
                                // correr mapa
                                nextActivity = new Intent(getApplicationContext(), MapStore.class);
                                nextActivity.putExtra("cordenada", item.getStoreCoordinate());
                                break;
                        }

                        startActivity(nextActivity);
                    }catch(Exception ex){
                        Toast.makeText(getApplicationContext(), "Error: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    //Toast.makeText(getApplicationContext(), adapter.getItem(position).getStorePhone(), Toast.LENGTH_SHORT).show();
                }
            });
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private class AsyncListViewLoader extends AsyncTask<String, Void, List<StoreInformation>> {
        private final ProgressDialog dialog = new ProgressDialog(StoreDetailActivity.this);

        @Override
        protected void onPostExecute(List<StoreInformation> result) {
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
        protected List<StoreInformation> doInBackground(String... params){
            List<StoreInformation> result = new ArrayList<StoreInformation>();

            try{
                // recibiendo la url
                URL url = new URL(params[0]);

                HttpURLConnection cnn = (HttpURLConnection) url.openConnection();
                cnn.setRequestMethod("POST");
                cnn.setDoOutput(true);
                cnn.setRequestProperty("Content-Type", "application/json");

                // cuerpo de la peticion
                JSONObject jsonOut = new JSONObject();
                jsonOut.put("storeCode", StoreDetailActivity.storeSelected.getCode());
                OutputStream output = cnn.getOutputStream();
                output.write(jsonOut.toString().getBytes("UTF-8"));

                // conectando
                cnn.connect();
                InputStream stream = cnn.getInputStream();
                byte[] b = new byte[1024];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                while (stream.read(b) != -1)
                    baos.write(b);
                String responseJson = new String(baos.toByteArray());

                JSONObject jsonObject = new JSONObject(responseJson);
                JSONObject storeInfoJson = jsonObject.getJSONObject("d");

                for (int index=0;index<4;index++){
                    // hacer 5 veces el mismo objeto jaja
                    result.add(new StoreInformation(index,StoreDetailActivity.storeSelected.getName(), storeInfoJson.getString("domicilio"), storeInfoJson.getString("telefono"), storeInfoJson.getString("email"), storeInfoJson.getString("coordinate")));
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

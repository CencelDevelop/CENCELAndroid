package mx.com.cencel.comercial.cencel.activities.micencel;


import android.app.Activity;
import mx.com.cencel.comercial.cencel.R;
import mx.com.cencel.comercial.cencel.activities.stores.StoreArrayAdapter;
import mx.com.cencel.comercial.cencel.pojo.StoreSimple;
import mx.com.cencel.comercial.cencel.util.CencelUtils;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by vcid on 28/08/15.
 */
public class MiCencel extends Activity  {

    public Intent callIntent;
    public EditText usuario;
    public EditText contraseña;
    public Button Enviar;


    private StoreArrayAdapter adapter;
    private static StoreSimple storeSelected;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.micencel);

      usuario = (EditText)findViewById(R.id.txtusuario);
      contraseña = (EditText)findViewById(R.id.txtcontra);

        Enviar = (Button)findViewById(R.id.btoingresa);
        Enviar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                String usr= usuario.getText().toString();
                String cont= contraseña.getText().toString();



                if (usr.equals("") || cont.equals("") ) {
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Llene los campos faltantes", Toast.LENGTH_SHORT);
                    toast1.show();

                }
                else{

                        Toast toast1 = Toast.makeText(getApplicationContext(), "Listo", Toast.LENGTH_SHORT);
                        toast1.show();
                        (new PushToServerHelper()).execute(CencelUtils.buildUrlRequest(MiCencel.this, "loginMiCencel"));

                        usuario.setText("");
                        contraseña.setText("");
                        Enviar.setText("");

                    {
                        Toast toast2 = Toast.makeText(getApplicationContext(), "Usuario Correcto", Toast.LENGTH_SHORT);
                        toast2.show();
                    }



                }}
        });

    }

    private class PushToServerHelper extends AsyncTask<String, Void, String> {
        private final ProgressDialog dialog = new ProgressDialog(MiCencel.this);

        @Override
        protected void onPostExecute(String message) {
            super.onPostExecute(message);
            dialog.dismiss();
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage(getString(R.string.dialog_message));
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {



            String result = "";

            try {

                URL url = new URL(params[0]);

                HttpURLConnection cnn = (HttpURLConnection) url.openConnection();
                cnn.setRequestMethod("POST");
                cnn.setDoOutput(true);
                cnn.setRequestProperty("Content-Type", "application/json");

                // cuerpo de la peticion
                JSONObject datosContactoObj = new JSONObject();

                datosContactoObj.put("Guid", usuario.getText());
                datosContactoObj.put("Contraseña", contraseña.getText());


                JSONObject requestBody = new JSONObject();
                requestBody.put("datosRegistro", datosContactoObj);

                OutputStream output = cnn.getOutputStream();
                output.write(requestBody.toString().getBytes("UTF-8"));


                cnn.connect();
                InputStream stream = cnn.getInputStream();
                byte[] b = new byte[1024];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                while (stream.read(b) != -1)
                    baos.write(b);
                String responseJson = new String(baos.toByteArray());

                JSONObject jsonObject = new JSONObject(responseJson);

                result = jsonObject.getString("d");
                //JSONObject storeInfoJson = jsonObject.getJSONObject("d");
            } catch (Throwable t) {
                t.printStackTrace();
                return null;
            }

            return result;
        }
    }


    public void llamar(View view){
        try {
            callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:44459999"));
            startActivity(callIntent);
        } catch (ActivityNotFoundException activityException) {
            Log.e("dialing-example", "Call failed", activityException);
        }
    }

    public void correo(View view){
        Intent mailIntent = new Intent(Intent.ACTION_SEND);
        mailIntent.setType("text/plain");
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.mailSubject));
        mailIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.mailMessage));
        mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{ "contacto@cencel.com.mx" });
        startActivity(Intent.createChooser(mailIntent, getString(R.string.mailMessageSendig)));
    }


}




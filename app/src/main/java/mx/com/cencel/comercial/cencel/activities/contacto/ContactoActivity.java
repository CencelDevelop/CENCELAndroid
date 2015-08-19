package mx.com.cencel.comercial.cencel.activities.contacto;

import android.app.Activity;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
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
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;

import mx.com.cencel.comercial.cencel.R;

import mx.com.cencel.comercial.cencel.activities.stores.StoreArrayAdapter;

import mx.com.cencel.comercial.cencel.pojo.StoreSimple;
import mx.com.cencel.comercial.cencel.util.CencelUtils;


/**
 * Created by vcid on 03/08/15.
 */
public class ContactoActivity extends Activity {
    public Intent callIntent;
    public EditText email;
    public EditText nombre;
    public EditText telefono;
    public EditText comentario;
    public Button Enviar;


    private StoreArrayAdapter adapter;
    private static StoreSimple storeSelected;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacto);

        email = (EditText)findViewById(R.id.Email);
        nombre = (EditText)findViewById(R.id.Nombre);
        telefono = (EditText)findViewById(R.id.Telefono);
        comentario = (EditText)findViewById(R.id.Comentario);
        Enviar = (Button)findViewById(R.id.enviar);

        Enviar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                // cuando se hace click en el boton

                // validaciones de datos de entrada
                //que sea correo
                //numero de 10 digitos
                //Campos requeridos


                //  Enviar.setEnabled(true);
                String em= email.getText().toString();
                String nb= nombre.getText().toString();
                String tel= telefono.getText().toString();
                String com= comentario.getText().toString();


                if (em.equals("") || nb.equals("") || tel.equals("") || com.equals("")) {
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Llene los campos faltantes", Toast.LENGTH_SHORT);
                    toast1.show();
                    // Enviar.setEnabled(false);


                }
                else{



                    String getText=email.getText().toString();
                    String Expn =
                            "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                                    +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                    +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                                    +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                    +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                                    +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

                    if (getText.matches(Expn) && getText.length() > 0)
                    {
                        //Toast toast2 = Toast.makeText(getApplicationContext(), "Email correcto", Toast.LENGTH_SHORT);
                        //toast2.show();
                        Toast toast1 = Toast.makeText(getApplicationContext(), "Listo", Toast.LENGTH_SHORT);
                        toast1.show();
                        (new PushToServerHelper()).execute(CencelUtils.buildUrlRequest(ContactoActivity.this, "sendComments"));

                        email.setText("");
                        nombre.setText("");
                        telefono.setText("");
                        comentario.setText("");
                        Enviar.setText("");
                    }
                    else
                    {
                        Toast toast2 = Toast.makeText(getApplicationContext(), "Email incorrecto", Toast.LENGTH_SHORT);
                        toast2.show();
                    }



                }}
        });

    }

    private class PushToServerHelper extends AsyncTask<String, Void, String>{
        private final ProgressDialog dialog = new ProgressDialog(ContactoActivity.this);

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
                datosContactoObj.put("Comentario", comentario.getText());

                StringBuilder builder = new StringBuilder();
                builder.append("Android - ");
                builder.append(Build.VERSION.RELEASE.toString());

                datosContactoObj.put("DispOrigen", builder.toString());
                datosContactoObj.put("EmailContacto", email.getText());
                datosContactoObj.put("Nombre", nombre.getText());
                datosContactoObj.put("TelefonoContacto", telefono.getText());

                JSONObject requestBody = new JSONObject();
                requestBody.put("datosRegistro", datosContactoObj);

                OutputStream output = cnn.getOutputStream();
                output.write(requestBody.toString().getBytes("UTF-8"));

                // {'datosRegistro':{'Comentario':'buena app', "DispOrigen": 'Android - 5.2' ... }}

                // conectando
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
}





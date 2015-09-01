package mx.com.cencel.comercial.cencel.activities.micencel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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

import mx.com.cencel.comercial.cencel.R;
import mx.com.cencel.comercial.cencel.util.CencelUtils;

/**
 * Created by vcid on 01/09/15.
 */
public class RegistroMiCencel extends Activity {

    public Intent callIntent;
    public EditText Nombre;
    public EditText Correo;
    public EditText Telefono;
    public EditText Contraseña;
    public Button Registrar;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registromicencel);

        Nombre = (EditText) findViewById(R.id.nom2);
        Correo = (EditText) findViewById(R.id.correo2);
        Telefono = (EditText) findViewById(R.id.tel2);
        Contraseña = (EditText) findViewById(R.id.contra2);

        Registrar = (Button) findViewById(R.id.btoregis);
        Registrar.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                String nom = Nombre.getText().toString();
                String cor = Correo.getText().toString();
                String tel = Telefono.getText().toString();
                String con = Contraseña.getText().toString();

                if (nom.equals("") || cor.equals("") || tel.equals("") || con.equals("") ) {
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Llene los campos faltantes", Toast.LENGTH_SHORT);
                    toast1.show();

                } String getText=Correo.getText().toString();
                String Expn =
                        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                                +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                                +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                                +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

                if (getText.matches(Expn) && getText.length() > 0)
                {
                  
                    (new PushToServerHelper()).execute(CencelUtils.buildUrlRequest(RegistroMiCencel.this, "generarNuevoAsociado"));


                }
                else
                {
                    Toast toast2 = Toast.makeText(getApplicationContext(), "Email incorrecto", Toast.LENGTH_SHORT);
                    toast2.show();
                }
            }
        });

    }

    private class PushToServerHelper extends AsyncTask<String, Void, String> {
        private final ProgressDialog dialog = new ProgressDialog(RegistroMiCencel.this);

        @Override
        protected void onPostExecute(String message) {
            super.onPostExecute(message);
            dialog.dismiss();
           // Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            Toast toastR = Toast.makeText(getApplicationContext(), "Se ha registrado el usuario", Toast.LENGTH_SHORT);
            toastR.show();
                Nombre.setText("");
                Correo.setText("");
                Telefono.setText("");
                Contraseña.setText("");
                Registrar.setText("");


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
                JSONObject datosAsociadoObj = new JSONObject();

                datosAsociadoObj.put("NombreCompleto", Nombre.getText());
                datosAsociadoObj.put("Contrasena", Contraseña.getText());
                datosAsociadoObj.put("Telefono", Telefono.getText());
                datosAsociadoObj.put("Correo", Correo.getText());
                OutputStream output = cnn.getOutputStream();


                output.write(datosAsociadoObj.toString().getBytes("UTF-8"));
                cnn.connect();

                //Validar si regreso algo o no


                InputStream stream = cnn.getInputStream();


                byte[] b = new byte[1024];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                while (stream.read(b) != -1)
                    baos.write(b);
                String responseJson = new String(baos.toByteArray());

                JSONObject jsonObject = new JSONObject(responseJson);

                result = jsonObject.getString("d");



            } catch (Throwable t) {
                t.printStackTrace();
                return null;
            }
            return result;
        }
    }




}





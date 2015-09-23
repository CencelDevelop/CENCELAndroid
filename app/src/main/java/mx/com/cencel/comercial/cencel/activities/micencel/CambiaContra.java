package mx.com.cencel.comercial.cencel.activities.micencel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
 * Created by vcid on 15/09/15.
 */
public class CambiaContra extends Activity {


    public EditText Guid;
    public EditText Email;
    public EditText Contraseña;
    public EditText Contraseña2;
    public Button Cambia;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cambiacontra);

        Guid = (EditText) findViewById(R.id.txtguid);
        Email = (EditText) findViewById(R.id.txtemail);
        Contraseña = (EditText) findViewById(R.id.txtcontra1);
        Contraseña2 = (EditText) findViewById(R.id.txtcontra2);


        Cambia = (Button) findViewById(R.id.btocambia);
        Cambia.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                String guid = Guid.getText().toString();
                String ema = Email.getText().toString();
                String con = Contraseña.getText().toString();
                String con2 = Contraseña2.getText().toString();

                if (guid.equals("") || ema.equals("") || con.equals("") ) {
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Llene los campos faltantes", Toast.LENGTH_SHORT);
                    toast1.show();

                } String getText=Email.getText().toString();
                String Expn =
                        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                                +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                                +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                                +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                                +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

                if (getText.matches(Expn) && getText.length() > 0)
                {
                    if (con.equals(con2)) {
                        (new PushToServerHelper()).execute(CencelUtils.buildUrlRequest(CambiaContra.this, "recuperaContrasena"));
                    }
                    else {
                        Toast toast2 = Toast.makeText(getApplicationContext(), "tus contraseñas no coinciden favor de verificar", Toast.LENGTH_SHORT);
                        toast2.show();
                    }

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
        private final ProgressDialog dialog = new ProgressDialog(CambiaContra.this);

        @Override
        protected void onPostExecute(String message) {
            super.onPostExecute(message);
            dialog.dismiss();
            // Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            Toast toastR = Toast.makeText(getApplicationContext(), "Se actualizo tu contraseña", Toast.LENGTH_SHORT);
            toastR.show();
            Guid.setText("");
            Email.setText("");
            Contraseña.setText("");
            Contraseña2.setText("");
            Intent intent = new Intent(getApplicationContext(), MiCencel.class);
            startActivity(intent);

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

                datosAsociadoObj.put("guid", Guid.getText());
                datosAsociadoObj.put("email", Email.getText());
                datosAsociadoObj.put("nuevaContrasena", Contraseña.getText());

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





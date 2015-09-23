package mx.com.cencel.comercial.cencel.activities.micencel;


import android.app.Activity;

import mx.com.cencel.comercial.cencel.R;
import mx.com.cencel.comercial.cencel.pojo.AsociadoSimple;
import mx.com.cencel.comercial.cencel.util.CencelUtils;
import mx.com.cencel.comercial.cencel.util.SQLite;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;




public class MiCencel extends Activity {


    public EditText usuario;
    public EditText contraseña;
    public Button Enviar;


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.micencel);
        usuario = (EditText) findViewById(R.id.txtusuario);
        contraseña = (EditText) findViewById(R.id.txtcontra);
        Enviar = (Button) findViewById(R.id.btoingresa);



      /*  usuario.setEnabled(false);
        contraseña.setEnabled(false);
        Enviar.setEnabled(false);*/

        // correr un hilo que compruebe que se tenga una sesion iniciada

        // VideoView videoView = (VideoView) findViewById(R.id.inimicencel);
        //Uri path = Uri.parse("android.resource://mx.com.cencel.comercial.cencel/" + R.raw.foncen);
        //videoView.setVideoURI(path);
        //videoView.start();


        Enviar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String usr = usuario.getText().toString();
                String cont = contraseña.getText().toString();

                if (usr.equals("") || cont.equals("")) {
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Llene los campos faltantes", Toast.LENGTH_SHORT);
                    toast1.show();

                } else {
                    (new PushToServerHelper()).execute(CencelUtils.buildUrlRequest(MiCencel.this, "loginMiCencel"));
                }
            }
        });

    }







    //////
//    // este es el hilo que comprueba sesion iniciada
//    private class CompruebaSesionHelper extends AsyncTask<String, Void, Integer>{
//        private final ProgressDialog dialog = new ProgressDialog(MiCencel.this);
//
//        @Override
//        protected void onPostExecute(Integer result) {
//            // if-else para saber el status y que hacer
//
//            if (result == 0 )
//            {
//                SQLite sqlite = new SQLite( getApplicationContext() );
//                sqlite.abrir();
//                Log.i("SQLite", "Se eliminan registros ");
//                sqlite.eliminarGuid();
//                sqlite.cerrar();
//                Toast toast2 = Toast.makeText(getApplicationContext(), "Secion  no iniciada", Toast.LENGTH_SHORT);
//                toast2.show();
//            }
//            else {
//
//
//                Toast toast3 = Toast.makeText(getApplicationContext(), "Vista de informacion", Toast.LENGTH_SHORT);
//                toast3.show();
//                //se pasa a la actividad despues de que el usuario es correcto
//                Intent intent = new Intent(getApplicationContext(), InicioUsuario.class);
//                startActivity(intent);
//
//
//            }
//
//
//            }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            dialog.setMessage(getString(R.string.dialog_message));
//            dialog.show();
//        }
//
//        protected Integer doInBackground(String... params) {
//
//            Integer result = new Integer(0);
//            try{
//                URL url = new URL(params[0]);
//
//                HttpURLConnection cnn = (HttpURLConnection) url.openConnection();
//                cnn.setRequestMethod("POST");
//                cnn.setDoOutput(true);
//                cnn.setRequestProperty("Content-Type", "application/json");
//
//                //se obtiene el guid de la base de datos local///////////////////////////////////////////////////
//                SQLite sqlite = new SQLite( getApplicationContext() );
//                sqlite.abrir();
//                Log.i("SQLite", "Se imprime registros de tabla");
//                Cursor cursor = sqlite.getGuid();//Se obtiene registros
//                String lista = sqlite.imprimirListaguid(cursor);
//                Log.i("SQLite", "Registros: \r\n" + lista);
//                sqlite.cerrar();
//                ////////////////////////////////////////////////////////////////////////////////////////////////
//
//                // cuerpo de la peticion
//                JSONObject guidEnvio = new JSONObject();
//                guidEnvio.put("guid",lista);
//                OutputStream output = cnn.getOutputStream();
//               // cuerpo de la peticion
//                JSONObject datosAsociadoObj = new JSONObject();
//                output.write(datosAsociadoObj.toString().getBytes("UTF-8"));
//                cnn.connect();
//
//                //Validar si regreso algo o no
//
//
//                InputStream stream = cnn.getInputStream();
//                byte[] b = new byte[1024];
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                while (stream.read(b) != -1)
//                    baos.write(b);
//                String responseJson = new String(baos.toByteArray());
//
//                JSONObject jsonObject = new JSONObject(responseJson);
//
//                // lo que viene dentro de "d" es el estatus de la conexion
//                result = jsonObject.getInt("d");
//
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//            return result;
//        }
//    }
//


    private class PushToServerHelper extends AsyncTask<String, Void, AsociadoSimple> {
        private final ProgressDialog dialog = new ProgressDialog(MiCencel.this);

        @Override
        protected void onPostExecute(AsociadoSimple resultado) {
            super.onPostExecute(resultado);
            dialog.dismiss();
            try {
            if (resultado.getResponseCode() == 1){
                // cambiar pantalla
                //se pasa a la actividad despues de que el usuario es correcto
                Intent intent = new Intent(getApplicationContext(), InicioUsuario.class);
                // antes de lanzar la actividad, se debe configurar el objeto destino
                intent.putExtra("vane", resultado);
                usuario.setText("");
                contraseña.setText("");
                startActivity(intent);

            }
            else{

                // algo paso, regresar al menu
                Toast toast2 = Toast.makeText(getApplicationContext(), "Error De Usuario", Toast.LENGTH_SHORT);
                toast2.show();
                Intent intent = new Intent(getApplicationContext(), MiCencel.class);
                startActivity(intent);

            }
            } catch (Throwable t) {
                t.printStackTrace();

            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage(getString(R.string.dialog_message));
            dialog.show();
        }

        @Override
        protected AsociadoSimple doInBackground(String... params) {
            AsociadoSimple result = new AsociadoSimple("",0);

            try {

                URL url = new URL(params[0]);

                HttpURLConnection cnn = (HttpURLConnection) url.openConnection();
                cnn.setRequestMethod("POST");
                cnn.setDoOutput(true);
                cnn.setRequestProperty("Content-Type", "application/json");

                // cuerpo de la peticion
                JSONObject datosAsociadoObj = new JSONObject();

                datosAsociadoObj.put("param", usuario.getText());
                datosAsociadoObj.put("Contrasena", contraseña.getText());
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

                // lo que viene dentro de "d" es el guid
                //  result = jsonObject.getString("d");
                String guid = jsonObject.getString("d");
                result.setGuid(guid);

                if (guid == "null" )
                {
                    result.setResponseCode(0);
                }
                else {
                    result.setResponseCode(1);
                }


                //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                Log.i("SQLite", "===================================================");
                Log.i("SQLite", "Inicio de aplicación SQLite");
                SQLite sqlite = new SQLite(getApplicationContext());
                sqlite.abrir();

                Log.i("SQLite", "Se eliminan registros ");
                sqlite.eliminarGuid();

                Log.i("SQLite", "Se inserto 1 registro");
                sqlite.insertarGuid(guid);

                Log.i("SQLite", "Se imprime registros de tabla");
                Cursor cursor = sqlite.getGuid();//Se obtiene registros
                String lista = sqlite.imprimirListaguid(cursor);
                Log.i("SQLite", "Registros: \r\n" + lista);


                // sqlite.cerrar();
                Log.i("SQLite", "fin :)  ");
                Log.i("SQLite", "===================================================");
                //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                //////////////////////////////////////////////////////////
                // mandar informacion al servidor y cambia el estusconexion  con el SPU
                String urlStr = getApplicationContext().getString(R.string.urlWSCencel)+"actualizaEstatusConexion";
                url = new URL(urlStr);

                HttpURLConnection cnn2 = (HttpURLConnection) url.openConnection();
                cnn2.setRequestMethod("POST");
                cnn2.setDoOutput(true);
                cnn2.setRequestProperty("Content-Type", "application/json");

                // cuerpo de la peticion
                JSONObject datosAsociadoEstObj = new JSONObject();

                datosAsociadoEstObj.put("guid", guid);

                output = cnn2.getOutputStream();

                output.write(datosAsociadoEstObj.toString().getBytes("UTF-8"));
                cnn2.connect();

                //Validar si regreso algo o no
                stream = cnn2.getInputStream();

                b = new byte[1024];
                baos = new ByteArrayOutputStream();

                while (stream.read(b) != -1)
                    baos.write(b);
                responseJson = new String(baos.toByteArray());
                jsonObject = new JSONObject(responseJson);

                //////////////////////////////////////////////////////////////

                //result.setResponseCode(1);

            } catch (Throwable t) {
                t.printStackTrace();
                //result.setResponseCode(0); // algo fallo en el metodo
            }
            return result;
        }
    }

    public void Registro(View view) {
        Intent intent = new Intent(this, RegistroMiCencel.class);
        startActivity(intent);

    }
    public void CambiaContra(View view) {
        Intent intent = new Intent(this, CambiaContra.class);
        startActivity(intent);

    }
    public void RegistroFacebook(View view) {
        Intent intent = new Intent(this, FacebookRegis.class);
        startActivity(intent);

    }

    // @Override
    // public void onResume(){
    //   super.onResume();

    // VideoView videoView = (VideoView) findViewById(R.id.inimicencel);
    //Uri path = Uri.parse("android.resource://mx.com.cencel.comercial.cencel/" + R.raw.foncen);

    //videoView.setVideoURI(path);

    //videoView.start();

//    }


}





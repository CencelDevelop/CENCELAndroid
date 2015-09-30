package mx.com.cencel.comercial.cencel.activities.micencel;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONException;
import org.json.JSONObject;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import mx.com.cencel.comercial.cencel.R;
import mx.com.cencel.comercial.cencel.activities.MenuMainActivity;
import mx.com.cencel.comercial.cencel.pojo.AsociadoSimple;
import mx.com.cencel.comercial.cencel.util.CencelUtils;
import mx.com.cencel.comercial.cencel.util.SQLite;


public class FacebookRegis extends AppCompatActivity {
    CallbackManager callbackManager;

    ShareDialog shareDialog;
    LoginButton login;
    ProfilePictureView profile;
    Dialog details_dialog;
    TextView details_txt;
    TextView nombre;
    TextView correo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);

        callbackManager = CallbackManager.Factory.create();
        login = (LoginButton)findViewById(R.id.login_button);
        profile = (ProfilePictureView)findViewById(R.id.picture);
        shareDialog = new ShareDialog(this);
        nombre = (TextView)findViewById(R.id.txtNombre);
        correo = (TextView)findViewById(R.id.txtCorreo);
        login.setReadPermissions("public_profile email");
        details_dialog = new Dialog(this);
        details_dialog.setContentView(R.layout.dialog_details);
        details_dialog.setTitle("Detalles");
        details_txt = (TextView)details_dialog.findViewById(R.id.details);
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        Log.i("SQLite", "===================================================");
        Log.i("SQLite", "Inicio de aplicación SQLite");
        SQLite sqlite = new SQLite(getApplicationContext());
        sqlite.abrir();

        Log.i("SQLite", "Se imprime registros de tabla");
        Cursor cursor = sqlite.getGuid();//Se obtiene registros
        String lista = sqlite.imprimirListaguid(cursor);
        Log.i("SQLite", "Registros: \r\n" + lista);
        Log.i("SQLite", "Se eliminan registros ");
        sqlite.eliminarGuid();

        // sqlite.cerrar();
        Log.i("SQLite", "fin :)  ");
        Log.i("SQLite", "===================================================");
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //////////////////////////////////////////////////////////
        if(AccessToken.getCurrentAccessToken() != null){
            RequestData();

        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AccessToken.getCurrentAccessToken() != null) {
                    profile.setProfileId(null);
                }
            }
        });

        login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                if(AccessToken.getCurrentAccessToken() != null){
                    RequestData();

                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException exception) {
            }
        });

    }
    public void RequestData(){
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object,GraphResponse response) {

                JSONObject json = response.getJSONObject();
                try {
                    if(json != null){
                        String text = "<b>Nombre :</b> "+json.getString("name")+"<br><br><b>Email :</b> "+json.getString("email")+"<br><br><b>Link de perfil :</b> "+json.getString("link");
                        details_txt.setText(Html.fromHtml(text));
                        profile.setProfileId(json.getString("id"));
                        nombre.setText(json.getString("name"));
                        correo.setText(json.getString("email"));
                        (new LoginFacebookServerHelper()).execute(CencelUtils.buildUrlRequest(FacebookRegis.this, "loginMiCencel"));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }



    ////// verificacion de usuario
    private class LoginFacebookServerHelper extends AsyncTask<String, Void, AsociadoSimple> {
        private final ProgressDialog dialog = new ProgressDialog(FacebookRegis.this);

        @Override
        protected void onPostExecute(AsociadoSimple resultado) {
            super.onPostExecute(resultado);
            dialog.dismiss();
            try {
                if (resultado.getResponseCode() == 1){
                    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                    Log.i("SQLite", "===================================================");
                    Log.i("SQLite", "Inicio de aplicación SQLite");
                    SQLite sqlite = new SQLite(getApplicationContext());
                    sqlite.abrir();

                    Log.i("SQLite", "Se imprime registros de tabla");
                    Cursor cursor = sqlite.getGuid();//Se obtiene registros
                    String lista = sqlite.imprimirListaguid(cursor);
                    Log.i("SQLite", "Registros: \r\n" + lista);


                    // sqlite.cerrar();
                    Log.i("SQLite", "fin :)  ");
                    Log.i("SQLite", "===================================================");
                    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                    //////////////////////////////////////////////////////////



                    // cambiar pantalla
                    //se pasa a la actividad despues de que el usuario es correcto
                    Intent intent = new Intent(getApplicationContext(), InicioUsuario.class);
                    // antes de lanzar la actividad, se debe configurar el objeto destino
                    intent.putExtra("vane", resultado);
                    startActivity(intent);


                }
                else{

                    // algo paso, regresar al menu
                    Toast toast2 = Toast.makeText(getApplicationContext(), "Listo", Toast.LENGTH_SHORT);
                    toast2.show();
                    (new RegisUsuarioServerHelper()).execute(CencelUtils.buildUrlRequest(FacebookRegis.this, "generarNuevoAsociado"));


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

                datosAsociadoObj.put("param", correo.getText());
                datosAsociadoObj.put("Contrasena", correo.getText());
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
    ////// verificacion de usuario

    //////Registro Usuario de Facebook
    private class RegisUsuarioServerHelper extends AsyncTask<String, Void, String> {
        private final ProgressDialog dialog = new ProgressDialog(FacebookRegis.this);

        @Override
        protected void onPostExecute(String message) {
            super.onPostExecute(message);
            dialog.dismiss();




            // Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            Toast toastR = Toast.makeText(getApplicationContext(), "Se ha registrado el usuario", Toast.LENGTH_SHORT);
            toastR.show();
            Intent intent = new Intent(getApplicationContext(), InicioUsuario.class);
            intent.putExtra("vane", message);
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

                datosAsociadoObj.put("NombreCompleto", nombre.getText());
                datosAsociadoObj.put("Contrasena", correo.getText());
                datosAsociadoObj.put("Telefono", 1111);
                datosAsociadoObj.put("Correo", correo.getText());
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

                String urlStr = getApplicationContext().getString(R.string.urlWSCencel)+"actualizaEstatusConexion";
                url = new URL(urlStr);

                HttpURLConnection cnn2 = (HttpURLConnection) url.openConnection();
                cnn2.setRequestMethod("POST");
                cnn2.setDoOutput(true);
                cnn2.setRequestProperty("Content-Type", "application/json");

                // cuerpo de la peticion
                JSONObject datosAsociadoEstObj = new JSONObject();




                datosAsociadoEstObj.put("guid", result);


                //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                Log.i("SQLite", "===================================================");
                Log.i("SQLite", "Inicio de aplicación SQLite");
                SQLite sqlite = new SQLite(getApplicationContext());
                sqlite.abrir();

                Log.i("SQLite", "Se eliminan registros ");
                sqlite.eliminarGuid();

                Log.i("SQLite", "Se inserto 1 registro");
                sqlite.insertarGuid(result);

                Log.i("SQLite", "Se imprime registros de tabla");
                Cursor cursor = sqlite.getGuid();//Se obtiene registros
                String lista = sqlite.imprimirListaguid(cursor);
                Log.i("SQLite", "Registros: \r\n" + lista);


                // sqlite.cerrar();
                Log.i("SQLite", "fin :)  ");
                Log.i("SQLite", "===================================================");
                //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                //////////////////////////////////////////////////////////



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


            } catch (Throwable t) {
                t.printStackTrace();
                return null;
            }
            return result;
        }
    }
    //////Registro Usuario de Facebook



    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            Intent intent = new Intent(getApplicationContext(), MenuMainActivity.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }

}
package mx.com.cencel.comercial.cencel.activities.micencel;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.widget.ShareDialog;

import com.squareup.picasso.Picasso;
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
import mx.com.cencel.comercial.cencel.pojo.ResultadoThread;
import mx.com.cencel.comercial.cencel.util.CencelUtils;
import mx.com.cencel.comercial.cencel.util.SQLite;


public class InicioUsuario extends Activity {

    public TextView asociado;
    public TextView puntos;
    public TextView cenceles;
    public ImageView imagenpun;
    public TextView guid;
    public TextView puntos2;
    ShareDialog shareDialog;
    ProfilePictureView profile;


    private static AsociadoSimple AsociadoRegis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.menu_micencel);

        asociado = (TextView)findViewById(R.id.asociado);
        puntos = (TextView)findViewById(R.id.puntos);
        puntos2 = (TextView)findViewById(R.id.numeroes);
        cenceles = (TextView)findViewById(R.id.saldo);
        imagenpun= (ImageView)findViewById(R.id.esfera);
        guid =(TextView)findViewById(R.id.guid);
        profile = (ProfilePictureView)findViewById(R.id.picture);
        shareDialog = new ShareDialog(this);


//        Bundle extras = getIntent().getExtras();
//        if(extras != null){
//            AsociadoRegis = (AsociadoSimple) extras.get("vane");
//        }


        (new PushToServerHelper()).execute(CencelUtils.buildUrlRequest(InicioUsuario.this, "vistaInfoAsociado"));


//        //extraemos el drawable en un bitmap
//        Drawable originalDrawable = getResources().getDrawable(R.drawable.usrnull2);
//        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();
//
//
//        //creamos el drawable redondeado
//        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);
//        //asignamos el CornerRadius
//        roundedDrawable.setCornerRadius(originalBitmap.getHeight());
//        ImageView imageView = (ImageView) findViewById(R.id.picture);
//        imageView.setImageDrawable(roundedDrawable);

//
//        Profile profileDefault = Profile.getCurrentProfile();
//        //Librería usada para poder mostrar la foto de perfil de facebook con una transformación circular
//        Picasso.with(InicioUsuario.this).load(profileDefault.getProfilePictureUri(100,100)).transform(new CircleTransform()).into(profile);


        if(AccessToken.getCurrentAccessToken() != null){
            RequestData();

        }



    }



    public void RequestData(){
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object,GraphResponse response) {

                JSONObject json = response.getJSONObject();
                try {
                    if(json != null){
                        String text = "<b>Nombre :</b> "+json.getString("name")+"<br><br><b>Email :</b> "+json.getString("email")+"<br><br><b>Link de perfil :</b> "+json.getString("link");
                        profile.setProfileId(json.getString("id"));

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

    }







    private class PushToServerHelper extends AsyncTask<String, Void, ResultadoThread> {
        private final ProgressDialog dialog = new ProgressDialog(InicioUsuario.this);

        @Override
        protected void onPostExecute(ResultadoThread result) {
            super.onPostExecute(result);

            // cargando resultado en la otra vista
            puntos.setText(result.puntos.toString());
            cenceles.setText(result.cenceles.toString());
            asociado.setText(result.saludo);
            guid.setText(result.guid);
            // validar puntos para mostrar imagen
            imagenpun.setBackgroundResource(R.drawable.esfera2);
            puntos2.setText(result.puntos.toString());

//            switch (result.puntos2)
//            {
//                case 0:
//                   imagenpun.setBackgroundResource(R.drawable.esfera);
//                    break;
//                case 1:
//                    imagenpun.setBackgroundResource(R.drawable.e1);
//                    break;
//                case 2:
//                    imagenpun.setBackgroundResource(R.drawable.e2);
//                    break;
//                case 3:
//                    imagenpun.setBackgroundResource(R.drawable.e3);
//                    break;
//                case 4:
//                    imagenpun.setBackgroundResource(R.drawable.e4);
//                    break;
//                case 5:
//                    imagenpun.setBackgroundResource(R.drawable.e5);
//                    break;
//                case 6:
//                    imagenpun.setBackgroundResource(R.drawable.e6);
//                    break;
//                case 7:
//                    imagenpun.setBackgroundResource(R.drawable.e7);
//                    break;
//                case 8:
//                    imagenpun.setBackgroundResource(R.drawable.e8);
//                    break;
//                case 9:
//                    imagenpun.setBackgroundResource(R.drawable.e9);
//                    break;
//                case 10:
//                    imagenpun.setBackgroundResource(R.drawable.e10);
//                    break;
//                case 11:
//                    imagenpun.setBackgroundResource(R.drawable.e11);
//                    break;
//                case 12:
//                    imagenpun.setBackgroundResource(R.drawable.e12);
//                    break;
//                case 13:
//                    imagenpun.setBackgroundResource(R.drawable.e13);
//                    break;
//                case 14:
//                    imagenpun.setBackgroundResource(R.drawable.e14);
//                    break;
//                case 15:
//                    imagenpun.setBackgroundResource(R.drawable.e15);
//                    break;
//
//
//            }


            dialog.dismiss();
            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage(getString(R.string.dialog_message));
            dialog.show();
        }

        @Override
        protected ResultadoThread doInBackground(String... params) {
            ResultadoThread result = new ResultadoThread();

            try{
                // recibiendo la url
                URL url = new URL(params[0]);

                HttpURLConnection cnn = (HttpURLConnection) url.openConnection();
                cnn.setRequestMethod("POST");
                cnn.setDoOutput(true);
                cnn.setRequestProperty("Content-Type", "application/json");



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
                // cuerpo de la peticion
                JSONObject jsonOut = new JSONObject();
                jsonOut.put("guid", lista);

               // jsonOut.put("guid", InicioUsuario.AsociadoRegis.getGuid());
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
                JSONObject asociadoInfoJson = jsonObject.getJSONObject("d");

                // asociado.setText(saludo);
                String saludo = String.format("Hola %s", asociadoInfoJson.getString("Asociado"));
                String puntosAsociado = String.format("%d", asociadoInfoJson.getInt("Puntos"));
                String saldoAsociado = String.format("%d", asociadoInfoJson.getInt("Cenceles"));
                String guidAsociado = String.format("Tu codigo de usuario es: %s", asociadoInfoJson.getString("GUID"));

              //  Integer puntosAsociado = asociadoInfoJson.getInt("Puntos");
                //Double saldoAsociado = asociadoInfoJson.getDouble("Saldo");




                result = new ResultadoThread(saludo, puntosAsociado, saldoAsociado, asociadoInfoJson.getInt("Puntos"),guidAsociado);


                //return result;
            }
            catch (Throwable t){
                t.printStackTrace();
            }

            return result;
        }
    }













    public void CerrarSesion(View view) {
        (new CerrarServerHelper()).execute(CencelUtils.buildUrlRequest(InicioUsuario.this, "actualizaEstatusCierraConexion"));
        //Cierra la sesion inciada en la aplicación

        Intent intent = new Intent(this, MenuMainActivity.class);
        LoginManager.getInstance().logOut();
        startActivity(intent);
    }

        class CerrarServerHelper extends AsyncTask<String, Void, ResultadoThread> {
            private final ProgressDialog dialog = new ProgressDialog(InicioUsuario.this);

            @Override
            protected void onPostExecute(ResultadoThread result) {
                super.onPostExecute(result);

                dialog.dismiss();
                //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog.setMessage(getString(R.string.dialog_message));
                dialog.show();
            }

            @Override
            protected ResultadoThread doInBackground(String... params) {
                ResultadoThread result = new ResultadoThread();

                try{
                    // recibiendo la url
                    URL url = new URL(params[0]);

                    HttpURLConnection cnn = (HttpURLConnection) url.openConnection();
                    cnn.setRequestMethod("POST");
                    cnn.setDoOutput(true);
                    cnn.setRequestProperty("Content-Type", "application/json");
                    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                    Log.i("SQLite", "===================================================");
                    Log.i("SQLite", "Inicio de aplicación SQLite");
                    SQLite sqlite = new SQLite(getApplicationContext());
                    sqlite.abrir();
                    Log.i("SQLite", "Se imprime registros de tabla");
                    Cursor cursor = sqlite.getGuid();//Se obtiene registros
                    String lista = sqlite.imprimirListaguid(cursor);
                    Log.i("SQLite", "Registros: \r\n" + lista);
                    Log.i("SQLite", "fin :)  ");
                    Log.i("SQLite", "===================================================");
                    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                    //////////////////////////////////////////////////////////

                    // cuerpo de la peticion
                    JSONObject jsonOut = new JSONObject();
                    jsonOut.put("guid", lista);
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
                    JSONObject asociadoInfoJson = jsonObject.getJSONObject("d");
                    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                    Log.i("SQLite", "Se eliminan registros ");
                    sqlite.eliminarGuid();
                    sqlite.cerrar();
                    Log.i("SQLite", "===================================================");
                    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

                }
                catch (Throwable t){
                    t.printStackTrace();
                }

                return result;
            }
        }










    public void ActializaInfo(View view) {
        Intent intent = new Intent(this, ActuInfo.class);
        startActivity(intent);

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == event.KEYCODE_BACK) {
            Intent intent = new Intent(getApplicationContext(), MenuMainActivity.class);
            startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}
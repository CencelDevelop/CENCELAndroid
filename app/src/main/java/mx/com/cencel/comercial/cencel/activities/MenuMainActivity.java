package mx.com.cencel.comercial.cencel.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.VideoView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import mx.com.cencel.comercial.cencel.R;
import mx.com.cencel.comercial.cencel.activities.constramite.Qrlector;
import mx.com.cencel.comercial.cencel.activities.empresas.EmpresaActivity;
import mx.com.cencel.comercial.cencel.activities.micencel.InicioUsuario;
import mx.com.cencel.comercial.cencel.activities.micencel.MiCencel;
import mx.com.cencel.comercial.cencel.activities.promos.PromocionesActivity;
import mx.com.cencel.comercial.cencel.activities.stores.StoresListActivity;
import mx.com.cencel.comercial.cencel.activities.contacto.ContactoActivity;
import mx.com.cencel.comercial.cencel.menuList.MenuListAdapter;
import mx.com.cencel.comercial.cencel.pojo.AsociadoSimple;
import mx.com.cencel.comercial.cencel.util.CencelUtils;
import mx.com.cencel.comercial.cencel.util.SQLite;


public class MenuMainActivity extends Activity {

    ListView list;

    String[] menuItemNames = {
            "Promociones",
            "Sucursales",
            "Consulta Tu Trámite",
            "Contacto",
            "Empresas",
            "Visítanos",
            "Recompensas"
    };

    String[] menuItemsDescriptions ={
            "Checa las promociones que tenemos",
            "Ubica tu sucursal más cercana",
            "Consulta el estatus de tu trámite",
            "Envíanos tus datos",
            "Revisa las ofertas empresariales",
            "Visita nuestro sitio web",
            "Obten recompensas"
    };

    int[] menuImages = {
            R.drawable.menupromociones,
            R.drawable.menusucursales,
            R.drawable.menuconsultatramite,
            R.drawable.menucontacto,
            R.drawable.menuempresas,
            R.drawable.web,
            R.drawable.menupromociones
    };

    // se ejecuta siempre al generar una nueva actividad
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // generando la vista
        setContentView(R.layout.menu_main);

        VideoView videoView = (VideoView) findViewById(R.id.surface_view);
        Uri path = Uri.parse("android.resource://mx.com.cencel.comercial.cencel/" + R.raw.cencel);

        videoView.setVideoURI(path);

        videoView.start();



        // rellenando
        MenuListAdapter adapter = new MenuListAdapter(this, menuItemNames, menuImages, menuItemsDescriptions);
        list = (ListView) findViewById(R.id.menu_list);
        list.setAdapter(adapter);

        // manejando el evento de tap o click
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // String selectedItemText= menuItemNames[+position];
                // Toast.makeText(getApplicationContext(), selectedItemText, Toast.LENGTH_SHORT).show();

                // pantallas consecuentes
                Intent nextActivity = new Intent();
                switch (position) {
                    case 0:
                        //Promociones
                        nextActivity = new Intent(getApplicationContext(), PromocionesActivity.class);
                        startActivity(nextActivity);
                        break;
                    case 1:
                        // Sucursales
                        nextActivity = new Intent(getApplicationContext(), StoresListActivity.class);
                        startActivity(nextActivity);
                        break;

                    case 2:
                        //Consulta Tramite
                        nextActivity = new Intent(getApplicationContext(), Qrlector.class);
                        startActivity(nextActivity);
                        break;

                    case 3:
                        //Contacto
                        nextActivity = new Intent(getApplicationContext(), ContactoActivity.class);
                        startActivity(nextActivity);

                        break;

                    case 4:
                        //Empresas
                        nextActivity = new Intent(getApplicationContext(), EmpresaActivity.class);
                        startActivity(nextActivity);
                        break;

                    case 5:
                        // Visitanos
                        nextActivity = new Intent(getApplicationContext(), CencelSiteWebActivity.class);
                        startActivity(nextActivity);
                        break;

                    case 6:

                        //dialgo de cargando
                        ProgressDialog dialog = new ProgressDialog(MenuMainActivity.this);
                        dialog.dismiss();
                        // este es el hilo que comprueba sesion iniciada

                        //Hilo de AsyncTask
                        class CompruebaSesionHelper extends AsyncTask<String, Void, AsociadoSimple>{
                            private final ProgressDialog dialog = new ProgressDialog(MenuMainActivity.this);

                            @Override
                            protected void onPostExecute(AsociadoSimple result) {
                                // if-else para saber el status y que hacer
                                //si la conexion es 0 borrar el registro del guid y cargar login
                                //si la conexion es igual a 1 pasa a la de asociado

                                SQLite sqlite = new SQLite(getApplicationContext());
                                sqlite.abrir();


                                if (sqlite == null ){
                                    Intent intent = new Intent(getApplicationContext(), MiCencel.class);
                                    startActivity(intent);
                                }

                                else {
                                    if (result.getResponseCode() == 1) {

                                        Toast toast3 = Toast.makeText(getApplicationContext(), "Sesion iniciada", Toast.LENGTH_SHORT);
                                        toast3.show();
                                        //se pasa a la actividad despues de que el usuario es correcto
                                        Intent intent = new Intent(getApplicationContext(), InicioUsuario.class);
                                        startActivity(intent);

                                    } else {


//                                        Log.i("SQLite", "Se eliminan registros ");
//                                        sqlite.eliminarGuid();
//                                        sqlite.cerrar();
                                        Toast toast2 = Toast.makeText(getApplicationContext(), "Sesion  no iniciada", Toast.LENGTH_SHORT);
                                        toast2.show();
                                        Intent intent = new Intent(getApplicationContext(), MiCencel.class);
                                        startActivity(intent);
                                    }

                                }
                            }

                            @Override
                            protected void onPreExecute() {
                                super.onPreExecute();
                                dialog.setMessage(getString(R.string.dialog_message));
                                dialog.show();
                            }

                            protected AsociadoSimple doInBackground(String... params) {

                                AsociadoSimple result = new AsociadoSimple("",0);

                                try{
                                    URL url = new URL(params[0]);

                                    HttpURLConnection cnn = (HttpURLConnection) url.openConnection();
                                    cnn.setRequestMethod("POST");
                                    cnn.setDoOutput(true);
                                    cnn.setRequestProperty("Content-Type", "application/json");

                                    //se obtiene el guid de la base de datos local///////////////////////////////////////////////////
                                    SQLite sqlite = new SQLite(getApplicationContext());
                                    sqlite.abrir();
                                    Log.i("SQLite", "Se imprime registros de tabla");
                                    Cursor cursor = sqlite.getGuid();//Se obtiene registros
                                    String lista = sqlite.imprimirListaguid(cursor);
                                    Log.i("SQLite", "Registros:\r\n" + lista);
                                    //sqlite.cerrar();
                                    ////////////////////////////////////////////////////////////////////////////////////////////////

                                    // cuerpo de la peticion
                                    JSONObject guidEnvio = new JSONObject();
                                    guidEnvio.put("guid",lista);
                                    OutputStream output = cnn.getOutputStream();

                                    output.write(guidEnvio.toString().getBytes("UTF-8"));
                                    cnn.connect();

                                    //Validar si regreso algo o no


                                    InputStream stream = cnn.getInputStream();
                                    byte[] b = new byte[1024];
                                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                    while (stream.read(b) != -1)
                                        baos.write(b);
                                    String responseJson = new String(baos.toByteArray());

                                    JSONObject jsonObject = new JSONObject(responseJson);

                                    // lo que viene dentro de "d" es el estatus de la conexion
                                    result.setResponseCode(jsonObject.getInt("d"));

                                }catch(Exception e){
                                    e.printStackTrace();
                                    result.setResponseCode(-1);
                                }
                                return result;
                            }
                        }





                        //checar tabla de sqlite para saber si contiene el GUID
                        //si tabla vacia mandar a login
                        // de lo contrario se obtiene el guid de la tabla y se manada al servidor para comprobar la conexion


                        (new CompruebaSesionHelper()).execute(CencelUtils.buildUrlRequest(MenuMainActivity.this, "obtenerEstatusConexion"));


                        // Recompensas
                      //  nextActivity = new Intent(getApplicationContext(), MiCencel.class);





                        break;


                }



            }
        });


    }

    @Override
    public void onResume(){
        super.onResume();

        VideoView videoView = (VideoView) findViewById(R.id.surface_view);
        Uri path = Uri.parse("android.resource://mx.com.cencel.comercial.cencel/" + R.raw.cencel);

        videoView.setVideoURI(path);

        videoView.start();


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

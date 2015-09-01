package mx.com.cencel.comercial.cencel.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.VideoView;

import mx.com.cencel.comercial.cencel.R;
import mx.com.cencel.comercial.cencel.activities.constramite.Qrlector;
import mx.com.cencel.comercial.cencel.activities.empresas.EmpresaActivity;
import mx.com.cencel.comercial.cencel.activities.micencel.MiCencel;
import mx.com.cencel.comercial.cencel.activities.promos.PromocionesActivity;
import mx.com.cencel.comercial.cencel.activities.stores.StoresListActivity;
import mx.com.cencel.comercial.cencel.activities.contacto.ContactoActivity;
import mx.com.cencel.comercial.cencel.menuList.MenuListAdapter;


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

                        break;
                    case 1:
                        // Sucursales
                        nextActivity = new Intent(getApplicationContext(), StoresListActivity.class);

                        break;

                    case 2:
                        //Consulta Tramite
                        nextActivity = new Intent(getApplicationContext(), Qrlector.class);

                        break;

                    case 3:
                        //Contacto
                        nextActivity = new Intent(getApplicationContext(), ContactoActivity.class);


                        break;

                    case 4:
                        //Empresas
                        nextActivity = new Intent(getApplicationContext(), EmpresaActivity.class);

                        break;

                    case 5:
                        // Visitanos
                        nextActivity = new Intent(getApplicationContext(), CencelSiteWebActivity.class);

                        break;

                    case 6:
                        // Recompensas
                        nextActivity = new Intent(getApplicationContext(), MiCencel.class);

                        break;


                }

                startActivity(nextActivity);

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



}

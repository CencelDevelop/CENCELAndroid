package mx.com.cencel.comercial.cencel.activities.empresas;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import mx.com.cencel.comercial.cencel.R;


import mx.com.cencel.comercial.cencel.activities.CencelSiteWebActivity;
import mx.com.cencel.comercial.cencel.activities.MenuMainActivity;
import mx.com.cencel.comercial.cencel.menuList.MenuListAdapter;
import mx.com.cencel.comercial.cencel.util.CencelUtils;

/**
 * Created by vcid on 03/08/15.
 */
public class EmpresaActivity extends Activity {
    public Intent callIntent;
    ListView list;

    String[] menuItemNames = {

            "RED PRIVADA DE DATOS",
            "RECUERDA SMS EMPRESAS",
            "MENSAJERÍA EMPRESARIAL",
            "E-MAIL CORTO EN TU TELCEL",
            "PUSH TELCEL",
            "LOCALIZACIÓN VEHICULAR TELCEL",
            "LOCALIZACIÓN EMPRESARIAL TELCEL",
            "M2M"
    };

    String[] menuItemsDescriptions ={
            "conexiones privadas de datos",
            "canal sencillo de comunicación personalizada",
            "comunicación instantánea",
            "recibir alertas (SMS)",
            "comunicacion con otros usuarios PUSH ",
            "permite monitorear vehículos particulares",
            "Podras conocer la ubicación de tus empleados,",
            "conexion entre si, en la empresa"
    };

    int[] menuImages = {
            R.drawable.redpridatos,
            R.drawable.recuersmsemp,
            R.drawable.mensemp,
            R.drawable.emailcort,
            R.drawable.pushtelcel,
            R.drawable.locavehitel,
            R.drawable.locaemprtel,
            R.drawable.m2m_02
    };

    // se ejecuta siempre al generar una nueva actividad
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // generando la vista
        setContentView(R.layout.empresa);

        // rellenando
        MenuListAdapter adapter = new MenuListAdapter(this, menuItemNames, menuImages, menuItemsDescriptions);
        list = (ListView) findViewById(R.id.menu_emp);
        list.setAdapter(adapter);

        // manejando el evento de tap o click
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                // pantallas consecuentes
                Intent nextActivity = new Intent();
                switch (position) {
                    case 0:
                        // "Red Privada de Datos"
                        setContentView(R.layout.red_privada);
                        break;
                    case 1:
                        // "Recuerda SMS Empresa"
                        setContentView(R.layout.recuerda_sms);

                        break;
                    case 2:
                        //     "Mensajeria Empresarial",
                        setContentView(R.layout.mensajeria_emp);
                        break;
                    case 3:
                        //"Email Corto",
                        setContentView(R.layout.email_corto);
                        break;

                    case 4:
                        // "Push Telcel",
                        setContentView(R.layout.push_telcel);
                        break;

                    case 5:
                        //       "Localizacion Vehicular",
                        setContentView(R.layout.localizacion_veh);
                        break;
                    case 6:

                        // "Localizacion Empresarial",
                        setContentView(R.layout.localizacion_emp);

                        break;
                    case 7:
                        //   "M2M",
                        setContentView(R.layout.m2m);
                        break;

                }


                // startActivity(nextActivity);
            }



        });





    }

    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            Intent intent = new Intent(EmpresaActivity.this, EmpresaActivity.class);
            startActivity(intent);
            finish();
        }


        return super.onKeyDown(keyCode, event);

    }

    public void menu(View view) {
        new Intent(getApplicationContext(), MenuMainActivity.class);
        finish();

    }



    public void llamar(View view){
        try {
            callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:018000998000"));
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

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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import mx.com.cencel.comercial.cencel.R;


import mx.com.cencel.comercial.cencel.activities.CencelSiteWebActivity;
import mx.com.cencel.comercial.cencel.activities.MenuMainActivity;
import mx.com.cencel.comercial.cencel.menuList.MenuListAdapter;

/**
 * Created by vcid on 03/08/15.
 */
public class EmpresaActivity extends Activity {
    public Intent callIntent;
    ListView list;

    String[] menuItemNames = {
            "Email Corto",
            "Localizacion Empresarial",
            "Localizacion Vehicular",
            "M2M",
            "Mensajeria Empresarial",
            "Push Telcel",
            "Recuerda SMS Empresa"
    };

    String[] menuItemsDescriptions ={
            "E-mail corto en tu telcel",
            "Conoce la ubicacion de tus empleados",
            "Monitorear vehiculos particulareso utilitarios",
            "Conexion entre dispositivos de tu empresa",
            "Comunicacion real entre tus empleados",
            "Comunicacion entre usuarios PUSH Telcel",
            "Comunicacion personalizada entre operadores"
    };

    int[] menuImages = {
            R.drawable.emailcorto,
            R.drawable.localizacionempresarial,
            R.drawable.localizacionvehicular,
            R.drawable.m2m,
            R.drawable.mensajeriaempresarial,
            R.drawable.pushtelcel,
            R.drawable.recuerdasms
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
                // String selectedItemText= menuItemNames[+position];
                // Toast.makeText(getApplicationContext(), selectedItemText, Toast.LENGTH_SHORT).show();

            /*    ViewGroup rootView=(ViewGroup)findViewById(android.R.id.content);
                RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.root_layout);
                rootView.removeView(rLayout);*/

                // pantallas consecuentes
                Intent nextActivity = new Intent();
                switch (position) {
                    case 0:
                        //"Email Corto",
                        setContentView(R.layout.email_corto);

                        break;
                    case 1:
                        // "Localizacion Empresarial",
                        setContentView(R.layout.localizacion_emp);

                        break;

                    case 2:

                        //       "Localizacion Vehicular",
                        setContentView(R.layout.localizacion_veh);
                        break;

                    case 3:
                        //   "M2M",
                        setContentView(R.layout.m2m);
                        break;

                    case 4:
                        //     "Mensajeria Empresarial",
                        setContentView(R.layout.mensajeria_emp);
                        break;

                    case 5:
                        // "Push Telcel",
                        setContentView(R.layout.push_telcel);
                        break;
                    case 6:
                        // "Recuerda SMS Empresa"
                        setContentView(R.layout.recuerda_sms);
                        break;

                }


                // startActivity(nextActivity);
            }



        });





    }

    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {

            Intent intent = new Intent(EmpresaActivity.this, EmpresaActivity.class);
            finish();
            startActivity(intent);

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

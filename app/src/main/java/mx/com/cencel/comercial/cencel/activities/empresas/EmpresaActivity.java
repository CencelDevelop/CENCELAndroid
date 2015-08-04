package mx.com.cencel.comercial.cencel.activities.empresas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import mx.com.cencel.comercial.cencel.R;

import mx.com.cencel.comercial.cencel.activities.CencelSiteWebActivity;
import mx.com.cencel.comercial.cencel.activities.contacto.ContactoActivity;
import mx.com.cencel.comercial.cencel.activities.empresas.menuempre.Email_Corto_Act;
import mx.com.cencel.comercial.cencel.activities.empresas.menuempre.Localizacion_Emp_Act;
import mx.com.cencel.comercial.cencel.activities.empresas.menuempre.Localizacion_Veh_Act;
import mx.com.cencel.comercial.cencel.activities.empresas.menuempre.M2m_Act;
import mx.com.cencel.comercial.cencel.activities.empresas.menuempre.Mensajeria_Emp_Act;
import mx.com.cencel.comercial.cencel.activities.empresas.menuempre.Push_Telcel;
import mx.com.cencel.comercial.cencel.activities.empresas.menuempre.Recuerda_Sms_Act;
import mx.com.cencel.comercial.cencel.activities.stores.StoresListActivity;
import mx.com.cencel.comercial.cencel.menuList.MenuListAdapter;

/**
 * Created by vcid on 03/08/15.
 */
public class EmpresaActivity extends Activity {

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

                // pantallas consecuentes
                Intent nextActivity = new Intent();
                switch (position){
                    case 0:
                        //"Email Corto",
                        nextActivity = new Intent(getApplicationContext(), Email_Corto_Act.class);

                        break;
                    case 1:
                        // "Localizacion Empresarial",
                        nextActivity = new Intent(getApplicationContext(), Localizacion_Emp_Act.class);
                        break;

                    case 2:

                        //       "Localizacion Vehicular",
                        nextActivity = new Intent(getApplicationContext(), Localizacion_Veh_Act.class);
                        break;

                    case 3:
                        //   "M2M",
                        nextActivity = new Intent(getApplicationContext(), M2m_Act.class);

                        break;

                    case 4:
                        //     "Mensajeria Empresarial",
                        nextActivity = new Intent(getApplicationContext(), Mensajeria_Emp_Act.class);
                        break;

                    case 5:
                        // "Push Telcel",
                        nextActivity = new Intent(getApplicationContext(), Push_Telcel.class);
                        break;
                    case 6:
                        // "Recuerda SMS Empresa"
                        nextActivity = new Intent(getApplicationContext(), Recuerda_Sms_Act.class);
                        break;

                }

                startActivity(nextActivity);
            }
        });
    }
}

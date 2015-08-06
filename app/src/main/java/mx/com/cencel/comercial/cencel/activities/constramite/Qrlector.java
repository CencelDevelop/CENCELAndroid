package mx.com.cencel.comercial.cencel.activities.constramite;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import mx.com.cencel.comercial.cencel.R;
import mx.com.cencel.comercial.cencel.activities.constramite.IntentIntegrator;
import mx.com.cencel.comercial.cencel.activities.constramite.IntentResult;


public class Qrlector extends ActionBarActivity implements View.OnClickListener {

    private Button scanBtn;
    private TextView formatTxt, contentTxt;

    @Override
    protected void onCreate (Bundle savedInateceState)

    {
        super.onCreate(savedInateceState);
        setContentView(R.layout.qr);

        VideoView videoView = (VideoView) findViewById(R.id.surface_view);
       Uri path = Uri.parse("android.resource://mx.com.cencel.comercial.cencel/" + R.raw.cencel_cam);


        videoView.setVideoURI(path);

        videoView.start();

        //Se Instancia el botón de Scan
        scanBtn = (Button)findViewById(R.id.scan_button);
        //Se Instancia el Campo de Texto para el nombre del formato de código de barra
        formatTxt = (TextView)findViewById(R.id.scan_format);
        //Se Instancia el Campo de Texto para el contenido  del código de barra
        contentTxt = (TextView)findViewById(R.id.scan_content);
        //Se agrega la clase MainActivity.java como Listener del evento click del botón de Scan
        scanBtn.setOnClickListener(this);


    }
    @Override
    public void onClick(View view) {
        //Se responde al evento click
        if(view.getId()==R.id.scan_button){
            //Se instancia un objeto de la clase IntentIntegrator
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            //Se procede con el proceso de scaneo
            scanIntegrator.initiateScan();
        }
    }




    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        //Se obtiene el resultado del proceso de scaneo y se parsea
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            //Quiere decir que se obtuvo resultado pro lo tanto:
            //Desplegamos en pantalla el contenido del código de barra scaneado
            String scanContent = scanningResult.getContents();
            contentTxt.setText("Contenido: " + scanContent);
            //Desplegamos en pantalla el nombre del formato del código de barra scaneado
            String scanFormat = scanningResult.getFormatName();
            formatTxt.setText("Formato: " + scanFormat);
        }else{
            //Quiere decir que NO se obtuvo resultado
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No se ha recibido datos del scaneo!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


}

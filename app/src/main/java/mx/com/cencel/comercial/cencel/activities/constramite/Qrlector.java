package mx.com.cencel.comercial.cencel.activities.constramite;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import mx.com.cencel.comercial.cencel.R;
import mx.com.cencel.comercial.cencel.activities.constramite.IntentIntegrator;
import mx.com.cencel.comercial.cencel.activities.constramite.IntentResult;
import mx.com.cencel.comercial.cencel.pojo.ResultadoTramite;
import mx.com.cencel.comercial.cencel.util.CencelUtils;
import mx.com.cencel.comercial.cencel.util.CustomDialog;


public class Qrlector extends ActionBarActivity implements View.OnClickListener {

    private Button scanBtn;
    private TextView formatTxt, contentTxt;
    public static String folioData = "";

    @Override
    protected void onCreate(Bundle savedInateceState)

    {
        super.onCreate(savedInateceState);
        setContentView(R.layout.qr);

        VideoView videoView = (VideoView) findViewById(R.id.surface_view);
        Uri path = Uri.parse("android.resource://mx.com.cencel.comercial.cencel/" + R.raw.cencel_cam);

        videoView.setVideoURI(path);

        videoView.start();

        //Se Instancia el botón de Scan
        scanBtn = (Button) findViewById(R.id.scan_button);
        //Se Instancia el Campo de Texto para el nombre del formato de código de barra
        formatTxt = (TextView) findViewById(R.id.scan_format);
        //Se Instancia el Campo de Texto para el contenido  del código de barra
        contentTxt = (TextView) findViewById(R.id.scan_content);
        //Se agrega la clase MainActivity.java como Listener del evento click del botón de Scan
        scanBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        //Se responde al evento click
        if (view.getId() == R.id.scan_button) {
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
            //Quiere decir que se obtuvo resultado por lo tanto:
            //Se trabaja con la peticion en el servidor
            this.folioData = scanningResult.getContents();

            if (CencelUtils.isNumeric(this.folioData)) {
                (new PushToServerHelper()).execute(CencelUtils.buildUrlRequest(Qrlector.this, getString(R.string.getEstatusDataMethod)));
            } else {
                Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.not_number_message), Toast.LENGTH_SHORT).show();
            }
        } else {
            //Quiere decir que NO se obtuvo resultado
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No se ha recibido datos del scaneo!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    // tarea asincrona
    private class PushToServerHelper extends AsyncTask<String, Void, ResultadoTramite> {
        private final ProgressDialog dialog = new ProgressDialog(Qrlector.this);

        @Override
        protected void onPostExecute(ResultadoTramite result) {
            super.onPostExecute(result);
            dialog.dismiss();
            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

            // tratamiento del objeto si existe
            if (result.getFolio() != -1) {
                int stringRes = 0;

                // if else if !!!
                if (result.getEstatusData().equalsIgnoreCase("COMPLETADO") && result.getEstatusTELCEL().equalsIgnoreCase("ACEPTADO")) {
                    stringRes = R.string.consulta_tramie_aceptado;
                } else if (result.getEstatusData().equalsIgnoreCase("COMPLETADO") && result.getEstatusTELCEL().equalsIgnoreCase("RECHAZADO")) {
                    stringRes = R.string.consulta_tramite_rechazado;
                } else if (result.getEstatusData().equalsIgnoreCase("COMPLETADO") && result.getEstatusTELCEL().equalsIgnoreCase("PENDIENTE")) {
                    stringRes = R.string.consulta_tramite_pendiente;
                } else if (result.getEstatusData().equalsIgnoreCase("PENDIENTE") && result.getEstatusTELCEL().equalsIgnoreCase("PENDIENTE")) {
                    stringRes = R.string.consulta_tramite_pendiente;
                } else if (result.getEstatusData().equalsIgnoreCase("EN PROCESO")) {
                    stringRes = R.string.consulta_tramite_proceso;
                } else if (result.getEstatusData().equalsIgnoreCase("EN PREVALIDACION")) {
                    stringRes = R.string.consulta_tramite_preval;
                } else if (result.getEstatusData().equalsIgnoreCase("ALMACEN")) {
                    stringRes = R.string.consulta_tramite_almacen;
                } else if (result.getEstatusData().equalsIgnoreCase("NO DISPONIBLE")) {
                    stringRes = R.string.consulta_tramite_no_disponible;
                } else if (result.getEstatusData().equalsIgnoreCase("TRANSITO LOCAL")) {
                    stringRes = R.string.consulta_tramite_transito;
                } else if (result.getEstatusData().equalsIgnoreCase("TRANSITO FORANEO")) {
                    stringRes = R.string.consulta_tramite_transito;
                }

                // Toast.makeText(getApplicationContext(), getString(stringRes), Toast.LENGTH_SHORT).show();
                Dialog resultDialog = (new CustomDialog()).getDialog(Qrlector.this, getString(R.string.app_name), getString(stringRes));
                resultDialog.show();
            } else {
                Toast.makeText(getApplicationContext(), R.string.error_tramite, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage(getString(R.string.dialog_message));
            dialog.show();
        }

        @Override
        protected ResultadoTramite doInBackground(String... params) {
            //String result = "";
            ResultadoTramite resultadoTramite = new ResultadoTramite();
            resultadoTramite.setFolio(-1);

            try {

                URL url = new URL(params[0]);

                HttpURLConnection cnn = (HttpURLConnection) url.openConnection();
                cnn.setRequestMethod("POST");
                cnn.setDoOutput(true);
                cnn.setRequestProperty("Content-Type", "application/json");

                // cuerpo de la peticion
                // {'folio':'123456'}
                JSONObject bodyJson = new JSONObject();
                bodyJson.put("folio", Qrlector.folioData);

                OutputStream output = cnn.getOutputStream();
                output.write(bodyJson.toString().getBytes("UTF-8"));

                // conectando
                cnn.connect();
                InputStream stream = cnn.getInputStream();
                byte[] b = new byte[1024];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                while (stream.read(b) != -1)
                    baos.write(b);
                String responseJson = new String(baos.toByteArray());

                JSONObject jsonObject = new JSONObject(responseJson);
                JSONObject jsonDataObj = jsonObject.getJSONObject("d");
                resultadoTramite = new ResultadoTramite();

                // solo lo necesario
                resultadoTramite.setEstatusData(jsonDataObj.getString("EstatusData"));
                resultadoTramite.setEstatusTELCEL(jsonDataObj.getString("EstatusTELCEL"));
                resultadoTramite.setFolio(jsonDataObj.getInt("folio"));
            } catch (Throwable t) {
                t.printStackTrace();
                return null;
            }

            return resultadoTramite;
        }
    }
}

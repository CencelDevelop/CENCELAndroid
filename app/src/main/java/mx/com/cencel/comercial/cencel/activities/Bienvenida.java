package mx.com.cencel.comercial.cencel.activities;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import mx.com.cencel.comercial.cencel.R;
import mx.com.cencel.comercial.cencel.activities.stores.StoresListActivity;

public class Bienvenida extends Activity {

    final int WELCOME		= 25;

    TextView linea_ayuda;
    ProgressBar mProgressBar;
    int progreso=0;
    int paso = 500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bienvenida);
        mProgressBar=(ProgressBar) findViewById(R.id.progressbar);
        linea_ayuda = (TextView) findViewById(R.id.linea_ayuda);
    }

    @Override
    protected void onResume() {
        super.onResume();

        linea_ayuda.setText(getString(R.string.updating_db));
        cuentaAtras(3000);


    }

    private void cuentaAtras(long milisegundos){
        CountDownTimer mCountDownTimer;

        mProgressBar.setMax((int)milisegundos);

        mProgressBar.setProgress(paso);

        mCountDownTimer=new CountDownTimer(milisegundos, paso) {

            @Override
            public void onTick(long millisUntilFinished) {
                Log.v("Log_tag", "Tick of Progress"+ progreso+ millisUntilFinished);
                progreso+=paso;
                mProgressBar.setProgress(progreso);

            }

            @Override
            public void onFinish() {

                //Toast.makeText(getApplicationContext(), getString(R.string.db_ok), Toast.LENGTH_LONG).show();
                progreso+=	paso;
                mProgressBar.setProgress(progreso);
                mProgressBar.setVisibility(View.INVISIBLE);
                Intent i = new Intent (".activities.PANTALLA_PRINCIPAL");
                startActivityForResult(i, WELCOME);
                new Intent(getApplicationContext(), MenuMainActivity.class);
            }
        };

        mCountDownTimer.start();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==WELCOME)
            // volvemos a la pantalla de bienvenida desde la pantalla principal,
            // la cerramos (no tiene sentido permanecer aquí):
            finish();
        else
            super.onActivityResult(requestCode, resultCode, data);
    }


}


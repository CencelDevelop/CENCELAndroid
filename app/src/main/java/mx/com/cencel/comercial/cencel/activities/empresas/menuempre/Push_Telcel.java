package mx.com.cencel.comercial.cencel.activities.empresas.menuempre;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import mx.com.cencel.comercial.cencel.R;

/**
 * Created by vcid on 04/08/15.
 */
public class Push_Telcel extends Activity {

    // se ejecuta siempre al generar una nueva actividad
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // generando la vista
        setContentView(R.layout.push_telcel);
        ImageView image = (ImageView) findViewById(R.id.psht);
        Bitmap bMap = BitmapFactory.decodeFile("/sdcard/pushtelcel_01.png");
        image.setImageBitmap(bMap);
    }
}
package mx.com.cencel.comercial.cencel.activities.promos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import mx.com.cencel.comercial.cencel.R;
import mx.com.cencel.comercial.cencel.pojo.PromoSlideFotos;
import mx.com.cencel.comercial.cencel.util.CencelUtils;
import mx.com.cencel.comercial.cencel.widget.CustomGallery;

/**
 * Created by iHouse on 06/08/15.
 */
public class PromocionesActivity extends Activity {

    private static final int SHOW_PERIOD = 6000;
    private CustomGallery customGallery;
    private List<PromoSlideFotos> promoSlideFotosList;
    private GetAdsAsyncTask getAdsAsyncTask;
    private Timer galleryTimer;

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.promos_slider);

        customGallery = (CustomGallery) findViewById(R.id.galleryBanners);
        customGallery.setCustomGalleryEventListener(new CustomGallery.CustomGalleryEventListener() {
            @Override
            public void onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                startTimer();
            }
        });

        executeGetAds();
    }

    private void startTimer() {
        stopTimer();
        galleryTimer = new Timer();
        galleryTimer.schedule(new GalleryTimerTask(), SHOW_PERIOD, SHOW_PERIOD);
    }

    private void stopTimer() {
        if (galleryTimer != null) {
            galleryTimer.cancel();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            cancelTasks();
            stopTimer();
        }
    }

    public View.OnTouchListener getTouchListener(final ImageView im, final int Orig, final int Sust) {
        return new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case (MotionEvent.ACTION_DOWN):
                        im.setImageDrawable(getResources().getDrawable(Sust));
                        break;
                    case (MotionEvent.ACTION_UP):
                        im.setImageDrawable(getResources().getDrawable(Orig));
                        break;
                    case (MotionEvent.ACTION_MOVE):
                        break;
                    case (MotionEvent.ACTION_CANCEL):
                        im.setImageDrawable(getResources().getDrawable(Orig));
                        break;
                }
                return false;
            }
        };
    }

    private void cancelTasks() {
        if (getAdsAsyncTask != null) {
            if (!getAdsAsyncTask.isCancelled()) {
                getAdsAsyncTask.cancel(true);
            }
        }
    }

    private void executeGetAds() {
        cancelTasks();
        getAdsAsyncTask = new GetAdsAsyncTask();
        getAdsAsyncTask.execute();
    }

    // tareas en segundo plano
    private class GetAdsAsyncTask extends AsyncTask<Void, Void, List<PromoSlideFotos>> {

        //private DaoException generatedException;
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(
                    PromocionesActivity.this,
                    "",
                    getString(R.string.dialog_message),
                    true,
                    true,
                    new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                            cancel(true);
                            finish();
                        }
                    }
            );
        }

        @Override
        protected List<PromoSlideFotos> doInBackground(Void... params) {
            List<PromoSlideFotos> list = new ArrayList<PromoSlideFotos>();

            try {

                URL url = new URL(CencelUtils.buildUrlRequest(PromocionesActivity.this, getString(R.string.getPromosMethod)));

                HttpURLConnection cnn = (HttpURLConnection) url.openConnection();
                cnn.setRequestMethod("POST");
                cnn.setDoOutput(true);
                cnn.setRequestProperty("Content-Type", "application/json");

                // conectando
                cnn.connect();
                InputStream stream = cnn.getInputStream();
                byte[] b = new byte[1024];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();

                while (stream.read(b) != -1)
                    baos.write(b);
                String responseJson = new String(baos.toByteArray());

                JSONObject jsonObject = new JSONObject(responseJson);
                JSONArray promosArray = jsonObject.getJSONArray("d");

                // iterar para generar nuevos objetos
                for (int index = 0; index < promosArray.length(); index++) {
                    JSONObject promoJson = promosArray.getJSONObject(index);
                    PromoSlideFotos promo =
                            new PromoSlideFotos(promoJson.getString("DescripcionPromo"), promoJson.getBoolean("Estatus"), promoJson.getString("FotoActual"), promoJson.getInt("IdRegistro"));
                    list.add(promo);
                }
                return list;
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<PromoSlideFotos> result) {
            super.onPostExecute(result);

            try
            {
                if (result != null) {
                    if (!result.isEmpty()) {
                        promoSlideFotosList = result;
                        customGallery.setAdapter(new BannerGalleryAdapter(PromocionesActivity.this, result, getDrawable(R.drawable.bannr_sucursales)));
                        startTimer();
                    } else {
                        Toast.makeText(PromocionesActivity.this, R.string.msg_no_ads_found, Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }catch(Exception ex){
                ex.printStackTrace();
                finish();
            }
            progressDialog.dismiss();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            progressDialog.dismiss();
            finish();
        }
    }

    /**
     * Timer task que controla el flujo de banners
     *
     * @author Vanesa Cid Garcia
     */
    private class GalleryTimerTask extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (customGallery.getSelectedItemPosition() == (customGallery.getCount() - 1)) {
                        customGallery.setSelection(0, true);
                    } else if (customGallery.getSelectedItemPosition() < (customGallery.getCount() - 1)) {
                        customGallery.setSelection(customGallery.getSelectedItemPosition() + 1, true);
                    }
                }
            });
        }

    }


}

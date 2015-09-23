package mx.com.cencel.comercial.cencel;
import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
/**
 * Created by vcid on 18/09/15.
 */
public class ClaveFacebook  extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        printHashKey();
    }

    public void printHashKey(){
        // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "mx.com.cencel.comercial.cencel",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("KeyHash", "===================================================");
                Log.i("KeyHash", "===================================================");
                Log.i("KeyHash", "===================================================");
                Log.i("KeyHash", "===================================================");
                Log.i("KeyHash", "===================================================");
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Log.i("KeyHash", "===================================================");
                Log.i("KeyHash", "===================================================");
                Log.i("KeyHash", "===================================================");
                Log.i("KeyHash", "===================================================");
                Log.i("KeyHash", "===================================================");
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
}
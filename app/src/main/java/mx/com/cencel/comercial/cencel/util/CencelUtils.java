package mx.com.cencel.comercial.cencel.util;

import android.app.Activity;

import mx.com.cencel.comercial.cencel.R;


public class CencelUtils {

    public static String buildUrlRequest(Activity activity, String method){
        StringBuilder builder = new StringBuilder();
        builder.append(activity.getString(R.string.urlWSCencel));
        builder.append(method);
        return builder.toString();
    }

    public static boolean isNumeric(String str)
    {
        try
        {
            int d = Integer.parseInt(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
}

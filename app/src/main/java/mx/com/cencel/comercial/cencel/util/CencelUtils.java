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

    public static String buildUrlPhoto(Activity activity, String photoName){
        StringBuilder builder = new StringBuilder();
        builder.append(activity.getString(R.string.urlPromosSmartcen));
        builder.append(photoName);
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


    /**
     * Reemplaza los espacios repetidos consecutivos con uno solo
     *
     * @param str
     * @return
     */
    public static String removeRepeatedSpaces(String str) {
        try {
            String result = str.trim();
            while (result.contains("  ")) {
                result = result.replaceAll("  ", " ");
            }
            return result;
        } catch (RuntimeException e) {
            return "";
        }
    }

    /**
     * Verifica si un String esta vacio
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        if( str == null || "".equals(str.trim()) ) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Null safe trim
     *
     * @param str
     * @return
     */
    public static String trim(String str) {
        if(str == null) {
            return "";
        } else {
            return str.trim();
        }
    }
}

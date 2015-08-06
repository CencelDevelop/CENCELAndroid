package mx.com.cencel.comercial.cencel.util;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import mx.com.cencel.comercial.cencel.R;

/**
 * Created by iHouse on 06/08/15.
 */
public class CustomDialog {
    public Dialog dialog;
    public Dialog getDialog(Context context,String title, String message){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(false);
        dialog.setTitle(title);
        TextView tvMsg = (TextView) dialog.findViewById(R.id.tvMessage);
        tvMsg.setText(message);
        Button btnClose = (Button) dialog.findViewById(R.id.btnAccept);
        btnClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        return dialog;
    }
}

package org.androidtown.stew;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by limjeonghyun on 2017. 12. 1..
 */

public class CustomDialog extends Dialog {

    public CustomDialog(Context context, int typeNum, String msg) {

        super(context);
        final Dialog dialog = new Dialog(context);

        if (typeNum == StewConstant.WARNING_DIALOG) {

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog);

            TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
            text.setText(msg);

            Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        } else if (typeNum == StewConstant.SELECT_DIALOG) {
            // ...
        }
        dialog.show();
    }

    public CustomDialog(Context context, int typeNum, BookInfo info) {

        super(context);
        final Dialog dialog = new Dialog(context);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.book_info_dialog);

        TextView textDate = (TextView) dialog.findViewById(R.id.lblDate);
        textDate.setText(info.getStrDate() + "\n" +  info.getStrTime());

        TextView textRoom = (TextView) dialog.findViewById(R.id.lblRoom);
        textRoom.setText(info.getStrRoom());

        /*TextView textUser = (TextView) dialog.findViewById(R.id.lblUser);
        textDate.setText(info.getStrRoom());*/


        Button dialogButton = (Button) dialog.findViewById(R.id.btnClose);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }
}

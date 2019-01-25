package averageme.first.com.average2me.ui.reusable;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import averageme.first.com.average2me.api.helpers.ResultatCallback;


public class AlertFragment extends DialogFragment {

    private ResultatCallback<String> alertFragmentCB;


    private final int ok_label = 1;


    public static AlertFragment newInstance(String title, String message, boolean showCancel, ResultatCallback<String> alertFragmentCB) {
        AlertFragment frag = new AlertFragment();
        frag.alertFragmentCB = alertFragmentCB;

        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        args.putBoolean("show_cancel", showCancel);
        frag.setArguments(args);

        return frag;
    }

    @Nullable
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = "", message = "";
        int ko_label = 1;



        boolean showCancel = true;
        if(getArguments() != null) {
            title = getArguments().getString("title");
            message = getArguments().getString("message");
            showCancel = getArguments().getBoolean("show_cancel");
        }

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(this.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        do_ok_callback();
                    }
                });

        if(showCancel) {
            dialog.setNegativeButton(ko_label, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }

        return dialog.create();
    }

    public void do_ok_callback() {
        if(this.alertFragmentCB != null ) {
            this.alertFragmentCB.onWaitingResultat(getString(this.ok_label));
        }
    }


}

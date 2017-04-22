package tk.forest_tales.gmeter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class AddMeterDialog extends DialogFragment implements DialogInterface.OnClickListener{

    private View form = null;

    private Long meterId = null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        form = getActivity().getLayoutInflater().inflate(R.layout.add_meter_dialog, null);

        Bundle args = getArguments();
        if(args != null){
            EditText etName = (EditText)form.findViewById(R.id.meterName);
            etName.setText(args.getString(App.METER_NAME));

            EditText etNumber = (EditText)form.findViewById(R.id.meterNumber);
            etNumber.setText(args.getString(App.METER_NUMBER));

            meterId = args.getLong(App.METER_ID);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return(
                builder.setTitle(R.string.add_meter_dlg_title)
                        .setView(form)
                        .setPositiveButton(android.R.string.ok, this)
                        .setNegativeButton(android.R.string.cancel, null)
                        .create()
        );
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        String number = ((EditText)form.findViewById(R.id.meterNumber)).getText().toString();
        String name = ((EditText)form.findViewById(R.id.meterName)).getText().toString();

        Intent intent = new Intent();
        intent.putExtra(App.METER_NUMBER, number);
        intent.putExtra(App.METER_NAME, name);
        intent.putExtra(App.METER_ID, meterId);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }

    @Override
    public void onDismiss(DialogInterface unused) {
        super.onDismiss(unused);
    }

    @Override
    public void onCancel(DialogInterface unused) {
        super.onCancel(unused);
    }

}

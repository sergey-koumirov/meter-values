package tk.forest_tales.gmeter;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddMeterValueDialog extends DialogFragment implements DialogInterface.OnClickListener{

    public static final String NEW_METER_VALUE_DATE = "meter_value_date";
    public static final String NEW_METER_VALUE_VALUE = "meter_value_value";

    private View form=null;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        form = getActivity().getLayoutInflater().inflate(R.layout.add_meter_value_dialog, null);

        ((EditText)form.findViewById(R.id.meterValueDate)).setText( new SimpleDateFormat("yyyy-MM-dd").format(new Date()) );

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
        String date = ((EditText)form.findViewById(R.id.meterValueDate)).getText().toString();
        Double value = 0.0;
        try{
            value = Double.parseDouble( ((EditText)form.findViewById(R.id.meterValueValue)).getText().toString() );
        }catch(NumberFormatException e){

        }
        Intent intent = new Intent();
        intent.putExtra(NEW_METER_VALUE_DATE, date);
        intent.putExtra(NEW_METER_VALUE_VALUE, value);
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

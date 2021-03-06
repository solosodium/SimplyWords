package studio.unispace.simplywords.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import studio.unispace.simplywords.DictActivity;
import studio.unispace.simplywords.R;

/**
 * Created by haofu on 8/19/16.
 */

public class AddDictDialog extends DialogFragment {

    public static final String TAG = "ADD_DICT_DIALOG";

    public AddDictDialog () {
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // set customized view
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_dict_dialog, null);
        final EditText name = (EditText)view.findViewById(R.id.add_dict_name);
        builder.setView(view);
        // set basic dialog stuff
        builder.setTitle(R.string.add_dict_dialog_title);
        builder.setCancelable(true);
        builder.setNegativeButton(R.string.add_dict_dialog_cancel_label, null);
        builder.setPositiveButton(R.string.add_dict_dialog_done_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!name.getText().toString().equals("")) {
                    if (!((DictActivity)getActivity()).addDictionaryToLibrary(name.getText().toString())) {
                        ((DictActivity)getActivity()).showCandyMessage(getString(R.string.dict_message_duplicate_dict_name));
                    }
                } else {
                    ((DictActivity)getActivity()).showCandyMessage(getString(R.string.dict_message_empty_dict_name));
                }
            }
        });
        final AlertDialog ad = builder.create();
        ad.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                ad.getButton(Dialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(getActivity(), R.color.colorAccent));
                ad.getButton(Dialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryLight));
            }
        });
        return ad;
    }
}

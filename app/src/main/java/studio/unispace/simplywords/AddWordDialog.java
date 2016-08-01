package studio.unispace.simplywords;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import studio.unispace.simplywords.models.Dictionary;

/**
 * Created by haof on 7/31/2016.
 */
public class AddWordDialog extends DialogFragment {

    private Dictionary mDict;

    public AddWordDialog () {
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.add_word_dialog, null);
        builder.setView(v);
        builder.setTitle(R.string.add_word_dialog_title);
        builder.setPositiveButton(R.string.add_word_dialog_done_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // create word
                v.findViewById(R.id.add_word_word);
            }
        });
        builder.setNegativeButton(R.string.add_word_dialog_cancel_label, null);
        builder.setCancelable(true);
        return builder.create();
    }

}

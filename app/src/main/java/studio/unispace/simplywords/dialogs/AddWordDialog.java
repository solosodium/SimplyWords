package studio.unispace.simplywords.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import studio.unispace.simplywords.MainActivity;
import studio.unispace.simplywords.R;
import studio.unispace.simplywords.models.Word;

/**
 * Created by haof on 7/31/2016.
 */
public class AddWordDialog extends DialogFragment {

    public static final String TAG = "ADD_WORD_DIALOG";

    public AddWordDialog () {
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.add_word_dialog, null);
        ((AppCompatSeekBar)v.findViewById(R.id.add_word_rating)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ((TextView)v.findViewById(R.id.add_word_rating_display)).setText(String.valueOf(progress));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        builder.setView(v);
        builder.setTitle(R.string.add_word_dialog_title);
        builder.setPositiveButton(R.string.add_word_dialog_done_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (((EditText)v.findViewById(R.id.add_word_word)).getText().toString().equals("")) {
                    ((MainActivity)getActivity()).showCandyMessage(getString(R.string.main_message_empty_word_name));
                    return;
                }
                // create word
                Word word = new Word();
                word.word = ((EditText)v.findViewById(R.id.add_word_word)).getText().toString();
                word.definition = ((EditText)v.findViewById(R.id.add_word_definition)).getText().toString();
                word.remark = ((EditText)v.findViewById(R.id.add_word_remark)).getText().toString();
                word.rating = (float)((AppCompatSeekBar)v.findViewById(R.id.add_word_rating)).getProgress();
                // add word to dictionary
                ((MainActivity)getActivity()).addWordToDictionary(word);
            }
        });
        builder.setNegativeButton(R.string.add_word_dialog_cancel_label, null);
        builder.setCancelable(true);
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

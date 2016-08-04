package studio.unispace.simplywords.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSeekBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import studio.unispace.simplywords.MainActivity;
import studio.unispace.simplywords.R;
import studio.unispace.simplywords.adapters.WordListAdapter;
import studio.unispace.simplywords.models.Word;

/**
 * Created by haof on 8/4/2016.
 */
public class EditWordDialog extends DialogFragment {

    public EditWordDialog () {
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.add_word_dialog, null);    // reuse add word dialog UI
        // get data
        final MainActivity activity = (MainActivity)getActivity();
        int pos = getArguments().getInt(WordListAdapter.REVIEW_WORD_DIALOG_WORD_POS);
        final Word word = activity.dict.words.get(pos);
        // set views
        ((EditText)v.findViewById(R.id.add_word_word)).setText(word.word);
        ((EditText)v.findViewById(R.id.add_word_definition)).setText(word.definition);
        ((EditText)v.findViewById(R.id.add_word_remark)).setText(word.remark);
        ((TextView)v.findViewById(R.id.add_word_rating_display)).setText(String.valueOf(word.rating.intValue()));
        ((AppCompatSeekBar)v.findViewById(R.id.add_word_rating)).setProgress(word.rating.intValue());
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
        builder.setTitle(R.string.edit_word_dialog_title);                  // only title positive and negative are different
        builder.setPositiveButton(R.string.edit_word_dialog_done_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // update word
                word.word = ((EditText)v.findViewById(R.id.add_word_word)).getText().toString();
                word.definition = ((EditText)v.findViewById(R.id.add_word_definition)).getText().toString();
                word.remark = ((EditText)v.findViewById(R.id.add_word_remark)).getText().toString();
                word.rating = (float)((AppCompatSeekBar)v.findViewById(R.id.add_word_rating)).getProgress();
                // add word to dictionary
                ((MainActivity)getActivity()).saveDictionary();
                ((MainActivity)getActivity()).refreshList();
            }
        });
        builder.setNegativeButton(R.string.edit_word_dialog_cancel_label, null);
        builder.setCancelable(true);
        return builder.create();
    }

}
package studio.unispace.simplywords.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import studio.unispace.simplywords.MainActivity;
import studio.unispace.simplywords.R;
import studio.unispace.simplywords.adapters.WordListAdapter;
import studio.unispace.simplywords.models.Word;

/**
 * Created by haof on 8/3/2016.
 */
public class ReviewWordDialog extends DialogFragment {

    public ReviewWordDialog () {
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.review_word_dialog, null);
        // get data
        final MainActivity activity = (MainActivity)getActivity();
        int pos = getArguments().getInt(WordListAdapter.REVIEW_WORD_DIALOG_WORD_POS);
        final Word word = activity.dict.words.get(pos);
        // show data
        ((TextView)v.findViewById(R.id.review_word_word)).setText(word.word);
        ((TextView)v.findViewById(R.id.review_word_definition)).setText(word.definition);
        ((TextView)v.findViewById(R.id.review_word_remark)).setText(word.remark);
        final TextView rating = (TextView)v.findViewById(R.id.review_word_rating);
        rating.setText(String.valueOf(String.valueOf(word.rating.intValue())));
        // buttons
        (v.findViewById(R.id.review_word_down_vote)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word.rating -= 1;
                word.rating = Math.min( Math.max(0, word.rating), 10 );
                rating.setText(String.valueOf(String.valueOf(word.rating.intValue())));
                activity.saveDictionary();
            }
        });
        (v.findViewById(R.id.review_word_up_vote)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word.rating += 1;
                word.rating = Math.min( Math.max(0, word.rating), 10 );
                rating.setText(String.valueOf(String.valueOf(word.rating.intValue())));
                activity.saveDictionary();
            }
        });
        // set rest
        builder.setView(v);
        builder.setTitle(R.string.review_word_dialog_title);
        builder.setNegativeButton(R.string.review_word_dialog_done_label, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.refreshList();
            }
        });
        builder.setCancelable(true);
        final AlertDialog ad = builder.create();
        ad.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                ad.getButton(Dialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(getActivity(), R.color.colorPrimaryLight));
            }
        });
        return ad;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        ((MainActivity)getActivity()).refreshList();
    }

}

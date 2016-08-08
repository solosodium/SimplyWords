package studio.unispace.simplywords.adapters;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import studio.unispace.simplywords.MainActivity;
import studio.unispace.simplywords.R;
import studio.unispace.simplywords.dialogs.EditWordDialog;
import studio.unispace.simplywords.dialogs.ReviewWordDialog;
import studio.unispace.simplywords.models.Word;
import studio.unispace.simplywords.utils.Utilities;
import studio.unispace.simplywords.views.RatingView;

/**
 * Created by haofu on 7/29/16.
 */

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.ViewHolder> {

    public static final String REVIEW_WORD_DIALOG_WORD_POS = "WORD_POS";
    public static final String EDIT_WORD_DIALOG_WORD_POS = "WORD_POS";

    private MainActivity mActivity;
    private List<Word> mWords;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public ViewHolder (View v) {
            super(v);
            mView = v;
        }
    }

    public WordListAdapter (MainActivity activity) {
        mActivity = activity;
        mWords = activity.dict.words;
        // has unique id for animation
        setHasStableIds(true);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public WordListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ((TextView)holder.mView.findViewById(R.id.word_list_item_word)).setText(mWords.get(position).word);
        ((RatingView)holder.mView.findViewById(R.id.word_list_item_rating)).setRating(mWords.get(position).rating);
        holder.mView.findViewById(R.id.word_list_item_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setTitle(R.string.delete_word_dialog_title);
                builder.setMessage(R.string.delete_word_dialog_body);
                builder.setCancelable(true);
                builder.setNegativeButton(R.string.delete_word_dialog_negative, null);
                builder.setPositiveButton(R.string.delete_word_dialog_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mActivity.deleteWordFromDictionary(position);
                    }
                });
                final AlertDialog ad = builder.create();
                ad.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        ad.getButton(Dialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(mActivity, R.color.colorPrimaryLight));
                        ad.getButton(Dialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(mActivity, R.color.colorAccent));
                    }
                });
                ad.show();
            }
        });
        holder.mView.findViewById(R.id.word_list_item_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create edit dialog
                FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
                Fragment prev = mActivity.getFragmentManager().findFragmentByTag("edit_word_dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                // create dialog
                EditWordDialog editWordDialog = new EditWordDialog();
                // pass arguments
                Bundle args = new Bundle();
                args.putInt(EDIT_WORD_DIALOG_WORD_POS, position);
                editWordDialog.setArguments(args);
                // show dialog
                editWordDialog.show(ft, "edit_word_dialog");
            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // create review dialog
                FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
                Fragment prev = mActivity.getFragmentManager().findFragmentByTag("review_word_dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                // create dialog
                ReviewWordDialog reviewWordDialog = new ReviewWordDialog();
                // pass arguments
                Bundle args = new Bundle();
                args.putInt(REVIEW_WORD_DIALOG_WORD_POS, position);
                reviewWordDialog.setArguments(args);
                // show dialog
                reviewWordDialog.show(ft, "review_word_dialog");
            }
        });
    }

    // Return the size of your data set (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mWords.size();
    }

    // Return unique ID to enable item update animation
    @Override
    public long getItemId(int position) {
        return mWords.get(position).id;
    }

    //
    // public methods
    //

    public void filter (String query) {
        if (!query.equals("")) {
            mWords = new LinkedList<>();
            for (Word w : mActivity.dict.words) {
                if (w.word.startsWith(query)) {
                    mWords.add(w);
                }
            }
        } else {
            consolidateWords();
        }
    }

    public void consolidateWords () {
        mWords = mActivity.dict.words;
    }

    public void sortByInitialLetter () {
        Utilities.sortWordsByInitialLetter(mWords);
    }

    public void sortByRating () {
        Utilities.sortWordsByRating(mWords);
    }

    public void sortByCreatedDate () {
        Utilities.sortWordsByCreatedDate(mWords);
    }

}
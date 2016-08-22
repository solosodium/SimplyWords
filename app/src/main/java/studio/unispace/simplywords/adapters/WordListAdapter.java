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
    private List<Integer> mRawPositions;

    private boolean lastSortByInitialLetter = false;
    private boolean lastSortByRating = false;
    private boolean lastSortByCreatedDate = false;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public ViewHolder (View v) {
            super(v);
            mView = v;
        }
    }

    public WordListAdapter (MainActivity activity) {
        mActivity = activity;
        mWords = mActivity.dict.words;
        mRawPositions = new LinkedList<>();
        for (int i=0; i<mActivity.dict.words.size(); i++) {
            mRawPositions.add(i);
        }
        // has unique id for animation
        setHasStableIds(true);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public WordListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.word_list_item, parent, false);
        // set the view's size, margins, padding and layout parameters
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int pos = holder.getAdapterPosition();
        ((TextView)holder.mView.findViewById(R.id.word_list_item_word)).setText(mWords.get(pos).word);
        ((RatingView)holder.mView.findViewById(R.id.word_list_item_rating)).setRating(mWords.get(pos).rating);
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
                        mActivity.deleteWordFromDictionary(mRawPositions.get(pos));
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
                Fragment prev = mActivity.getFragmentManager().findFragmentByTag(EditWordDialog.TAG);
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                // create dialog
                EditWordDialog editWordDialog = new EditWordDialog();
                // pass arguments
                Bundle args = new Bundle();
                args.putInt(EDIT_WORD_DIALOG_WORD_POS, mRawPositions.get(pos));
                editWordDialog.setArguments(args);
                // show dialog
                editWordDialog.show(ft, EditWordDialog.TAG);
            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // create review dialog
                FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
                Fragment prev = mActivity.getFragmentManager().findFragmentByTag(ReviewWordDialog.TAG);
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                // create dialog
                ReviewWordDialog reviewWordDialog = new ReviewWordDialog();
                // pass arguments
                Bundle args = new Bundle();
                args.putInt(REVIEW_WORD_DIALOG_WORD_POS, mRawPositions.get(pos));
                reviewWordDialog.setArguments(args);
                // show dialog
                reviewWordDialog.show(ft, ReviewWordDialog.TAG);
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
            mRawPositions.clear();
            for (int i=0; i<mActivity.dict.words.size(); i++) {
                Word w = mActivity.dict.words.get(i);
                if (w.word.startsWith(query)) {
                    mWords.add(w);
                    mRawPositions.add(i);
                }
            }
        } else {
            consolidateWords();
        }
    }

    public void consolidateWords () {
        mWords = mActivity.dict.words;
        mRawPositions.clear();
        for (int i=0; i<mActivity.dict.words.size(); i++) {
            mRawPositions.add(i);
        }
        lastSortByInitialLetter = lastSortByRating = lastSortByCreatedDate = false;
    }

    public void sortByInitialLetter () {
        if (!lastSortByInitialLetter) {
            Utilities.sortWordsByInitialLetter(mWords);
        } else {
            Utilities.sortWordsByInitialLetterReverse(mWords);
        }
        lastSortByInitialLetter = !lastSortByInitialLetter;
        lastSortByRating = lastSortByCreatedDate = false;
    }

    public void sortByRating () {
        if (!lastSortByRating) {
            Utilities.sortWordsByRating(mWords);
        } else {
            Utilities.sortWordsByRatingReverse(mWords);
        }
        lastSortByRating = !lastSortByRating;
        lastSortByInitialLetter = lastSortByCreatedDate = false;
    }

    public void sortByCreatedDate () {
        if (!lastSortByCreatedDate) {
            Utilities.sortWordsByCreatedDate(mWords);
        } else {
            Utilities.sortWordsByCreatedDateReverse(mWords);
        }
        lastSortByCreatedDate = !lastSortByCreatedDate;
        lastSortByInitialLetter = lastSortByRating = false;
    }

}
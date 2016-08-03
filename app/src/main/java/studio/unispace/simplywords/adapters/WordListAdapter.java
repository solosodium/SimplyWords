package studio.unispace.simplywords.adapters;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import studio.unispace.simplywords.MainActivity;
import studio.unispace.simplywords.R;
import studio.unispace.simplywords.view.RatingView;

/**
 * Created by haofu on 7/29/16.
 */

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.ViewHolder> {

    private MainActivity mActivity;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public ViewHolder (View v) {
            super(v);
            mView = v;
        }
    }

    public WordListAdapter (MainActivity activity) {
        mActivity = activity;
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
        ((TextView)holder.mView.findViewById(R.id.word_list_item_word)).setText(mActivity.dict.words.get(position).word);
        ((RatingView)holder.mView.findViewById(R.id.word_list_item_rating)).setRating(mActivity.dict.words.get(position).rating);
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
                builder.show();
            }
        });
        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.d("SS", "SS");
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mActivity.dict.words.size();
    }
}
package studio.unispace.simplywords.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import studio.unispace.simplywords.R;
import studio.unispace.simplywords.models.Dictionary;
import studio.unispace.simplywords.view.RatingView;

/**
 * Created by haofu on 7/29/16.
 */

public class WordListAdapter extends RecyclerView.Adapter<WordListAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public ViewHolder (View v) {
            super(v);
            mView = v;
        }
    }

    private Dictionary mDict;

    public WordListAdapter (Dictionary dict) {
        mDict = dict;
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ((TextView)holder.mView.findViewById(R.id.word_list_item_word)).setText(mDict.words.get(position).word.toUpperCase());
        ((RatingView)holder.mView.findViewById(R.id.word_list_item_rating)).setRating(mDict.words.get(position).rating);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDict.words.size();
    }
}
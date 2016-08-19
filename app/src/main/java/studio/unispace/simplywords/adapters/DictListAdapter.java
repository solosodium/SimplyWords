package studio.unispace.simplywords.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import studio.unispace.simplywords.DictActivity;
import studio.unispace.simplywords.MainActivity;
import studio.unispace.simplywords.R;
import studio.unispace.simplywords.models.Library;

/**
 * Created by haof on 8/17/2016.
 */
public class DictListAdapter extends RecyclerView.Adapter<DictListAdapter.ViewHolder> {

    public static String DICT_POS = "DICT_POS";

    public DictActivity mActivity;
    public Library mLibrary;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public ViewHolder (View v) {
            super(v);
            mView = v;
        }
    }

    public DictListAdapter (DictActivity activity) {
        mActivity = activity;
        mLibrary = activity.library;
    }

    @Override
    public int getItemCount() {
        return mLibrary.dictionaries.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dict_list_item, parent, false);
        // set the view's size, margins, padding and layout parameters
        return new DictListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final int pos = holder.getAdapterPosition();
        TextView name = (TextView)holder.mView.findViewById(R.id.dict_list_item_name);
        name.setText(mActivity.library.dictionaries.get(pos));
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start word list
                Intent it = new Intent(mActivity, MainActivity.class);
                it.putExtra(MainActivity.DICTIONARY_KEY, mActivity.library.dictionaries.get(pos));
                mActivity.startActivity(it);
            }
        });
    }
}

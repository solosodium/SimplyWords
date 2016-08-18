package studio.unispace.simplywords.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import studio.unispace.simplywords.DictActivity;
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
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }
}

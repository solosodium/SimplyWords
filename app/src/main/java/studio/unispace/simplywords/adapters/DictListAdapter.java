package studio.unispace.simplywords.adapters;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import studio.unispace.simplywords.DictActivity;
import studio.unispace.simplywords.MainActivity;
import studio.unispace.simplywords.R;
import studio.unispace.simplywords.dialogs.EditDictDialog;
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
                mActivity.startActivityForResult(it, 0);
            }
        });
        ImageButton delete = (ImageButton)holder.mView.findViewById(R.id.dict_list_item_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // show delete dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
                builder.setTitle(R.string.delete_dict_dialog_title);
                builder.setMessage(R.string.delete_dict_dialog_message);
                builder.setCancelable(true);
                builder.setNegativeButton(R.string.delete_dict_dialog_cancel_label, null);
                builder.setPositiveButton(R.string.delete_dict_dialog_done_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // delete dictionary
                        mActivity.removeDictionaryFromLibrary(mLibrary.dictionaries.get(pos));
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
        ImageButton edit = (ImageButton)holder.mView.findViewById(R.id.dict_list_item_edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create add dictionary dialog
                FragmentTransaction ft = mActivity.getFragmentManager().beginTransaction();
                Fragment prev = mActivity.getFragmentManager().findFragmentByTag(EditDictDialog.TAG);
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                // create and show the dialog
                EditDictDialog editDictDialog = new EditDictDialog();
                Bundle args = new Bundle();
                args.putString(EditDictDialog.OLD_NAME_KEY, mLibrary.dictionaries.get(pos));
                editDictDialog.setArguments(args);
                editDictDialog.show(ft, EditDictDialog.TAG);
            }
        });
    }

}

package studio.unispace.simplywords;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import studio.unispace.simplywords.adapters.WordListAdapter;
import studio.unispace.simplywords.dialogs.AddWordDialog;
import studio.unispace.simplywords.models.Dictionary;
import studio.unispace.simplywords.models.Word;

public class MainActivity extends AppCompatActivity {

    private RecyclerView wordList;
    private LinearLayoutManager linearLayoutManager;
    private WordListAdapter wordListAdapter;

    public Dictionary dict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //
        // FAB
        //
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    Fragment prev = getFragmentManager().findFragmentByTag("add_word_dialog");
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);
                    // create and show the dialog
                    AddWordDialog addWordDialog = new AddWordDialog();
                    addWordDialog.show(ft, "add_word_dialog");
                }
            });
        }
        //
        // load dictionary data
        //
        dict = Dictionary.load(this);
        //
        // initialize views
        //
        wordList = (RecyclerView)findViewById(R.id.main_word_list);
        if (wordList != null) {
            wordList.setHasFixedSize(true);
            linearLayoutManager = new LinearLayoutManager(this);
            wordList.setLayoutManager(linearLayoutManager);
            wordListAdapter = new WordListAdapter(this);
            wordList.setAdapter(wordListAdapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        else if (id == R.id.action_sort_initial_letter) {
            return true;
        }
        else if (id == R.id.action_sort_created_date) {
            return true;
        }
        else if (id == R.id.action_sort_visited_date) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //
    // public functions
    //

    public void addWordToDictionary (Word word) {
        // add word
        dict.addWord(word);
        // save
        Dictionary.save(this, dict);
        // update view
        wordListAdapter.notifyDataSetChanged();
    }

    public void deleteWordFromDictionary (int position) {
        // delete word
        dict.deleteWord(position);
        // save
        Dictionary.save(this, dict);
        // update view
        wordListAdapter.notifyDataSetChanged();
    }

    public void saveDictionary () {
        // save
        Dictionary.save(this, dict);
    }

    public void refreshList () {
        // update view
        wordListAdapter.notifyDataSetChanged();
    }

}

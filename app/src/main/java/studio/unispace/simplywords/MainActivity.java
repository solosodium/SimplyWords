package studio.unispace.simplywords;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import studio.unispace.simplywords.adapters.WordListAdapter;
import studio.unispace.simplywords.dialogs.AddWordDialog;
import studio.unispace.simplywords.models.Dictionary;
import studio.unispace.simplywords.models.Word;

public class MainActivity extends AppCompatActivity {

    private TextView hintText;
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
        hintText = (TextView)findViewById(R.id.main_hint);
        hintText.setVisibility(dict.words.size() == 0 ? View.VISIBLE : View.INVISIBLE);
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
        // initialize search view
        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView)MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                wordListAdapter.filter(query);
                refreshList();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                wordListAdapter.filter(newText);
                refreshList();
                return true;
            }
        });
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
            wordListAdapter.sortByInitialLetter();
            refreshList();
            return true;
        }
        else if (id == R.id.action_sort_rating) {
            wordListAdapter.sortByRating();
            refreshList();
            return true;
        }
        else if (id == R.id.action_sort_created_date) {
            wordListAdapter.sortByCreatedDate();
            refreshList();
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
        saveDictionary();
        wordListAdapter.consolidateWords();
        refreshList();
    }

    public void deleteWordFromDictionary (int position) {
        // delete word
        dict.deleteWord(position);
        saveDictionary();
        wordListAdapter.consolidateWords();
        refreshList();
    }

    public void saveDictionary () {
        // save
        Dictionary.save(this, dict);
    }

    public void refreshList () {
        // update view
        wordListAdapter.notifyDataSetChanged();
        // update hint text
        if (hintText != null) {
            hintText.setVisibility(dict.words.size() == 0 ? View.VISIBLE : View.INVISIBLE);
        }
    }

}

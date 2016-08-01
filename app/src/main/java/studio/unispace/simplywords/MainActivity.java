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
import studio.unispace.simplywords.models.Dictionary;
import studio.unispace.simplywords.models.Word;

public class MainActivity extends AppCompatActivity {

    private RecyclerView wordList;
    private LinearLayoutManager linearLayoutManager;
    private WordListAdapter wordListAdapter;

    private Dictionary dict;
    private int stackLevel;

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
                    // Create and show the dialog.
                    AddWordDialog addWordDialog = new AddWordDialog();
                    addWordDialog.show(ft, "add_word_dialog");
                }
            });
        }
        //
        // initialize data (TESTING)
        //
        dict = new Dictionary();
        for (int i=0; i<10; i++) {
            Word w = new Word();
            w.word = "Word " + i;
            w.rating = (float) Math.random() * 10;
            dict.addWord(w);
        }
        Dictionary.save(this, dict);
        //
        // initialize views
        //
        wordList = (RecyclerView)findViewById(R.id.main_word_list);
        if (wordList != null) {
            wordList.setHasFixedSize(true);
            linearLayoutManager = new LinearLayoutManager(this);
            wordList.setLayoutManager(linearLayoutManager);
            wordListAdapter = new WordListAdapter(dict);
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
}

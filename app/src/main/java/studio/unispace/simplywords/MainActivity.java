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
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import studio.unispace.simplywords.adapters.WordListAdapter;
import studio.unispace.simplywords.dialogs.AddWordDialog;
import studio.unispace.simplywords.models.Dictionary;
import studio.unispace.simplywords.models.Word;
import studio.unispace.simplywords.utils.Utilities;

public class MainActivity extends AppCompatActivity {

    public static final String DICTIONARY_KEY = "DICTIONARY_KEY";

    private TextView hintText;
    private RecyclerView wordList;
    private LinearLayoutManager linearLayoutManager;
    private WordListAdapter wordListAdapter;
    private FloatingActionButton fab;

    private float wordListPrevTouchY = 0f;
    private Animation fabHide;
    private Animation fabShow;
    private boolean isFabShow = true;
    private long fabAnimationDuration = 200L;

    public Dictionary dict;
    public String dictName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        // load dictionary data
        //
        dictName = getIntent().getExtras().getString(DICTIONARY_KEY);
        dict = Dictionary.load(this, dictName);
        //
        // tool bar
        //
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(dictName);
        setSupportActionBar(toolbar);
        //
        // FAB
        //
        fab = (FloatingActionButton) findViewById(R.id.main_fab);
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
        fabHide = new AlphaAnimation(1f, 0f);
        fabHide.setDuration(fabAnimationDuration);
        fabHide.setFillEnabled(true);
        fabHide.setFillAfter(true);
        fabShow = new AlphaAnimation(0f, 1f);
        fabShow.setDuration(fabAnimationDuration);
        fabShow.setFillEnabled(true);
        fabShow.setFillAfter(true);
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
            wordList.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                @Override
                public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                    if (e.getAction() == MotionEvent.ACTION_DOWN) {
                        if (e.getPointerCount() > 0) {
                            MotionEvent.PointerCoords coord = new MotionEvent.PointerCoords();
                            e.getPointerCoords(0, coord);
                            wordListPrevTouchY = coord.y;
                        }
                    }
                    else if (e.getAction() == MotionEvent.ACTION_MOVE){
                        if (e.getPointerCount() > 0) {
                            int maxScroll = rv.computeVerticalScrollRange();
                            int currentScroll = rv.computeVerticalScrollOffset() + rv.computeVerticalScrollExtent();
                            MotionEvent.PointerCoords coord = new MotionEvent.PointerCoords();
                            e.getPointerCoords(0, coord);
                            if (currentScroll >= maxScroll && coord.y - wordListPrevTouchY < -10f) {
                                // over scroll
                                showHideFab(false);
                            }
                            if (coord.y - wordListPrevTouchY > 10f) {
                                // cancel over scroll
                                showHideFab(true);
                            }
                            wordListPrevTouchY = coord.y;
                        }
                    }
                    return false;
                }
                @Override
                public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                    // DO NOTHING
                }
                @Override
                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
                    // DO NOTHING
                }
            });
        }
        hintText = (TextView)findViewById(R.id.main_hint);
        hintText.setVisibility(wordListAdapter.getItemCount() == 0 ? View.VISIBLE : View.INVISIBLE);
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
            Utilities.sortWordsByInitialLetter(dict.words);
            saveDictionary();
            wordListAdapter.sortByInitialLetter();
            refreshList();
            return true;
        }
        else if (id == R.id.action_sort_rating) {
            Utilities.sortWordsByRating(dict.words);
            saveDictionary();
            wordListAdapter.sortByRating();
            refreshList();
            return true;
        }
        else if (id == R.id.action_sort_created_date) {
            Utilities.sortWordsByCreatedDate(dict.words);
            saveDictionary();
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
        hintText.setVisibility(wordListAdapter.getItemCount() == 0 ? View.VISIBLE : View.INVISIBLE);
        // show fab
        if (wordListAdapter.getItemCount() == 0) {
            showHideFab(true);
        }
    }

    //
    // utility
    //

    private void showHideFab (boolean isShow) {
        if (!isShow) {
            // hide
            if (isFabShow && wordListAdapter.getItemCount() > 0) {
                isFabShow = false;
                fab.startAnimation(fabHide);
                fab.setClickable(false);
            }
        } else {
            // show
            if (!isFabShow) {
                isFabShow = true;
                fab.startAnimation(fabShow);
                fab.setClickable(true);
            }
        }
    }

}

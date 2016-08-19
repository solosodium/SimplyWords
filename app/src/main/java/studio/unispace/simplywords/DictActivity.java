package studio.unispace.simplywords;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import studio.unispace.simplywords.adapters.DictListAdapter;
import studio.unispace.simplywords.models.Library;

public class DictActivity extends AppCompatActivity {



    private RecyclerView dictList;
    private GridLayoutManager gridLayoutManager;
    private DictListAdapter dictListAdapter;
    private FloatingActionButton fab;
    private TextView hintText;

    public Library library;

    private float wordListPrevTouchY = 0f;
    private Animation fabHide;
    private Animation fabShow;
    private boolean isFabShow = true;
    private long fabAnimationDuration = 200L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //
        // initialize data
        //
        library = Library.load(this);
        //
        // fab initialize
        //
        fab = (FloatingActionButton) findViewById(R.id.dict_fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: add behavior
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
        // dict list initialize
        //
        dictList = (RecyclerView)findViewById(R.id.dict_dict_list);
        if (dictList != null) {
            dictList.setHasFixedSize(true);
            gridLayoutManager = new GridLayoutManager(this, 2);
            dictList.setLayoutManager(gridLayoutManager);
            dictListAdapter = new DictListAdapter(this);
            dictList.setAdapter(dictListAdapter);
            dictList.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
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
        //
        // other views
        //
        hintText = (TextView)findViewById(R.id.dict_hint);
        hintText.setVisibility(library.dictionaries.size() > 0 ? View.INVISIBLE : View.VISIBLE);
    }

    //
    // utility
    //

    private void showHideFab (boolean isShow) {
        if (!isShow) {
            // hide
            if (isFabShow && dictListAdapter.getItemCount() > 0) {
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

package studio.unispace.simplywords;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import studio.unispace.simplywords.models.Library;

public class DictActivity extends AppCompatActivity {

    private RecyclerView dictList;
    private GridLayoutManager gridLayoutManager;

    public Library library;

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
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: add behavior
                }
            });
        }
        //
        // dict list initialize
        //
        dictList = (RecyclerView)findViewById(R.id.dict_dict_list);
        if (dictList != null) {
            dictList.setHasFixedSize(true);
            gridLayoutManager = new GridLayoutManager(this, 2);
            dictList.setLayoutManager(gridLayoutManager);
        }
    }

}

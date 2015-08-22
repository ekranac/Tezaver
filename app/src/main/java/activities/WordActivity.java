package activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.widget.TextView;

import com.ziga.tezaver.R;

import helpers.Constants;
import helpers.getWordData;


public class WordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        Intent intent = getIntent();
        String id = intent.getStringExtra(Constants.WORD_ID);
        if(id!=null)
        {
            id = id.toLowerCase();
            new getWordData(id, this).execute();
        }


        TextView tvWord = (TextView) findViewById(R.id.tv_word);
        TextView tvPronunciation = (TextView) findViewById(R.id.tv_pronunciation);

        RecyclerView relatedList = (RecyclerView) findViewById(R.id.list_related);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        relatedList.setLayoutManager(mLayoutManager);

        Typeface robotoSlab = Typeface.createFromAsset(this.getAssets(), "RobotoSlab.ttf");

        tvWord.setTypeface(robotoSlab);
        tvPronunciation.setTypeface(robotoSlab);


        ActionBar bar = getSupportActionBar();
        if(bar!=null)
        {
            bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dark_blue)));
            bar.setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_word_activity, menu);

        return true;
    }


}

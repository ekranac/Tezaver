package activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import com.ziga.tezaver.R;

import adapters.WordSearchAdapter;
import helpers.Constants;
import helpers.getStatistics;


public class MainActivity extends AppCompatActivity {

    WordSearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar bar = getSupportActionBar();
        if(bar!=null)
        {
            bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dark_blue)));
        }

        if(isConnected())
        {
            setContentView(R.layout.activity_main);

            final AutoCompleteTextView search = (AutoCompleteTextView) findViewById(R.id.main_search);
            final Button searchIcon = (Button) findViewById(R.id.btn_search_icon);
            final Button clearSearch = (Button) findViewById(R.id.btn_clear_search);
            Typeface robotoSlab = Typeface.createFromAsset(this.getAssets(), "RobotoSlab.ttf");

            TextView statSynonyms = (TextView) findViewById(R.id.synonyms_stat);
            TextView statSynonymsNum = (TextView) findViewById(R.id.synonyms_stat_num);
            TextView statAntonyms = (TextView) findViewById(R.id.antonyms_stat);
            TextView statAntonymsNum = (TextView) findViewById(R.id.antonyms_stat_num);

            statSynonyms.setTypeface(robotoSlab);
            statSynonymsNum.setTypeface(robotoSlab);
            statAntonyms.setTypeface(robotoSlab);
            statAntonymsNum.setTypeface(robotoSlab);

            RecyclerView latestList = (RecyclerView) findViewById(R.id.latest_list);
            latestList.setHasFixedSize(true);

            LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
            latestList.setLayoutManager(mLayoutManager);

            adapter = new WordSearchAdapter(this, null);
            search.setAdapter(adapter);

            new getStatistics(this).execute();


            search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    search.setText("");

                    Intent intent = new Intent(MainActivity.this, WordActivity.class);
                    intent.putExtra(Constants.WORD_ID, adapter.getItem(i).getId().toLowerCase());
                    startActivity(intent);
                }
            });

            search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (search.length() == 0) {
                        clearSearch.setVisibility(View.INVISIBLE);
                        searchIcon.setVisibility(View.VISIBLE);
                    } else {
                        clearSearch.setVisibility(View.VISIBLE);
                        searchIcon.setVisibility(View.GONE);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });

            clearSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    search.setText("");
                }
            });

            searchIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    search.requestFocus();

                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.showSoftInput(search, InputMethodManager.SHOW_IMPLICIT);
                }
            });
        }

        else
        {
            setContentView(R.layout.layout_no_connection);

            Button refreshButton = (Button) findViewById(R.id.refresh_btn);
            refreshButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
            });
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

        //noinspection SimplifiableIfStatement
        if(id==R.id.menu_help)
        {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        AutoCompleteTextView search = (AutoCompleteTextView) findViewById(R.id.main_search);
        if(search!=null && search.hasFocus())
        {
            search.clearFocus();
        }
    }

    private boolean isConnected()
    {
        Boolean isConnected = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if(ni!=null)
        {
            isConnected = true;
        }

        return isConnected;
    }
}

package activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ziga.tezaver.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import adapters.WordSearchAdapter;
import models.HTTPDataHandler;


public class MainActivity extends AppCompatActivity {

    WordSearchAdapter adapter;
    public final static String WORD_ID = "com.ziga.tezaver.WORD_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AutoCompleteTextView search = (AutoCompleteTextView) findViewById(R.id.main_search);
        final Button clearSearch = (Button) findViewById(R.id.btn_clear_search);
        ActionBar bar = getSupportActionBar();
        Typeface robotoSlab = Typeface.createFromAsset(this.getAssets(), "RobotoSlab.ttf");

        TextView statSynonyms = (TextView) findViewById(R.id.synonyms_stat);
        TextView statSynonymsNum = (TextView) findViewById(R.id.synonyms_stat_num);
        TextView statAntonyms = (TextView) findViewById(R.id.antonyms_stat);
        TextView statAntonymsNum = (TextView) findViewById(R.id.antonyms_stat_num);

        statSynonyms.setTypeface(robotoSlab);
        statSynonymsNum.setTypeface(robotoSlab);
        statAntonyms.setTypeface(robotoSlab);
        statAntonymsNum.setTypeface(robotoSlab);


        if(bar!=null)
        {
            bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dark_blue)));
        }

        adapter = new WordSearchAdapter(this, null);
        search.setAdapter(adapter);

        new getStatistics(this).execute();


        search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                search.setText("");

                Intent intent = new Intent(MainActivity.this, WordActivity.class);
                intent.putExtra(WORD_ID, adapter.getItem(i).getId().toLowerCase());
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
                } else {
                    clearSearch.setVisibility(View.VISIBLE);
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
        if (id == R.id.search_btn) {
            AutoCompleteTextView search = (AutoCompleteTextView) findViewById(R.id.main_search);
            search.setFocusableInTouchMode(true);
            search.requestFocus();

            final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(search, InputMethodManager.SHOW_IMPLICIT);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        AutoCompleteTextView search = (AutoCompleteTextView) findViewById(R.id.main_search);
        if(search.hasFocus())
        {
            search.clearFocus();
        }
    }

}


class getStatistics extends AsyncTask<Void, Void, Void>
{
    private static final String STATISTICS_URL = "http://sopomenke.si/api/v1/stats";

    Activity activity;
    Context context;

    String stream = null;
    HTTPDataHandler hh = new HTTPDataHandler();

    JSONObject object;

    String word;
    String linkedWord;
    String result;

    public getStatistics(Activity activity)
    {
        this.activity = activity;
        this.context = activity.getBaseContext();
    }


    @Override
    protected Void doInBackground(Void... params)
    {
        stream = hh.GetHTTPData(STATISTICS_URL);

        if (stream != null)
        {
            try
            {
                // Get the full HTTP Data as JSONObject
                JSONObject reader = new JSONObject(stream);

                JSONArray latestSynonyms = reader.getJSONArray("last_synonyms");
                JSONArray latestAntonyms = reader.getJSONArray("last_antonyms");

                ArrayList<String> synonyms = new ArrayList<String>();
                ArrayList<String> antonyms = new ArrayList<String>();

                final ListView latestSynonymsList = (ListView) activity.findViewById(R.id.list_latest_synonyms);
                final ListView latestAntonymsList = (ListView) activity.findViewById(R.id.list_latest_antonyms);

                final TextView statSynonymsNum = (TextView) activity.findViewById(R.id.synonyms_stat_num);
                final TextView statAntonymsNum = (TextView) activity.findViewById(R.id.antonyms_stat_num);



                final String synonymCount = reader.getString("synonym_count");
                final String antonymCount = reader.getString("antonym_count");

                for(int i = 0; i < latestSynonyms.length(); i++)
                {
                    if(i%2==0) // Work only on every second JSON object so there's no repetition
                    {
                        object = latestSynonyms.getJSONObject(i);
                        word = object.getJSONObject("word").getString("word");
                        linkedWord = object.getJSONObject("linked_word").getString("word");

                        result = word + " >> " + linkedWord;
                        synonyms.add(result);
                    }

                }

                for(int i = 0; i < latestAntonyms.length(); i++)
                {
                    if(i%2==0)
                    {
                        object = latestAntonyms.getJSONObject(i);
                        word = object.getJSONObject("word").getString("word");
                        linkedWord = object.getJSONObject("linked_word").getString("word");

                        result = word + " >> " + linkedWord;
                        antonyms.add(result);
                    }
                }

                final ArrayAdapter<String> synonymsAdapter = new ArrayAdapter<String>(
                        context,
                        R.layout.list_item_word,
                        R.id.word_txt,
                        synonyms
                );

                final ArrayAdapter<String> antonymsAdapter = new ArrayAdapter<String>(
                        context,
                        R.layout.list_item_word,
                        R.id.word_txt,
                        antonyms
                );


                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        statSynonymsNum.setText(synonymCount);
                        statAntonymsNum.setText(antonymCount);

                        latestSynonymsList.setAdapter(synonymsAdapter);
                        latestAntonymsList.setAdapter(antonymsAdapter);
                    }
                });


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}

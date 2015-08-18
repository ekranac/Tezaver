package activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.ziga.tezaver.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapters.WordListAdapter;
import models.HTTPDataHandler;
import models.Word;


public class WordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        TextView tvWord = (TextView) findViewById(R.id.tv_word);
        TextView tvPronunciation = (TextView) findViewById(R.id.tv_pronunciation);
        Typeface robotoSlab = Typeface.createFromAsset(this.getAssets(), "RobotoSlab.ttf");

        tvWord.setTypeface(robotoSlab);
        tvPronunciation.setTypeface(robotoSlab);


        ActionBar bar = getSupportActionBar();
        if(bar!=null)
        {
            bar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.dark_blue)));
            bar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        String id = intent.getStringExtra(MainActivity.WORD_ID);

        if(id!=null)
        {
            id = id.toLowerCase();
            new getWordData(id, this).execute();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_word_activity, menu);

        return true;
    }


}

class getWordData extends AsyncTask<Void, Void, Word>
{

    public final static String WORD_ID = "com.ziga.tezaver.WORD_ID";
    private static final String WORD_URL = "http://sopomenke.si/api/v1/words/";

    String id;
    Word wordResult;
    Activity activity;


    String word;
    String pronunciation;

    List<Word> synonyms = new ArrayList<Word>(); // Sopomenke
    List<Word> antonyms = new ArrayList<Word>(); // Protipomenke

    String wordURL;

    String stream = null;
    HTTPDataHandler hh = new HTTPDataHandler();

    public getWordData(String id, Activity activity)
    {
        this.id = id;
        wordResult = new Word();
        this.activity = activity;

        this.wordURL = createWordURL(id);
    }

    @Override
    protected Word doInBackground(Void... params)
    {
        stream = hh.GetHTTPData(wordURL);

        if (stream != null)
        {
            try
            {
                // Get the full HTTP Data as JSONObject
                JSONArray reader = new JSONArray(stream);
                JSONObject object = reader.getJSONObject(0);

                word = object.getString("word");
                pronunciation = object.getString("pronunciation");

                JSONArray JSONsynonyms = object.getJSONArray("synonyms");
                JSONArray JSONantonyms = object.getJSONArray("antonyms");

                Word tempWordObject;
                for(int i = 0; i < JSONsynonyms.length(); i++)
                {
                    JSONObject tempJSONObject = JSONsynonyms.getJSONObject(i);

                    String tempId = tempJSONObject.getString("id");
                    String tempWord = tempJSONObject.getString("word");
                    String tempPronunciation = tempJSONObject.getString("pronunciation");

                    tempWordObject = new Word(tempId, tempWord, tempPronunciation, null, null);
                    synonyms.add(tempWordObject);
                }

                for(int i = 0; i < JSONantonyms.length(); i++)
                {
                    JSONObject tempJSONObject = JSONantonyms.getJSONObject(i);

                    String tempId = tempJSONObject.getString("id");
                    String tempWord = tempJSONObject.getString("word");
                    String tempPronunciation = tempJSONObject.getString("pronunciation");

                    tempWordObject = new Word(tempId, tempWord, tempPronunciation, null, null);
                    antonyms.add(tempWordObject);
                }

                wordResult = new Word(id, word, pronunciation, synonyms, antonyms);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return wordResult;
    }

    @Override
    protected void onPostExecute(Word wordResult)
    {
        TextView tvWord = (TextView) activity.findViewById(R.id.tv_word);
        TextView tvPronunciation = (TextView) activity.findViewById(R.id.tv_pronunciation);
        ListView listSynonyms = (ListView) activity.findViewById(R.id.list_synonyms);
        ListView listAntonyms = (ListView) activity.findViewById(R.id.list_antonyms);

        tvWord.setText(wordResult.getWord());
        tvPronunciation.setText(wordResult.getPronunciation());
        final WordListAdapter synonymsAdapter = new WordListAdapter(activity, wordResult.getSynonyms());
        final WordListAdapter antonymsAdapter = new WordListAdapter(activity, wordResult.getAntonyms());

        listSynonyms.setAdapter(synonymsAdapter);
        listAntonyms.setAdapter(antonymsAdapter);

        TextView emptySynonyms = (TextView) activity.findViewById(R.id.synonyms_empty);
        TextView emptyAntonyms = (TextView) activity.findViewById(R.id.antonyms_empty);

        listSynonyms.setEmptyView(emptySynonyms);
        listAntonyms.setEmptyView(emptyAntonyms);


        listSynonyms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(activity, WordActivity.class);
                intent.putExtra(WORD_ID, synonymsAdapter.getItem(i).getId().toLowerCase());
                activity.startActivity(intent);
            }
        });

        listAntonyms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(activity, WordActivity.class);
                intent.putExtra(WORD_ID, antonymsAdapter.getItem(i).getId().toLowerCase());
                activity.startActivity(intent);
            }
        });
    }

    private static String createWordURL(String wordId)
    {
        return WORD_URL + wordId.toLowerCase();
    }

}

package helpers;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.ziga.tezaver.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import adapters.LatestWordsListAdapter;
import models.HTTPDataHandler;

public class getStatistics extends AsyncTask<Void, Void, Void>
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
                HashMap<String, String> map;

                ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();;

                final JSONArray latestSynonyms = reader.getJSONArray("last_synonyms");
                JSONArray latestAntonyms = reader.getJSONArray("last_antonyms");

                ArrayList<String> synonyms = new ArrayList<String>();
                ArrayList<String> antonyms = new ArrayList<String>();

                final RecyclerView latestList = (RecyclerView) activity.findViewById(R.id.latest_list);


                final TextView statSynonymsNum = (TextView) activity.findViewById(R.id.synonyms_stat_num);
                final TextView statAntonymsNum = (TextView) activity.findViewById(R.id.antonyms_stat_num);



                final String synonymCount = reader.getString("synonym_count");
                final String antonymCount = reader.getString("antonym_count");


                map = new HashMap<String, String>();
                map.put(Constants.FIRST_COLUMN, "");
                map.put(Constants.SECOND_COLUMN, "Zadnje sopomenke");
                map.put(Constants.THIRD_COLUMN, "");

                list.add(map);

                for(int i = 0; i < latestSynonyms.length(); i+=2)
                {
                    object = latestSynonyms.getJSONObject(i);
                    word = object.getJSONObject("word").getString("word");
                    linkedWord = object.getJSONObject("linked_word").getString("word");


                    map = new HashMap<String, String>();
                    map.put(Constants.FIRST_COLUMN, word);
                    map.put(Constants.SECOND_COLUMN, ">>");
                    map.put(Constants.THIRD_COLUMN, linkedWord);

                    map.put(Constants.FIRST_ID, object.getJSONObject("word").getString("id"));
                    map.put(Constants.SECOND_ID, object.getJSONObject("linked_word").getString("id"));

                    list.add(map);
                }

                map = new HashMap<String, String>();
                map.put(Constants.FIRST_COLUMN, "");
                map.put(Constants.SECOND_COLUMN, "Zadnje protipomenke");
                map.put(Constants.THIRD_COLUMN, "");

                list.add(map);

                for(int i = 0; i < latestAntonyms.length(); i+=2)
                {
                    object = latestAntonyms.getJSONObject(i);
                    word = object.getJSONObject("word").getString("word");
                    linkedWord = object.getJSONObject("linked_word").getString("word");


                    map = new HashMap<String, String>();
                    map.put(Constants.FIRST_COLUMN, word);
                    map.put(Constants.SECOND_COLUMN, ">>");
                    map.put(Constants.THIRD_COLUMN, linkedWord);

                    map.put(Constants.FIRST_ID, object.getJSONObject("word").getString("id"));
                    map.put(Constants.SECOND_ID, object.getJSONObject("linked_word").getString("id"));

                    list.add(map);
                }

                final LatestWordsListAdapter adapter = new LatestWordsListAdapter(activity, list);



                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        statSynonymsNum.setText(synonymCount);
                        statAntonymsNum.setText(antonymCount);

                        latestList.setAdapter(adapter);
                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
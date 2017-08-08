package helpers;

import adapters.RelatedWordsListAdapter;
import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import com.ziga.tezaver.R;
import models.HTTPDataHandler;
import models.Word;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class getWordData extends AsyncTask<Void, Void, Word> {

    private static final String WORD_URL = "https://sopomenke.si/api/v1/words/";

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

    public getWordData(String id, Activity activity) {
        this.id = id;
        wordResult = new Word();
        this.activity = activity;

        this.wordURL = createWordURL(id);
    }

    @Override
    protected Word doInBackground(Void... params) {
        stream = hh.GetHTTPData(wordURL);

        if (stream != null) {
            try {
                // Get the full HTTP Data as JSONObject
                JSONArray reader = new JSONArray(stream);
                JSONObject object = reader.getJSONObject(0);

                word = object.getString("word");
                pronunciation = object.getString("pronunciation");

                JSONArray JSONsynonyms = object.getJSONArray("synonyms");
                JSONArray JSONantonyms = object.getJSONArray("antonyms");

                Word tempWordObject;
                for (int i = 0; i < JSONsynonyms.length(); i++) {
                    JSONObject tempJSONObject = JSONsynonyms.getJSONObject(i);

                    String tempId = tempJSONObject.getString("id");
                    String tempWord = tempJSONObject.getString("word");
                    String tempPronunciation = tempJSONObject.getString("pronunciation");

                    tempWordObject = new Word(tempId, tempWord, tempPronunciation, null, null);
                    synonyms.add(tempWordObject);
                }

                for (int i = 0; i < JSONantonyms.length(); i++) {
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

        RecyclerView relatedWordsList = (RecyclerView) activity.findViewById(R.id.list_related);

        tvWord.setText(wordResult.getWord());
        tvPronunciation.setText(wordResult.getPronunciation());

        final List<Word> relatedWords = new ArrayList<Word>();
        if(wordResult.getSynonyms().size()!=0)
        {
            relatedWords.add(0, new Word(null, "Sopomenke", null, null, null));

            for(Word word : wordResult.getSynonyms())
            {
                relatedWords.add(word);
            }
        }
        else
        {
            relatedWords.add(new Word(null, "Ni sopomenk! Dodaj jih na sopomenke.si", null, null, null));
        }

        if(wordResult.getAntonyms().size()!=0)
        {
            relatedWords.add(new Word(null, "Protipomenke", null, null, null));

            for(Word word : wordResult.getAntonyms())
            {
                relatedWords.add(word);
            }
        }
        else
        {
            relatedWords.add(new Word(null, "Ni protipomenk! Dodaj jih na sopomenke.si", null, null, null));
        }

        final RelatedWordsListAdapter relatedWordsAdapter = new RelatedWordsListAdapter(activity, relatedWords);
        relatedWordsList.setAdapter(relatedWordsAdapter);

        /*relatedWordsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String id = relatedWordsAdapter.getItem(i).getId();
                if(id!=null)
                {
                    Intent intent = new Intent(activity, WordActivity.class);
                    intent.putExtra(WORD_ID, id.toLowerCase());
                    activity.startActivity(intent);
                }
            }
        });*/
    }

    private static String createWordURL(String wordId)
    {
        return WORD_URL + wordId.toLowerCase();
    }


}
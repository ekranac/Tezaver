package helpers;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.ziga.tezaver.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapters.WordSearchAdapter;
import models.HTTPDataHandler;
import models.Word;

public class JSONHelper
{

    private static final String QUERY_URL = "http://sopomenke.si/api/v1/words?query=";

    public static List<Word> getWordList(Activity activity, String query)
    {
        List<Word> words = new ArrayList<Word>();
        Word wordObject;

        String id;
        String word;
        String pronunciation;

        String stream = null;
        String urlString = createQueryURL(query);

        HTTPDataHandler hh = new HTTPDataHandler();
        stream = hh.GetHTTPData(urlString);

        if (stream != null)
        {
            try
            {
                // Get the full HTTP Data as JSONObject
                JSONObject reader = new JSONObject(stream);

                JSONArray array = reader.getJSONArray("data");

                for (int i = 0; i < array.length(); i++)
                {
                    JSONObject wordJSONObject = array.getJSONObject(i);
                    id = wordJSONObject.getString("id");
                    word = wordJSONObject.getString("word");
                    pronunciation = wordJSONObject.getString("pronunciation");

                    wordObject = new Word(id, word, pronunciation, null, null);
                    words.add(wordObject);
                }

                /*JSONObject object = array.getJSONObject(0);
                JSONArray inside_array = object.getJSONArray("antonyms");

                tv.setText(inside_array.toString());*/

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return words;


    }

    private static String createQueryURL(String query)
    {
        return QUERY_URL + query;
    }
}

package helpers;

import models.HTTPDataHandler;
import models.Word;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JSONHelper
{

    private static final String QUERY_URL = "https://sopomenke.si/api/v1/words?query=";

    public static List<Word> getWordList(String query)
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

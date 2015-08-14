package adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.ziga.tezaver.R;

import java.util.ArrayList;
import java.util.List;

import helpers.JSONHelper;
import models.Word;

public class WordSearchAdapter extends ArrayAdapter<Word> implements Filterable
{
    private Activity activity;
    private Context context;
    private List<Word> wordList = new ArrayList<Word>();

    public WordSearchAdapter(Activity activity, List<Word> wordList)
    {
        super(activity.getBaseContext(), R.layout.list_item_search, wordList);
        this.wordList = wordList;
        this.activity = activity;
        this.context = activity.getBaseContext();
    }


    @Override
    public Word getItem(int position) {
        if (wordList != null)
            return wordList.get(position);

        return null;
    }

    @Override
    public int getCount() {
        if (wordList != null)
            return wordList.size();

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View result = convertView;

        if (result == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            result = inf.inflate(R.layout.list_item_search, parent, false);

        }

        TextView tv = (TextView) result.findViewById(R.id.word_txt);
        String text = wordList.get(position).getWord();
        tv.setText(text);

        return result;
    }

    @Override
    public long getItemId(int position) {
        if (wordList != null)
            return wordList.get(position).hashCode();

        return 0;
    }

    @Override
    public Filter getFilter() {
        Filter cityFilter = new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new Filter.FilterResults();
                if (constraint == null || constraint.length() < 2)
                    return results;

                List<Word> cityResultList = JSONHelper.getWordList(activity, constraint.toString());
                results.values = cityResultList;
                results.count = cityResultList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                wordList = (List) results.values;
                notifyDataSetChanged();
            }
        };

        return cityFilter;
    }
}

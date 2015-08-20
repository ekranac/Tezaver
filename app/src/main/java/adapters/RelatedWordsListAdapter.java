package adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ziga.tezaver.R;

import java.util.ArrayList;
import java.util.List;

import models.Word;


public class RelatedWordsListAdapter extends BaseAdapter
{
    private Activity activity;
    private Context context;
    private List<Word> list = new ArrayList<Word>();

    private TextView relatedWord;
    private Word wordObject;

    public RelatedWordsListAdapter(Activity activity, List<Word> list)
    {
        super();
        this.list = list;
        this.activity = activity;
        this.context = activity.getBaseContext();
    }


    @Override
    public Word getItem(int position) {
        if (list != null)
            return list.get(position);

        return null;
    }

    @Override
    public int getCount() {
        if (list != null)
            return list.size();

        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View result = convertView;

        if (result == null) {
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            result = inf.inflate(R.layout.list_item_related_word, parent, false);
        }

        relatedWord = (TextView) result.findViewById(R.id.related_word);

        wordObject = list.get(position);
        if(wordObject!=null)
        {
            relatedWord.setText(wordObject.getWord());
        }

        return result;
    }

    @Override
    public long getItemId(int position) {
        if (list != null)
            return list.get(position).hashCode();

        return 0;
    }
}

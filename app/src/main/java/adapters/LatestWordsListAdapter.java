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
import java.util.HashMap;

import helpers.Constants;


public class LatestWordsListAdapter extends BaseAdapter
{
    private Activity activity;
    private Context context;
    private ArrayList<HashMap<String, String>> list;

    TextView firstWord;
    TextView middleSpace;
    TextView secondWord;

    public LatestWordsListAdapter(Activity activity, ArrayList<HashMap<String, String>> list)
    {
        super();
        this.list = list;
        this.activity = activity;
        this.context = activity.getBaseContext();
    }


    @Override
    public Object getItem(int position) {
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
            result = inf.inflate(R.layout.list_item_latest_word, parent, false);
        }

        firstWord = (TextView) result.findViewById(R.id.first_word);
        middleSpace = (TextView) result.findViewById(R.id.middle_space);
        secondWord = (TextView) result.findViewById(R.id.second_word);

        HashMap<String, String> map = list.get(position);
        if(map!=null)
        {
            firstWord.setText(map.get(Constants.FIRST_COLUMN));
            middleSpace.setText(map.get(Constants.SECOND_COLUMN));
            secondWord.setText(map.get(Constants.THIRD_COLUMN));
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

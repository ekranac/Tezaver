package adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ziga.tezaver.R;

import java.util.ArrayList;
import java.util.List;

import models.RelatedWordViewHolder;
import models.Word;


public class RelatedWordsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Activity activity;
    private Context context;
    private List<Word> list = new ArrayList<Word>();

    private TextView relatedWord;
    private Word wordObject;

    private static final int TYPE_TITLE = 0;
    private static final int TYPE_STANDARD = 1;

    public RelatedWordsListAdapter(Activity activity, List<Word> list)
    {
        this.activity = activity;
        this.context = activity.getBaseContext();
        this.list = list;
    }

    public void updateList(List<Word> data)
    {
        list = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount()
    {
        if(list!=null)
        {
            return list.size();
        }

        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType;

        if(list.get(position).getId()==null)
        {
            viewType = TYPE_TITLE;
        }
        else
        {
            viewType = TYPE_STANDARD;
        }
        return viewType;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.list_item_related_word, viewGroup, false);

        return new RelatedWordViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position)
    {

        wordObject= list.get(position);
        RelatedWordViewHolder titleHolder = (RelatedWordViewHolder) viewHolder;
        titleHolder.title.setText(wordObject.getWord());

        if(viewHolder.getItemViewType()==TYPE_TITLE)
        {
            titleHolder.title.setTypeface(null, Typeface.BOLD);
        }
    }

}

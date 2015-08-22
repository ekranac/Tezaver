package adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ziga.tezaver.R;

import java.util.ArrayList;
import java.util.List;

import activities.WordActivity;
import helpers.Constants;
import models.RelatedWordViewHolder;
import models.Word;


public class RelatedWordsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private Activity activity;
    private List<Word> list = new ArrayList<Word>();

    private Word wordObject;

    private static final int TYPE_TITLE = 0;
    private static final int TYPE_STANDARD = 1;

    public RelatedWordsListAdapter(Activity activity, List<Word> list)
    {
        this.activity = activity;
        this.list = list;
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

        return new RelatedWordViewHolder(activity, itemView, null);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position)
    {

        wordObject= list.get(position);
        RelatedWordViewHolder titleHolder = (RelatedWordViewHolder) viewHolder;
        titleHolder.title.setText(wordObject.getWord());

        if(viewHolder.getItemViewType()==TYPE_TITLE)
        {
            titleHolder.title.setTypeface(null, Typeface.BOLD);
        }
        else
        {
            titleHolder.setWordId(list.get(position).getId());
            titleHolder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String wordId = list.get(position).getId();
                    if(wordId!=null)
                    {
                        Intent intent = new Intent(activity, WordActivity.class);
                        intent.putExtra(Constants.WORD_ID, wordId.toLowerCase());
                        activity.startActivity(intent);
                    }
                }
            });
        }
    }

}

package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ziga.tezaver.R;

import java.util.ArrayList;
import java.util.HashMap;

import activities.WordActivity;
import helpers.Constants;
import models.LatestWordViewHolder;
import models.RelatedWordViewHolder;


public class LatestWordsListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

    // RecyclerView is awesome
    // http://33.media.tumblr.com/tumblr_m951cfH1hq1rxbf33o4_250.gif

    private ArrayList<HashMap<String, String>> list;
    private Activity activity;
    private Context context;

    private static final int TYPE_TITLE = 0;
    private static final int TYPE_STANDARD = 1;


    public final static String WORD_ID = "com.ziga.tezaver.WORD_ID";

    public LatestWordsListAdapter(Activity activity, ArrayList<HashMap<String, String>> list)
    {
        this.activity = activity;
        this.context = activity.getBaseContext();
        this.list = list;
    }

    public void updateList(ArrayList<HashMap<String, String>> data)
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

        if(list.get(position).get(Constants.FIRST_COLUMN)=="")
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
        View itemView;
        switch(viewType)
        {
            case TYPE_TITLE:
                itemView = inflater.inflate(R.layout.list_item_related_word, viewGroup, false);
                RelatedWordViewHolder titleHolder = new RelatedWordViewHolder(activity, itemView, null);

                return titleHolder;

            default:
                itemView = inflater.inflate(R.layout.list_item_latest_word, viewGroup, false);
                LatestWordViewHolder mainHolder = new LatestWordViewHolder(activity, itemView, null, null);

                return mainHolder;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position)
    {

        HashMap<String, String> map = list.get(position);
        String firstValue = map.get(Constants.FIRST_COLUMN);
        String secondValue = map.get(Constants.SECOND_COLUMN);
        String thirdValue = map.get(Constants.THIRD_COLUMN);

        switch(viewHolder.getItemViewType())
        {
            case TYPE_TITLE:
                RelatedWordViewHolder titleHolder = (RelatedWordViewHolder) viewHolder;

                titleHolder.title.setTypeface(null, Typeface.BOLD);
                titleHolder.title.setText(secondValue);
                break;

            default:
                LatestWordViewHolder mainHolder = (LatestWordViewHolder) viewHolder;

                final String firstId = map.get(Constants.FIRST_ID);
                final String secondId = map.get(Constants.SECOND_ID);

                mainHolder.setFirstWordId(firstId);
                mainHolder.setSecondWordId(secondId);

                mainHolder.firstWord.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(activity, WordActivity.class);
                        intent.putExtra(WORD_ID, firstId.toLowerCase());
                        activity.startActivity(intent);
                    }
                });

                mainHolder.secondWord.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(activity, WordActivity.class);
                        intent.putExtra(WORD_ID, secondId.toLowerCase());
                        activity.startActivity(intent);
                    }
                });

                mainHolder.firstWord.setText(firstValue);
                mainHolder.middleSpace.setText(secondValue);
                mainHolder.secondWord.setText(thirdValue);
                break;
        }

    }
}

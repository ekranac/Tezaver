package models;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ziga.tezaver.R;

import activities.WordActivity;

public class RelatedWordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public Activity activity;
    public TextView title;
    public String wordId;

    public final static String WORD_ID = "com.ziga.tezaver.WORD_ID";

    public RelatedWordViewHolder(Activity activity, View itemView, String id)
    {
        super(itemView);
        itemView.setClickable(true);
        itemView.setOnClickListener(this );
        this.activity = activity;
        this.title = (TextView) itemView.findViewById(R.id.related_word);
        this.wordId = id;
    }

    public void setWordId(String wordId)
    {
        this.wordId=wordId;
    }


    @Override
    public void onClick(View view)
    {
        if(wordId!=null)
        {
            Intent intent = new Intent(activity, WordActivity.class);
            intent.putExtra(WORD_ID, wordId.toLowerCase());
            activity.startActivity(intent);
        }
    }
}
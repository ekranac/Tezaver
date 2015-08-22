package models;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ziga.tezaver.R;


public class RelatedWordViewHolder extends RecyclerView.ViewHolder {

    public Activity activity;
    public TextView title;
    public String wordId;

    public RelatedWordViewHolder(Activity activity, View itemView, String id)
    {
        super(itemView);
        itemView.setClickable(true);
        this.activity = activity;
        this.title = (TextView) itemView.findViewById(R.id.related_word);
        this.wordId = id;
    }

    public void setWordId(String wordId)
    {
        this.wordId=wordId;
    }

}
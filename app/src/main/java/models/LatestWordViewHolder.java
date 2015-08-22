package models;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ziga.tezaver.R;

public class LatestWordViewHolder extends RecyclerView.ViewHolder {

    public TextView firstWord;
    public TextView middleSpace;
    public TextView secondWord;

    public Activity activity;
    public String firstWordId;
    public String secondWordId;


    public LatestWordViewHolder(final Activity activity, View itemView, final String firstWordId, final String secondWordId)
    {
        super(itemView);

        this.activity = activity;
        this.firstWordId = firstWordId;
        this.secondWordId = secondWordId;

        firstWord = (TextView) itemView.findViewById(R.id.first_word);
        middleSpace = (TextView) itemView.findViewById(R.id.middle_space);
        secondWord = (TextView) itemView.findViewById(R.id.second_word);
    }

    public void setFirstWordId(String firstWordId)
    {
        this.firstWordId = firstWordId;
    }

    public void setSecondWordId(String secondWordId)
    {
        this.secondWordId = secondWordId;
    }
}

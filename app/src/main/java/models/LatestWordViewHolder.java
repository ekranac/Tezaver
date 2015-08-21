package models;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ziga.tezaver.R;

public class LatestWordViewHolder extends RecyclerView.ViewHolder {

    public TextView firstWord;
    public TextView middleSpace;
    public TextView secondWord;

    public LatestWordViewHolder(View itemView)
    {
        super(itemView);
        firstWord = (TextView) itemView.findViewById(R.id.first_word);
        middleSpace = (TextView) itemView.findViewById(R.id.middle_space);
        secondWord = (TextView) itemView.findViewById(R.id.second_word);
    }
}

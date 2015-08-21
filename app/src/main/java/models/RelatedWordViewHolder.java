package models;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ziga.tezaver.R;

public class RelatedWordViewHolder extends RecyclerView.ViewHolder {

    public TextView title;

    public RelatedWordViewHolder(View itemView)
    {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.related_word);
    }

}
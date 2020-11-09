package edu.depaul.assignment4;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OfficialViewHolder extends RecyclerView.ViewHolder {

    TextView officeTitle;
    TextView officeHolder;
    ImageView separator;

    public OfficialViewHolder(@NonNull View itemView) {
        super(itemView);
        officeTitle = itemView.findViewById(R.id.listTitleTextview);
        officeHolder = itemView.findViewById(R.id.listNameTextview);
        separator = itemView.findViewById(R.id.separatorImageView);
    }
}

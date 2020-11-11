package edu.depaul.assignment4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OfficialAdapter extends RecyclerView.Adapter<OfficialViewHolder> {

    private final List<Official> officialList;
    private final MainActivity mainActivity;

    public OfficialAdapter(List<Official> list, MainActivity mainActivity) {
        this.officialList = list;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public OfficialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.official_list_entry, parent, false);
        itemView.setOnClickListener(mainActivity);
        return new OfficialViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OfficialViewHolder holder, int position) {
        Official official = officialList.get(position);
        holder.officeTitle.setText(official.getOfficeName());
        holder.officeHolder.setText(String.format("%s (%s)", official.getOfficeHolder(), official.getParty()));
    }

    @Override
    public int getItemCount() {
        return officialList.size();
    }
}

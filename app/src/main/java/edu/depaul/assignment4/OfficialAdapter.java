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
    private String party = "";

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

        if(official.getParty().equals("Republican") || official.getParty().equals("Democratic")) {
            party = String.format("%s Party", official.getParty());
        }else
            party = official.getParty();

        holder.officeHolder.setText(String.format("%s (%s)", official.getOfficeHolder(), party));
    }

    @Override
    public int getItemCount() {
        return officialList.size();
    }
}

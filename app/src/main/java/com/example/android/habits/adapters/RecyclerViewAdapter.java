package com.example.android.habits.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.habits.R;
import com.example.android.habits.models.RemindMe;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private int itemsNumber;
    private final ListItemClickListener mListener;
    private List<RemindMe> remindMeList;

    public RecyclerViewAdapter(int itemsNumber, ListItemClickListener listener, List<RemindMe> remindMeList) {
        this.itemsNumber = itemsNumber;
        this.mListener = listener;
        this.remindMeList = remindMeList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recycler_view_remind_me;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        holder.remindMeTitleTv.setText(this.remindMeList.get(position).getTitle().toString());
    }

    @Override
    public int getItemCount() {
        return this.remindMeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView remindMeTitleTv;

        public ViewHolder(View itemView) {
            super(itemView);
            remindMeTitleTv = (TextView) itemView.findViewById(R.id.taskTitleTv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mListener.OnListItemClick(position);
        }
    }

    public interface ListItemClickListener{
        void OnListItemClick(int clickedItemId);
    }
}

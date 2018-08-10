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
import com.example.android.habits.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TasksRecyclerViewAdapter extends RecyclerView.Adapter<TasksRecyclerViewAdapter.ViewHolder> {

    private int itemsNumber;
    private final ListItemClickListener mListener;
    private List<String> tasksList;


    public List<Task> getTasksList() {
        List<Task> tasks = new ArrayList<>();
        for (String s : tasksList)
            tasks.add(new Task(s));

        return tasks;
    }

    public TasksRecyclerViewAdapter(int itemsNumber, ListItemClickListener listener, List<String> tasksList) {
        this.itemsNumber = itemsNumber;
        this.mListener = listener;
        this.tasksList = tasksList;
    }

    @NonNull
    @Override
    public TasksRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recycler_view_task;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TasksRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.taskNameTv.setText(this.tasksList.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return this.tasksList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView taskNameTv;

        public ViewHolder(View itemView) {
            super(itemView);
            taskNameTv = (TextView) itemView.findViewById(R.id.taskNameTv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            mListener.OnListItemClick(position);
        }
    }

    public interface ListItemClickListener {
        void OnListItemClick(int clickedItemId);
    }
}

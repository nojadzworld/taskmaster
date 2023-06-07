package com.dajone.taskmaster.adapters;

import static com.dajone.taskmaster.MainActivity.TASK_DESCRIPTION_EXTRAS_TAG;
import static com.dajone.taskmaster.MainActivity.TASK_NAME_EXTRAS_TAG;
import static com.dajone.taskmaster.MainActivity.TASK_STATUS_EXTRAS_TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dajone.taskmaster.R;
import com.dajone.taskmaster.activities.TaskDetail;
import com.dajone.taskmaster.models.Task;

import java.util.List;

public class TaskListRecyclerViewAdapter<T> extends RecyclerView.Adapter<TaskListRecyclerViewAdapter.TaskListViewHolder> {

    private List<Task> tasks;
    Context callingActivity;

    public TaskListRecyclerViewAdapter(List<Task> tasks, Context callingActivity) {
        this.tasks = tasks;
        this.callingActivity = callingActivity;
    }


    @NonNull
    @Override
    public TaskListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View taskFragment = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task_list, parent, false);
        return new TaskListViewHolder(taskFragment);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Button taskFragmentButton = (Button) holder.itemView.findViewById(R.id.button_task_list_fragment_task_list_item);
        Task task = (Task) tasks.get(position);
        String taskName = task.getTitle();
        taskFragmentButton.setText(taskName);

        setupTaskButton(taskFragmentButton, task);
    }

    public void setupTaskButton(Button goToTaskButton, Task task) {
        goToTaskButton.setOnClickListener(v -> {
            Intent goToTaskDetailsIntent = new Intent(callingActivity, TaskDetail.class);

            String taskName = task.getTitle();
            goToTaskDetailsIntent.putExtra(TASK_NAME_EXTRAS_TAG, taskName);

            String taskDescription = task.getBody();
            goToTaskDetailsIntent.putExtra(TASK_DESCRIPTION_EXTRAS_TAG, taskDescription);
            String taskStatus = task.getStatus().toString();
            goToTaskDetailsIntent.putExtra(TASK_STATUS_EXTRAS_TAG, taskStatus);

            callingActivity.startActivity(goToTaskDetailsIntent);
        });
    }


    public void updateTasksData(List<Task> updatedTasks) {
        tasks.clear();
        tasks.addAll(updatedTasks);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public static class TaskListViewHolder extends RecyclerView.ViewHolder {
        public TaskListViewHolder(View fragmentItemView) {
            super(fragmentItemView);
        }
    }
}

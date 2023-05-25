package com.dajone.taskmaster.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.dajone.taskmaster.R;

public class TaskListFragment extends Fragment {




    public TaskListFragment() {

    }

    public static TaskListFragment newInstance(String param1, String param2) {
        TaskListFragment fragment = new TaskListFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     return inflater.inflate(R.layout.fragment_task_list, container, false);
    }

}
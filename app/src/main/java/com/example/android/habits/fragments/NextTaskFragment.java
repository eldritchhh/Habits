package com.example.android.habits.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.habits.R;

public class NextTaskFragment extends Fragment {

    private TextView nextElementTv;
    private String nextElement;

    public NextTaskFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_next_element, container, false);

        nextElementTv = (TextView) rootView.findViewById(R.id.nextElementTv);

        nextElementTv.setText(nextElement);

        return rootView;
    }

    public void setNextTask(String text) {
        this.nextElementTv.setText(text);
    }
}

package com.example.finalwork2.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.finalwork2.Activity.PYQActivity;
import com.example.finalwork2.R;

public class WorldFragment extends Fragment implements View.OnClickListener{
    private ConstraintLayout l_friend,l_letter;
    private View view;
    private Intent intent;


    public void init_view(){
        l_friend=view.findViewById(R.id.w_friend);
        l_letter=view.findViewById(R.id.w_letter);
        l_friend.setOnClickListener(this);
        l_letter.setOnClickListener(this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_world, container, false);
        init_view();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.w_friend:
                intent=new Intent(getActivity(), PYQActivity.class);
                startActivity(intent);
                break;
            case R.id.w_letter:
                break;
        }
    }
}

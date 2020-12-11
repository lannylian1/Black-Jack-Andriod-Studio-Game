package com.example.bottom_nav_shell;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class ResultFragment extends Fragment {
    TextView tvResult;
    SharedPreferences pref0, pref1, pref2, pref3;
    String s0, s1, s2,s3;

    //background tap dismiss keys
    private Context mContext;
    private Activity mActivity;
    private ConstraintLayout cLayout;


    public static ResultFragment newInstance() {
        return new ResultFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.result_fragment, container, false);


        /*all activity code here*/
        //add background tap to dismiss keyboard
        mContext = getContext().getApplicationContext();
        mActivity = getActivity();
        cLayout = (ConstraintLayout) root.findViewById(R.id.constraint_result);
        cLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });


        tvResult = (TextView)root.findViewById(R.id.lblView);
        pref0 = getContext().getSharedPreferences("result_score", Context.MODE_PRIVATE);
        pref3 = getContext().getSharedPreferences("result_name", Context.MODE_PRIVATE);

        s0 = pref0.getString("score", null);
        s3 = pref3.getString("name", null);

        tvResult.setText(s3 + " Your Points are : " + s0);

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

}
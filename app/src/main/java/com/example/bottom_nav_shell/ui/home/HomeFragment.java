package com.example.bottom_nav_shell.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.bottom_nav_shell.R;

public class HomeFragment extends Fragment {
    EditText edName;
    Button btnNext;
    SharedPreferences pref;
    Intent intent;

    //background tap dismiss keys
    private Context mContext;
    private Activity mActivity;
    private ConstraintLayout cLayout;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        /*all activity code here*/
        //add background tap to dismiss keyboard
        mContext = getContext().getApplicationContext();
        mActivity = getActivity();

        cLayout = (ConstraintLayout) root.findViewById(R.id.constraint_home);
        cLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });

        edName = (EditText)root.findViewById(R.id.txtName);
        btnNext = (Button) root.findViewById(R.id.btnNext);
        //intent = new Intent(HomeFragment.this, WelcomeFragment.class);
        //shared preferences
        pref = getActivity().getSharedPreferences("result_name", Context.MODE_PRIVATE);

        //button listener
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtName = edName.getText().toString();
                if (!txtName.equals("")) {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("name", txtName);
                    editor.commit();
                    Toast.makeText(getContext().getApplicationContext(),"Name Successfully stored. Go to Welcome page", Toast.LENGTH_SHORT).show();

                } else {
                    System.out.println("testElse");
                    Toast.makeText(getContext().getApplicationContext(),"Please Enter a Name", Toast.LENGTH_SHORT).show();
                }

            }
        });



        return root;
    }
}
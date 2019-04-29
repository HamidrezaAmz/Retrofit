package com.example.myretrofittest.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.example.myretrofittest.MainActivity;
import com.example.myretrofittest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMenu extends Fragment implements View.OnClickListener {


    public FragmentMenu() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        view.findViewById(R.id.button_simple_call).setOnClickListener(this);
        view.findViewById(R.id.button_send_object).setOnClickListener(this);
        view.findViewById(R.id.button_upload_file).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_simple_call:
                if (getActivity() != null)
                    ((MainActivity) getActivity()).setFragment(new FragmentSimpleApiCall());
                break;
            case R.id.button_send_object:
                if (getActivity() != null)
                    ((MainActivity) getActivity()).setFragment(new FragmentSendObjectRequestBody());
                break;
            case R.id.button_upload_file:
                if (getActivity() != null)
                    ((MainActivity) getActivity()).setFragment(new FragmentUploadFile());
                break;
        }
    }
}

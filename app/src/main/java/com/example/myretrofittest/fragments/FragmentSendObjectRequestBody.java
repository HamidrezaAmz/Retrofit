package com.example.myretrofittest.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myretrofittest.BuildConfig;
import com.example.myretrofittest.Interfaces.UserClient;
import com.example.myretrofittest.MainActivity;
import com.example.myretrofittest.Objects.User;
import com.example.myretrofittest.R;
import com.example.myretrofittest.Utils.PublicValue;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSendObjectRequestBody extends Fragment implements View.OnClickListener {

    Button buttonSubmit;
    EditText editTextTitle, editTextBody, editTextUserId;

    public FragmentSendObjectRequestBody() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_send_object_request_body, container, false);

        view.findViewById(R.id.button_submit).setOnClickListener(this);
        editTextTitle = view.findViewById(R.id.editText_title);
        editTextBody = view.findViewById(R.id.editText_body);
        editTextUserId = view.findViewById(R.id.editText_user_id);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_submit:
                User user = new User(
                        editTextTitle.getText().toString(),
                        editTextBody.getText().toString(),
                        Integer.parseInt(editTextUserId.getText().toString())
                );
                sendNetworkRequest(user);
                break;
        }
    }

    private void sendNetworkRequest(User user) {

        if (getActivity() == null)
            return;

        ((MainActivity) getActivity()).showDialog();

        // create okHttpClient
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(loggingInterceptor);
        }

        // create retrofit instance
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(PublicValue.BASE_URL_API)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClientBuilder.build());

        Retrofit retrofit = builder.build();

        // get client & call object for the request
        UserClient client = retrofit.create(UserClient.class);

        Call<User> call = client.createAccount(user);

        // execute network request
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                if (getActivity() != null)
                    ((MainActivity) getActivity()).hideDialog();
                Toast.makeText(getContext(), "Success :) ID: " + response.body().getId(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<User> call, Throwable t) {
                if (getActivity() != null)
                    ((MainActivity) getActivity()).hideDialog();
                Toast.makeText(getContext(), "Error :(", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

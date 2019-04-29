package com.example.myretrofittest.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myretrofittest.Adapters.GithubRepoAdapter;
import com.example.myretrofittest.Interfaces.GitHubClient;
import com.example.myretrofittest.MainActivity;
import com.example.myretrofittest.Objects.GitHubRepo;
import com.example.myretrofittest.R;
import com.example.myretrofittest.Utils.PublicValue;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSimpleApiCall extends Fragment {

    private RecyclerView recyclerView;

    public FragmentSimpleApiCall() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_simple_api_call, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        recyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getGitHubRepos();
    }

    private void getGitHubRepos() {

        if (getActivity() == null)
            return;

        ((MainActivity) getActivity()).showDialog();
        Retrofit.Builder builder = new Retrofit
                .Builder()
                .baseUrl(PublicValue.BASE_URL_GITHUB)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        GitHubClient gitHubClient = retrofit.create(GitHubClient.class);

        Call<List<GitHubRepo>> call = gitHubClient.reposFromUser("hamidrezaamz");

        call.enqueue(new Callback<List<GitHubRepo>>() {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response) {
                ((MainActivity) getActivity()).hideDialog();
                List<GitHubRepo> gitHubRepos = response.body();
                recyclerView.setAdapter(new GithubRepoAdapter(getContext(), gitHubRepos));
            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t) {
                ((MainActivity) getActivity()).hideDialog();
                Toast.makeText(getContext(), "Error!", Toast.LENGTH_LONG).show();
            }
        });

    }


}

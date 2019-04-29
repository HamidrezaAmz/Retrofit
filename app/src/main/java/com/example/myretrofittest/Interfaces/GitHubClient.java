package com.example.myretrofittest.Interfaces;

import com.example.myretrofittest.Objects.GitHubRepo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubClient {

    @GET("/users/{user}/repos")
    Call<List<GitHubRepo>> reposFromUser(@Path("user") String user);

}

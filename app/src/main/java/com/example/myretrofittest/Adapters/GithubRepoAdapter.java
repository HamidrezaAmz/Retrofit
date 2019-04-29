package com.example.myretrofittest.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myretrofittest.Objects.GitHubRepo;
import com.example.myretrofittest.R;

import java.util.List;

public class GithubRepoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<GitHubRepo> gitHubRepos;

    public GithubRepoAdapter(Context context, List<GitHubRepo> gitHubRepos) {
        this.context = context;
        this.gitHubRepos = gitHubRepos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyler_view_item, parent, false);
        return new RepoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((RepoViewHolder) holder).bind(gitHubRepos.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return gitHubRepos.size();
    }

    private class RepoViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle;

        public RepoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.title);
        }

        public void bind(String title) {
            textViewTitle.setText(title);
        }
    }
}

package com.example.android.steamsearchapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.steamsearchapp.data.SteamSearchCatalogue;
import com.example.android.steamsearchapp.data.SteamApp;
import com.example.android.steamsearchapp.data.SteamAppDataContainer;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SteamSearchAdapter extends RecyclerView.Adapter<SteamSearchAdapter.SearchResultsViewHolder> {
    private List<SteamApp> steamAppList;
    private SearchResultClickListener resultClickListener;

    interface SearchResultClickListener {
        void onSearchResultClicked(SteamApp steamApp);
    }

    public SteamSearchAdapter(SteamSearchAdapter.SearchResultClickListener listener) {
        this.resultClickListener = listener;
    }

    public void updateSearchResults(List<SteamApp> steamAppList) {
        this.steamAppList = steamAppList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SteamSearchAdapter.SearchResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.search_result_item, parent, false);
        return new SteamSearchAdapter.SearchResultsViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (this.steamAppList != null) {
            return this.steamAppList.size();
        } else {
            return 0;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull SteamSearchAdapter.SearchResultsViewHolder holder, int position) {
        holder.bind(this.steamAppList.get(position));
    }

    class SearchResultsViewHolder extends RecyclerView.ViewHolder {
        private TextView searchResultTV;

        SearchResultsViewHolder(View itemView) {
            super(itemView);
            this.searchResultTV = itemView.findViewById(R.id.tv_search_result);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resultClickListener.onSearchResultClicked(
                            steamAppList.get(getAdapterPosition())
                    );
                }
            });
        }
        void bind(SteamApp app) {
            this.searchResultTV.setText(app.name);
        }
    }
}

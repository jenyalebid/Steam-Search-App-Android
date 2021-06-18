package com.example.android.steamsearchapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.steamsearchapp.data.SteamApp;
import com.example.android.steamsearchapp.data.SteamAppDataContainer;

import java.util.List;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class favsAdapter extends RecyclerView.Adapter<favsAdapter.favsHolder> {

    //private static final String TAG = weatherRetrofit.class.getSimpleName();

    private List<SteamAppDataContainer> favsTable;

    private OnFavClickListener onFavFavClickListener;

    public interface OnFavClickListener {

        void onFavClick(SteamApp fav);

    }

    public favsAdapter(OnFavClickListener favClickListener) {

        this.onFavFavClickListener = favClickListener;
    }

    public void updateFavs(List<SteamAppDataContainer> fav) {

        favsTable = fav;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public favsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wishlist_item, parent, false);

        favsHolder favsHolderVar = new favsHolder(view);

        return favsHolderVar;
    }

    @Override
    public void onBindViewHolder(@NonNull favsHolder holder, int position) {
        holder.bind(this.favsTable.get(position));

    }

    @Override
    public int getItemCount() {

        if(favsTable != null) {

            return favsTable.size();
        }
        else {
            return 0;
        }
    }

    public class favsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView fav;
        TextView price;
        TextView category;
        ImageView banner;

        public favsHolder(@NonNull View itemView) {

            super(itemView);
            fav = itemView.findViewById(R.id.tv_game_name);
            price = itemView.findViewById(R.id.tv_game_price);
            category = itemView.findViewById(R.id.tv_game_category);
            banner = itemView.findViewById(R.id.tv_game_image);
            itemView.setOnClickListener(this);
        }

        public void bind(SteamAppDataContainer favTable) {
            Context ctx = this.itemView.getContext();
            fav.setText(favTable.name);
            price.setText(favTable.finalPrice);
            category.setText(favTable.type);
            Glide.with(ctx)
                    .load(favTable.headerImageUrl)
                    .into(banner);
            Log.d(TAG, "GOT GAME: " + favTable.name);
        }

        @Override
        public void onClick(View v) {

            SteamAppDataContainer appDataContainer = favsTable.get(getAdapterPosition());
            SteamApp pass = new SteamApp();
            pass.appid = appDataContainer.appid.toString();
            onFavFavClickListener.onFavClick(pass);

        }
    }
}

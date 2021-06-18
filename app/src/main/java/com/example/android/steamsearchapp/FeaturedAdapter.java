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
import com.example.android.steamsearchapp.data.Featured;
import com.example.android.steamsearchapp.data.FeaturedWin;
import com.example.android.steamsearchapp.data.SteamApp;
import com.example.android.steamsearchapp.data.SteamAppDataContainer;

import java.util.List;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class FeaturedAdapter extends RecyclerView.Adapter<FeaturedAdapter.featuredHolder> {

    //private static final String TAG = weatherRetrofit.class.getSimpleName();

    private List<FeaturedWin> featuredWinList;

    private OnFeaturedClickListener onFeaturedClickListener;

    public interface OnFeaturedClickListener {

        void onFeaturedClick(SteamApp fav);

    }

    public FeaturedAdapter(OnFeaturedClickListener featuredClickListener) {

        onFeaturedClickListener = featuredClickListener;
    }

    public void updateFeatured(List<FeaturedWin> featured) {

        featuredWinList = featured;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public featuredHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_item, parent, false);

        featuredHolder featuredHolderVar = new featuredHolder(view);

        return featuredHolderVar;
    }

    @Override
    public void onBindViewHolder(@NonNull featuredHolder holder, int position) {

        holder.bind(this.featuredWinList.get(position));
    }

    @Override
    public int getItemCount() {

        if(featuredWinList != null) {

            return featuredWinList.size();
        }
        else {
            return 0;
        }
    }

    public class featuredHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView fav;
        TextView price;
        TextView category;
        ImageView banner;

        public featuredHolder(@NonNull View itemView) {

            super(itemView);
            fav = itemView.findViewById(R.id.f_game_name);
            //price = itemView.findViewById(R.id.tv_game_price);
            //category = itemView.findViewById(R.id.tv_game_category);
            banner = itemView.findViewById(R.id.f_game_image);
            itemView.setOnClickListener(this);
        }

        public void bind(FeaturedWin featuredWin) {
            Context ctx = this.itemView.getContext();
            fav.setText(featuredWin.name);
            //price.setText(favTable.finalPrice);
            //category.setText(featuredWin);
            Glide.with(ctx)
                    .load(featuredWin.header_image)
                    .into(banner);
            Log.d(TAG, "GOT GAME: " + featuredWin.name);
        }

        @Override
        public void onClick(View v) {

            FeaturedWin featuredWin = featuredWinList.get(getAdapterPosition());
            SteamApp pass = new SteamApp();
            pass.appid = featuredWin.id.toString();
            onFeaturedClickListener.onFeaturedClick(pass);

        }
    }
}

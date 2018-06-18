package com.enihsyou.androidexamination.adapter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.enihsyou.androidexamination.R;
import com.enihsyou.androidexamination.adapter.MyMovieRecyclerViewAdapter.MovieViewHolder;
import com.enihsyou.androidexamination.domain.MovieItem;
import com.enihsyou.androidexamination.domain.MovieSearchResult;
import com.enihsyou.androidexamination.fragment.MovieFragment.OnMovieListFragmentInteractionListener;
import com.enihsyou.androidexamination.network.Networking;
import com.enihsyou.androidexamination.network.Networking.SuccessCallback;


/**
 * 用于电影列表的数据适配器
 */
public class MyMovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private static final String TAG = MyMovieRecyclerViewAdapter.class.getSimpleName();

    private MovieSearchResult movieItems;

    private final OnMovieListFragmentInteractionListener interactionListener;

    private final Networking networking;

    public MyMovieRecyclerViewAdapter(
        MovieSearchResult items, Networking networking, OnMovieListFragmentInteractionListener listener) {
        this.movieItems = items;
        this.interactionListener = listener;
        this.networking = networking;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_movie_item, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder holder, int position) {

        final MovieItem[] subjects = this.movieItems.getSubjects();
        if (position >= subjects.length || position < 0) {
            Log.d(TAG, String.format("尝试获取不存在第%d个的元素，共有%d个", position, subjects.length));
            return;
        }
        final MovieItem item = subjects[position];
        holder.bind(item);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != MyMovieRecyclerViewAdapter.this.interactionListener) {
                    MyMovieRecyclerViewAdapter.this.interactionListener.onMovieItemSelected(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (this.movieItems == null) return 0;
        return this.movieItems.getSubjects().length;
    }

    /** 保存数据的类 */
    class MovieViewHolder extends RecyclerView.ViewHolder {

        final View view;

        final TextView movieTitle;

        final ImageView movieImage;

        MovieViewHolder(View view) {
            super(view);
            this.view = view;
            this.movieTitle = view.findViewById(R.id.movie_title);
            this.movieImage = view.findViewById(R.id.movie_post);
        }

        void bind(MovieItem newItem) {
            this.movieTitle.setText(newItem.getTitle());
            MyMovieRecyclerViewAdapter.this.networking.downloadImage(newItem.getImageUrl(),
                new SuccessCallback<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        MovieViewHolder.this.movieImage.setImageBitmap(response);
                    }
                }, null);
        }

        @Override
        public String toString() {
            return String.format("%s '%s'", super.toString(), this.movieTitle.getText());
        }
    }
}

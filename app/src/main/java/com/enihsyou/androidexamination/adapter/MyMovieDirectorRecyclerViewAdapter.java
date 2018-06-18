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
import com.enihsyou.androidexamination.adapter.MyMovieDirectorRecyclerViewAdapter.DirectorViewHolder;
import com.enihsyou.androidexamination.domain.MovieCastItem;
import com.enihsyou.androidexamination.network.Networking;
import com.enihsyou.androidexamination.network.Networking.SuccessCallback;


/**
 * 用于职员导演列表的数据适配器
 */
public class MyMovieDirectorRecyclerViewAdapter extends RecyclerView.Adapter<DirectorViewHolder> {

    private static final String TAG = MyMovieDirectorRecyclerViewAdapter.class.getSimpleName();

    private MovieCastItem[] castItems;

    private final Networking networking;

    public MyMovieDirectorRecyclerViewAdapter(
        MovieCastItem[] items, Networking networking) {
        this.castItems = items;
        this.networking = networking;
    }

    @NonNull
    @Override
    public DirectorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_director_item, parent, false);

        return new DirectorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final DirectorViewHolder holder, int position) {

        final MovieCastItem[] subjects = this.castItems;
        if (position >= subjects.length || position < 0) {
            Log.d(TAG, String.format("尝试获取不存在第%d个的元素，共有%d个", position, subjects.length));
            return;
        }
        holder.bind(subjects[position]);
    }

    @Override
    public int getItemCount() {
        return this.castItems.length;
    }

    /** 保存数据的类 */class DirectorViewHolder extends RecyclerView.ViewHolder {

        final View view;

        final TextView directoryTitle;

        final ImageView directoryImage;

        DirectorViewHolder(View view) {
            super(view);
            this.view = view;
            this.directoryTitle = view.findViewById(R.id.director_name);
            this.directoryImage = view.findViewById(R.id.director_image);
        }

        void bind(MovieCastItem newItem) {
            // 设置导演名字
            this.directoryTitle.setText(newItem.getName());

            // 获取并设置导演图片
            if (newItem.getAvatarImageUrl() != null) {
                MyMovieDirectorRecyclerViewAdapter.this.networking.downloadImage(newItem.getAvatarImageUrl(),
                    new SuccessCallback<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            DirectorViewHolder.this.directoryImage.setImageBitmap(response);
                        }
                    }, null); // 这里不需要提供异常回调函数
            }
        }

        @Override
        public String toString() {
            return String.format("%s '%s'", super.toString(), this.directoryTitle.getText());
        }
    }
}

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
import com.enihsyou.androidexamination.adapter.MyMovieCelebrityRecyclerViewAdapter.CelebrityViewHolder;
import com.enihsyou.androidexamination.domain.MovieCastItem;
import com.enihsyou.androidexamination.network.Networking;
import com.enihsyou.androidexamination.network.Networking.SuccessCallback;


/**
 * 用于电影演员列表的数据适配器
 */
public class MyMovieCelebrityRecyclerViewAdapter extends RecyclerView.Adapter<CelebrityViewHolder> {

    private static final String TAG = MyMovieCelebrityRecyclerViewAdapter.class.getSimpleName();

    private MovieCastItem[] castItems;

    private final Networking networking;

    public MyMovieCelebrityRecyclerViewAdapter(
        MovieCastItem[] items, Networking networking) {
        this.castItems = items;
        this.networking = networking;
    }

    @NonNull
    @Override
    public CelebrityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_celebrity_item, parent, false);

        return new CelebrityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CelebrityViewHolder holder, int position) {

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

    /** 保存数据的类 */
    class CelebrityViewHolder extends RecyclerView.ViewHolder {

        final View view;

        final TextView castTitle;

        final ImageView castImage;

        CelebrityViewHolder(View view) {
            super(view);
            this.view = view;
            this.castTitle = view.findViewById(R.id.cast_name);
            this.castImage = view.findViewById(R.id.cast_image);
        }

        void bind(MovieCastItem newItem) {
            // 设置演员名字
            this.castTitle.setText(newItem.getName());

            // 获取并设置演员图片
            if (newItem.getAvatarImageUrl() != null) {
                MyMovieCelebrityRecyclerViewAdapter.this.networking.downloadImage(newItem.getAvatarImageUrl(),
                    new SuccessCallback<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            CelebrityViewHolder.this.castImage.setImageBitmap(response);
                        }
                    }, null); // 这里不需要提供异常回调函数
            }
        }

        @Override
        public String toString() {
            return String.format("%s '%s'", super.toString(), this.castTitle.getText());
        }
    }
}

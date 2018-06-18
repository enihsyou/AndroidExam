package com.enihsyou.androidexamination;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.enihsyou.androidexamination.adapter.MyMovieCelebrityRecyclerViewAdapter;
import com.enihsyou.androidexamination.adapter.MyMovieDirectorRecyclerViewAdapter;
import com.enihsyou.androidexamination.domain.MovieItem;
import com.enihsyou.androidexamination.network.Networking;
import com.enihsyou.androidexamination.network.Networking.SuccessCallback;
import com.enihsyou.androidexamination.network.VolleyNetworking;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String ARG_MOVIE_ITEM = "movie_item";

    public static final String ACK_MOVIE_DETAIL = "movie_detail";

    private ImageView moviePost;

    private TextView movieTitle;

    private TextView movieAltTitle;

    private TextView movieDate;

    private TextView movieGenre;

    private TextView movieRating;

    private TextView movieViewCounts;

    private RecyclerView celebrityList;

    private RecyclerView directorList;

    // private TextView movieSummery;

    private MovieItem item;

    private Networking networking;

    public static Intent newIntent(Context parent, @NonNull MovieItem movieItem) {
        Intent intent = new Intent(parent, MovieDetailActivity.class);

        intent.putExtra(ARG_MOVIE_ITEM, movieItem);

        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        getWindow().setTitle("当前热映");

        this.moviePost = findViewById(R.id.movie_image);
        this.movieTitle = findViewById(R.id.movie_title);
        this.movieAltTitle = findViewById(R.id.movie_alt_title);
        this.movieDate = findViewById(R.id.movie_date);
        this.movieGenre = findViewById(R.id.movie_genre);
        this.movieRating = findViewById(R.id.movie_rating);
        this.movieViewCounts = findViewById(R.id.movie_view_counts);

        this.celebrityList = findViewById(R.id.celebrity_list);
        this.directorList = findViewById(R.id.directory_list);

        // 从保存状态中获得items列表
        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.item = (MovieItem) extras.getSerializable(ARG_MOVIE_ITEM);
        } else if (savedInstanceState != null) {
            this.item = (MovieItem) savedInstanceState.getSerializable(ARG_MOVIE_ITEM);
        }

        this.networking = new VolleyNetworking(this);

        bind(this.item);

        this.moviePost.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent();
                final CharSequence text = movieTitle.getText();
                intent.putExtra(ACK_MOVIE_DETAIL, text);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        this.movieTitle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // https://blog.csdn.net/weixin_40604111/article/details/78674563
                // 点击影片标题的时候，弹出通知栏
                NotificationManager manager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                final String channelId = "Channel 001";
                final Notification notification;
                // 处理API26的新变化
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel channel =
                        new NotificationChannel("0", channelId, NotificationManager.IMPORTANCE_LOW);
                    manager.createNotificationChannel(channel);


                    notification = new Builder(MovieDetailActivity.this, channelId)
                        .setCategory(Notification.CATEGORY_MESSAGE)
                        .setContentText(movieTitle.getText())
                        .setContentInfo("评分: " + movieRating.getText())
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setAutoCancel(true)
                        .build();
                } else {

                    notification = new Builder(MovieDetailActivity.this, channelId)
                        .setContentText(movieTitle.getText())
                        .setContentInfo("评分: " + movieRating.getText())
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .build();
                }
                manager.notify(0, notification);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_MOVIE_ITEM, this.item);
    }

    @SuppressLint("DefaultLocale")
    public void bind(@Nullable MovieItem item) {
        if (item == null) return;

        downloadAndBindImageTo(item.getImageUrl(), this.moviePost);

        // 暂时不使用字符串资源
        // https://developer.android.com/guide/topics/resources/string-resource?hl=zh-cn
        this.movieTitle.setText(String.format("片名: %s", item.getTitle()));

        final String originalTitle = item.getOriginalTitle();
        if (originalTitle != null) this.movieAltTitle.setText(String.format("又名: %s", originalTitle));

        this.movieDate.setText(String.format("上映日期: %d 年", item.getYear()));
        this.movieGenre.setText(String.format("类型: %s", TextUtils.join(", ", item.getGeneres())));
        this.movieRating.setText(String.format("平均得分: %.1f分", item.getRating()));
        this.movieViewCounts.setText(String.format("评分人数: %d人", item.getCollectCount()));

        // 设置演职员列表
        this.celebrityList.setAdapter(new MyMovieCelebrityRecyclerViewAdapter(item.getCasts(), this.networking));
        this.directorList.setAdapter(new MyMovieDirectorRecyclerViewAdapter(item.getDirectors(), this.networking));
    }

    private void downloadAndBindImageTo(@NonNull String resourseUrl, @NonNull final ImageView bindTo) {
        this.networking.downloadImage(resourseUrl, new SuccessCallback<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                bindTo.setImageBitmap(response);
            }
        }, null);
    }
}

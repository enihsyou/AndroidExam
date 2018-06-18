package com.enihsyou.androidexamination.network;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.enihsyou.androidexamination.domain.MovieSearchResult;

public interface Networking {

    void downloadImage(
        @NonNull final String imageUrl,
        @Nullable final SuccessCallback<Bitmap> successCallback,
        @Nullable final ErrorCallback errorCallback);

    void searchMovie(
        @NonNull String keyword,
        @Nullable final SuccessCallback<MovieSearchResult> successCallback,
        @Nullable final ErrorCallback errorCallback);

    void doubanInTheater(
        @Nullable final SuccessCallback<MovieSearchResult> successCallback,
        @Nullable final ErrorCallback errorCallback);

    /**未实现*/
    @Deprecated
    void searchBook(
        @NonNull String keyword,
        @Nullable final SuccessCallback<MovieSearchResult>successCallback,
        @Nullable final ErrorCallback errorCallback);

    /**获取成功的回调*/
    interface SuccessCallback<T> {

        void onResponse(T response);
    }

    /** 函数调用失败的回调 */
    interface ErrorCallback {

        void onError(Exception exception);
    }
}

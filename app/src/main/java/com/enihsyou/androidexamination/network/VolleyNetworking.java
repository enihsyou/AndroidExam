package com.enihsyou.androidexamination.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.enihsyou.androidexamination.domain.MovieCastItem;
import com.enihsyou.androidexamination.domain.MovieItem;
import com.enihsyou.androidexamination.domain.MovieSearchResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class VolleyNetworking implements Networking {

    private final RequestQueue queue;

    public VolleyNetworking(final Context context) {
        this.queue = Volley.newRequestQueue(context);
    }

    @Override
    public void downloadImage(
        @NonNull final String imageUrl,
        @Nullable final SuccessCallback<Bitmap> successCallback,
        @Nullable final ErrorCallback errorCallback) {

        final ImageRequest imageRequest = new ImageRequest(imageUrl, new Listener<Bitmap>() {
            @Override
            public void onResponse(final Bitmap response) {
                if (successCallback != null) successCallback.onResponse(response);
            }
        }, 0, 0, null, null, new ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                if (errorCallback != null) errorCallback.onError(error);
            }
        });
        this.queue.add(imageRequest);
    }

    @Override
    public void searchMovie(
        @NonNull String keyword,
        @Nullable final SuccessCallback<MovieSearchResult> successCallback,
        @Nullable final ErrorCallback errorCallback) {

        Uri uri = Uri.parse("https://api.douban.com/v2/movie/search").buildUpon()
            .appendQueryParameter("q", keyword)
            .build();

        final JsonObjectRequest request = makeRequest(successCallback, errorCallback, uri);

        this.queue.add(request);
    }

    @Override
    public void doubanInTheater(
        @Nullable final SuccessCallback<MovieSearchResult> successCallback,
        @Nullable final ErrorCallback errorCallback) {

        Uri uri = Uri.parse("https://api.douban.com/v2/movie/in_theaters");

        final JsonObjectRequest request = makeRequest(successCallback, errorCallback, uri);

        this.queue.add(request);
    }

    @NonNull
    private JsonObjectRequest makeRequest(
        @Nullable final SuccessCallback<MovieSearchResult> successCallback,
        @Nullable final ErrorCallback errorCallback, Uri uri) {
        return new JsonObjectRequest(Method.GET, uri.toString(), null, new Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (successCallback != null) {
                    final MovieSearchResult parsedResult = parseToMovieSearchResult(response);
                    successCallback.onResponse(parsedResult);
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (errorCallback != null) errorCallback.onError(error);
            }
        });
    }

    private MovieSearchResult parseToMovieSearchResult(JSONObject object) {
        final MovieSearchResult result = new MovieSearchResult();
        try {
            result.setCount(object.getInt("count"));
            result.setStart(object.getInt("start"));
            result.setTotal(object.getInt("total"));
            final JSONArray subjectsArray = object.getJSONArray("subjects");
            MovieItem[] subjects = new MovieItem[subjectsArray.length()];
            for (int index = 0; index < subjectsArray.length(); index++) {
                subjects[index] = parseToMovieItem(subjectsArray.getJSONObject(index));
            }
            result.setSubjects(subjects);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private MovieItem parseToMovieItem(JSONObject object) {
        final MovieItem result = new MovieItem();
        try {

            // 解析演员部分
            final JSONArray castsArray = object.getJSONArray("casts");
            MovieCastItem[] casts = new MovieCastItem[castsArray.length()];
            for (int index = 0; index < castsArray.length(); index++) {
                casts[index] = parseToMovieCastItem(castsArray.getJSONObject(index));
            }

            // 解析导演部分
            final JSONArray directorsArray = object.getJSONArray("directors");
            MovieCastItem[] directors = new MovieCastItem[directorsArray.length()];
            for (int index = 0; index < directorsArray.length(); index++) {
                directors[index] = parseToMovieCastItem(directorsArray.getJSONObject(index));
            }

            // 解析类型部分
            final JSONArray generesArray = object.getJSONArray("genres");
            String[] generes = new String[generesArray.length()];
            for (int index = 0; index < generesArray.length(); index++) {
                generes[index] = generesArray.getString(index);
            }

            result
                .setId(object.getString("id"))
                .setTitle(object.getString("title"))
                .setOriginalTitle(object.getString("original_title"))
                .setAltUrl(object.getString("alt"))
                .setCollectCount(object.getInt("collect_count"))
                .setImageUrl(object.getJSONObject("images").getString("large"))
                .setYear(object.getInt("year"))
                .setRating(object.getJSONObject("rating").getDouble("average"))
                .setCasts(casts)
                .setDirectors(directors)
                .setGeneres(generes);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    private MovieCastItem parseToMovieCastItem(JSONObject object) {
        final MovieCastItem result = new MovieCastItem();
        try {
            // 处理有的演职人员在豆瓣数据库里没有信息的情况
            JSONObject avatars = object.optJSONObject("avatars");
            if (avatars == null) avatars = new JSONObject();

            result
                .setId(object.getString("id"))
                .setName(object.getString("name"))
                .setAvatarImageUrl(avatars.optString("large"));
        } catch (JSONException e) {
            System.out.println(result.getName());
            e.printStackTrace();
        }
        return result;
    }

    /** 在 Java 1.7 环境下不好使用 */
    @Deprecated
    private <R> List<R> parse(JSONObject object, java.lang.reflect.Method method) {
        List<R> result = new ArrayList<>(object.length());
        for (int index = 0; index < object.length(); index++) {
            try {
                //noinspection unchecked
                result.add((R) method.invoke(this, object));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}

package com.enihsyou.androidexamination.domain;

import java.io.Serializable;

public class MovieItem implements Serializable {

    private String id;

    private String title;

    private String originalTitle;

    /** 电影封面图片地址 */
    private String imageUrl;

    /** 电影在豆瓣的地址 */
    private String altUrl;

    /** 分类的数组 */
    private String[] generes;

    /** 平均评分 */
    private int collectCount;

    private String summary;

    private int year;

    /** 平均评分 */
    private double rating;

    /** 演员 */
    private MovieCastItem[] casts;

    /** 导演 */
    private MovieCastItem[] directors;

    public String getId() {
        return this.id;
    }

    public MovieItem setId(String id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public MovieItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getOriginalTitle() {
        return this.originalTitle;
    }

    public MovieItem setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
        return this;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public MovieItem setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getAltUrl() {
        return this.altUrl;
    }

    public MovieItem setAltUrl(String altUrl) {
        this.altUrl = altUrl;
        return this;
    }

    public String[] getGeneres() {
        return this.generes;
    }

    public MovieItem setGeneres(String[] generes) {
        this.generes = generes;
        return this;
    }

    public int getCollectCount() {
        return this.collectCount;
    }

    public MovieItem setCollectCount(int collectCount) {
        this.collectCount = collectCount;
        return this;
    }

    public String getSummary() {
        return this.summary;
    }

    public MovieItem setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public int getYear() {
        return this.year;
    }

    public MovieItem setYear(int year) {
        this.year = year;
        return this;
    }

    public double getRating() {
        return this.rating;
    }

    public MovieItem setRating(double rating) {
        this.rating = rating;
        return this;
    }

    public MovieCastItem[] getCasts() {
        return this.casts;
    }

    public MovieItem setCasts(MovieCastItem[] casts) {
        this.casts = casts;
        return this;
    }

    public MovieCastItem[] getDirectors() {
        return this.directors;
    }

    public MovieItem setDirectors(MovieCastItem[] directors) {
        this.directors = directors;
        return this;
    }

    @Override
    public String toString() {
        return "MovieItem{" +
               "id='" + id + '\'' +
               ", title='" + title + '\'' +
               '}';
    }
}


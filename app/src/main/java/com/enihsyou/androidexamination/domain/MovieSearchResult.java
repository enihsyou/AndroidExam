package com.enihsyou.androidexamination.domain;

import java.io.Serializable;

public class MovieSearchResult implements Serializable {

    private int count;
    private int start;
    private int total;

    private MovieItem[] subjects;

    public int getCount() {
        return this.count;
    }

    public MovieSearchResult setCount(int count) {
        this.count = count;
        return this;
    }

    public int getStart() {
        return this.start;
    }

    public MovieSearchResult setStart(int start) {
        this.start = start;
        return this;
    }

    public int getTotal() {
        return this.total;
    }

    public MovieSearchResult setTotal(int total) {
        this.total = total;
        return this;
    }

    public MovieItem[] getSubjects() {
        return this.subjects;
    }

    public MovieSearchResult setSubjects(MovieItem[] subjects) {
        this.subjects = subjects;
        return this;
    }
}

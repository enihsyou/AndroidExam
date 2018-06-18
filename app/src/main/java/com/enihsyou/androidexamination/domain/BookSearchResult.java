package com.enihsyou.androidexamination.domain;

@Deprecated
public class BookSearchResult {

    private int count;
    private int start;
    private int total;

    private BookItem[] subjects;

    public int getCount() {
        return this.count;
    }

    public BookSearchResult setCount(int count) {
        this.count = count;
        return this;
    }

    public int getStart() {
        return this.start;
    }

    public BookSearchResult setStart(int start) {
        this.start = start;
        return this;
    }

    public int getTotal() {
        return this.total;
    }

    public BookSearchResult setTotal(int total) {
        this.total = total;
        return this;
    }

    public BookItem[] getSubjects() {
        return this.subjects;
    }

    public BookSearchResult setSubjects(BookItem[] subjects) {
        this.subjects = subjects;
        return this;
    }
}

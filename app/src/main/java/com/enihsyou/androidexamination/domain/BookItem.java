package com.enihsyou.androidexamination.domain;

@Deprecated
public class BookItem {

    private String id;

    private String isbn;

    private String title;

    /** 图书封面图片地址 */
    private String imageUrl;

    /** 图书在豆瓣的地址 */
    private String altUrl;

    /** 作者名字的数组 */
    private String[] authors;

    /** 出版商 */
    private String publisher;

    /** 出版日期 */
    private String pubdate;

    /** 平均评分 */
    private double rating;

    /** 评分人数 */
    private int collectCount;

    private String summary;

    /** 页数 */
    private int pages;

    public String getId() {
        return this.id;
    }

    public BookItem setId(String id) {
        this.id = id;
        return this;
    }

    public String getIsbn() {
        return this.isbn;
    }

    public BookItem setIsbn(String isbn) {
        this.isbn = isbn;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public BookItem setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public BookItem setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getAltUrl() {
        return this.altUrl;
    }

    public BookItem setAltUrl(String altUrl) {
        this.altUrl = altUrl;
        return this;
    }

    public String[] getAuthors() {
        return this.authors;
    }

    public BookItem setAuthors(String[] authors) {
        this.authors = authors;
        return this;
    }

    public String getPublisher() {
        return this.publisher;
    }

    public BookItem setPublisher(String publisher) {
        this.publisher = publisher;
        return this;
    }

    public String getPubdate() {
        return this.pubdate;
    }

    public BookItem setPubdate(String pubdate) {
        this.pubdate = pubdate;
        return this;
    }

    public double getRating() {
        return this.rating;
    }

    public BookItem setRating(double rating) {
        this.rating = rating;
        return this;
    }

    public int getCollectCount() {
        return this.collectCount;
    }

    public BookItem setCollectCount(int collectCount) {
        this.collectCount = collectCount;
        return this;
    }

    public String getSummary() {
        return this.summary;
    }

    public BookItem setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public int getPages() {
        return this.pages;
    }

    public BookItem setPages(int pages) {
        this.pages = pages;
        return this;
    }

    @Override
    public String toString() {
        return "BookItem{" +
               "id='" + id + '\'' +
               ", title='" + title + '\'' +
               '}';
    }
}

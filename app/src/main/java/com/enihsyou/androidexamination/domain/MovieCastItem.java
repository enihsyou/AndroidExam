package com.enihsyou.androidexamination.domain;

import java.io.Serializable;

public class MovieCastItem implements Serializable {

    private String name;

    private String id;

    private String avatarImageUrl;

    public String getName() {
        return this.name;
    }

    public MovieCastItem setName(String name) {
        this.name = name;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public MovieCastItem setId(String id) {
        this.id = id;
        return this;
    }

    public String getAvatarImageUrl() {
        return this.avatarImageUrl;
    }

    public MovieCastItem setAvatarImageUrl(String avatarImageUrl) {
        this.avatarImageUrl = avatarImageUrl;
        return this;
    }
}

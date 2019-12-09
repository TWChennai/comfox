package com.comfox.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
// moving relatedMessages separately

public class ComfoxMessage {
    @JsonProperty
    private String content;

    @JsonProperty
    private String postedBy;

    @JsonProperty
    private String userImage;

    @JsonProperty
    private List<ComfoxMessage> relatedMessages;

    public String getContent() {
        return content;
    }

    public ComfoxMessage(String content, String postedBy, String userImage, List<ComfoxMessage> relatedMessages) {
        this.content = content;
        this.postedBy = postedBy;
        this.userImage = userImage;
        this.relatedMessages = relatedMessages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ComfoxMessage that = (ComfoxMessage) o;

        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (postedBy != null ? !postedBy.equals(that.postedBy) : that.postedBy != null) return false;
        if (userImage != null ? !userImage.equals(that.userImage) : that.userImage != null) return false;
        return relatedMessages != null ? relatedMessages.equals(that.relatedMessages) : that.relatedMessages == null;
    }
}

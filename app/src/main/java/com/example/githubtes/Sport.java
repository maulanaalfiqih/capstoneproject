package com.example.githubtes;

public class Sport {
    private String title;
    private String author;
    private String description;
    private String photo;
    private String content;
    private String publishedAt;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() { return author; }

    public void setAuthor(String author) { this.author = author; }

    public String getContent() { return content;}

    public void setContent(String content) { this.content = content; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPublishedAt() { return publishedAt; }

    public void setPublishedAt(String publishedAt) { this.publishedAt = publishedAt;}
}

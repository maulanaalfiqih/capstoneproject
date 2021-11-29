package com.example.githubtes.dataBookmark;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName="tblbookmark")
public class BookmarkList implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @ColumnInfo(name = "title")
    @NonNull
    private String title;
    @ColumnInfo(name = "author")
    private String author;
    @ColumnInfo(name = "description")
    private String description;
    @ColumnInfo(name = "content")
    private String content;
    @ColumnInfo(name = "publish")
    private String publish;
    @ColumnInfo(name = "photo")
    private String photo;

    public int getStatus_fav() {
        return status_fav;
    }

    public void setStatus_fav(int status_fav) {
        this.status_fav = status_fav;
    }

    private int status_fav;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}

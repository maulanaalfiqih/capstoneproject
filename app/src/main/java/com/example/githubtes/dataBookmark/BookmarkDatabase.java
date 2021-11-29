package com.example.githubtes.dataBookmark;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities={BookmarkList.class},version = 1)
public abstract class BookmarkDatabase extends RoomDatabase {
    public abstract BookmarkDao bookmarkDao();
}

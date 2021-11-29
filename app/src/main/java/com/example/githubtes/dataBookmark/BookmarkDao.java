package com.example.githubtes.dataBookmark;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long addData(BookmarkList bookmarkList);

    @Query("SELECT * FROM tblbookmark ORDER BY id DESC")
    List<BookmarkList> selectAllBookmark();

    @Delete
    int deleteBookmark(BookmarkList bookmarkList);

    @Query("SELECT EXISTS (SELECT 1 FROM tblbookmark WHERE title = :id)")
    public int isBookmark(String id);

}

package com.folahan.unilorinapp.CommentClassDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.folahan.unilorinapp.Model.CommentClass;

import java.util.List;

@Dao
public interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CommentClass comment);

    @Update
    void update(CommentClass comment);

    @Delete
    void delete(CommentClass comment);

    @Query("DELETE FROM comment_class")
    void deleteAllComments();

    @Query("SELECT * FROM comment_class ORDER BY likeNo ")
    LiveData<List<CommentClass>> getAllNotes();
}

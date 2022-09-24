package com.folahan.unilorinapp.PostQuestionDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.folahan.unilorinapp.Model.QuestionList;

import java.util.List;

@Dao
public interface QuestionDoa {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     void insert(QuestionList list);

    @Update
     void update(QuestionList list);

    @Delete
    void delete(QuestionList list);

    @Query("DELETE FROM QuestionList")
     void deleteAllNotes();

    @Query("SELECT * FROM QUESTIONLIST ORDER BY id DESC")
     LiveData<List<QuestionList>> getAllNotes();
}

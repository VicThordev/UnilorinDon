package com.folahan.unilorinapp.CommentClassDatabase;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.folahan.unilorinapp.Model.CommentClass;


import java.util.List;

class CommentRepository {
    private final CommentDao commentDao;
    private final LiveData<List<CommentClass>> allComments;

    CommentRepository(Application application) {
        CommentDatabase database = CommentDatabase.getDatabase(application);
        commentDao = database.commentDao();
        allComments = commentDao.getAllNotes();
    }

    LiveData<List<CommentClass>> getAllComments() {
        return allComments;
    }

    void insert(CommentClass comment) {
        CommentDatabase.databaseWriteExecutor.execute(() ->
                commentDao.insert(comment));
    }

    void update(CommentClass comment) {
        CommentDatabase.databaseWriteExecutor.execute(() ->
                commentDao.update(comment));
    }

    void delete(CommentClass comment) {
        CommentDatabase.databaseWriteExecutor.execute(() ->
                commentDao.delete(comment));
    }

    void deleteAll() {
        CommentDatabase.databaseWriteExecutor.execute(commentDao::deleteAllComments);
    }
}

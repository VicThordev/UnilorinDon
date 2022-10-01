package com.folahan.unilorinapp.CommentClassDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.folahan.unilorinapp.Model.CommentClass;


import java.util.List;

public class CommentViewModel extends AndroidViewModel {

    private final CommentRepository mRepository;

    private final LiveData<List<CommentClass>> mAllComments;

    public CommentViewModel (@NonNull Application application) {
        super(application);
        mRepository = new CommentRepository(application);
        mAllComments = mRepository.getAllComments();
    }

    public void insert(CommentClass comment) {
        mRepository.insert(comment);
    }

    public void update(CommentClass comment) {
        mRepository.update(comment);
    }

    public void delete(CommentClass comment) {
        mRepository.delete(comment);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public LiveData<List<CommentClass>> getAllComment() {
        return mAllComments;
    }
}

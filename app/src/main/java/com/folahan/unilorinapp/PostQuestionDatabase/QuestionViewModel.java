package com.folahan.unilorinapp.PostQuestionDatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.folahan.unilorinapp.Model.QuestionList;

import java.util.List;

public class QuestionViewModel extends AndroidViewModel {

    private final QuestionRepository mRepository;

    private final LiveData<List<QuestionList>> mAllQuestions;


    public QuestionViewModel (@NonNull Application application) {
        super(application);
        mRepository = new QuestionRepository(application);
        mAllQuestions = mRepository.getAllQuestion();
    }



    public void insert(QuestionList list) {
        mRepository.insert(list);
    }

    public void update1(QuestionList list) {
        mRepository.update(list);
    }

    public void delete(QuestionList list) {
        mRepository.delete(list);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public LiveData<List<QuestionList>> getAllQuestion() {
        return mAllQuestions;
    }

}

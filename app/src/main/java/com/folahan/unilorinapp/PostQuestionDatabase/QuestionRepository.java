package com.folahan.unilorinapp.PostQuestionDatabase;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.folahan.unilorinapp.Model.QuestionList;

import java.util.List;

class QuestionRepository {
    private final QuestionDoa questionDoa;
    private final LiveData<List<QuestionList>> allQuestion;

    QuestionRepository(Application application) {
        QuestionDatabase database = QuestionDatabase.getDatabase(application);
        questionDoa = database.questionDoa();
        allQuestion = questionDoa.getAllNotes();;
    }

    LiveData<List<QuestionList>> getAllQuestion() {
        return allQuestion;
    }

    void insert(QuestionList list) {
        QuestionDatabase.databaseWriteExecutor.execute(() ->
                questionDoa.insert(list));
    }

    void update(QuestionList list) {
        QuestionDatabase.databaseWriteExecutor.execute(() ->
                questionDoa.update(list));
    }

    void delete(QuestionList list) {
        QuestionDatabase.databaseWriteExecutor.execute(() ->
                questionDoa.delete(list));
    }

    void deleteAll() {
        QuestionDatabase.databaseWriteExecutor.execute(questionDoa::deleteAllNotes);
    }
}

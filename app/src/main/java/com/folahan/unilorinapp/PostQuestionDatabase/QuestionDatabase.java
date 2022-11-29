package com.folahan.unilorinapp.PostQuestionDatabase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.folahan.unilorinapp.Model.QuestionList;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database( entities = {QuestionList.class}, version =1, exportSchema = false)
public abstract class QuestionDatabase extends RoomDatabase {

    private static volatile QuestionDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public abstract QuestionDoa questionDoa();

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static QuestionDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (QuestionDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            QuestionDatabase.class, "QuestionList")
                            .addCallback(sQuestionDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sQuestionDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {
                QuestionDoa doa = INSTANCE.questionDoa();
                doa.deleteAllNotes();


            });
        }
    };
 }

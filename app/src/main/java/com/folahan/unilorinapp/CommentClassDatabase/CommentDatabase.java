package com.folahan.unilorinapp.CommentClassDatabase;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.folahan.unilorinapp.Model.CommentClass;
import com.folahan.unilorinapp.Model.QuestionList;
import com.folahan.unilorinapp.PostQuestionDatabase.QuestionDatabase;
import com.folahan.unilorinapp.PostQuestionDatabase.QuestionDoa;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {CommentClass.class}, version =2, exportSchema = false)
public abstract class CommentDatabase extends RoomDatabase {

    private static volatile CommentDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public abstract CommentDao commentDao();

    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static CommentDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CommentDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CommentDatabase.class, "QuestionList")
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
                CommentDao doa = INSTANCE.commentDao();
                doa.deleteAllComments();


            });
        }
    };
}

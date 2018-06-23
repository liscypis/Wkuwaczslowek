package com.lisowski.wojtek.wkuwaczswek.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.lisowski.wojtek.wkuwaczswek.DAO.SectionDao;
import com.lisowski.wojtek.wkuwaczswek.DAO.WordsDao;
import com.lisowski.wojtek.wkuwaczswek.entities.Section;
import com.lisowski.wojtek.wkuwaczswek.entities.Words;

import java.util.concurrent.Executors;

@Database(entities = {Words.class, Section.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract WordsDao wordsDao();
    public abstract SectionDao sectionDao();

    private static final String TAG = "AppDatabase";

    private static AppDatabase INSTANCE;

    public synchronized static AppDatabase getInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
            Log.d(TAG, "getInstance: duuuupa");
        }
        return INSTANCE;
    }

    private static AppDatabase buildDatabase(final Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "database")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG, "run: stop");
                                INSTANCE.sectionDao().insertAll(Section.populateData());
                                Log.d(TAG, "chuju dobrze");
                                INSTANCE.wordsDao().insertAll(Words.populateDataWords());
                            }
                        });
                    }
                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {

                    }
                })
                .build();
    }
}

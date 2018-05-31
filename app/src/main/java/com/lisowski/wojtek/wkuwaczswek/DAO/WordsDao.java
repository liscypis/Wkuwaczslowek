package com.lisowski.wojtek.wkuwaczswek.DAO;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.lisowski.wojtek.wkuwaczswek.entities.Words;

import java.util.List;
@Dao
public interface WordsDao {
    @Query("SELECT * FROM Words")
    List<Words> getAll();

    @Query("SELECT * FROM Words WHERE wid IN (:wordsIds)")
    List<Words> loadAllByIds(int[] wordsIds);

    @Insert
    void insertAll(Words... words);

}

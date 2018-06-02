package com.lisowski.wojtek.wkuwaczswek.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.lisowski.wojtek.wkuwaczswek.entities.Section;


import java.util.List;
@Dao
public interface SectionDao {
    @Query("SELECT * FROM Section")
    List<Section> getAll();

    @Query("SELECT * FROM Section WHERE sid IN (:sectionsIds)")
    List<Section> loadAllByIds(int[] sectionsIds);

    @Query("SELECT sid FROM Section WHERE sid = (SELECT max(sid) FROM Section)")
    int getLastID();

    @Insert
    void insertAll(Section... sections);

    @Delete
    void delete(Section sections);

    @Update
    void updateSection(Section section);
}

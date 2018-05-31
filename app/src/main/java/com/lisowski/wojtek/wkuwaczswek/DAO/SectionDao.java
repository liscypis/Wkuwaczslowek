package com.lisowski.wojtek.wkuwaczswek.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.lisowski.wojtek.wkuwaczswek.entities.Section;


import java.util.List;
@Dao
public interface SectionDao {
    @Query("SELECT * FROM Section")
    List<Section> getAll();

    @Query("SELECT * FROM Section WHERE sid IN (:sectionsIds)")
    List<Section> loadAllByIds(int[] sectionsIds);

    @Insert
    void insertAll(Section... sections);
}

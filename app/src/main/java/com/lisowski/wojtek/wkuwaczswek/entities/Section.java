package com.lisowski.wojtek.wkuwaczswek.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.util.Log;

import java.util.ArrayList;

@Entity
public class Section {
    private static final String TAG = "Section";
    @PrimaryKey
    private int sid;
    @ColumnInfo(name = "nameOfSection")
    private String nameOfSection;

    public Section(int sid, String nameOfSection) {
        this.sid = sid;
        this.nameOfSection = nameOfSection;
    }
    @Ignore
    public Section(String nameOfSection) {
        this.nameOfSection = nameOfSection;
    }

    @Ignore
    private ArrayList<Words> wordsArrayList = new ArrayList<>();
    @Ignore
    private boolean isSelected;

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }



    public String getNameOfSection() {
        return nameOfSection;
    }

    public void setNameOfSection(String nameOfSection) {
        this.nameOfSection = nameOfSection;
    }

    public ArrayList<Words> getWordsArrayList() {
        return wordsArrayList;
    }

    public void addWord(Words words) {
        wordsArrayList.add(words);
    }

    public void printWords() {
        for (Words w: wordsArrayList) {
            Log.d(TAG, "printWords: "+ w.toString());
        }
    }
    public static Section[] populateData() {
        return new Section[]{
                new Section(1, "Rodzina"),
                new Section(2, "a"),
                new Section(3, "s"),
                new Section(4, "d"),
                new Section(5, "f"),
                new Section(6, "s"),
                new Section(7, "Rodzina"),
                new Section(8, "a"),
                new Section(9, "s"),
                new Section(10, "d"),
                new Section(11, "f"),
                new Section(12, "s"),
                new Section(13, "Rodzina"),
                new Section(14, "a"),
                new Section(15, "s"),
                new Section(16, "d"),
                new Section(17, "f"),
                new Section(18, "s"),
                new Section(19, "Rodzina"),
                new Section(20, "a"),
                new Section(21, "s"),
                new Section(22, "d"),
                new Section(23, "f"),
                new Section(24, "s"),
        };
    }

    @Override
    public String toString() {
        return nameOfSection;
    }
}

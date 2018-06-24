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
                new Section(2, "Przedmioty szkolne"),
                new Section(3, "Sprzęty domowe"),
                new Section(4, "Meble"),
                new Section(5, "Budynki"),
                new Section(6, "Kuchnia")
        };
    }

    @Override
    public String toString() {
        return nameOfSection;
    }
}

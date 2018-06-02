package com.lisowski.wojtek.wkuwaczswek.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;


@Entity(foreignKeys = @ForeignKey(entity = Section.class,
        parentColumns = "sid",
        childColumns = "section_id",
        onDelete = CASCADE))
public class Words {
    @PrimaryKey
    private int wid;
    @ColumnInfo(name = "word")
    private String word;
    @ColumnInfo(name = "translation")
    private String translation;
    @ColumnInfo(name = "section_id")
    private int section_id;

    @Ignore
    private boolean isSelected;

    public Words(int wid, String word, String translation, int section_id) {
        this.wid = wid;
        this.word = word;
        this.translation = translation;
        this.section_id = section_id;
    }

    public int getSection_id() {
        return section_id;
    }

    public void setSection_id(int section_id) {
        this.section_id = section_id;
    }

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    @Ignore
    public Words(String word, String translation) {
        this.word = word;
        this.translation = translation;
    }

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public static Words[] populateDataWords() {
        return new Words[]{
                new Words(1, "Tata", "Dad", 1),
                new Words(2, "Tata", "Dad", 1),
                new Words(3, "Tata", "Dad", 1),
                new Words(4, "Tata", "Dad", 1),
                new Words(5, "Tata", "Dad", 1),
                new Words(6, "Tata", "Dad", 1),
                new Words(7, "Tata", "Dad", 1),
        };
    }

    @Override
    public String toString() {
        return "Words{" +
                "wid=" + wid +
                ", word='" + word + '\'' +
                ", translation='" + translation + '\'' +
                ", section_id=" + section_id +
                ", isSelected=" + isSelected +
                '}';
    }
}

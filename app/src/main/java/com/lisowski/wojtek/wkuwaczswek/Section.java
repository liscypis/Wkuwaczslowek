package com.lisowski.wojtek.wkuwaczswek;

import java.util.ArrayList;

public class Section {
    private String nameOfSection;
    private ArrayList<Words> wordsArrayList = new ArrayList<>();
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public Section(String nameOfSection) {
        this.nameOfSection = nameOfSection;
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

    @Override
    public String toString() {
        return nameOfSection;
    }
}

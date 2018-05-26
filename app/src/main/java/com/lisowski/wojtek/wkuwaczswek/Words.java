package com.lisowski.wojtek.wkuwaczswek;

public class Words {
    private String word;
    private String translation;
    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

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

    @Override
    public String toString() {
        return "Words{" +
                "word='" + word + '\'' +
                ", translation='" + translation + '\'' +
                '}';
    }
}

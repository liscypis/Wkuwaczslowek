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
                new Words(1, "gospodyni ", "housewife", 1),
                new Words(2, "kochanka", "lover", 1),
                new Words(3, "kochanek", "lover", 1),
                new Words(4, "małżeństwo", "marriage", 1),
                new Words(5, "mąż", "husband", 1),
                new Words(6, "pan młody", "groom", 1),
                new Words(7, "panna młoda", "bride ", 1),
                new Words(8, "babcia", "grandmother", 1),
                new Words(9, "bliźnięta", "twins", 1),
                new Words(10, "brat", "brother", 1),
                new Words(11, "bratanek", "nephew", 1),
                new Words(12, "bratanica", "niece", 1),
                new Words(13, "ciotka", "aunt", 1),
                new Words(14, "córka", "daughter", 1),
                new Words(15, "dziadek", "grandfather", 1),
                new Words(16, "dzieci", "children", 1),
                new Words(17, "dziecko", "child", 1),
                new Words(18, "krewni", "relatives", 1),
                new Words(19, "kuzyn", "cousin", 1),
                new Words(20, "kuzynka", "cousin", 1),
                new Words(21, "małżeństwo", "marriage", 1),
                new Words(22, "matka", "mother", 1),
                new Words(23, "ojciec", "father", 1),
                new Words(24, "potomek", "descendant", 1),
                new Words(25, "prawnuki", "grandchildren", 1),
                new Words(26, "przodkowie", "ancestors", 1),
                new Words(27, "rodzice", "parents", 1),
                new Words(28, "sierota", "orphan", 1),
                new Words(29, "siostra", "sister", 1),
                new Words(30, "siostrzenica", "niece", 1),
                new Words(31, "siostrzeniec", "nephew", 1),
                new Words(32, "syn", "son", 1),
                new Words(33, "wdowa", "widow", 1),
                new Words(34, "wdowiec", "widower", 1),
                new Words(35, "wujek", "uncle", 1),
                new Words(36, "plastyka", "art", 2),
                new Words(37, "biologia", "biology", 2),
                new Words(38, "chemia", "chemistry", 2),
                new Words(39, "angielski", "english", 2),
                new Words(40, "geografia", "geography", 2),
                new Words(41, "historia", "history", 2),
                new Words(42, "informatyka", "IT", 2),
                new Words(43, "matematyka", "maths", 2),
                new Words(44, "muzyka", "music", 2),
                new Words(45, "wychowanie fizyczne", "PE", 2),
                new Words(46, "fizyka", "physics", 2),
                new Words(47, "pralka", "washing machine", 3),
                new Words(48, "lodówka", "fridge", 3),
                new Words(49, "zamrażarka", "freezer", 3),
                new Words(50, "komputer", "computer", 3),
                new Words(51, "drukarka", "printer", 3),
                new Words(52, "skaner", "scanner", 3),
                new Words(53, "mikrofalówka", "microwave", 3),
                new Words(54, "mikser", "blender", 3),
                new Words(55, "suszarka do włosów", "hairdryer", 3),
                new Words(56, "telewizor", "TV", 3),
                new Words(57, "krzesło", "chair", 4),
                new Words(58, "fotel", "armchair", 4),
                new Words(59, "stół", "table", 4),
                new Words(60, "szafa", "wardrobe", 4),
                new Words(61, "kanapa", "sofa", 4),
                new Words(62, "łóżko", "bed", 4),
                new Words(63, "umywalka", "washbasin", 4),
                new Words(64, "zlew", "sink", 4),
                new Words(65, "kredens", "cupboard", 4),
                new Words(66, "kuchenka", "cooker", 4),
                new Words(67, "schody", "stairs", 4),
                new Words(68, "kuźnia", "smithy", 5),
                new Words(69, "stajnia", "stable", 5),
                new Words(70, "zamek", "castle", 5),
                new Words(71, "obora", "byre", 5),
                new Words(72, "latarnia", "lighthouse", 5),
                new Words(73, "rezydencja", "mansion", 5),
                new Words(74, "pałac", "palace", 5),
                new Words(75, "stodoła", "barn", 5),
                new Words(76, "nóż", "knife", 6),
                new Words(77, "piekarnik", "oven", 6),
                new Words(78, "łyżka", "spoon", 6),
                new Words(79, "widelec", "fork", 6),
                new Words(80, "fartuch", "apron", 6),
                new Words(81, "talerz", "plate", 6),
                new Words(82, "garnek", "pot", 6),
                new Words(83, "szklanka", "glass", 6),
                new Words(84, "kubek", "mug", 6),
                new Words(85, "zmywarka", "dishwasher", 6),
                new Words(86, "czajnik", "kettle", 6),
                new Words(87, "kran", "tap", 6),
                new Words(88, "żywność", "food", 6)
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

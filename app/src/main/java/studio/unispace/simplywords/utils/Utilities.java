package studio.unispace.simplywords.utils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import studio.unispace.simplywords.models.Word;

/**
 * Created by haof on 8/7/2016.
 */
public class Utilities {

    public static void sortWordsByInitialLetter (List<Word> words) {
        Comparator<Word> comparator = new Comparator<Word>() {
            @Override
            public int compare(Word lhs, Word rhs) {
                return lhs.word.compareToIgnoreCase(rhs.word);
            }
        };
        sortWordsBasic(words, comparator);
    }

    public static void sortWordsByInitialLetterReverse (List<Word> words) {
        Comparator<Word> comparator = new Comparator<Word>() {
            @Override
            public int compare(Word lhs, Word rhs) {
                return rhs.word.compareToIgnoreCase(lhs.word);
            }
        };
        sortWordsBasic(words, comparator);
    }

    public static void sortWordsByRating (List<Word> words) {
        Comparator<Word> comparator = new Comparator<Word>() {
            @Override
            public int compare(Word lhs, Word rhs) {
                return (int)(rhs.rating - lhs.rating);
            }
        };
        sortWordsBasic(words, comparator);
    }

    public static void sortWordsByRatingReverse (List<Word> words) {
        Comparator<Word> comparator = new Comparator<Word>() {
            @Override
            public int compare(Word lhs, Word rhs) {
                return (int)(lhs.rating - rhs.rating);
            }
        };
        sortWordsBasic(words, comparator);
    }

    public static void sortWordsByCreatedDate (List<Word> words) {
        Comparator<Word> comparator = new Comparator<Word>() {
            @Override
            public int compare(Word lhs, Word rhs) {
                return (int)(rhs.createTime - lhs.createTime);
            }
        };
        sortWordsBasic(words, comparator);
    }

    public static void sortWordsByCreatedDateReverse (List<Word> words) {
        Comparator<Word> comparator = new Comparator<Word>() {
            @Override
            public int compare(Word lhs, Word rhs) {
                return (int)(lhs.createTime - rhs.createTime);
            }
        };
        sortWordsBasic(words, comparator);
    }

    private static void sortWordsBasic (List<Word> words, Comparator<Word> comparator) {
        Collections.sort(words, comparator);
    }

}

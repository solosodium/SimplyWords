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
        Collections.sort(words, new Comparator<Word>() {
            @Override
            public int compare(Word lhs, Word rhs) {
                return lhs.word.compareToIgnoreCase(rhs.word);
            }
        });
    }

    public static void sortWordsByRating (List<Word> words) {
        Collections.sort(words, new Comparator<Word>() {
            @Override
            public int compare(Word lhs, Word rhs) {
                return (int)(rhs.rating - lhs.rating);
            }
        });
    }

    public static void sortWordsByCreatedDate (List<Word> words) {
        Collections.sort(words, new Comparator<Word>() {
            @Override
            public int compare(Word lhs, Word rhs) {
                return (int)(rhs.createTime - lhs.createTime);
            }
        });
    }

}

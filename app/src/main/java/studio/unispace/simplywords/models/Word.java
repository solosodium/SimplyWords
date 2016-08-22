package studio.unispace.simplywords.models;

import java.util.Calendar;

/**
 * Created by haof on 7/21/2016.
 */
public class Word {

    public static final String TAG = "Word.java";

    public Long id;
    public String word;
    public String definition;
    public String remark;
    public Long createTime;
    public Float rating;

    public Word () {
        id = Calendar.getInstance().getTimeInMillis();
        word = "";
        definition = "";
        remark = "";
        createTime = Calendar.getInstance().getTimeInMillis() / 1000L;
        rating = 0f;
    }

    // copy constructor
    public Word (Word w) {
        id = w.id;
        word = w.word;
        definition = w.definition;
        remark = w.remark;
        createTime = w.createTime;
        rating = w.rating;
    }

}

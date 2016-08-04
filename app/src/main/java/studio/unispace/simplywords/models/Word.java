package studio.unispace.simplywords.models;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by haof on 7/21/2016.
 */
public class Word {

    public String word;
    public String definition;
    public String remark;
    public Long createTime;
    public List<Long> visitTimes;
    public Float rating;

    public Word () {
        word = "";
        definition = "";
        remark = "";
        createTime = Calendar.getInstance().getTimeInMillis() / 1000L;
        visitTimes = new LinkedList<>();
        rating = 0f;
    }

    // copy constructor
    public Word (Word w) {
        word = w.word;
        definition = w.definition;
        remark = w.remark;
        createTime = w.createTime;
        visitTimes = new LinkedList<>();
        for (Long i : w.visitTimes) {
            visitTimes.add(i);
        }
        rating = w.rating;
    }

    public void addVisitTime () {
        visitTimes.add(Calendar.getInstance().getTimeInMillis() / 1000L);
    }

}

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

}

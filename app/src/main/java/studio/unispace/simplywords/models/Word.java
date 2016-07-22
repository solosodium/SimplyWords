package studio.unispace.simplywords.models;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by haof on 7/21/2016.
 */
public class Word {

    public String word;
    public List<String> definitions;
    public Map<String, String> remarks;
    public Long createTime;
    public List<Long> visitTimes;
    public Integer rating;

    public Word () {
        word = "";
        definitions = new LinkedList<>();
        remarks = new HashMap<>();
        createTime = System.currentTimeMillis() / 1000L;
        visitTimes = new LinkedList<>();
        rating = 0;
    }

}

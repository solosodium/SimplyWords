package studio.unispace.simplywords.views;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;
import java.util.List;

/**
 * Created by haof on 7/31/2016.
 */
public class VisitedView extends View {

    public float markerWidth = 0.05f;

    private List<Long> mVisited;

    public VisitedView (Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setVisited (List<Long> visited) {
        mVisited = visited;
        invalidate();
    }

    @Override
    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);

    }

    private long getEndEpoch () {
        return Calendar.getInstance().getTimeInMillis() / 1000L;
    }

    private long getStartEpoch (long end) {
        return end - (long) (60 * 60 * 24 * 7);     // back by 1 week
    }

}

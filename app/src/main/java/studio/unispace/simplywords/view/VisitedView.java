package studio.unispace.simplywords.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * Created by haof on 7/31/2016.
 */
public class VisitedView extends View {

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

}

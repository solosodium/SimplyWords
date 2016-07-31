package studio.unispace.simplywords.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import studio.unispace.simplywords.R;

/**
 * Created by haof on 7/31/2016.
 */
public class RatingView extends View {

    public float minRating = 0f;
    public float maxRating = 10f;

    private int minColor = ContextCompat.getColor(getContext(), R.color.colorPrimary);
    private int maxColor = ContextCompat.getColor(getContext(), R.color.colorAccent);

    private float mRating = 10f;
    private Paint mPaint;

    public RatingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // initialize paint
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
    }

    public void setRating (float rating) {
        mRating = rating;
        // redraw
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(minColor);
        mPaint.setColor(maxColor);
        canvas.drawRect(0, 0f, canvas.getWidth() * getRatio(), canvas.getHeight(), mPaint);
    }

    private float getRatio () {
        mRating = Math.max( Math.min(mRating, maxRating), minRating );
        return (mRating - minRating) / (maxRating - minRating);
    }

}

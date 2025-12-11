package hr.fipu.foodtracker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;

public class ProgressView extends View {
    private float currentProgress = 0;
    private float maxProgress = 100;

    private final Paint backgroundPaint;
    private final Paint progressPaint;
    private final Paint textPaint;

    public ProgressView(Context context) {
        super(context);
        backgroundPaint = initPaints();
        progressPaint = new Paint(backgroundPaint);
        textPaint = new Paint(backgroundPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredHeight = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 32, getResources().getDisplayMetrics()
        );
        setMeasuredDimension(widthMeasureSpec, desiredHeight);
    }

    private Paint initPaints() {
        Paint base = new Paint();
        base.setStyle(Paint.Style.FILL);
        base.setAntiAlias(true);
        return base;
    }

    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        Log.d("TAG", "onDraw: " + height);

        // Pozadina
        backgroundPaint.setColor(Color.LTGRAY);
        canvas.drawRect(0, 0, width, height, backgroundPaint);

        // Progress
        float ratio = (maxProgress > 0) ? Math.min(currentProgress / maxProgress, 1f) : 0f;
        int progressWidth = (int) (width * ratio);

        // Boja
        progressPaint.setColor(ratio < 1.0 ? Color.rgb(76, 175, 80) : Color.RED);
        canvas.drawRect(0, 0, progressWidth, height, progressPaint);

        // Tekst
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(height / 2f);
        textPaint.setTextAlign(Paint.Align.CENTER);
        String text = currentProgress + " / " + maxProgress ;
        canvas.drawText(text, width / 2f, height / 2f + textPaint.getTextSize() / 2, textPaint);
    }

    public void setCurrentProgress(float currentProgress) {
        this.currentProgress = currentProgress;
        invalidate();
    }

    public void setMaxProgress(float maxProgress) {
        this.maxProgress = maxProgress;
        invalidate();
    }

}

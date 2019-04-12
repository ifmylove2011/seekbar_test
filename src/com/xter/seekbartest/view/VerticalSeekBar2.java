package com.xter.seekbartest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.SeekBar;

public class VerticalSeekBar2 extends SeekBar {
	private Drawable mThumb;
	private OnSeekBarChangeListener mOnSeekBarChangeListener;

	public VerticalSeekBar2(Context context) {
		super(context);
	}

	public VerticalSeekBar2(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public VerticalSeekBar2(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setOnSeekBarChangeListener(OnSeekBarChangeListener l) {
		mOnSeekBarChangeListener = l;
	}

	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(h, w, oldh, oldw);
	}

	@Override
	protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(heightMeasureSpec, widthMeasureSpec);
		//倒置宽与高
		setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
	}

	/**
	 * 逆时针转90度，并向下移整个控件的高度
	 *
	 * @param c
	 */
	protected void onDraw(Canvas c) {
		c.rotate(-90);
		c.translate(-getHeight(), 0);

		super.onDraw(c);
	}

	private void onProgressRefresh(float scale, boolean fromUser) {
		if (scale < 0) {
			scale = 0;
		}

		if (scale > getMax()) {
			scale = getMax();
		}

		Drawable thumb = mThumb;
		if (thumb != null) {
			setThumbPos(getHeight(), thumb, scale);
			invalidate();
		}
		if (mOnSeekBarChangeListener != null) {
			mOnSeekBarChangeListener.onProgressChanged(this, getProgress(), fromUser);
		}
	}

	/**
	 * 直接套用AbsSeekBar;唯一不同的是这里的
	 * thumb的边界计算依旧是按原视图（左右）方向计算的，而不是以变换后的图形计算
	 *
	 * @param height
	 * @param thumb
	 * @param scale  {@link android.widget.AbsSeekBar)}
	 */
	private void setThumbPos(int height, Drawable thumb, float scale) {
		int available = height - getPaddingBottom() - getPaddingTop();

		int thumbWidth = thumb.getIntrinsicWidth();
		int thumbHeight = thumb.getIntrinsicHeight();

		available -= thumbHeight;

		int thumbPos = (int) (scale * available / 100 + 0.5f);

		int topBound, bottomBound;

		Rect oldBounds = thumb.getBounds();
		topBound = oldBounds.top;
		bottomBound = oldBounds.bottom;
		thumb.setBounds(thumbPos, topBound, thumbPos + thumbHeight, bottomBound);
	}

	public void setThumb(Drawable thumb) {
		mThumb = thumb;
		super.setThumb(thumb);
	}

	void onStartTrackingTouch() {
		if (mOnSeekBarChangeListener != null) {
			mOnSeekBarChangeListener.onStartTrackingTouch(this);
		}
	}

	void onStopTrackingTouch() {
		if (mOnSeekBarChangeListener != null) {
			mOnSeekBarChangeListener.onStopTrackingTouch(this);
		}
	}

	private void attemptClaimDrag() {
		if (getParent() != null) {
			getParent().requestDisallowInterceptTouchEvent(true);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (!isEnabled()) {
			return false;
		}

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				setPressed(true);
				onStartTrackingTouch();
				break;
			case MotionEvent.ACTION_MOVE:
				int progress = getMax() - (int) (getMax() * event.getY() / getHeight());
				onProgressRefresh(progress, false);
				attemptClaimDrag();
				break;
			case MotionEvent.ACTION_UP:
				onStopTrackingTouch();
				setPressed(false);
				break;
			case MotionEvent.ACTION_CANCEL:
				onStopTrackingTouch();
				setPressed(false);
				break;
		}
		return true;
	}

}
package com.lostntkdgmail.workout.main;

/* Created by Tom Pedraza and Tyler Atkinson
 * Workout-App
 * https://github.com/tha7556/Workout-App
 */

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * A custom ViewPager to prevent the users from swiping between fragments
 */
public class NonSwipeViewPager extends ViewPager {
    /**
     * Creates the ViewPager
     * @param context The current context
     */
    public NonSwipeViewPager(Context context) {
        super(context);
        setMyScroller();
    }

    /**
     * Creates the ViewPager
     * @param context The current context
     * @param attrs The AttributeSet
     */
   public NonSwipeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMyScroller();
    }

    /**
     * Is called whenever the pager intercepts a touch event. Does nothing to prevent scrolling
     * @param event The touch event
     * @return Always false because nothing happens
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }

    /**
     * Is called whenever there is a touch event. Does nothing to prevent scrolling
     * @param event The touch event
     * @return Always false because nothing happens
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }

    /**
     * Sets the custom Scroller
     */
    private void setMyScroller() {
        try {
            Class<?> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            scroller.set(this, new MyScroller(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * A custom Scroller to control the scrolling through Fragments
     */
    public class MyScroller extends Scroller {
        /**
         * Creates the Scroller
         * @param context The current Context
         */
        public MyScroller(Context context) {
            super(context, new DecelerateInterpolator());
        }

        /**
         * Starts scrolling
         * @param startX The starting X coordinate
         * @param startY The starting Y coordinate
         * @param dx The horizontal distance to travel
         * @param dy The vertical distance to travel
         * @param duration The duration of the scroll in milliseconds
         */
        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, 350);
        }
    }
}

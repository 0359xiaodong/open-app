package me.naiyu.android.app.yanbaojian.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class AppViewPager extends ViewPager {
	
	public AppViewPager(Context context) {
		super(context);
	}

	public AppViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override  
    public boolean onInterceptTouchEvent(MotionEvent ev) {  
        return false;  
    }  
  
    @Override  
    public boolean onTouchEvent(MotionEvent arg0) {  
        return false;  
    }  

}

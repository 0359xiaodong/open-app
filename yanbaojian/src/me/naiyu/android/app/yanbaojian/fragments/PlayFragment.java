package me.naiyu.android.app.yanbaojian.fragments;

import me.naiyu.android.app.yanbaojian.R;
import me.naiyu.android.app.yanbaojian.adapters.ContentPagerAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PlayFragment extends Fragment implements OnPageChangeListener {
	
	private static final int[] TITLE_IDS = { R.string.title_1,
		R.string.title_2, R.string.title_3, R.string.title_4,
		R.string.title_5, R.string.title_6 };
	
	private ViewPager mViewPager;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			mViewPager.setCurrentItem(msg.what - 1, true);
		};
	};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.play_fragment, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		getActivity().setTitle(TITLE_IDS[0]);
		mViewPager = (ViewPager) view.findViewById(R.id.viewpager_play);
        mViewPager.setAdapter(new ContentPagerAdapter(getActivity()));
        mViewPager.setOnPageChangeListener(this);
	}
	
	public void setCurrentViewPager(int position) {
		mViewPager.setCurrentItem(position, true);
	}
	
	public void setNextViewPager() {
		int current = mViewPager.getCurrentItem();
		System.out.println("current " + current);
		setCurrentViewPager(current + 1);
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	@Override
	public void onPageSelected(int position) {
		getActivity().setTitle(TITLE_IDS[position]);
	}

}

package me.naiyu.android.app.yanbaojian.fragments;

import me.naiyu.android.app.yanbaojian.R;
import me.naiyu.android.app.yanbaojian.adapters.ContentPagerAdapter;
import me.naiyu.android.app.yanbaojian.views.SlidingTabLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ContentFragment extends Fragment {
	
	private SlidingTabLayout mSlidingTabLayout;
	
	private ViewPager mViewPager;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.comtent_fragment, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new ContentPagerAdapter(getActivity()));
		mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
	}

}

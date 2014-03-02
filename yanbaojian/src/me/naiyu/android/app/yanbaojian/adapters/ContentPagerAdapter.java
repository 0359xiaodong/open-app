package me.naiyu.android.app.yanbaojian.adapters;

import me.naiyu.android.app.yanbaojian.R;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ContentPagerAdapter extends PagerAdapter {

	private static final String[] TITLE_ITEM = {"第一节:按揉攒竹穴", "第二节:按压睛明穴", "第三节:按揉四白穴", "第四节:按揉太阳穴 刮上眼眶", "第五节:按揉风池穴", "第六节:揉捏耳垂 脚趾抓地"};
	private static final int[] PIC_IDS = {R.drawable.y1, R.drawable.y2, R.drawable.y3, R.drawable.y4, R.drawable.y5, R.drawable.y6};
	private static final int[] DES_IDS = {R.string.one, R.string.two, R.string.three, R.string.four, R.string.five, R.string.six};

	private Context mContext;
	
	public ContentPagerAdapter(Context context) {
		this.mContext = context;
	}
	
	@Override
	public int getCount() {
		return TITLE_ITEM.length;
	}

	@Override
	public boolean isViewFromObject(View view, Object o) {
		return o == view;
	}
	
	@Override
    public CharSequence getPageTitle(int position) {
        return TITLE_ITEM[position];
    }
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pager_item,
                container, false);
        container.addView(view);

        ImageView pic = (ImageView) view.findViewById(R.id.iv_item_pic);
        pic.setImageResource(PIC_IDS[position]);
        
        TextView textView = (TextView) view.findViewById(R.id.tv_item_desc);
        textView.setText(DES_IDS[position]);
        return view;
	}
	
	@Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}

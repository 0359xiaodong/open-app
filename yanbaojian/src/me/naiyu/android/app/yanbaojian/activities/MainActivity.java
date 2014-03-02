package me.naiyu.android.app.yanbaojian.activities;

import me.naiyu.android.app.yanbaojian.R;
import me.naiyu.android.app.yanbaojian.fragments.ContentFragment;
import me.naiyu.android.app.yanbaojian.utils.UpdateManager;
import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private long mFirstBackDownTime = 0;

	private LinearLayout mAdLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		ContentFragment contentFragment = new ContentFragment();
		transaction.replace(R.id.sample_content_fragment, contentFragment);
		transaction.commit();

		// 初始化应用的发布ID和密钥，以及设置测试模式
        AdManager.getInstance(this).init("d2368498ee86c41f","6648af84cc6fcace", false); 
		mAdLayout = (LinearLayout) findViewById(R.id.adLayout);
		// 实例化广告条
		AdView adView = new AdView(this, AdSize.FIT_SCREEN);
		// 获取要嵌入广告条的布局
		// 将广告条加入到布局中
		mAdLayout.addView(adView);
		
		UpdateManager update = new UpdateManager(this);
		update.checkUpdate();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_play) {
			Intent it = new Intent(this, YanbaojianActivity.class);
			startActivity(it);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mFirstBackDownTime = 0;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			long currentTime = System.currentTimeMillis();
			if (currentTime - mFirstBackDownTime > 1500) {
				Toast.makeText(this, "再按一次返回退出", Toast.LENGTH_SHORT).show();
				mFirstBackDownTime = currentTime;
				return true;
			} else {
				finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

}

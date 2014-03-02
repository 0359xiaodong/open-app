package me.naiyu.android.app.yanbaojian.activities;

import me.naiyu.android.app.yanbaojian.R;
import me.naiyu.android.app.yanbaojian.fragments.PlayFragment;
import me.naiyu.android.app.yanbaojian.services.MediaService;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

public class YanbaojianActivity extends ActionBarActivity {
	
	private PlayReceiver mReceiver;

	private boolean isPlaying = true;
	private boolean isStopped = false;
	
	private PlayFragment mFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
		setContentView(R.layout.yan_layout);

		ActionBar actionBar = getSupportActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color
				.parseColor("#30000000")));
		actionBar.setDisplayShowHomeEnabled(false);
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		mFragment = new PlayFragment();
		transaction.replace(R.id.play_fragment, mFragment);
		transaction.commit();
		
		mReceiver = new PlayReceiver();
		
		handleMusic(MediaService.TAG_PLAY);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (isPlaying) {
			getMenuInflater().inflate(R.menu.play_action_menu, menu);
		} else {
			getMenuInflater().inflate(R.menu.pause_action_menu, menu);
		}
        return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_play:
			updateMenu(true);
			if (!isStopped) {
				handleMusic(MediaService.TAG_PLAY);
			} else {
				handleMusic(MediaService.TAG_STOP_TO_START);
			}
			isStopped = false;
			break;
		case R.id.action_stop:
			updateMenu(false);
			isStopped = true;
			handleMusic(MediaService.TAG_STOP);
			break;
		case R.id.action_pause:
			updateMenu(false);
			handleMusic(MediaService.TAG_PAUSE);
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@SuppressLint("NewApi")
	private void updateMenu(boolean paramIsPlaying) {
		isPlaying = paramIsPlaying;
		getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
	}
	
	private void handleMusic(int msg) {
		Intent it = new Intent(this, MediaService.class);
		it.putExtra("PLAY_TAG", msg);
		startService(it);
	}

	@Override
	public void setTitle(CharSequence title) {
		getSupportActionBar().setTitle(title);
	}

	@Override
	public void setTitle(int titleId) {
		getSupportActionBar().setTitle(titleId);
	}
		
	@Override
	protected void onResume() {
		super.onResume();
		registerPlayReceiver();
		if (!isPlaying) {
			updateMenu(true);
			if (!isStopped) {
				handleMusic(MediaService.TAG_PLAY);
			} else {
				handleMusic(MediaService.TAG_STOP_TO_START);
			}
			isStopped = false;
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(mReceiver);
		updateMenu(false);
		handleMusic(MediaService.TAG_PAUSE);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		Intent it = new Intent(this, MediaService.class);
		stopService(it);
	}
	
	private void registerPlayReceiver() {
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("me.naiyu.android.app.yanbaojian.playservice");
		registerReceiver(mReceiver, intentFilter);
	}
	
	class PlayReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			int msg = intent.getIntExtra("service_msg", 1);
			switch (msg) {
			case MediaService.MSG_FINISH:
				finish();
				break;
			case MediaService.MSG_RESTART:
				mFragment.setCurrentViewPager(0);
				//重新启动定时器
//				mFragment.stopTask();
//				mFragment.initTask();
//				mFragment.startTimer();
				break;
			case MediaService.MSG_NEXT:
				mFragment.setNextViewPager();
				break;

			default:
				break;
			}
		}
		
	}
	
	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
    		if (isPlaying) {
    			return true;
    		}
    	}
    	return super.onKeyDown(keyCode, event);
    }

}

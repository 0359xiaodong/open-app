package me.naiyu.android.app.yanbaojian.services;

import java.io.IOException;

import me.naiyu.android.app.yanbaojian.R;
import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.IBinder;
import android.util.Log;

public class MediaService extends Service {

	public static final int TAG_PLAY = 0;
	public static final int TAG_PAUSE = 1;
	public static final int TAG_STOP = 2;
	public static final int TAG_STOP_TO_START = 3;

	public static final int MSG_FINISH = 1;
	public static final int MSG_RESTART = 2;
	public static final int MSG_NEXT = 3;

	private MediaPlayer mPlayer;

	private static final int[] MUSIC_IDS = { R.raw.music_1, R.raw.music_2,
			R.raw.music_3, R.raw.music_4, R.raw.music_5, R.raw.music_6 };
	private int mCurrentMusicIndex = 0;

	@Override
	public void onCreate() {
		super.onCreate();
		mPlayer = MediaPlayer.create(this, MUSIC_IDS[mCurrentMusicIndex]);
		mPlayer.setOnCompletionListener(new MyOnCompletionListener());
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		int what = intent.getIntExtra("PLAY_TAG", 0);
		if (what == TAG_PLAY) {
			// play;
			mPlayer.start();
		} else if (what == TAG_PAUSE) {
			// pause
			mPlayer.pause();
		} else if (what == TAG_STOP) {
			// stop
			mPlayer.stop();
			try {
				mPlayer.prepare();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			mCurrentMusicIndex = 0;
			playMusicWithId(MUSIC_IDS[mCurrentMusicIndex]);
			sendMsg(MSG_RESTART);
		}
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mPlayer.release();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	class MyOnCompletionListener implements OnCompletionListener {

		@Override
		public void onCompletion(MediaPlayer mp) {
			if (mCurrentMusicIndex == MUSIC_IDS.length - 1) {
				mp.release();
				sendMsg(MSG_FINISH);
				Log.d("service", "dddd");
			} else {
				mCurrentMusicIndex++;
				playMusicWithId(MUSIC_IDS[mCurrentMusicIndex]);
				sendMsg(MSG_NEXT);
			}
		}
	}
	
	private void playMusicWithId(int paramMusicId) {
		AssetFileDescriptor afd = getResources().openRawResourceFd(MUSIC_IDS[mCurrentMusicIndex]);
		try {
			mPlayer.reset();
			mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getDeclaredLength());
			mPlayer.prepare();
			mPlayer.start();
	        afd.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void sendMsg(int msg) {
		Intent intent = new Intent(
				"me.naiyu.android.app.yanbaojian.playservice");
		intent.putExtra("service_msg", msg);
		sendBroadcast(intent);
	}

}

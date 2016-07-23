package com.xter.seekbartest;

import com.xter.seekbartest.view.VerticalSeekBar;
import com.xter.seekbartest.view.VerticalSeekBar2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class MainActivity extends Activity {

	VerticalSeekBar vsTest1;
	VerticalSeekBar2 vsTest2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	protected void initView() {
		vsTest1 = (VerticalSeekBar) findViewById(R.id.vs_test_1);
		vsTest1.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				Log.d("vsTest1", "stop track");
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				Log.d("vsTest1", "start track");
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				Log.d("vsTest1", "progress" + progress);
			}
		});
		
		vsTest2 = (VerticalSeekBar2) findViewById(R.id.vs_test_2);
		vsTest2.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				Log.d("vsTest1", "stop track");
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				Log.d("vsTest2", "start track");
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				Log.d("vsTest21", "progress" + progress);
			}
		});
	}
}

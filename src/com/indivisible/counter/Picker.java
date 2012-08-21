package com.indivisible.counter;

//import java.util.Date;
//import android.widget.Toast;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.content.Intent;
//import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

public class Picker extends Activity implements View.OnClickListener{

	//////////////////////////////////////////////////////////////////////
	//// data
	
	AlarmManager am;
	Button bSet, bCancel;
	EditText etTitle;
	NumberPicker npHours, npMinutes, npSeconds;
	
	//int hoursIn, minutesIn, secondsIn, alarmID;
	//Date now, then;
	//long addMilliseconds;
	AlarmHandler ah;
	
	//////////////////////////////////////////////////////////////////////
	//// init
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picker);
        init();
        //ah.addAlarm(new Alarm("test", 0,0,15000));
    }
    
    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		ah.dumpAlarms();
    	super.onPause();
	}

	private void init() {
    	ah = new AlarmHandler(this);
    	
		bSet = (Button) findViewById(R.id.bSet);
		bCancel = (Button) findViewById(R.id.bCancel);
		etTitle = (EditText) findViewById(R.id.etTitle);
		npHours = (NumberPicker) findViewById(R.id.npHours);
		npMinutes = (NumberPicker) findViewById(R.id.npMinutes);
		npSeconds = (NumberPicker) findViewById(R.id.npSeconds);
		
		bSet.setOnClickListener(this);
		bCancel.setOnClickListener(this);
		setNumberPickers();
	}

	private void setNumberPickers() {
		String[] num24 = new String[24];
	    for(int i=0; i<num24.length; i++)
	           num24[i] = Integer.toString(i);
	    String[] num60 = new String[60];
	    for(int i=0; i<num60.length; i++)
	           num60[i] = Integer.toString(i);
		
		npHours.setMinValue(0);
	    npHours.setMaxValue(23);
	    npHours.setWrapSelectorWheel(true);
	    npHours.setDisplayedValues(num24);
	    npHours.setValue(0);
	    npMinutes.setMinValue(0);
	    npMinutes.setMaxValue(59);
	    npMinutes.setWrapSelectorWheel(true);
	    npMinutes.setDisplayedValues(num60);
	    npMinutes.setValue(0);
	    npSeconds.setMinValue(0);
	    npSeconds.setMaxValue(59);
	    npSeconds.setWrapSelectorWheel(true);
	    npSeconds.setDisplayedValues(num60);
	    npSeconds.setValue(0);
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.picker, menu);
        return true;
    }

	//////////////////////////////////////////////////////////////////////
	//// methods and control
	
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()){
		case R.id.bSet:
			clickSet();			
			break;
		case R.id.bCancel:
			finish();
			break;
		}
	}
	
	private void clickSet(){
		Alarm alarm = new Alarm(
				etTitle.getText().toString(),
				npHours.getValue(),
				npMinutes.getValue(),
				npSeconds.getValue()
				);
		ah.cancelAllAlarms();
		ah.addAlarm(alarm);
		ah.setAlarms();
	}
//	private void clickSet(){
//		hoursIn = npHours.getValue();
//		minutesIn = npMinutes.getValue();
//		secondsIn = npSeconds.getValue();
//		
//		addMilliseconds = (hoursIn * 3600000) + (minutesIn * 60000) + (secondsIn * 1000);
//		
//		now = new Date();
//		then = new Date(now.getTime() + addMilliseconds);
//		
//		setAlarm(12345);
//	}

//	private void setAlarm(int id) {
//		// Auto-generated method stub
//		Intent intent = new Intent(this, AlarmDisp.class);
//		PendingIntent pendingIntent = PendingIntent.getActivity(this,
//	            id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//		
//		AlarmManager am = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);
//	    am.set(AlarmManager.RTC_WAKEUP, then.getTime(), pendingIntent);
//	    
//	    saveAlarm();
//	}
//
//	private void saveAlarm() {
//		// Auto-generated method stub
//		SharedPreferences myPrefs = this.getSharedPreferences("myPrefs", MODE_PRIVATE);
//        SharedPreferences.Editor prefsEditor = myPrefs.edit();
//        prefsEditor.putString(Integer.toString(alarmID), etTitle.getText().toString());
//        prefsEditor.commit();
//	}
	
	
}

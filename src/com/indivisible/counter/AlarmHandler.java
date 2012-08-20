package com.indivisible.counter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmHandler {

	String FILE = "alarms.dat";
	ArrayList<Alarm> alarms;
	Date now;
	Context context;
	Intent intent;
	PendingIntent pIntent;
	
	///////////////////////////////////////////////////////////
	
	public AlarmHandler(Context context){
		this.context = context;
		getAlarms();						// any issues with this?
	}
	
	///////////////////////////////////////////////////////////
	
	/**
	 * Add an alarm to the list
	 * @param alarm
	 */
	public void addAlarm(Alarm alarm){
		alarms.add(alarm);
		Log.d("addAlarm", "added: " + alarm.title);
	}
	/**
	 * Remove one alarm from the list
	 * @param alarm
	 */
	public void delAlarm(Alarm alarm){
		alarms.remove(alarm);
		Log.d("delAlarm", "added: " + alarm.title);
	}
	/**
	 * Verify that an alarms due date hasn't passed
	 */
	public void verifyFuture(){
		now = new Date();
		for(int i=0; i<alarms.size(); i++){
			if (alarms.get(i).when.after(now)){
				alarms.get(i).active = true;
				Log.d("verifyFuture", "active: " + alarms.get(i).title);
			} else {
				alarms.get(i).active = false;
				Log.d("verifyFuture", "inactive: " + alarms.get(i).title);
			}
		}
	}
	/**
	 * Get the ids of all alarms in the list
	 * @return
	 */
	public int[] getIds(){
		int[] ids = new int[alarms.size()];
		for (int i=0; i<alarms.size(); i++){
			ids[i] = alarms.get(i).id; 
		}
		return ids;
	}
	/**
	 * Register one alarm
	 * @param alarm
	 */
	public void setAlarm(Alarm alarm){
		AlarmManager am = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
		intent = new Intent(context, AlarmReceiver.class);
		pIntent = PendingIntent.getActivity(context, alarm.id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
	    am.set(AlarmManager.RTC_WAKEUP, alarm.set.getTime(), pIntent);
	    Log.d("setAlarm", "set: " + alarm.title);
	}
	/**
	 * Register all (active) alarms
	 */
	public void setAlarms(){
		AlarmManager am = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
		intent = new Intent(context, AlarmReceiver.class);
		for(Alarm alarm : alarms){
			if (alarm.active){
				pIntent = PendingIntent.getActivity(context, alarm.id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
			    am.set(AlarmManager.RTC_WAKEUP, alarm.set.getTime(), pIntent);
			    Log.d("setAlarm", "set: " + alarm.title);
			}
		}
	}
	/**
	 * Cancel one alarm
	 * @param alarm
	 */
	public void cancelAlarm(Alarm alarm){
		AlarmManager am = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
		intent = new Intent(context, AlarmReceiver.class);
		pIntent = PendingIntent.getActivity(context, alarm.id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		try{
			am.cancel(pIntent);
		} catch (Exception ex) {
			Log.e("MyAlarms", "AlarmManager update was not canceled. " + ex.toString());
		}
		Log.d("cancelAlarm", "disabled: " + alarm.title);
	}
	/**
	 * Cancel all alarms
	 */
	public void cancelAllAlarms(){
		AlarmManager am = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
		intent = new Intent(context, AlarmReceiver.class);
		for (Alarm alarm : alarms){
			pIntent = PendingIntent.getActivity(context, alarm.id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
			try{
				am.cancel(pIntent);
				Log.d("cancelAllAlarms", "Cancelled alarm: " + alarm.title);
			} catch (Exception ex) {
				Log.d("cancelAllAlarms", "Alarm not cancelled. " + ex.toString());
			}
		}
	}
	/**
	 * Write ArrayList of alarms to disk
	 */
	public void dumpAlarms(){
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = context.openFileOutput(FILE, Context.MODE_PRIVATE);
			out = new ObjectOutputStream(fos);
			out.writeObject(alarms);
			out.close();
			Log.d("dumpAlarms", "dumped");
		} catch(IOException ex) {
			Log.d("dumpAlarms", "not dumped");
			ex.printStackTrace();
		}
	}
	/**
	 * Read ArrayList of alarms from disk
	 */
	@SuppressWarnings("unchecked")
	public void getAlarms(){
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = context.openFileInput(FILE);
			ois = new ObjectInputStream(fis);
			alarms = (ArrayList<Alarm>) ois.readObject();
			Log.d("getAlarms", "got");
		} catch (IOException exi) {
			alarms = new ArrayList<Alarm>();
			Log.d("getAlarms", "not got");
			exi.printStackTrace();
		} catch (ClassNotFoundException exc) {
			alarms = new ArrayList<Alarm>();
			Log.d("getAlarms", "not got");
			exc.printStackTrace();
		}
	}
	
}

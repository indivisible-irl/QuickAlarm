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

	ArrayList<Alarm> alarms;
	Date now;
	Context context;
	Intent intent;
	PendingIntent pIntent;
	
	///////////////////////////////////////////////////////////
	
	public AlarmHandler(Context context){
		this.context = context;
		alarms = new ArrayList<Alarm>();
		getAlarms();						// any issues with this?
	}
	
	///////////////////////////////////////////////////////////
	
	/**
	 * Add an alarm to the list
	 * @param alarm
	 */
	public void addAlarm(Alarm alarm){
		alarms.add(alarm);
	}
	/**
	 * Remove one alarm from the list
	 * @param alarm
	 */
	public void delAlarm(Alarm alarm){
		alarms.remove(alarm);
	}
	/**
	 * Verify that an alarms due date hasn't passed
	 */
	public void verifyFuture(){
		now = new Date();
		for(int i=0; i<alarms.size(); i++){
			if (alarms.get(i).when.after(now)){
				alarms.get(i).active = true;
			} else {
				alarms.get(i).active = false;
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
		intent = new Intent(context, AlarmDisp.class);
		pIntent = PendingIntent.getActivity(context, alarm.id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
	    am.set(AlarmManager.RTC_WAKEUP, alarm.set.getTime(), pIntent);
	}
	/**
	 * Register all (active) alarms
	 */
	public void setAlarms(){
		AlarmManager am = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
		intent = new Intent(context, AlarmDisp.class);
		for(Alarm alarm : alarms){
			if (alarm.active){
				pIntent = PendingIntent.getActivity(context, alarm.id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
			    am.set(AlarmManager.RTC_WAKEUP, alarm.set.getTime(), pIntent);
			}
		}
	}
	/**
	 * Cancel one alarm
	 * @param alarm
	 */
	public void cancelAlarm(Alarm alarm){
		AlarmManager am = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
		intent = new Intent(context, AlarmDisp.class);
		pIntent = PendingIntent.getActivity(context, alarm.id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
		am.cancel(pIntent);
	}
	/**
	 * Cancel all alarms
	 */
	public void cancelAllAlarms(){
		AlarmManager am = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
		intent = new Intent(context, AlarmDisp.class);
		for (Alarm alarm : alarms){
			pIntent = PendingIntent.getActivity(context, alarm.id, intent, PendingIntent.FLAG_CANCEL_CURRENT);
			try{
				am.cancel(pIntent);
			} catch (Exception ex) {
				Log.e("MyAlarms", "AlarmManager update was not canceled. " + ex.toString());
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
			fos = context.openFileOutput("Alarms", Context.MODE_PRIVATE);
			out = new ObjectOutputStream(fos);
			out.writeObject(alarms);
			out.close();
		} catch(IOException ex) {
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
			fis = context.openFileInput("Alarms");
			ois = new ObjectInputStream(fis);
			alarms = (ArrayList<Alarm>) ois.readObject();
		} catch (IOException exi) {
			exi.printStackTrace();
		} catch (ClassNotFoundException exc) {
			exc.printStackTrace();
		}
	}
	
}
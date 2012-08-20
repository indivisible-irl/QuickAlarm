package com.indivisible.counter;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

/**
 * Class to hold one alarm
 * @author indivisible
 */
public class Alarm implements Serializable{

	///////////////////////////////////////////////////////////////////////////////
	//// data
	
	private static final long serialVersionUID = 1L;
	String title;
	int id;
	Date set, when;
	boolean active;
	
	///////////////////////////////////////////////////////////////////////////////
	//// constructors
	
	/**
	 * Create new alarm with a time offset
	 * @param name
	 * @param hoursIn
	 * @param minutesIn
	 * @param secondsIn
	 */
	public Alarm(String title, int hoursIn, int minutesIn, int secondsIn){
		init(title);
		setWhenAdd(hoursIn, minutesIn, secondsIn);
	}
	/**
	 * Create a new alarm using a proper date obj
	 * @param title
	 * @param when
	 */
	public Alarm(String title, Date when){
		init(title);
		this.when = when;
	}
	/**
	 * Create a new alarm with a long of the alarm time
	 * @param name
	 * @param due
	 */
	public Alarm(String name, long due){
		init(name);
		setWhenDate(due);
	}
	/**
	 * Full alarm constructor for use with serialize
	 * @param title
	 * @param id
	 * @param set
	 * @param when
	 * @param active
	 */
	public Alarm(String title, int id, Date set, Date when, boolean active){
		this.title = title;
		this.id = id;
		this.set = set;
		this.when = when;
		this.active = active;
	}
	
	///////////////////////////////////////////////////////////////////////////////
	//// methods
	
	/**
	 * Common settings for new alarms
	 * @param name
	 */
	public void init(String name){
		title = name;
		id = new Random(999).nextInt();
		set = new Date();
		active = true;
	}
	/**
	 * Calculate and set the alarm date using an offset
	 * @param hours
	 * @param minutes
	 * @param seconds
	 */
	public void setWhenAdd(int hours, int minutes, int seconds){
		long thenLong = set.getTime() +
				(hours * 3600000) +
				(minutes * 60000) +
				(seconds * 1000);
		when = new Date(thenLong);
	}
	/**
	 * Set the alarm date from a long
	 * @param due
	 */
	public void setWhenDate(long due){
		set = new Date(due);
	}
	
}

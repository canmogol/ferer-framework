package com.fererlab.schedule;


public class CronJob {

	private String name;
	private String triggerName;
	private String canonicalName;
	
	private String seconds;
	private String minutes;
	private String hours;
	private String dayOfMonth;
	private String month;
	private String dayOfWeek;
	private String year;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTriggerName() {
		return triggerName;
	}
	
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}
	
	public String getCanonicalName() {
		return canonicalName;
	}
	
	public void setCanonicalName(String canonicalName) {
		this.canonicalName = canonicalName;
	}
	
	public String getSeconds() {
		return seconds;
	}
	
	public void setSeconds(String seconds) {
		this.seconds = seconds;
	}
	
	public String getMinutes() {
		return minutes;
	}
	
	public void setMinutes(String minutes) {
		this.minutes = minutes;
	}
	
	public String getHours() {
		return hours;
	}
	
	public void setHours(String hours) {
		this.hours = hours;
	}
	
	public String getDayOfMonth() {
		return dayOfMonth;
	}
	
	public void setDayOfMonth(String dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}
	
	public String getMonth() {
		return month;
	}
	
	public void setMonth(String month) {
		this.month = month;
	}
	
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	
	public void setDayOfWeek(String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	
	public String getYear() {
		return year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
}

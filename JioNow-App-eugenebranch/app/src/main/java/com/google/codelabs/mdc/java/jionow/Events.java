package com.google.codelabs.mdc.java.jionow;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Events {
    public final String TAG = "Logcat";
    private String Name;
    private String Description;
    private Date StartDate;
    private Date  EndDate;
    private String Host;
    public Events(){}


    public Events(String Name, String Description, Date StartDate, Date EndDate, String Host){
        this.Name = Name;
        this.Description = Description;
        this.StartDate = StartDate;
        this.EndDate = EndDate;
        this.Host = Host;

    }

    public String getDescription() {
        return Description;
    }

    public String status(){
        long millis=System.currentTimeMillis();
        Date now =new Date(millis);
        //Log.d(TAG, String.valueOf(now));
        //Log.d(TAG, String.valueOf(this.Description));
        //Log.d(TAG, String.valueOf(this.EndDate));
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        String date = simpleDateFormat.format(StartDate);
        if (now.before(StartDate)){
            return "Upcoming: " + String.valueOf(date);
        }

        if (now.after(StartDate) && now.before(EndDate)){
            return "Ongoing";
        }

        return "Past Event";
    }

    public boolean isEventsHost(String User){
        //Log.d(TAG, String.valueOf(this.Host));
        //Log.d(TAG, String.valueOf(User));
        if (this.Host.equals(User)){
            return true;
        }
        return false;
    }

    public Date getStartDate() {
        return StartDate;
    }

    public Date getEndDate() {
        return EndDate;
    }

    public String getName(){
       return Name;
    }

    public String getHost() {
        return Host;
    }
}

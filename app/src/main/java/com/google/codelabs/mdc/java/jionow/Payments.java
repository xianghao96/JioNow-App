package com.google.codelabs.mdc.java.jionow;

import android.util.Log;

import java.util.Map;

public class Payments {
    public final String TAG = "Logcat";
    private String Name;
    private String Host;
    private Map<String, Object>  Owed;
    public Payments(){}


    public Payments(String Name, String Host, Map<String, Object> Owed){
        this.Name = Name;
        this.Owed = Owed;
        this.Host = Host;
        //Log.d(TAG, String.valueOf(this.Owed.get("eugenechia95")));
    }

    public Map<String, Object> getOwed() {
        return Owed;
    }

    public String getPayment(String test) {
        //String ans = String.valueOf((int)this.Owed.get("eugenechia95"));
        //return "test";
        return String.valueOf(Owed.get(test));
        //return String.valueOf(Owed.get("eugenechia95"));
    }

    public String getHostPayment() {
        double total = 0;
        for (Map.Entry<String, Object> Owed : Owed.entrySet()) {
            total += Double.parseDouble(String.valueOf(Owed.getValue()));
        }
        return String.valueOf(total);

    }

    public String getName(){
       return Name;
    }

    public String getHost() {
        return Host;
    }
}

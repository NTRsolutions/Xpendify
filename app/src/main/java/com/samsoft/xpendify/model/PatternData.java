package com.samsoft.xpendify.model;

/**
 * Created by Fred on 09-Sep-15.
 */
public class PatternData {
    int id;
    String passcode;

    public PatternData() {
    }

    public PatternData(String passcode) {
        this.passcode = passcode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPasscode() {
        return passcode;
    }

    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    @Override
    public String toString() {
        return "PatternData{" + "id=" + id + ", passcode='" + passcode + '\'' + '}';
    }
}

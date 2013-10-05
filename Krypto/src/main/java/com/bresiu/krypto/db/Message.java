package com.bresiu.krypto.db;


public class Message {
    private long id;
    private String phone;
    private String time;
    private String message;
    private int own;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    //Theres now bool variable in SQLite?
    public int getOwn() {
        return own;
    }

    public void setOwn(int own) {
        this.own = own;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return message;
    }

}

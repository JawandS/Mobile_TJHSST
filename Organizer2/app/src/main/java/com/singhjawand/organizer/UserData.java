package com.singhjawand.organizer;

import java.util.ArrayList;

public class UserData {
    String user_id = "DEFAULT";
    String nick = "DEFAULT";

    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> notes = new ArrayList<>();

    public UserData(){
        user_id = "jawandsingh@gmailcom";
        nick = "Jawand";
    }

    public UserData(String user_id){
        this.user_id = user_id;
    }

    public void addNote(String title, String note){
        titles.add(title);
        notes.add(note);
    }

    public ArrayList<String> getTitles() {
        return titles;
    }

    public void setTitles(ArrayList<String> titles) {
        this.titles = titles;
    }

    public ArrayList<String> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<String> notes) {
        this.notes = notes;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}

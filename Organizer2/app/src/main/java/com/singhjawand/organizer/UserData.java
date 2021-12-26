package com.singhjawand.organizer;

public class UserData {
    String user_id = "DEFAULT";
    String nick = "DEFAULT";

    public UserData(){
        user_id = "jawandsingh@gmailcom";
        nick = "Jawand";
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

package com.singhjawand.lab12_firebase;

public class UserData {
    String id;
    String nick;

    public UserData() {
        id = "JawandSingh@gmail.com";
        nick = "Jawand";
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}

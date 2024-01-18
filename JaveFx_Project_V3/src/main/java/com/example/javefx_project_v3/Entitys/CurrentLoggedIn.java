package com.example.javefx_project_v3.Entitys;

public class CurrentLoggedIn {
    private static CurrentLoggedIn instance;
    private LoggedIn loggedIn;

    private CurrentLoggedIn() {
        // Private constructor to prevent instantiation
    }

    public static synchronized CurrentLoggedIn getInstance() {
        if (instance == null) {
            instance = new CurrentLoggedIn();
        }
        return instance;
    }

    public LoggedIn getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(LoggedIn loggedIn) {
        this.loggedIn = loggedIn;
    }
}
package com.example.javefx_project_v3.Entitys;

public class LoggedInModel {
    private final CurrentLoggedIn currentLoggedIn = CurrentLoggedIn.getInstance();

    public LoggedIn getLoggedIn() {
        return currentLoggedIn.getLoggedIn();
    }

    public void setLoggedIn(LoggedIn loggedIn) {
        currentLoggedIn.setLoggedIn(loggedIn);
    }
}
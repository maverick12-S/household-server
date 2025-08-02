package com.example.household_server.application.dto;

public class NotificationRequest {
    private String email;
    private boolean resolver;

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isResolver() {
        return resolver;
    }
}

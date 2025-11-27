package com.parkdots.permit.applicationa.model;

public class UserSession {

    private String sessionId;
    private byte[] payload;

    public UserSession(String sessionId, byte[] payload) {
        this.sessionId = sessionId;
        this.payload = payload;
    }

    public String getSessionId() {
        return sessionId;
    }

    public byte[] getPayload() {
        return payload;
    }
}

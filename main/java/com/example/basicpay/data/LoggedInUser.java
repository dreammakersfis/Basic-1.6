package com.example.basicpay.data;

import java.io.Serializable;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser implements Serializable {

    private String displayName;
    private String userId;
    private String passWord;
    private int state = 0;

    public LoggedInUser() {
        setUserId("fis");
        setPassWord("1234");
        setDisplayName("DreamMakersFIS");
    }

    private void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    private void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    private void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setLogState(int i) {
        state = i;
    }

    public String getLogState() {
        if (state == 1) {
            return "1";
        } else {
            return "0";
        }
    }
}
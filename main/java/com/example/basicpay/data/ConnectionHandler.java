package com.example.basicpay.data;

public interface ConnectionHandler {

    public void didReceiveData(byte[] data, int len);

    public void didDisconnect(Exception error);

    public void didConnect();
}
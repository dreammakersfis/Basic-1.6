package com.example.basicpay.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncConnection extends AsyncTask<Void, String, Exception> {
    private ConnectionHandler connectionHandler;
    private static final int SERVERPORT = 18488;

    private static final String SERVER_IP = "10.135.6.12";
    private InputStream in;
    private OutputStream out;
    private Socket socket;
    private boolean interrupted = false;

    private String TAG = getClass().getName();

    public AsyncConnection(ConnectionHandler connectionHandler) {
        this.connectionHandler = connectionHandler;
    }

    @Override
    protected void onPreExecute() {
        Log.i("AsyncConnection", "onPreExecute");
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Exception result) {
        super.onPostExecute(result);
        Log.d(TAG, "Finished communication with the socket. Result = " + result);
        //TODO If needed move the didDisconnect(error); method call here to implement it on UI thread.
    }

    @Override
    protected Exception doInBackground(Void... params) {
        Exception error = null;

        try {
            Log.d(TAG, "Opening socket connection.");
            SocketAddress sockaddr = new InetSocketAddress(SERVER_IP,SERVERPORT);
            socket = new Socket();
            socket.connect(sockaddr, 5000);//10 second connection timeout
            if (socket.isConnected()){
                in = socket.getInputStream();
                out = socket.getOutputStream();
                connectionHandler.didConnect();
                while(!interrupted) {
                    byte[] line = new byte[256];
                    int len = in.read(line);
                    //Log.d(TAG, "Received:" + line);
                    connectionHandler.didReceiveData(line, len);
                    Log.e(TAG, "Data received from PI: " + line);

                }
            }
        } catch (UnknownHostException ex) {
            Log.e(TAG, "doInBackground(): " + ex.toString());
            error = interrupted ? null : ex;
        } catch (IOException ex) {
            Log.d(TAG, "doInBackground(): " + ex.toString());
            error = interrupted ? null : ex;
        } catch (Exception ex) {
            Log.e(TAG, "doInBackground(): " + ex.toString());
            error = interrupted ? null : ex;
        } finally {
            try {
                socket.close();
                out.close();
                in.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        connectionHandler.didDisconnect(error);
        return error;
    }

    public void write(final byte[] data) {
        try {
            Log.d(TAG, "writ(): data = " + data);
            out.write(data);
            out.flush();
        } catch (IOException | NullPointerException ex) {
            Log.e(TAG, "write(): " + ex.toString());
        }
    }

    public void disconnect() {
        try {
            Log.d(TAG, "Closing the socket connection.");

            interrupted = true;
            if(socket != null) {
                socket.close();
            }
            if(out != null & in != null) {
                out.close();
                in.close();
            }
        } catch (IOException ex) {
            Log.e(TAG, "disconnect(): " + ex.toString());
        }
    }

}


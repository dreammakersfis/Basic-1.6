package com.example.basicpay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.basicpay.data.AsyncConnection;
import com.example.basicpay.data.ConnectionHandler;
import com.example.basicpay.data.ISOFormatter;
import com.example.basicpay.data.ISOMessage;
import com.example.basicpay.data.ISOParser;
import com.example.basicpay.data.LoggedInUser;
import com.example.basicpay.data.MAPCODES;

import com.example.basicpay.data.MerchList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {
    private static Logger l = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private LoggedInUser loggers = new LoggedInUser();
    ConnectionHandler newHandler;
    AsyncConnection newConnect;
    private MerchList rlist;
    private int traceNo = 329;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView userDisp = findViewById(R.id.userDisp);
        Button geoButton = findViewById(R.id.geoButton);
        Button qrButton = findViewById(R.id.qrButton);
        Button srhButton = findViewById(R.id.srhButton);

        geoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SelectRecipActivity.class);
                startActivityForResult(intent, MAPCODES.LIST_CODE.getCode());
            }
        });
        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ISOMessage isoMsg = new ISOMessage(getTraceNo());
                ISOFormatter bldMsg = new ISOFormatter(isoMsg);
                try {
                    bldMsg.buildFinancial();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                sendTxn(bldMsg.getBuffer());
            }
        });
        srhButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SelectRecipActivity.class);
                startActivity(intent);
            }
        });
        l.log(Level.INFO, "start called *************:" + loggers.getLogState());
        // checkUser();
        loggers.setLogState(1);
        userDisp.setText(loggers.getDisplayName());
        makePayment("000000002500", "AutoCall1");
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        l.log(Level.INFO, "onbeforeResult *************:" + loggers.getLogState());
        if (requestCode == MAPCODES.LOGIN_CODE.getCode()) {
            if (resultCode == Activity.RESULT_OK) {
                loggers = (LoggedInUser) Objects.requireNonNull(data.getExtras()).getSerializable("result");
                if (loggers != null && loggers.getLogState().equals("0")) {
                    checkUser();
                }
            }
            //Write your code if there's no result
        }
        if (requestCode == MAPCODES.LIST_CODE.getCode()) {
            if (resultCode == Activity.RESULT_OK) {
                rlist = (MerchList) Objects.requireNonNull(data.getExtras()).getSerializable("MERCH");
                l.log(Level.INFO, "After List Activity : " + (rlist != null ? rlist.getSelectedRecipId() : 0));
                getTranDetails();
            }
        }
        if (requestCode == MAPCODES.TRAN_CODE.getCode()) {
            if (resultCode == Activity.RESULT_OK) {
                makePayment(data.getStringExtra("AMOUNT"), data.getStringExtra("DESC"));
            }
            //Write your code if there's no result
        }
    }

    private void checkUser() {
        l.log(Level.INFO, "checkUser called  *************:" + loggers.getLogState());
        if (loggers.getLogState().equals("0")) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivityForResult(intent, MAPCODES.LOGIN_CODE.getCode());
        }
    }

    private void getTranDetails() {
        Intent intent = new Intent(MainActivity.this, TranDetActivity.class);
        intent.putExtra("USER", loggers.getDisplayName());
        intent.putExtra("RECIPIENT", rlist.getRecip(rlist.getSelectedRecipId()));
        l.log(Level.INFO, "Selected Recipient : " + rlist.getRecip(rlist.getSelectedRecipId()));
        startActivityForResult(intent, MAPCODES.TRAN_CODE.getCode());
    }

    private void makePayment(String amt, String desc) {
        ISOMessage isoMsg = new ISOMessage(getTraceNo());
        isoMsg.setTranAmount(amt);
        ISOFormatter bldMsg = new ISOFormatter(isoMsg);
        try {
            bldMsg.buildFinancial();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sendTxn(bldMsg.getBuffer());
    }

    private void sendTxn(final byte[] sdata) {

        newHandler = new ConnectionHandler() {
            @Override
            public void didReceiveData(byte[] data, int len) {
                ISOParser parser = new ISOParser(data, len);
                parser.unpack();
                ISOMessage reqMsg = parser.getIsoMsg();
                if (reqMsg.getMsgcode().equals("0800")) {
                    ISOFormatter repMsg = new ISOFormatter(reqMsg);
                    repMsg.loginReply();
                    putTraceNo(reqMsg.getTraceNoInt());
                    sendTxn(repMsg.getBuffer());
                }
                if (reqMsg.getMsgcode().equals("0820")) {
                    ISOFormatter repMsg = new ISOFormatter(reqMsg);
                }
                if (reqMsg.getMsgcode().equals("0210")) {
                    ISOFormatter repMsg = new ISOFormatter(reqMsg);
                }
            }
            @Override
            public void didDisconnect(Exception error) {
            }
            @Override
            public void didConnect() {
                l.log(Level.INFO,  "Sending data to server : " + sdata);
                newConnect.write(sdata);
            }
        };
        newConnect = new AsyncConnection(newHandler);
        newConnect.execute();
    }

    private int getTraceNo() {
        traceNo = traceNo + 1;
        return traceNo;
    }

    private void putTraceNo(int trcno) {
        traceNo = trcno;
    }



    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}

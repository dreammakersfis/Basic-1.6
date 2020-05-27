package com.example.basicpay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.basicpay.data.GeoLocFinder;
import com.example.basicpay.data.MerchList;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SelectRecipActivity extends AppCompatActivity {
    MerchList rlist;
    private static Logger l = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    TextView latval;
    TextView lonval;
    TextView pText;
    ProgressBar pbar;

    Button recip1;
    Button recip2;
    Button recip3;
    Button recip4;
    Button recip5;
    Button recip6;
    Button recip7;
    Button recip8;
    Button recip9;
    Button recip10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        latval = findViewById(R.id.latitude);
        lonval = findViewById(R.id.longitude);
        pbar = findViewById(R.id.progressBar);
        pText = findViewById(R.id.progressText);

        recip1 = findViewById(R.id.recip1);
        recip2 = findViewById(R.id.recip2);
        recip3 = findViewById(R.id.recip3);
        recip4 = findViewById(R.id.recip4);
        recip5 = findViewById(R.id.recip5);
        recip6 = findViewById(R.id.recip6);
        recip7 = findViewById(R.id.recip7);
        recip8 = findViewById(R.id.recip8);
        recip9 = findViewById(R.id.recip9);
        recip10 = findViewById(R.id.recip10);

/*        Button lout = findViewById(R.id.action_logout);
        lout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("result", "logout");
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });*/

        callGeoLoc();
        //l.log(Level.INFO, "ISO Message : " + isoMsg.toString());
    }

    private void fillRecipeList() {
        rlist = new MerchList();
        if (rlist.getRecip(0) != null) {
            recip1.setEnabled(true);
            recip1.setVisibility(View.VISIBLE);
            recip1.setText(rlist.getRecip(0));
            recip1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateRecipient(0);
                }
            });
        }
        if (rlist.getRecip(1) != null) {
            recip2.setEnabled(true);
            recip2.setVisibility(View.VISIBLE);
            recip2.setText(rlist.getRecip(1));
            recip2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateRecipient(1);
                }
            });
        }
        if (rlist.getRecip(2) != null) {
            recip3.setEnabled(true);
            recip3.setVisibility(View.VISIBLE);
            recip3.setText(rlist.getRecip(2));
            recip3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateRecipient(2);
                }
            });
        }
        if (rlist.getRecip(3) != null) {
            recip4.setEnabled(true);
            recip4.setVisibility(View.VISIBLE);
            recip4.setText(rlist.getRecip(3));
            recip4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateRecipient(3);
                }
            });
        }
        if (rlist.getRecip(4) != null) {
            recip5.setEnabled(true);
            recip5.setVisibility(View.VISIBLE);
            recip5.setText(rlist.getRecip(4));
            recip5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateRecipient(4);
                }
            });
        }
        if (rlist.getRecip(5) != null) {
            recip6.setEnabled(true);
            recip6.setVisibility(View.VISIBLE);
            recip6.setText(rlist.getRecip(5));
            recip6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateRecipient(5);
                }
            });
        }
        if (rlist.getRecip(6) != null) {
            recip7.setEnabled(true);
            recip7.setVisibility(View.VISIBLE);
            recip7.setText(rlist.getRecip(6));
            recip7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateRecipient(6);
                }
            });
        }
        if (rlist.getRecip(7) != null) {
            recip8.setEnabled(true);
            recip8.setVisibility(View.VISIBLE);
            recip8.setText(rlist.getRecip(7));
            recip8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateRecipient(7);
                }
            });
        }
        if (rlist.getRecip(8) != null) {
            recip9.setEnabled(true);
            recip9.setVisibility(View.VISIBLE);
            recip9.setText(rlist.getRecip(8));
            recip9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateRecipient(8);
                }
            });
        }
        if (rlist.getRecip(9) != null) {
            recip10.setEnabled(true);
            recip10.setVisibility(View.VISIBLE);
            recip10.setText(rlist.getRecip(9));
            recip10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateRecipient(9);
                }
            });
        }
    }

    private void updateRecipient(int i) {
        rlist.setSelectedRecipId(i);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("MERCH", rlist);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void callGeoLoc() {
        final GeoLocFinder geo = new GeoLocFinder(this);
        CountDownTimer mCountDownTimer;
        pbar.setEnabled(true);
        pText.setEnabled(true);

        mCountDownTimer=new CountDownTimer(1000,500) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                //Do what you want
                pbar.setVisibility(View.GONE);
                pText.setVisibility(View.GONE);
                l.log(Level.INFO, "Latitude : " + geo.getLat());
                l.log(Level.INFO, "Longitude : " + geo.getLon());
                latval.setText(String.format("Latitude : %s", geo.getLat()));
                lonval.setText(String.format("Longitude : %s", geo.getLon()));
                fillRecipeList();
            }
        };
        mCountDownTimer.start();
    }
}

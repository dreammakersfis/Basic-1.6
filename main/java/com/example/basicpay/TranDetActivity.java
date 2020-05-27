package com.example.basicpay;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TranDetActivity extends AppCompatActivity {

    EditText amountText;
    EditText descText;
    TextView userText;
    TextView recipText;
    Button payButton;
    private String payment_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tran_det);

        amountText = findViewById(R.id.amounText);
        descText = findViewById(R.id.descText);
        payButton = findViewById(R.id.payButton);
        userText = findViewById(R.id.UserName);
        recipText = findViewById(R.id.recipient);
        userText.setText(getIntent().getStringExtra("USER"));
        recipText.setText(getIntent().getStringExtra("RECIPIENT"));
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (payment_amount.equals("000000000000")) {
                    Toast.makeText(TranDetActivity.this,"Amount not entered!",
                            Toast.LENGTH_LONG).show();
                }
                if (payButton.isEnabled())
                {
                    Toast.makeText(TranDetActivity.this,"Sending " + payment_amount + " to "
                                    + recipText.getText(), Toast.LENGTH_LONG).show();
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("AMOUNT", payment_amount);
                    returnIntent.putExtra("DESC", descText.getText().toString().trim());
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
        });
        amountText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (!TextUtils.isEmpty(amountText.getText().toString().trim())){
                    payment_amount = amountText.getText().toString().trim();
                    payButton.setTextColor(Color.BLACK);
                    payButton.setEnabled(true);
                    payButton.setBackgroundColor(getResources().getColor(R.color.common_google_signin_btn_text_dark));
                }
                else{
                    Toast.makeText(TranDetActivity.this,"Please enter the amount", Toast.LENGTH_LONG).show();
                    payButton.setBackgroundColor(getResources().getColor(R.color.common_google_signin_btn_text_dark_disabled));
                    payButton.setTextColor(Color.LTGRAY);
                    payButton.setEnabled(false);
                }
            }
        });
    }
}

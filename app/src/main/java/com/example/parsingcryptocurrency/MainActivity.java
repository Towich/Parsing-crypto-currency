package com.example.parsingcryptocurrency;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Document doc;
    Runnable runnable;
    Thread secThread;

    Button checkPriceButton;
    TextView priceBTCTextView;

    String btc_price_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        runnable = new Runnable() {
            @Override
            public void run() {
                getPriceBTC();
            }
        };

        secThread = new Thread(runnable);
        secThread.start();


        checkPriceButton = findViewById(R.id.button_check_price);
        priceBTCTextView = findViewById(R.id.text_view_btc_price);

        checkPriceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secThread = new Thread(runnable);
                secThread.start();
                priceBTCTextView.setText(btc_price_text);
            }
        });

    }

    private void getPriceBTC(){
        try {
            doc = Jsoup.connect("https://coinmarketcap.com/").get();
            Element table_crypto = doc.getElementsByTag("tbody").get(0);
            Elements elements_cryptocurrency = table_crypto.children();
            Elements BTC_row = elements_cryptocurrency.get(0).children();

            Element BTC_name = BTC_row.get(2).children().get(0);
            Elements BTC_price = BTC_row.get(3).children();

            btc_price_text = BTC_price.text();

            Log.d("MyTag", "MyMessage 1: " + BTC_name.text());
            Log.d("MyTag", "MyMessage 2: " + BTC_price.text());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
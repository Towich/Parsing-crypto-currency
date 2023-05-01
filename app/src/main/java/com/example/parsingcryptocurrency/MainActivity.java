package com.example.parsingcryptocurrency;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Document doc;
    Runnable runnable;
    Thread secThread;

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
                getWeb();
            }
        };

        secThread = new Thread(runnable);
        secThread.start();
    }

    private void getWeb(){
        try {
            doc = Jsoup.connect("https://coinmarketcap.com/").get();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
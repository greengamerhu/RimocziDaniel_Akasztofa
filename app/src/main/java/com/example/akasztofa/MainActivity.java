package com.example.akasztofa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button buttonBal, buttonJobb, buttonTipp;
    TextView textviewTipp, textviewBetuk;
    ImageView imageviewAkasztofa;
    private List<String> betuLista;
    private List<String> tippeltBetuk;
    // kellett hogy egyszerüen megcsinaljam a betuk közti valtast
    private int scrollHelper;
    private String valasztottszo;
    private int maxElronthato = 12;
    private int rosszTippekSzama = 0;
    AlertDialog.Builder jatekVege;
    // a kitalálandó szónak a rejtett változata
    StringBuilder display = new StringBuilder();
    Toast toast;
    // ezeket egy elöző projektemből loptam, gondoltam ha már van miért ne használnám :D
    private String[] car = {
            "audi", "bmw", "ford", "toyota", "wolvo", "lada", "volkswagen", "skoda", "trabant",
            "tesla", "jeep", "ford", "honda", "mazda", "nissan", "mitsubishi", "subaru", "opel",
            "porsche", "fiat", "ferrari", "bentley", "jaguar", "lotus", "koenigsegg", "uaz"
    };

    private String[] furniture = {
            "kanapé", "fotel", "szék", "szekrény", "asztal", "ágy", "forgószék", "függőágy",
            "íróasztal", "polc", "könyvespolc", "gardrób"
    };
    private String[] fruit = {
            "alma", "banán", "narancs", "körte", "szilva", "szőlő", "körte", "eper",
            "gránátalma", "meggy", "málna", "őszibarack", "birsalma", "áfonya", "szeder",
            "dinnye", "kiwi", "barack", "mandarin"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        ujJatek();
        //betuk közti lepkedes
        buttonJobb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollHelper++;
                if (scrollHelper > betuLista.size()-1) {
                    scrollHelper = 0;
                }
                textviewBetuk.setText(betuLista.get(scrollHelper));
            }
        });
        buttonBal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollHelper--;
                if (scrollHelper < 0) {
                    scrollHelper = betuLista.size()-1;
                }
                textviewBetuk.setText(betuLista.get(scrollHelper));
            }
        });
        //Ellenorzom hogy már volt e kattintva és átszinezem
        textviewBetuk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (tippeltBetuk.contains(textviewBetuk.getText().toString())) {
                    textviewBetuk.setTextColor(Color.BLACK);
                } else{
                    textviewBetuk.setTextColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //A játéknak a nagy része itt történik
        buttonTipp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textviewBetuk.setTextColor(Color.BLACK);
                String elozoAllapot = display.toString();
                char karakter = textviewBetuk.getText().charAt(0);
                for (int i = 0; i < valasztottszo.length(); i++ ) {
                    if (valasztottszo.charAt(i) == karakter) {
                        display.setCharAt(i,karakter);
                    }
                }
                if (elozoAllapot.equals(display.toString())){
                    if (tippeltBetuk.contains(String.valueOf(karakter))) {
                        toast = Toast.makeText(MainActivity.this, "A tippelt betü már volt :O", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.BOTTOM | Gravity.START, 40,90);
                        toast.show();
                    } else {
                        Toast.makeText(MainActivity.this, "Rossz tipp volt :(", Toast.LENGTH_SHORT).show();
                        rosszTippekSzama++;
                        checkGameState();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "A tipp jó Volt :D", Toast.LENGTH_SHORT).show();

                }
                textviewTipp.setText(display.toString());
                if (valasztottszo.equals(display.toString())) {
                    jatekVege.setTitle("Nyertél!!!").show();
                }
                tippeltBetuk.add(String.valueOf(karakter));

            }
        });
    }

    private void ujJatek() {
        Random rnd = new Random();
        int chooseWordList = rnd.nextInt(3);
        switch (chooseWordList) {
            case 0:
                valasztottszo =  car[rnd.nextInt(car.length-1)].toUpperCase();
                break;
            case 1:
                valasztottszo =  furniture[rnd.nextInt(furniture.length-1)].toUpperCase();
                break;
            case 2:
                valasztottszo =  fruit[rnd.nextInt(fruit.length-1)].toUpperCase();
                break;
        }



        //Már választott betük listája
        tippeltBetuk = new ArrayList<>();
        display = new StringBuilder();
        for (int i = 0; i < valasztottszo.length(); i++) {
            display.append("_");
        }
        imageviewAkasztofa.setImageResource(R.drawable.akasztofa00);
        textviewBetuk.setText("A");
        textviewTipp.setText(display);
        rosszTippekSzama = 0;
        scrollHelper = 0;

    }
    //Megnézi hogy hol tart a rossz találatokban a játékos
    private void checkGameState() {
        switch (rosszTippekSzama) {
            case 1:
                imageviewAkasztofa.setImageResource(R.drawable.akasztofa01);
                break;
            case 2:
                imageviewAkasztofa.setImageResource(R.drawable.akasztofa02);
                break;
            case 3:
                imageviewAkasztofa.setImageResource(R.drawable.akasztofa03);
                break;
            case 4:
                imageviewAkasztofa.setImageResource(R.drawable.akasztofa04);
                break;
            case 5:
                imageviewAkasztofa.setImageResource(R.drawable.akasztofa05);
                break;
            case 6:
                imageviewAkasztofa.setImageResource(R.drawable.akasztofa06);
                break;
            case 7:
                imageviewAkasztofa.setImageResource(R.drawable.akasztofa07);
                break;
            case 8:
                imageviewAkasztofa.setImageResource(R.drawable.akasztofa08);
                break;
            case 9:
                imageviewAkasztofa.setImageResource(R.drawable.akasztofa09);
                break;
            case 10:
                imageviewAkasztofa.setImageResource(R.drawable.akasztofa10);
                break;
            case 11:
                imageviewAkasztofa.setImageResource(R.drawable.akasztofa11);
                break;
            case 12:
                imageviewAkasztofa.setImageResource(R.drawable.akasztofa12);
                break;
            case 13:
                imageviewAkasztofa.setImageResource(R.drawable.akasztofa13);
                jatekVege.setTitle("Vesztettél").show();
                break;



        }
    }

    private void init() {
        buttonBal = findViewById(R.id.buttonBal);
        buttonJobb = findViewById(R.id.buttonJobb);
        buttonTipp = findViewById(R.id.buttonTipp);
        textviewTipp = findViewById(R.id.textviewTipp);
        textviewBetuk = findViewById(R.id.textviewBetuk);
        imageviewAkasztofa = findViewById(R.id.imageviewAkasztofa);
        //ez egy segéd lista ez alapján tud a felhasznála scrollozni a betük között
        betuLista = Arrays.asList("A", "Á", "B", "C", "D", "E", "É", "F", "G", "H", "I", "Í", "J", "K", "L", "M", "N", "O", "Ó", "Ö", "Ő", "P", "Q", "R", "S", "T", "U", "Ú", "Ü", "Ű", "V", "W", "X", "Y", "Z");


        jatekVege = new AlertDialog.Builder(MainActivity.this);
        jatekVege.setCancelable(false)
                .setTitle("")
                .setMessage("Szeretne új Játékot Indítani?")
                .setNegativeButton("Nem", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setPositiveButton("Igen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ujJatek();
                    }
                }).create();
    }
}
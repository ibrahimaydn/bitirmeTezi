package com.ibrahimaydin.firebaseeklesil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView  textViewHayvan1, textViewHayvan2, textViewHayvan3, textViewHayvan4;
    private DatabaseReference databaseReference;
    private SıcaklıkCircleView sıcaklıkCircleView;
    private NemCircleView nemCircleView;
    private DioksitCircleView dioksitCircleView;
    private MonoksitCircleView monoksitCircleView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sıcaklıkCircleView = findViewById(R.id.temperatureCircleView);
        nemCircleView = findViewById(R.id.nemCircleView);
        dioksitCircleView = findViewById(R.id.dioksitCircleView);
        monoksitCircleView = findViewById(R.id.monoksitCircleView);
        textViewHayvan1 = findViewById(R.id.koyun);
        textViewHayvan2 = findViewById(R.id.leylek);
        textViewHayvan3 = findViewById(R.id.bıldırcın);
        textViewHayvan4 = findViewById(R.id.kopek);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int sicaklik = dataSnapshot.child("sıcaklık").getValue(Integer.class);
                    int nem = dataSnapshot.child("nem").getValue(Integer.class);
                    int karbonmonoksit = dataSnapshot.child("karbonmonoksit").getValue(Integer.class);
                    int karbondioksit = dataSnapshot.child("karbondioksit").getValue(Integer.class);

                    sıcaklıkCircleView.setSıcaklık(sicaklik);
                    nemCircleView.setNem(nem);
                    monoksitCircleView.setMonoksit(karbonmonoksit);
                    dioksitCircleView.setDioksit(karbondioksit);

                    // textViewSicaklik.setText("Sıcaklık: " + sicaklik);
                    // textViewNem.setText("Nem: " + nem);
                    // textViewOksijen.setText("Oksijen: " + oksijen);

                    if ((sicaklik >= 10 && sicaklik <= 20) && (nem >= 40 && nem <= 60) && (karbonmonoksit >= 0 && karbonmonoksit <= 50)) {
                        textViewHayvan1.setText("Koyun: İdeal sıcaklık, nem ve hava kalitesi aralığındadır.");
                    } else {
                        textViewHayvan1.setText("Koyun: İdeal sıcaklık, nem ve hava kalitesi aralığında değildir.");
                    }

                    if ((sicaklik >= 20 && sicaklik <= 25) && (nem >= 50 && nem <= 70) && (karbonmonoksit >= 0 && karbonmonoksit <= 50)) {
                        textViewHayvan2.setText("Leylek: İdeal sıcaklık, nem ve hava kalitesi aralığındadır.");
                    } else {
                        textViewHayvan2.setText("Leylek: İdeal sıcaklık, nem ve hava kalitesi aralığında değildir.");
                    }

                    if ((sicaklik >= 15 && sicaklik <= 25) && (nem >= 40 && nem <= 60) && (karbonmonoksit >= 0 && karbonmonoksit <= 50)) {
                        textViewHayvan3.setText("Bıldırcın: İdeal sıcaklık, nem ve hava kalitesi aralığındadır.");
                    } else {
                        textViewHayvan3.setText("Bıldırcın: İdeal sıcaklık, nem ve hava kalitesi aralığında değildir.");
                    }

                    if ((sicaklik >= 18 && sicaklik <= 25) && (nem >= 40 && nem <= 60) && (karbonmonoksit >= 0 && karbonmonoksit <= 50)) {
                        textViewHayvan4.setText("Köpek: İdeal sıcaklık, nem ve hava kalitesi aralığındadır.");
                    } else {
                        textViewHayvan4.setText("Köpek: İdeal sıcaklık, nem ve hava kalitesi aralığında değildir.");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Hata durum işlemler
            }
        });
    }
}


//Sıcaklık (°C):
//Kedi ve Köpek: 20°C ile 25°C arası idealdir. 0°C'den düşük sıcaklıklar soğuk strese yol açabilir, 32°C'den yüksek sıcaklıklar ise sıcak strese neden olabilir.
//Kuş: 18°C ile 26°C arası idealdir. 10°C'den düşük sıcaklıklar soğuk strese yol açabilir, 30°C'den yüksek sıcaklıklar ise sıcak strese neden olabilir.
//Nem (% RH):
//Kedi ve Köpek: %40 ile %60 arası idealdir. %20'nin altı çok kuru, %80'nin üstü ise çok nemli olarak kabul edilir.
//Kuş: %50 ile %70 arası idealdir. %30'un altı çok kuru, %80'nin üstü ise çok nemli olarak kabul edilir.
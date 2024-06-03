package com.ibrahimaydin.firebaseeklesil;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView textViewHayvan1, textViewHayvan2, textViewHayvan3, textViewHayvan4;
    private DatabaseReference databaseReference;
    private SıcaklıkCircleView sıcaklıkCircleView;
    private NemCircleView nemCircleView;
    private DioksitCircleView dioksitCircleView;
    private MonoksitCircleView monoksitCircleView;
    public Button button;

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
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, API.class);
                startActivity(intent);
            }
        });

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

                    textViewHayvan1.setText(generateStatusMessage(
                            "Koyun", sicaklik, nem, karbonmonoksit, karbondioksit,
                            5, 25, 30, 70, 0, 300, 500,
                            "Aşırı sıcaklık, yüksek nem, yetersiz oksijen ve yüksek karbonmonoksit seviyeleri koyunların sağlığını olumsuz etkileyebilir."
                    ));
                    textViewHayvan2.setText(generateStatusMessage(
                            "Leylek", sicaklik, nem, karbonmonoksit, karbondioksit,
                            15, 25, 40, 60, 0, 300, 500,
                            "Yüksek sıcaklık, düşük nem ve hava kirliliği leyleklerin göç alışkanlıklarını ve beslenme düzenlerini olumsuz etkileyebilir."
                    ));
                    textViewHayvan3.setText(generateStatusMessage(
                            "Bıldırcın", sicaklik, nem, karbonmonoksit, karbondioksit,
                            18, 25, 40, 60, 0, 300, 500,
                            "Yüksek sıcaklık, düşük nem ve hava kirliliği bıldırcınların sağlığını ve üreme alışkanlıklarını olumsuz etkileyebilir."
                    ));
                    textViewHayvan4.setText(generateStatusMessage(
                            "Köpek", sicaklik, nem, karbonmonoksit, karbondioksit,
                            20, 35, 40, 60, 0, 300, 500,
                            "Yüksek sıcaklık, düşük nem ve hava kirliliği köpeklerin sağlığını olumsuz etkileyebilir."
                    ));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private String generateStatusMessage(String animal, int sicaklik, int nem, int karbonmonoksit, int karbondioksit,
                                         int tempMin, int tempMax, int humidityMin, int humidityMax, int coMax,
                                         int co2Min, int co2Max, String disease) {
        StringBuilder status = new StringBuilder(animal + ": ");
        int nonIdealCount = 0;

        if (sicaklik >= tempMin && sicaklik <= tempMax) {
            status.append("Sıcaklık ideal. ");
        } else if (sicaklik < tempMin) {
            status.append("Sıcaklık düşük. ");
            nonIdealCount++;
        } else {
            status.append("Sıcaklık yüksek. ");
            nonIdealCount++;
        }

        if (nem >= humidityMin && nem <= humidityMax) {
            status.append("Nem ideal. ");
        } else if (nem < humidityMin) {
            status.append("Nem düşük. ");
            nonIdealCount++;
        } else {
            status.append("Nem yüksek. ");
            nonIdealCount++;
        }

        if (karbonmonoksit == coMax) {
            status.append("Karbonmonoksit ideal. ");
        } else {
            status.append("Karbonmonoksit yüksek. ");
            nonIdealCount++;
        }

        if (karbondioksit >= co2Min && karbondioksit <= co2Max) {
            status.append("Karbondioksit ideal. ");
        } else if (karbondioksit < co2Min) {
            status.append("Karbondioksit düşük. ");
            nonIdealCount++;
        } else {
            status.append("Karbondioksit yüksek. ");
            nonIdealCount++;
        }

        if (nonIdealCount >= 3) {
            status.append("Uyarı: " + disease);
        }

        return status.toString();
    }
}


//Sıcaklık (°C):
//Kedi ve Köpek: 20°C ile 25°C arası idealdir. 0°C'den düşük sıcaklıklar soğuk strese yol açabilir, 32°C'den yüksek sıcaklıklar ise sıcak strese neden olabilir.
//Kuş: 18°C ile 26°C arası idealdir. 10°C'den düşük sıcaklıklar soğuk strese yol açabilir, 30°C'den yüksek sıcaklıklar ise sıcak strese neden olabilir.
//Nem (% RH):
//Kedi ve Köpek: %40 ile %60 arası idealdir. %20'nin altı çok kuru, %80'nin üstü ise çok nemli olarak kabul edilir.
//Kuş: %50 ile %70 arası idealdir. %30'un altı çok kuru, %80'nin üstü ise çok nemli olarak kabul edilir.
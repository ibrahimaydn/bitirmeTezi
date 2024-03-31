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

    private TextView textViewSicaklik, textViewNem, textViewOksijen, textViewHayvan1, textViewHayvan2, textViewHayvan3;
    private DatabaseReference databaseReference;
    private TemperatureCircleView temperatureCircleView;
    private NemCircleView nemCircleView;
    private OksijenCircleView oksijenCircleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewSicaklik = findViewById(R.id.textViewSicaklik);
        textViewNem = findViewById(R.id.textViewNem);
        textViewOksijen = findViewById(R.id.textViewOksijen);
        temperatureCircleView = findViewById(R.id.temperatureCircleView);
        nemCircleView = findViewById(R.id.nemCircleView);
        oksijenCircleView = findViewById(R.id.oksijenCircleView);
        textViewHayvan1 = findViewById(R.id.textViewHayvan1);
        textViewHayvan2 = findViewById(R.id.textViewHayvan2);
        textViewHayvan3 = findViewById(R.id.textViewHayvan3);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int sicaklik = dataSnapshot.child("sıcaklık").getValue(Integer.class);
                    int nem = dataSnapshot.child("nem").getValue(Integer.class);
                    int oksijen = dataSnapshot.child("oksijen").getValue(Integer.class);

                    temperatureCircleView.setSıcaklık(sicaklik);
                    nemCircleView.setNem(nem);
                    oksijenCircleView.setOksijen(oksijen);
                   // textViewSicaklik.setText("Sıcaklık: " + sicaklik);
                   // textViewNem.setText("Nem: " + nem);
                   // textViewOksijen.setText("Oksijen: " + oksijen);

                    // Kedi ve Köpek için ideal aralık: 20°C - 25°C, Kuş için ideal aralık: 18°C - 26°C
                    if ((sicaklik >= 20 && sicaklik <= 25) || (sicaklik >= 18 && sicaklik <= 26)) {
                        textViewHayvan1.setText("Kedi: İdeal sıcaklık aralığındadır.");
                        textViewHayvan2.setText("Köpek: İdeal sıcaklık aralığındadır.");
                        textViewHayvan3.setText("Kuş: İdeal sıcaklık aralığındadır.");
                    } else if ((sicaklik < 20 || sicaklik > 25) || (sicaklik < 18 || sicaklik > 26)) {
                        textViewHayvan1.setText("Kedi: Sıcaklık dengesi bozulmuştur.");
                        textViewHayvan2.setText("Köpek: Sıcaklık dengesi bozulmuştur.");
                        textViewHayvan3.setText("Kuş: Sıcaklık dengesi bozulmuştur.");
                    }
                    // Kedi ve Köpek için ideal nem aralığı: %40 - %60, Kuş için ideal nem aralığı: %50 - %70
                    if ((nem >= 40 && nem <= 60) || (nem >= 50 && nem <= 70)) {
                        textViewHayvan1.append("\nKedi: İdeal nem aralığındadır.");
                        textViewHayvan2.append("\nKöpek: İdeal nem aralığındadır.");
                        textViewHayvan3.append("\nKuş: İdeal nem aralığındadır.");
                    } else if ((nem < 40 || nem > 60) || (nem < 50 || nem > 70)) {
                        textViewHayvan1.append("\nKedi: Nem dengesi bozulmuştur.");
                        textViewHayvan2.append("\nKöpek: Nem dengesi bozulmuştur.");
                        textViewHayvan3.append("\nKuş: Nem dengesi bozulmuştur.");
                    }

                    // Oksijen değeri herhangi bir aralığa göre kontrol edilmiyor, sadece değeri textView'e yazdırılıyor
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
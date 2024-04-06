package com.ibrahimaydin.firebaseeklesil;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class API extends AppCompatActivity {

    TextView sehir, ulke, sicaklik, koordinat;
    EditText sehirIsmi;
    Button havaDurumuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);
        sehir = findViewById(R.id.sehir);
        ulke = findViewById(R.id.ulke);
        sicaklik = findViewById(R.id.sicaklik);
        koordinat = findViewById(R.id.koordinat);
        havaDurumuButton = findViewById(R.id.havaDurumuButton);
        sehirIsmi = findViewById(R.id.sehirIsmi);

        havaDurumuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new HavaDurumu().execute();
            }
        });
    }

    private class HavaDurumu extends AsyncTask<Void, Void, Void> {
        int tempNo;
        String descriptionN;
        String countryName;
        String name;
        Double enlem, boylam;

        @Override
        protected Void doInBackground(Void... voids) {
            String weatherUrl = "//api.openweathermap.org/data/2.5/find?q=" + sehirIsmi.getText() + "&units=metric";
            JSONObject jsonObject = null;
            try {                String json = JSONParser.getJSONFromUrl(weatherUrl);
                try {
                    jsonObject = new JSONObject(json);
                } catch (JSONException e) {
                    Log.e("JSONPARSER", "Error creating Json Object" + e.toString());
                }

                // En baştaki json objesinden list adlı array'ı çek
                JSONArray listArray = jsonObject.getJSONArray("list");
                // list'in ilk objesini çek
                JSONObject firstObj = listArray.getJSONObject(0);
                // Bu alanda Name'i çek
                name = firstObj.getString("name");
                // İlk objenin içindeki objelerden main'i çek
                JSONObject main = firstObj.getJSONObject("main");

                // Sıcaklık
                tempNo = main.getInt("temp");
                // Ülke
                JSONObject country = firstObj.getJSONObject("sys");
                countryName = country.getString("country");

                // Koordinat
                JSONObject koord = firstObj.getJSONObject("coord");
                enlem = koord.getDouble("lat");
                boylam = koord.getDouble("lon");
            } catch (JSONException e) {
                Log.e("json", "doINbackgrond");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void args) {
            sehir.setText("Sehir: " + name);
            ulke.setText("Ülke: " + countryName);
            sicaklik.setText("Sıcaklık: " + tempNo + "℃");
            koordinat.setText("Koordinatlar:\n Enlem: " + enlem + "\n Boylam: " + boylam);
        }

    }
}

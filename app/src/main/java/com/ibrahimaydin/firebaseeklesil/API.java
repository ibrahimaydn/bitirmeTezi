package com.ibrahimaydin.firebaseeklesil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class API extends AppCompatActivity {

    TextView sehir, ulke, sicaklik, koordinat;
    Button havaDurumuButton;

    // Varsayılan şehir
    String defaultCity = "Elazig";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);
        sehir = findViewById(R.id.sehir);
        ulke = findViewById(R.id.ulke);
        sicaklik = findViewById(R.id.sicaklik);
        koordinat = findViewById(R.id.koordinat);
        havaDurumuButton = findViewById(R.id.havaDurumuButton);

        havaDurumuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("TAG", "Butona tıklandı"); // Buton tıklandığında log mesajı
                new HavaDurumu().execute(defaultCity);
            }
        });
    }

    private class HavaDurumu extends AsyncTask<String, Void, Void> {
        int tempNo;
        String descriptionN;
        String countryName;
        String name;
        Double enlem, boylam;

        @Override
        protected Void doInBackground(String... params) {
            String city = params[0];
            String weatherUrl = "http://api.openweathermap.org/data/2.5/find?q=" + city + "&units=metric&appid=YOUR_API_KEY";
            JSONObject jsonObject = null;
            try {
                Log.d("TAG", "Arka planda çalışıyor");
                String json = JSONParser.getJSONFromUrl(weatherUrl);
                try {
                    jsonObject = new JSONObject(json);
                } catch (JSONException e) {
                    Log.e("JSONPARSER", "Error creating Json objesi" + e.toString());
                }

                JSONArray listArray = jsonObject.getJSONArray("list");
                JSONObject firstObj = listArray.getJSONObject(0);
                name = firstObj.getString("name");
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
            Log.d("TAG", "hata ayıklama çağrıldı");
            sehir.setText("Sehir: " + name);
            ulke.setText("Ülke: " + countryName);
            sicaklik.setText("Sıcaklık: " + tempNo + "℃");
            koordinat.setText("Koordinatlar:\n Enlem: " + enlem + "\n Boylam: " + boylam);
        }

    }
}

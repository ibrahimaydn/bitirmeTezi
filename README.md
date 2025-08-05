# Bitirme Tezi

**PoseiSensör - Su Kalitesi İzleme ve Alabalık Yetiştiriciliği Sistemi
Proje Hakkında
PoseiSensör, Keban ve Karakaya baraj göllerinde su kalitesi izleme ve gökkuşağı alabalığı yetiştiriciliğini desteklemek için geliştirilmiş otonom bir su üstü izleme sistemidir. Proje, Bitirme projesi kapsamında  tasarlanmış ve uygulanmıştır. Sistem, sensörler, yapay zeka tabanlı görüntü işleme (YOLOv8) ve otonom navigasyon (A* algoritması) ile su kalitesini gerçek zamanlı izler, kirliliği tespit eder ve balık sağlığını optimize eder.
Özellikler

Su Kalitesi İzleme: DS18B20 (sıcaklık), Gravity pH, Turbidity, EC ve YF-S201 (akış hızı) sensörleriyle %95 doğrulukta çalışır.
Kirlilik Tespiti: YOLOv8 ile %85 doğrulukta plastik çöp ve müsilaj tespiti.
Otonom Navigasyon: A* algoritmasıyla 0.5 m/s hızda %98 stabilite.
Enerji Verimliliği: 6V/150mA güneş paneli ve 3S 3.7V 3200 mAh batarya ile 8 saat otonom çalışma.
İletişim: NRF24L01+PA-LNA ve GSM modülleriyle Firebase entegrasyonu, 200 ms gecikmeyle mobil uygulama desteği.

Kurulum Talimatları

Gereksinimler:
Raspberry Pi 4 Model B (4GB RAM önerilir).
Python 3.9 ve OpenCV, TensorFlow Lite kütüphaneleri.
32GB SD kart (Raspbian ile).
Sensörler ve motorlar (liste yukarıda).


Adımlar:
SD karta Raspbian işletim sistemini yükle.
Depodaki PoseiSensor.py dosyasını kopyala ve Raspberry Pi’ye aktar.
Sensörleri SPI/I2C protokolleriyle bağla ve kabloları kontrol et.
git clone https://github.com/kullaniciadi/PoseiSensor-Tez.git ile depoyu indir.
Terminalde python PoseiSensor.py ile sistemi başlat.


Kullanım

Sistemi baraj gölünde başlatmak için GPS koordinatlarını gir.
Mobil uygulamada "Güncelle" ile sensör verilerini, "Haritada Göster" ile kirlilik haritalarını kontrol et.
Çiftçiler, balık sağlığı için kritik eşikleri (12-18°C, 6.5-8.5 pH) izleyebilir.





Sorular için: ibrahimaydn231@gmail.com


Teşekkür

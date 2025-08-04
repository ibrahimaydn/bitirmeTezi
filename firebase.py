import firebase_admin
from firebase_admin import credentials
from firebase_admin import db

# Firebase Setup
cred = credentials.Certificate("ecohesapp-66384-firebase-adminsdk-fbsvc-683051b01e.json")
firebase_admin.initialize_app(cred, {
    'databaseURL': 'https://ecohesapp-66384-default-rtdb.europe-west1.firebasedatabase.app/'
})

# Ürün verileri (gerçek ve manuel hazırlanmış)
urunler = [
    {
        "kategori": "Pil",
        "barkod": "8699958354338",
        "urun_id": "111",
        "marka": "Osaka",
        "urun_adi": "Kalem pil",
        "alis_fiyati": 10.0,
        "satis_fiyati": 12.00,
        "kdv_orani": 1,
        "son_kullanma_tarihi": "2025-06-01",
        "karbon_ayak_izi": 0.85,  # kg CO2e per unit (yaklaşık değer)
        "stok_miktari": 200,
        "toplam_satis_sayisi": 3000,
        "mobil_etiket": ["indirimde", "teknoloji"]
    }
]

# Firebase'e yaz
ref = db.reference("urunler")
for urun in urunler:
    barkod = urun["barkod"]
    ref.child(barkod).set(urun)
    print(f"{urun['urun_adi']} (Barkod: {barkod}) başarıyla yüklendi.")

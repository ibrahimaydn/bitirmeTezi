/*
  Birleştirilmiş Arduino Kodu
  - EC (Elektriksel İletkenlik)
  - Bulanıklık (Turbidity)
  - Su Akış Sensörü (YF-S201)
  - pH Sensörü
  - Simüle Edilen Sıcaklık 
*/

#include <EEPROM.h>
#include "DFRobot_EC.h"

#define EC_PIN A1
#define TURBIDITY_PIN A0
#define PH_SENSOR_PIN A2
#define PH_ARRAY_LENGTH 40
#define PH_OFFSET 0.0
#define FLOW_SENSOR_PIN 2

float voltageEC, ecValue;
float turbidityVoltage, turbidity;
float temperature = 25.0;  // Simüle edilen sıcaklık
int pHArray[PH_ARRAY_LENGTH];
int pHArrayIndex = 0;
volatile int flow_frequency = 0;
unsigned int l_hour;
unsigned long currentTime, cloopTime;
unsigned long lastEC = 0, lastTurbidity = 0, lastPH = 0, lastTempUpdate = 0;

DFRobot_EC ec;

void flowISR() {
  flow_frequency++;
}

void setup() {
  Serial.begin(115200);

  ec.begin();

  pinMode(TURBIDITY_PIN, INPUT);
  pinMode(PH_SENSOR_PIN, INPUT);
  pinMode(FLOW_SENSOR_PIN, INPUT);
  digitalWrite(FLOW_SENSOR_PIN, HIGH);
  attachInterrupt(digitalPinToInterrupt(FLOW_SENSOR_PIN), flowISR, RISING);

  currentTime = millis();
  cloopTime = currentTime;

  randomSeed(analogRead(A3));  // Boş bir analog pin ile rastgelelik
}

void loop() {
  currentTime = millis();

  // Su Akış Sensörü (her saniyede bir)
  if (currentTime >= (cloopTime + 1000)) {
    cloopTime = currentTime;
    l_hour = (flow_frequency * 60 / 7.5);
    flow_frequency = 0;
  }

  // Simüle Edilen Sıcaklık (20 saniyede bir değişir)
  if (millis() - lastTempUpdate >= 20000) {
    lastTempUpdate = millis();
    temperature = random(100, 221) / 10.0;  // 10.0 - 22.0 °C arası
  }

  // EC Ölçümü
  voltageEC = analogRead(EC_PIN) / 1024.0 * 5000;
  ecValue = ec.readEC(voltageEC, temperature);

  // Bulanıklık Ölçümü
  int sensorValue = analogRead(TURBIDITY_PIN);
  turbidityVoltage = sensorValue * (5.0 / 1023.0);
  turbidity = (-1120.4 * turbidityVoltage * turbidityVoltage) + (5742.3 * turbidityVoltage) - 4352.9;

  // pH Ölçümü
  pHArray[pHArrayIndex++] = analogRead(PH_SENSOR_PIN);
  if (pHArrayIndex >= PH_ARRAY_LENGTH) pHArrayIndex = 0;
  float avgVoltage = averageArray(pHArray, PH_ARRAY_LENGTH) * (5.0 / 1024.0);
  float pHValue = 3.5 * avgVoltage + PH_OFFSET;

  // Seri monitöre tüm verileri yaz
  Serial.print("[AKIŞ] Litre/saat: ");
  Serial.println(l_hour);

  Serial.print("[SICAKLIK] ");
  Serial.print(temperature);
  Serial.println(" °C");

  Serial.print("[EC] Değer: ");
  Serial.print(ecValue);
  Serial.println(" ms/cm");

  Serial.print("[BULANIKLIK] ");
  Serial.print(turbidity);
  Serial.println(" NTU");

  Serial.print("[pH] Değer: ");
  Serial.println(pHValue);

  // EC kalibrasyonu için seri komut
  if (Serial.available() > 0) {
    char cmd = Serial.read();
    if (cmd == 'c') {
      ec.calibration(voltageEC, temperature);
    }
  }

  delay(2000);  // Verileri her 2 saniyede bir gönder
}

int averageArray(int* arr, int len) {
  long sum = 0;
  for (int i = 0; i < len; i++) {
    sum += arr[i];
  }
  return sum / len;
}

// DHT Temperature & Humidity Sensor
// Unified Sensor Library Example
// Written by Tony DiCola for Adafruit Industries
// Released under an MIT license.

// Depends on the following Arduino libraries:
// - Adafruit Unified Sensor Library: https://github.com/adafruit/Adafruit_Sensor
// - DHT Sensor Library: https://github.com/adafruit/DHT-sensor-library

#include <Adafruit_Sensor.h>
#include <DHT.h>
#include <DHT_U.h>

#define DHTPIN            2         // Pin which is connected to the DHT sensor.

// Uncomment the type of sensor in use:
#define DHTTYPE           DHT11     // DHT 11 
//#define DHTTYPE           DHT22     // DHT 22 (AM2302)
//#define DHTTYPE           DHT21     // DHT 21 (AM2301)

// See guide for details on sensor wiring and usage:
//   https://learn.adafruit.com/dht/overview

DHT_Unified dht(DHTPIN, DHTTYPE);

uint32_t delayMS;
void setup() {
  Serial.begin(9600); 
  // Initialize device.
  dht.begin();
  //Serial.println("DHTxx Unified Sensor Example");
  // Print temperature sensor details.
  sensor_t sensor;
  dht.temperature().getSensor(&sensor);
 //  Serial.println("------------------------------------");
  // Serial.println("Temperature");
 //  Serial.print  ("Sensor:       "); Serial.println(sensor.name);
  // Serial.print  ("Driver Ver:   "); Serial.println(sensor.version);
 //  Serial.print  ("Unique ID:    "); Serial.println(sensor.sensor_id);
  // Serial.print  ("Max Value:    "); Serial.print(sensor.max_value); Serial.println(" *C");
 //  Serial.print  ("Min Value:    "); Serial.print(sensor.min_value); Serial.println(" *C");
  // Serial.print  ("Resolution:   "); Serial.print(sensor.resolution); Serial.println(" *C");  
 //  Serial.println("------------------------------------");
  // Print humidity sensor details.
 //  dht.humidity().getSensor(&sensor);
 //  Serial.println("------------------------------------");
 //  Serial.println("Humidity");
 //  Serial.print  ("Sensor:       "); Serial.println(sensor.name);
 //  Serial.print  ("Driver Ver:   "); Serial.println(sensor.version);
 //  Serial.print  ("Unique ID:    "); Serial.println(sensor.sensor_id);
 // //  Serial.print  ("Max Value:    "); Serial.print(sensor.max_value); Serial.println("%");
 //  Serial.print  ("Min Value:    "); Serial.print(sensor.min_value); Serial.println("%");
 //  Serial.print  ("Resolution:   "); Serial.print(sensor.resolution); Serial.println("%");  
//   Serial.println("------------------------------------");
  // Set delay between sensor readings based on sensor details.
  delayMS = sensor.min_delay / 1000;
}

void loop() {
  // Delay between measurements.
  delay(delayMS);
 
  uint32_t temp;
  uint32_t rh;

  // Get temperature event and print its value.
  sensors_event_t event;  
  dht.temperature().getEvent(&event);
  temp = event.temperature;
  // Get humidity event and print its value.
  dht.humidity().getEvent(&event);
  rh = event.relative_humidity;
  
  if (isnan(temp) || isnan(rh)) {
    Serial.println("Error reading temperature or relative humidity");
  }
  else {
    Serial.print("|");
    Serial.print(temp);
    Serial.print("|");
    Serial.print(rh);
    
    Serial.println("|");
  }
}

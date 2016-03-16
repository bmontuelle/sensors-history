package fr.montuelle.arduino.sensors

import com.google.inject.AbstractModule
import fr.montuelle.arduino.sensors.services.{SensorsServerImpl, SensorsServer}

class SensorsModule extends AbstractModule {
  def configure() = {

    bind(classOf[SensorsServer])
      .to(classOf[SensorsServerImpl]).asEagerSingleton
  }
}
package fr.thomas.maugin.arduino.firmata.config;

import fr.thomas.maugin.arduino.firmata.settings.SerialFirmataSettings;
import org.apache.log4j.Logger;
import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.firmata.FirmataDevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * Created by Thomas on 03/10/2015.
 */

/**
 * Firmata Config Class
 */
@Configuration
public class SerialFirmataConfig {

    final static Logger logger = Logger.getLogger(SerialFirmataConfig.class);

    @Autowired
    SerialFirmataSettings serialFirmataSettings;

    /**
     * Create and connect firmata service
     * @return IODevice to control arduino
     */
    @Bean
    public IODevice getFirmataSerial() {
        IODevice device = new FirmataDevice(serialFirmataSettings.getPort()); // construct the Firmata device instance using the name of a port
        try {
            device.start(); // initiate communication to the device
            device.ensureInitializationIsDone(); // wait for initialization is done
            Pin pin10 = device.getPin(10);
            Pin pin11 = device.getPin(11);
            pin10.setMode(Pin.Mode.PWM);
            pin11.setMode(Pin.Mode.PWM);
        } catch (IOException | InterruptedException e) {
            logger.error("Error : ", e);
        }
        return device;
    }
}

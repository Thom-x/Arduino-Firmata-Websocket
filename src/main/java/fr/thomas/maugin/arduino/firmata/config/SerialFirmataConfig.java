package fr.thomas.maugin.arduino.firmata.config;

import fr.thomas.maugin.arduino.firmata.domain.Leds;
import fr.thomas.maugin.arduino.firmata.settings.PinOutSettings;
import fr.thomas.maugin.arduino.firmata.settings.SerialFirmataSettings;
import org.apache.commons.lang3.tuple.Triple;
import org.firmata4j.IODevice;
import org.firmata4j.Pin;
import org.firmata4j.firmata.FirmataDevice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(SerialFirmataConfig.class);

    @Autowired
    SerialFirmataSettings serialFirmataSettings;

    @Autowired
    PinOutSettings pinOutSettings;

    @Autowired
    IODevice device;

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
            Pin pinRed = device.getPin(pinOutSettings.getRed());
            Pin pinGreen = device.getPin(pinOutSettings.getGreen());
            Pin pinBlue = device.getPin(pinOutSettings.getBlue());
            pinRed.setMode(Pin.Mode.PWM);
            pinGreen.setMode(Pin.Mode.PWM);
            pinBlue.setMode(Pin.Mode.PWM);
        } catch (IOException | InterruptedException e) {
            LOGGER.error("Error : ", e);
        }
        return device;
    }

    @Bean
    public Leds getLeds() {
        Pin pinRed = device.getPin(pinOutSettings.getRed());
        Pin pinGreen = device.getPin(pinOutSettings.getGreen());
        Pin pinBlue = device.getPin(pinOutSettings.getBlue());
        return new Leds(pinRed, pinGreen, pinBlue);
    }
}

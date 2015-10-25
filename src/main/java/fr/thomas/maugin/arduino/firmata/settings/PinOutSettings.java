package fr.thomas.maugin.arduino.firmata.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Thomas on 03/10/2015.
 */

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="pin")
public class PinOutSettings {

    Integer red;
    Integer green;
    Integer blue;

    public PinOutSettings() {
    }

    public Integer getRed() {
        return red;
    }

    public void setRed(Integer red) {
        this.red = red;
    }

    public Integer getGreen() {
        return green;
    }

    public void setGreen(Integer green) {
        this.green = green;
    }

    public Integer getBlue() {
        return blue;
    }

    public void setBlue(Integer blue) {
        this.blue = blue;
    }
}

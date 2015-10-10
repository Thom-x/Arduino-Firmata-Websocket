package fr.thomas.maugin.arduino.firmata.settings;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Thomas on 03/10/2015.
 */

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="serial")
public class SerialFirmataSettings {

    String port;

    public SerialFirmataSettings() {
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}

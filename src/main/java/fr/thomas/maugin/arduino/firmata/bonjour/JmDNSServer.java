package fr.thomas.maugin.arduino.firmata.bonjour;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;
import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by Thomas on 03/10/2015.
 */

@Configuration
public class JmDNSServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JmDNSServer.class);
    public static final String ARDUINO_FIRMATA = "_ws._tcp.local.";

    @Value("${local.server.port}")
    private String serverPort;

    @Bean
    /**
     * Register Bonjour service in a new thread
     */
    public boolean getServerJmDNS() throws IOException {
        new Thread(() -> {
            try {
                LOGGER.info("Registering Bonjour service...");
                JmDNS mdnsServer = JmDNS.create(InetAddress.getLocalHost());
                // Register a test service.
                ServiceInfo firmataService = ServiceInfo.create(ARDUINO_FIRMATA, "Firmata Arduino Service", Integer.valueOf(serverPort), "Firmata Arduino Service");
                mdnsServer.registerService(firmataService);
                LOGGER.info("Firmata service registered at port {}",serverPort);
            } catch (IOException e) {
                LOGGER.error("Error while registering bonjour service : ",e);
            }
        }).start();
        return true;
    }
}

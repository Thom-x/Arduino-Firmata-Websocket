package fr.thomas.maugin.arduino.firmata.contorller;

import com.google.protobuf.InvalidProtocolBufferException;
import fr.thomas.maugin.arduino.firmata.proto.Firmata;
import org.apache.commons.codec.binary.Base64;
import org.firmata4j.IODevice;
import org.firmata4j.Pin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class LedController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LedController.class);

    @Autowired
    IODevice device;

    /**
     * Set PWM of arduino according to Led command
     * @param ledCmdBuffer protobuff base64 string Led command
     * @return protobuff base64 string Led command
     * @throws InvalidProtocolBufferException
     */
    @MessageMapping("/led")
    @SendTo("/result/led")
    public String setLedPwn(String ledCmdBuffer) throws InvalidProtocolBufferException {
        byte[] decodedBytes = Base64.decodeBase64(ledCmdBuffer);
        final Firmata.Led ledCmd = Firmata.Led.parseFrom(decodedBytes);
        LOGGER.info("Getting command : {}",ledCmd);
        Pin pin10 = device.getPin(10);
        Pin pin11 = device.getPin(11);
        try {
            pin10.setValue(ledCmd.getRed());
            pin11.setValue(ledCmd.getGreen());
        } catch (IOException e) {
            LOGGER.error("Error : ", e);
        }
        return ledCmdBuffer;
    }
}

package fr.thomas.maugin.arduino.firmata.controller;

import com.google.protobuf.InvalidProtocolBufferException;
import fr.thomas.maugin.arduino.firmata.proto.Firmata;
import fr.thomas.maugin.arduino.firmata.service.FirmataService;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class LedController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LedController.class);

    @Autowired
    FirmataService firmataService;

    /**
     * Set PWM of arduino according to Led command
     *
     * @param ledCmdBuffer protobuff base64 string Led command
     * @return protobuff base64 string Led command
     * @throws InvalidProtocolBufferException
     */
    @MessageMapping("/led")
    @SendTo("/result/led")
    public String setLedPwn(String ledCmdBuffer) throws InvalidProtocolBufferException {
        byte[] decodedBytes = Base64.decodeBase64(ledCmdBuffer);
        final Firmata.Led ledCmd = Firmata.Led.parseFrom(decodedBytes);
        LOGGER.info("Getting command setColor : {}", ledCmd);
        firmataService.setColor(ledCmd.getRed(), ledCmd.getGreen(), ledCmd.getBlue());
        return ledCmdBuffer;
    }
}

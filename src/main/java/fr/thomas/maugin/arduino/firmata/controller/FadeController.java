package fr.thomas.maugin.arduino.firmata.controller;

import com.google.protobuf.InvalidProtocolBufferException;
import fr.thomas.maugin.arduino.firmata.annotation.Loggable;
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
public class FadeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FadeController.class);

    @Autowired
    FirmataService firmataService;

    @MessageMapping("/fade")
    @SendTo("/result/fade")
    @Loggable
    public String setLedPwn(String fadeCmdBuffer) throws InvalidProtocolBufferException {
        byte[] decodedBytes = Base64.decodeBase64(fadeCmdBuffer);
        final Firmata.FadeCommand fadeCmd = Firmata.FadeCommand.parseFrom(decodedBytes);
        LOGGER.info("Getting command fade : {}", fadeCmd);
        firmataService.setFading(fadeCmd);
        return fadeCmdBuffer;
    }
}

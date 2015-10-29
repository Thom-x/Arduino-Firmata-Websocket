package fr.thomas.maugin.arduino.firmata.controller;

import fr.thomas.maugin.arduino.firmata.service.FirmataService;
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
    public String setLedPwn() {
        LOGGER.info("Getting command fade");
        LOGGER.info("cmd FIN");
        firmataService.setFading(!firmataService.getLastFade());
        return "OK";
    }
}

package fr.thomas.maugin.arduino.firmata.contorller;

import fr.thomas.maugin.arduino.firmata.service.FirmataService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class FadeController {

    final static Logger logger = Logger.getLogger(FadeController.class);

    @Autowired
    FirmataService firmataService;

    private boolean fade = false;

    @MessageMapping("/fade")
    @SendTo("/result/fade")
    public String setLedPwn() {
        fade = !fade;
        firmataService.setFading(fade);
        return "OK";
    }
}

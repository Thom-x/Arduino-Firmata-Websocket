package fr.thomas.maugin.arduino.firmata.pojo;

import fr.thomas.maugin.arduino.firmata.api.LedController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by thoma on 31/10/2015.
 */

@Service
public class Off extends LedController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Off.class);

    public Off() {
        red.onNext(0);
        green.onNext(0);
        blue.onNext(0);
    }
}

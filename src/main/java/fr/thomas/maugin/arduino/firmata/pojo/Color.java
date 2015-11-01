package fr.thomas.maugin.arduino.firmata.pojo;

import fr.thomas.maugin.arduino.firmata.api.LedController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by thoma on 31/10/2015.
 */

public class Color extends LedController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Color.class);

    private final int redValue;
    private final int greenValue;
    private final int blueValue;

    public Color(int redValue, int greenValue, int blueValue) {
        this.redValue = redValue;
        this.greenValue = greenValue;
        this.blueValue = blueValue;

        red.onNext(redValue);
        green.onNext(greenValue);
        blue.onNext(blueValue);
    }
}

package fr.thomas.maugin.arduino.firmata.domain;

import org.firmata4j.Pin;

/**
 * Created by thoma on 25/10/2015.
 */
public class Leds {
    private final Pin red;
    private final Pin green;
    private final Pin blue;

    public Leds(Pin red, Pin green, Pin blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public Pin getRed() {
        return red;
    }

    public Pin getGreen() {
        return green;
    }

    public Pin getBlue() {
        return blue;
    }


}

package fr.thomas.maugin.arduino.firmata.pojo;

import fr.thomas.maugin.arduino.firmata.api.LedController;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import java.util.concurrent.TimeUnit;

/**
 * Created by thoma on 31/10/2015.
 */

public class Caterpillar extends LedController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Caterpillar.class);

    int periode = 0;

    public Caterpillar() {
        final Observable<Long> intervalCaterpillar = Observable.interval(100, TimeUnit.MILLISECONDS);
        intervalCaterpillar
                .subscribe(b -> {
                    Triple<Integer, Integer, Integer> leds = fadeCaterpillarUpdate();
                    red.onNext(leds.getLeft());
                    green.onNext(leds.getMiddle());
                    blue.onNext(leds.getRight());
                });
    }

    private Triple<Integer, Integer, Integer> fadeCaterpillarUpdate() {
        periode = ++periode % 3;
        int red = 0;
        int green = 0;
        int blue = 0;
        switch (periode) {
            case 0:
                red = 255;
                break;
            case 1:
                green = 255;
                break;
            case 2:
                blue = 255;
                break;
        }
        return Triple.of(red, green, blue);
    }
}

package fr.thomas.maugin.arduino.firmata.pojo;

import fr.thomas.maugin.arduino.firmata.api.LedController;
import org.apache.commons.lang3.tuple.Triple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.concurrent.TimeUnit;

/**
 * Created by thoma on 31/10/2015.
 */

@Service
public class Police extends LedController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Police.class);

    private int periode = 0;

    public Police() {
        final Observable<Long> intervalPolice = Observable.interval(100, TimeUnit.MILLISECONDS);
        intervalPolice
                .subscribe(b -> {
                    Triple<Integer, Integer, Integer> leds = fadePoliceUpdate();
                    red.onNext(leds.getLeft());
                    green.onNext(leds.getMiddle());
                    blue.onNext(leds.getRight());
                });
    }

    private Triple<Integer, Integer, Integer> fadePoliceUpdate() {
        periode = ++periode % 10;
        int red = 0;
        int green = 0;
        int blue = 0;
        switch (periode) {
            case 0:
            case 2:
                red = 255;
                break;
            case 5:
            case 7:
                blue = 255;
                break;
        }
        return Triple.of(red, green, blue);
    }
}

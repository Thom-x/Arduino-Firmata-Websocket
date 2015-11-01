package fr.thomas.maugin.arduino.firmata.pojo;

import fr.thomas.maugin.arduino.firmata.api.LedController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import rx.Observable;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by thoma on 31/10/2015.
 */

@Service
public class Rainbow extends LedController {

    private static final Logger LOGGER = LoggerFactory.getLogger(Rainbow.class);

    public Rainbow() {
        AtomicInteger redValue = new AtomicInteger(0);
        AtomicInteger blueValue = new AtomicInteger(127);
        AtomicInteger greenValue = new AtomicInteger(254);
        AtomicBoolean ascendingRed = new AtomicBoolean(true);
        AtomicBoolean ascendingGreen = new AtomicBoolean(true);
        AtomicBoolean ascendingBlue = new AtomicBoolean(true);
        final Observable<Long> intervalRainbow = Observable.interval(4L, TimeUnit.MILLISECONDS);
        intervalRainbow
                .subscribe(b -> {
                    Integer redValueNew = fadeRainbowUpdate(redValue, ascendingRed);
                    Integer greenValueNew = fadeRainbowUpdate(greenValue, ascendingGreen);
                    Integer blueValueNew = fadeRainbowUpdate(blueValue, ascendingBlue);
                    red.onNext(redValueNew);
                    green.onNext(greenValueNew);
                    blue.onNext(blueValueNew);
                });
    }

    private int fadeRainbowUpdate(AtomicInteger value, AtomicBoolean direction) {
        return value.updateAndGet(i -> {
            if (i > 254) {
                direction.set(false);
                return --i;
            } else if (i < 1) {
                direction.set(true);
                return ++i;
            } else {
                return direction.get() ? ++i : --i;
            }
        });
    }
}

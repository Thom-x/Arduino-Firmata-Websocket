package fr.thomas.maugin.arduino.firmata.service;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import fr.thomas.maugin.arduino.firmata.domain.Leds;
import org.apache.commons.lang3.tuple.Triple;
import org.firmata4j.Pin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;
import rx.subjects.BehaviorSubject;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Thomas on 11/10/2015.
 */

@Service
public class FirmataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirmataService.class);

    private boolean lastFade = false;
    private BehaviorSubject<Boolean> fade = BehaviorSubject.create(false);
    private BehaviorSubject<Triple<Integer, Integer, Integer>> set = BehaviorSubject.create();

    @Autowired
    MetricRegistry metrics;

    @Autowired
    Leds leds;

    @PostConstruct
    private void init() {

        Meter fadeCmd = metrics.meter("Fade command");

        AtomicInteger red = new AtomicInteger(0);
        AtomicInteger blue = new AtomicInteger(127);
        AtomicInteger green = new AtomicInteger(254);
        AtomicBoolean ascendingRed = new AtomicBoolean(true);
        AtomicBoolean ascendingGreen = new AtomicBoolean(true);
        AtomicBoolean ascendingBlue = new AtomicBoolean(true);

        Pin pinRed = leds.getRed();
        Pin pinGreen = leds.getGreen();
        Pin pinBlue = leds.getBlue();

        final Observable<Long> interval = Observable.interval(4L, TimeUnit.MILLISECONDS);
        Observable.combineLatest(interval, fade.distinctUntilChanged(), (a, b) -> b) //
                .filter(b -> b) //
                .subscribe(b -> {
                    fadeCmd.mark();
                    try {
                        Integer redValue = fadeUpdate(red, ascendingRed);
                        Integer greenValue = fadeUpdate(green, ascendingGreen);
                        Integer blueValue = fadeUpdate(blue, ascendingBlue);
                        pinRed.setValue(redValue);
                        pinGreen.setValue(greenValue);
                        pinBlue.setValue(blueValue);
                    } catch (IOException e) {
                        LOGGER.error("Erreur : ", e);
                    }
                });

        Observable.combineLatest(interval, fade, (a, b) -> b) //
                .distinctUntilChanged() //
                .filter(b -> !b) //
                .subscribe(b -> {
                    try {
                        pinRed.setValue(0);
                        pinGreen.setValue(0);
                        pinBlue.setValue(0);
                    } catch (IOException e) {
                        LOGGER.error("Erreur : ", e);
                    }
                });

        Observable.combineLatest(interval, set, (a, b) -> b) //
                .distinctUntilChanged() //
                .subscribe(triple -> {
                    try {
                        pinRed.setValue(triple.getLeft());
                        pinGreen.setValue(triple.getMiddle());
                        pinBlue.setValue(triple.getRight());
                    } catch (IOException e) {
                        LOGGER.error("Erreur : ", e);
                    }
                });
    }

    public FirmataService() {
    }

    public void setFading(boolean fading) {
        lastFade = fading;
        fade.onNext(fading);
    }

    public void setColor(int r, int g, int b) {
        fade.onNext(false);
        lastFade = false;
        set.onNext(Triple.of(r, g, b));
    }

    public boolean getLastFade() {
        return lastFade;
    }

    private int fadeUpdate(AtomicInteger value, AtomicBoolean direction) {
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

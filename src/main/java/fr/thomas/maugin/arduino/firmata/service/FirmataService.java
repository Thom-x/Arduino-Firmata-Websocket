package fr.thomas.maugin.arduino.firmata.service;

import org.apache.log4j.Logger;
import org.firmata4j.IODevice;
import org.firmata4j.Pin;
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

    final static Logger logger = Logger.getLogger(FirmataService.class);

    private BehaviorSubject<Boolean> fade = BehaviorSubject.create(false);

    @Autowired
    IODevice device;

    @PostConstruct
    private void init() {
        AtomicInteger red = new AtomicInteger(0);
        AtomicInteger blue = new AtomicInteger(127);
        AtomicInteger green = new AtomicInteger(254);
        AtomicBoolean ascendingRed = new AtomicBoolean(true);
        AtomicBoolean ascendingGreen = new AtomicBoolean(true);
        AtomicBoolean ascendingBlue = new AtomicBoolean(true);

        Pin pin10 = device.getPin(10);
        Pin pin11 = device.getPin(11);

        final Observable<Long> interval = Observable.interval(4L, TimeUnit.MILLISECONDS);
        Observable.combineLatest(interval, fade.distinctUntilChanged(), (a, b) -> b) //
                .filter(b -> b) //
                .subscribe(b -> {
                    try {
                        Integer redValue = red.updateAndGet(i -> {
                            if (i > 254) {
                                ascendingRed.set(false);
                                return --i;
                            } else if (i < 1) {
                                ascendingRed.set(true);
                                return ++i;
                            } else {
                                return ascendingRed.get() ? ++i : --i;
                            }
                        });
                        Integer greenValue = green.updateAndGet(i -> {
                            if (i > 254) {
                                ascendingGreen.set(false);
                                return --i;
                            } else if (i < 1) {
                                ascendingGreen.set(true);
                                return ++i;
                            } else {
                                return ascendingGreen.get() ? ++i : --i;
                            }
                        });
                        Integer blueValue = blue.getAndUpdate(i -> {
                            if (i > 254) {
                                ascendingBlue.set(false);
                                return --i;
                            } else if (i < 1) {
                                ascendingBlue.set(true);
                                return ++i;
                            } else {
                                return ascendingBlue.get() ? ++i : --i;
                            }
                        });
                        pin10.setValue(redValue);
                        pin11.setValue(blueValue);
                    } catch (IOException e) {
                        logger.error("Erreur : ", e);
                    }
                });

        Observable.combineLatest(interval, fade.distinctUntilChanged(), (a, b) -> !b) //
                .filter(b -> b) //
                .subscribe(b -> {
                    try {
                        pin10.setValue(0);
                        pin11.setValue(0);
                    } catch (IOException e) {
                        logger.error("Erreur : ", e);
                    }
                });
    }

    public FirmataService() {
    }

    public void setFading(boolean fading) {
        logger.info("Enable fading : " + fading);
        fade.onNext(fading);
    }
}

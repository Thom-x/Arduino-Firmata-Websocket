package fr.thomas.maugin.arduino.firmata.service;

import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import fr.thomas.maugin.arduino.firmata.api.ILEdController;
import fr.thomas.maugin.arduino.firmata.api.LedController;
import fr.thomas.maugin.arduino.firmata.domain.Leds;
import fr.thomas.maugin.arduino.firmata.pojo.*;
import fr.thomas.maugin.arduino.firmata.proto.Firmata;
import org.firmata4j.Pin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rx.Observable;
import rx.subjects.BehaviorSubject;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Created by Thomas on 11/10/2015.
 */

@Service
public class FirmataService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FirmataService.class);

    private final BehaviorSubject<ILEdController> ledControllerObs = BehaviorSubject.create(new Color(0, 0, 0));

    @Autowired
    MetricRegistry registry;

    @Autowired
    Leds leds;

    @PostConstruct
    private void init() {

        final Meter meter = registry.meter("Set led");

        Pin pinRed = leds.getRed();
        Pin pinGreen = leds.getGreen();
        Pin pinBlue = leds.getBlue();

        Observable.merge(//
                ledControllerObs.switchMap(s -> s.getRed()), //
                ledControllerObs.switchMap(s -> s.getRed()), //
                ledControllerObs.switchMap(s -> s.getRed())) //
                .subscribe(v -> {
                    meter.mark();
                });

        ledControllerObs //
                .switchMap(s -> s.getRed()) //
                .subscribe(v -> {
                    try {
                        pinRed.setValue(v);
                    } catch (IOException e) {
                        LOGGER.error("Erreur : ", e);
                    }
                });

        ledControllerObs //
                .switchMap(s -> s.getGreen()) //
                .subscribe(v -> {
                    try {
                        pinGreen.setValue(v);
                    } catch (IOException e) {
                        LOGGER.error("Erreur : ", e);
                    }
                });

        ledControllerObs //
                .switchMap(s -> s.getBlue()) //
                .subscribe(v -> {
                    try {
                        pinBlue.setValue(v);
                    } catch (IOException e) {
                        LOGGER.error("Erreur : ", e);
                    }
                });
    }

    public FirmataService() {
    }

    public void setFading(Firmata.FadeCommand fadeCommand) {
        switch (fadeCommand.getType()) {
            case OFF:
                ledControllerObs.onNext(new Off());
                break;
            case RAINBOW:
                ledControllerObs.onNext(new Rainbow());
                break;
            case POLICE:
                ledControllerObs.onNext(new Police());
                break;
            case CATERPILLAR:
                ledControllerObs.onNext(new Caterpillar());
                break;
        }
    }

    public void setColor(int r, int g, int b) {
        ledControllerObs.onNext(new Color(r, g, b));
    }
}

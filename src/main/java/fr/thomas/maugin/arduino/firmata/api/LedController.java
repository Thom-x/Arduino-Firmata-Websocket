package fr.thomas.maugin.arduino.firmata.api;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by thoma on 31/10/2015.
 */
public abstract class LedController implements ILEdController {
    protected final BehaviorSubject<Integer> red = BehaviorSubject.create();
    protected final BehaviorSubject<Integer> green = BehaviorSubject.create();
    protected final BehaviorSubject<Integer> blue = BehaviorSubject.create();

    @Override
    public Observable<Integer> getRed() {
        return red;
    }

    @Override
    public Observable<Integer> getGreen() {
        return green;
    }

    @Override
    public Observable<Integer> getBlue() {
        return blue;
    }

    protected void setRed(int red) {
        this.red.onNext(red);
    }

    protected void setGreen(int green) {
        this.green.onNext(green);
    }

    protected void setBlue(int blue) {
        this.blue.onNext(blue);
    }
}

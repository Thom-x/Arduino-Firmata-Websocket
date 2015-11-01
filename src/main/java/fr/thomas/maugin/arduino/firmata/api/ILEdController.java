package fr.thomas.maugin.arduino.firmata.api;

import rx.Observable;

/**
 * Created by thoma on 01/11/2015.
 */
public interface ILEdController {
    Observable<Integer> getRed();

    Observable<Integer> getGreen();

    Observable<Integer> getBlue();
}

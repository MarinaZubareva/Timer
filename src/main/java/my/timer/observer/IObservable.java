package my.timer.observer;

import java.util.Set;

// interface for Observable,
//

public interface IObservable {
    Set observers = null;

    void add(IObserver iObserver);

    void remove(IObserver iObserver);

    void notifyObserver();
}

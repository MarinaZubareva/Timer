package my.timer.observer;

import java.util.Set;

// interface for Observable,
//

public interface Observable {
    Set observers = null;

    void add(Observer observer);

    void remove(Observer observer);

    void notifyObserver();
}

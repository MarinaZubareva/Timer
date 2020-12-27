package my.timer.service;

import lombok.Data;
import my.timer.memento.Memento;
import my.timer.model.ResponseTimer;
import my.timer.model.Timer;
import my.timer.observer.IObservable;
import my.timer.observer.IObserver;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Data
public class TimersService implements IObservable {
    private List<Timer> timers = new ArrayList<>();
    public Set<IObserver> observers = new HashSet<>();

    public Iterable<ResponseTimer> list() {
        return timers.stream().map(ResponseTimer::new).collect(Collectors.toList());
    }

    public ResponseTimer start(String name) {
        Timer timer = findOrAddTimer(name);
        if (!timer.isActive()) {
            stopAll();
            timer.start();
            System.out.println(String.format("Timer %s started", name));
        } else {
            System.out.println(String.format("Timer %s already active", name));
        }
        return new ResponseTimer(timer);
    }

    public void stop(String name) {
        findTimer(name).ifPresent(Timer::stop);
    }

    public Optional<Timer> findTimer(String name) {
        return timers.stream().filter(t -> t.getName().equals(name)).findFirst();
    }

    public Timer findOrAddTimer(String name) {
        Optional<Timer> optionalTimer = findTimer(name);
        if (optionalTimer.isPresent()) {
            System.out.println(String.format("Timer %s found", name));
            return optionalTimer.get();
        } else {
            Timer timer = Timer.builder().name(name).build();
            timers.add(timer);
            System.out.println(String.format("Timer %s not found", name));
            System.out.println(String.format("Timer %s created", name));
            notifyObserver();
            return timer;
        }
    }

    public void stopAll() {
        timers.forEach(Timer::stop);
    }


    public void add(IObserver iObserver) {
        observers.add(iObserver);
    }

    public void remove(IObserver iObserver) {
        observers.remove(iObserver);
    }

    public void notifyObserver() {
        observers.forEach(IObserver::update);
    }

    @PreDestroy
    public void shutDown() {
        System.out.println("Shut down Timer Service");
        Memento memento = new Memento();
        memento.backup(this);
    }

    @PostConstruct
    public void init() {
        System.out.println("Initialize Timer Service");
        Memento memento = new Memento();
        List<Timer> timers = memento.restore();
        if (timers != null) this.timers = new ArrayList<>(timers);
    }

}


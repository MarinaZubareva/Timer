package my.timer.observer;

import my.timer.service.TimersService;

import java.util.List;

import static java.util.stream.Collectors.toList;


public class TimerObserver implements Observer {
    TimersService timersService;
    List<String> timersNames;

    public TimerObserver(TimersService timersService) {
        this.timersService = timersService;
        timersNames = fillTimersNameFromTimers(this.timersService);
    }

    @Override
    public void update() {
        timersNames = fillTimersNameFromTimers(this.timersService);
    }

    public List<String> getTimersNames() {
        return timersNames;
    }

    private static List<String> fillTimersNameFromTimers(TimersService timersService) {
        return timersService.getTimers().stream().map(t -> t.getName()).collect(toList());
    }
}

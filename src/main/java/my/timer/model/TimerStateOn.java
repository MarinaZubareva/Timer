package my.timer.model;

import java.time.Duration;
import java.time.Instant;

public class TimerStateOn implements TimerState {

    public TimerState start(Timer timer) {
        System.out.println("Timer is active");
        return this;
    }

    public TimerState stop(Timer timer) {
        timer.setLastFinish(Instant.now());
        timer.setLastDuration(timer.getLastDuration().plus(Duration.between(timer.getLastStart(), timer.getLastFinish())));
        return new TimerStateOff();
    }

    public Duration getCurrentDuration(Timer timer) {
        return timer.getLastDuration().plus(Duration.between(timer.getLastStart(), Instant.now()));
    }
}

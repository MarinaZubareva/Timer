package my.timer.model;

import java.time.Duration;
import java.time.Instant;

public class TimerStateOff implements TimerState {

    public TimerState start(Timer timer) {
        timer.setLastStart(Instant.now());
        timer.setLastFinish(null);
        return new TimerStateOn();
    }

    public TimerState stop(Timer timer) {
        return this;
    }

    public Duration getCurrentDuration(Timer timer) {
        return timer.getLastDuration();
    }
}

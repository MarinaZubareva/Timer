package my.timer.model;

import java.time.Duration;

public interface TimerState {

    TimerState start(Timer timer);

    TimerState stop(Timer timer);

    Duration getCurrentDuration(Timer timer);

}

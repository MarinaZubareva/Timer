package my.timer.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.Duration;
import java.time.Instant;

@Data
@Builder
public class Timer {
    private String name;
    private Instant lastStart;
    private Instant lastFinish;

    @Builder.Default
    @NonNull
    private TimerState timerState = new TimerStateOff();

    @Builder.Default
    @NonNull
    private Duration lastDuration = Duration.ZERO;

    public boolean isActive() {
        return timerState instanceof TimerStateOn;
    }

    public void start() {
        timerState = timerState.start(this);
    }

    public void stop() {
        timerState = timerState.stop(this);
    }

    public Duration getCurrentDuration() {
        return timerState.getCurrentDuration(this);
    }
}

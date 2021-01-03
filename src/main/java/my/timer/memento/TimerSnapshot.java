package my.timer.memento;

import lombok.Data;
import lombok.NoArgsConstructor;
import my.timer.dba.TimerEntity;
import my.timer.model.Timer;
import my.timer.model.TimerStateOff;
import my.timer.model.TimerStateOn;

import java.time.Duration;
import java.time.Instant;

@Data
@NoArgsConstructor
public class TimerSnapshot {
    private String name;
    private Instant lastStart;
    private Instant lastFinish;
    private Boolean isActive;
    private Duration lastDuration;

    public TimerSnapshot(Timer timer) {
        this.name = timer.getName();
        this.lastStart = timer.getLastStart();
        this.lastFinish = timer.getLastFinish();
        this.isActive = timer.isActive();
        this.lastDuration = timer.getLastDuration();
    }

    public Timer restoreTimer() {
        return Timer.builder()
                .name(this.name)
                .lastStart(this.lastStart)
                .lastFinish(this.lastFinish)
                .timerState((this.isActive) ? new TimerStateOn() : new TimerStateOff())
                .lastDuration(this.lastDuration)
                .build();
    }

    public Timer restoreTimerfromDb(TimerEntity timerEntity) {
        return Timer.builder()
                .name(timerEntity.getName())
                .lastStart(timerEntity.getLastStart() == null ? null : timerEntity.getLastStart().toInstant())
                .lastFinish(timerEntity.getLastFinish() == null ? null : timerEntity.getLastFinish().toInstant())
                .timerState((timerEntity.getIsactive()) ? new TimerStateOn() : new TimerStateOff())
                .lastDuration(Duration.parse(timerEntity.getLastDuration()))
                .build();
    }
}

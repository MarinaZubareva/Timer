package my.timer.dba;

import lombok.Data;
import my.timer.model.Timer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "timer")
public class TimerEntity {
    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "laststart")
    private Timestamp lastStart;

    @Column(name = "lastfinish")
    private Timestamp lastFinish;

    @Column(name = "isactive")
    private Boolean isactive;

    @Column(name = "lastduration")
    private String lastDuration;

    public TimerEntity() {
    }

    public TimerEntity(Timer timer) {
        this.name = timer.getName();
        this.lastStart = timer.getLastStart() == null ? null : Timestamp.from(timer.getLastStart());
        this.lastFinish = timer.getLastFinish() == null ? null : Timestamp.from(timer.getLastFinish());
        this.isactive = timer.isActive();
        this.lastDuration = timer.getLastDuration() == null ? null : String.valueOf(timer.getLastDuration());

    }
}

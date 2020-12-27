package my.timer.model;

import lombok.Data;


@Data
public class ResponseTimer {
    private String name;
    private String duration;

    public ResponseTimer(Timer timer) {
        this.name = timer.getName();

        long hours = timer.getCurrentDuration().toHours();
        long minutes = timer.getCurrentDuration().minusHours(hours).toMinutes();
        long seconds = timer.getCurrentDuration().minusHours(hours).minusMinutes(minutes).getSeconds();

        this.duration = String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

}

package my.timer;

import my.timer.controller.TimersController;
import my.timer.model.ResponseTimer;
import my.timer.observer.TimerObserver;
import my.timer.service.TimersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TimerApplicationTests {

    @Autowired
    private TimersService timerService;

    @Autowired
    private TimersController timersController;

    @Test
    void controllerTestStart() throws InterruptedException {
        // timer name
        String uuid = UUID.randomUUID().toString();
        // new timer creation
        ResponseTimer timer = timersController.start(uuid);

        Thread.sleep(5000);

        // start timer with the same name
        ResponseTimer timer1 = timersController.start(uuid);
        String[] timerDuration1 = timer.getDuration().split(":");
        System.out.println(String.format("Timer %s duration is %s", timer1.getName(), timer1.getDuration()));
        assertThat(Integer.valueOf(timerDuration1[2]) > 0);

    }

    @Test
    void testObserver() {
        String uuid1 = UUID.randomUUID().toString();
        String uuid2 = UUID.randomUUID().toString();

        timersController.start(uuid1);
        TimerObserver timerObserver = new TimerObserver(timerService);
        timerService.add(timerObserver);
        timersController.start(uuid2);

        System.out.println("timer's names in observer:");
        timerObserver.getTimersNames().forEach(name -> System.out.println(name));

        assertThat(timerObserver.getTimersNames().contains(uuid2));
    }

    @Test
    void testMemento() {
        String uuid = UUID.randomUUID().toString();
        timersController.start(uuid);
        timerService.shutDown();
        timerService.init();
        timerService.getTimers();
        assertThat(timerService.getTimers().stream().anyMatch(item -> uuid.equals(item.getName())));

    }

}

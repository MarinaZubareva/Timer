package my.timer.controller;

import lombok.AllArgsConstructor;
import my.timer.model.ResponseTimer;
import my.timer.service.TimersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimersController {
    @Autowired
    private TimersService timerService;

    @GetMapping("/start")
    public ResponseTimer start(@RequestParam(value = "name", defaultValue = "idle") String name) {
        return timerService.start(name);
    }

    @GetMapping("/stop")
    public void stop(@RequestParam(value = "name") String name) {
        timerService.stop(name);
    }

    @GetMapping("/list")
    public Iterable<ResponseTimer> list() {
        return timerService.list();
    }
}

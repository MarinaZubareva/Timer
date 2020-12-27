package my.timer.memento;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import my.timer.model.Timer;
import my.timer.service.TimersService;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class Memento {
    public void backup(TimersService timersService) {
        try {
            List<TimerSnapshot> snapshots = timersService.getTimers().stream().map(TimerSnapshot::new).collect(Collectors.toList());
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            System.out.println(objectMapper.writeValueAsString(snapshots));
            File file = new File("src/main/resources/memento.json");
            objectMapper.writeValue(file, snapshots);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<Timer> restore() {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            File file = new File("src/main/resources/memento.json");
            List<TimerSnapshot> snapshots = objectMapper.readValue(file, new TypeReference<>() {});
            return snapshots.stream().map(TimerSnapshot::restoreTimer).collect(Collectors.toList());
        } catch (IOException e) {
            System.out.println("memento backup not found");
            return null;
        }
    }
}

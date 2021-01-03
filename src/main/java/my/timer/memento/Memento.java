package my.timer.memento;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import my.timer.dba.TimerEntity;
import my.timer.dba.TimerRepository;
import my.timer.model.Timer;
import my.timer.service.TimersService;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class Memento implements TimerRepository {
    @Autowired
    private TimerRepository timerRepository;

    @Autowired
    TimersService timersService;

    final ObjectMapper objectMapper = new ObjectMapper();

    @Before("execution(restore())")
    public void init()    {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public void backup() {
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

    public void backupToDb() {
        timersService.getTimers().stream().forEach(timer -> {
            TimerEntity timerEntity = new TimerEntity(timer);
            timerRepository.save(timerEntity);
        });
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

    public List<Timer> restoreFromDb() {
        List<TimerEntity> timerEntities = findAll();
        return timerEntities.stream().map(timerEntity -> 
                new TimerSnapshot().restoreTimerfromDb(timerEntity)) 
                .collect(Collectors.toList());
    }

    @Override
    public <S extends TimerEntity> S save(S s) {
        timerRepository.save(s);
        return null;
    }

    @Override
    public <S extends TimerEntity> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<TimerEntity> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    public List<TimerEntity> findAll() {

        List<TimerEntity> timers = (List<TimerEntity>) timerRepository.findAll();

        return timers;
    }

    @Override
    public Iterable<TimerEntity> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(TimerEntity timerEntity) {

    }

    @Override
    public void deleteAll(Iterable<? extends TimerEntity> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}

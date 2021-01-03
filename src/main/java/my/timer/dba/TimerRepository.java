package my.timer.dba;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimerRepository extends CrudRepository<TimerEntity, Long> {

}
package grupo.uno.quickiz.repositories;

import grupo.uno.quickiz.models.Schedule;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface ScheduleRepository extends CrudRepository<Schedule, Integer> {

    Schedule findByCode(String code);
    void deleteByCode(String code);
    List<Schedule> findAll();

}


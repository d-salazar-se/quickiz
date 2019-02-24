package grupo.uno.quickiz.repositories;

import java.util.List;
import grupo.uno.quickiz.models.QuizSchedule;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface QuizScheduleRepository extends CrudRepository<QuizSchedule, Integer> {

    List<QuizSchedule> findAll();
}
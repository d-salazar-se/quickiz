package grupo.uno.quickiz.repositories;

import java.util.List;
import grupo.uno.quickiz.models.QuestionInstance;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface QuestionInstanceRepository extends CrudRepository<QuestionInstance, Integer> {

    List<QuestionInstance> findAll();

}


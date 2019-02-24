package grupo.uno.quickiz.repositories;

import java.util.List;
import grupo.uno.quickiz.models.Quiz;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface QuizRepository extends CrudRepository<Quiz, Integer> {

    List<Quiz> findAll();

}


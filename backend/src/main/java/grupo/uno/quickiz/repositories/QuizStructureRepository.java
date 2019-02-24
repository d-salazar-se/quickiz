package grupo.uno.quickiz.repositories;

import java.util.List;
import grupo.uno.quickiz.models.QuizStructure;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface QuizStructureRepository extends CrudRepository<QuizStructure, Integer> {

    List<QuizStructure> findAll();

}


package grupo.uno.quickiz.repositories;

import java.util.List;
import org.springframework.stereotype.Repository;
import grupo.uno.quickiz.models.QuestionStructure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface QuestionStructureRepository extends CrudRepository<QuestionStructure, Integer> {

    List<QuestionStructure> findAll();

}


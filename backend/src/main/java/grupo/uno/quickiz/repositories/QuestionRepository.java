package grupo.uno.quickiz.repositories;

import java.util.List;

import grupo.uno.quickiz.models.Question;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface QuestionRepository extends CrudRepository<Question, Integer> {

    List<Question> findAll();
    Question findByTitle(String title);
    void deleteByTitle(String title);
    
    @Query(value = "SELECT * FROM questions q WHERE q.score = :score AND q.pool_id= :poolId ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Question findQuestion(@Param("score") Integer score, @Param("poolId") Integer poolId);
}


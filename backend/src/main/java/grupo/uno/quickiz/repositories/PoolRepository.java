package grupo.uno.quickiz.repositories;

import java.util.List;
import grupo.uno.quickiz.models.Pool;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PoolRepository extends CrudRepository<Pool, Integer> {

    Pool findByName(String name);
    List<Pool> findAll();

}


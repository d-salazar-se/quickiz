package grupo.uno.quickiz.repositories;

import java.util.List;
import grupo.uno.quickiz.models.User;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findAll();
    User findByName(String name);
    void deleteByName(String name);

}


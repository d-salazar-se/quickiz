package grupo.uno.quickiz.repositories;

import grupo.uno.quickiz.models.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface RoleRepository extends CrudRepository<Role, Integer> {

    Role findByName(String name);
    void deleteByName(String name);
    List<Role> findAll();

}


package rest.crud.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rest.crud.app.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

}

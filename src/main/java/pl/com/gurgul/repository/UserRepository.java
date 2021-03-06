package pl.com.gurgul.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.com.gurgul.model.User;
import pl.com.gurgul.utils.UserRoles;

import java.util.List;

/**
 * Created by agurgul on 10.12.2016.
 */
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    User findByPesel(String pesel);
    User findByUuid(String uuid);
    List<User> findByUserRole(UserRoles role);
}

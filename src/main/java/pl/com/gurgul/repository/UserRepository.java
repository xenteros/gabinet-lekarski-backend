package pl.com.gurgul.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.com.gurgul.model.User;

import java.util.List;

/**
 * Created by agurgul on 10.12.2016.
 */
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUuid(String uuid);
}

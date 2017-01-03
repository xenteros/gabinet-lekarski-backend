package pl.com.gurgul.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.com.gurgul.model.Token;

/**
 * Created by agurgul on 02.01.2017.
 */
@Repository
@Transactional
public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByValue(String value);
    Token findByUuid(String uuid);
}
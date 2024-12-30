package workerService.security.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import workerService.security.entity.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    boolean existsByUsername(String s);
    boolean existsByUsernameAndIdNotIn(String s, List<Long> ids);
}


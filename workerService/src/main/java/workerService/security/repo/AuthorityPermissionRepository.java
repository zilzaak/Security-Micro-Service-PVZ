package workerService.security.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import workerService.security.entity.AuthorityPermission;

import java.util.List;

public interface AuthorityPermissionRepository extends JpaRepository<AuthorityPermission, Long> {
    List<AuthorityPermission> findByRoleName(String roleName);
}

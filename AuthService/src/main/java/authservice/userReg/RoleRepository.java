package authservice.userReg;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleName(String roleName);

    boolean existsByRoleName(String super_admin);

    boolean existsByAuthority(String authority);

    boolean existsByAuthorityAndIdNotIn(String authority, List<Long> asList);

    boolean existsByRoleNameAndIdNotIn(String roleName, List<Long> asList);
}

package applicant.security.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
select  * from acl_user
select * from acl_role
select * from acl_user_role

select  concat(u.id,'-',u.username) as username , concat(r.id,'-',r.authority) as authority ,
ap.role_name, ap.api_pattern
from acl_user_role ur
join acl_user u on u.id=ur.user_id
join acl_role r on r.id=ur.role_id
left join authority_permission ap on ap.role_name=r.authority


 */
@Entity
public class AuthorityPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roleName;        // Role name, e.g., "SUPER_ADMIN"
    private String apiPattern;      // API pattern, e.g., "/player/**"

    // Constructors, getters, and setters

    public AuthorityPermission() {}

    public AuthorityPermission(String roleName, String apiPattern) {
        this.roleName = roleName;
        this.apiPattern = apiPattern;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getApiPattern() {
        return apiPattern;
    }

    public void setApiPattern(String apiPattern) {
        this.apiPattern = apiPattern;
    }
}


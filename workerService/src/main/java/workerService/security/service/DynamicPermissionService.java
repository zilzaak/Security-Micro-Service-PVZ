package workerService.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import workerService.security.entity.AuthorityPermission;
import workerService.security.repo.AuthorityPermissionRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DynamicPermissionService {

    @Autowired
    private AuthorityPermissionRepository authorityPermissionRepository;

    public Map<String, String> getPermissions() {
        List<AuthorityPermission> permissions = authorityPermissionRepository.findAll();
        Map<String, String> maps = new HashMap<>();
          for(AuthorityPermission obj :  permissions ){
             if(maps.isEmpty()){
                 maps.put(obj.getApiPattern(),obj.getRoleName());
             }else{
                 if(maps.containsKey(obj.getApiPattern())){
                  String val=maps.get(obj.getApiPattern());
                  val=val+","+obj.getRoleName();
                  maps.put(obj.getApiPattern(),val);
              }else{
                     maps.put(obj.getApiPattern(),obj.getRoleName());
                 }

             }
          }
        return  maps;
    }
}

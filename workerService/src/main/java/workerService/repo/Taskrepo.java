package workerService.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import workerService.entity.Task;

public interface Taskrepo extends JpaRepository<Task,Integer> , JpaSpecificationExecutor<Task> {

}

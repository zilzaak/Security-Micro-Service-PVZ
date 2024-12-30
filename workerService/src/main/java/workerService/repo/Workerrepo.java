package workerService.repo;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import workerService.entity.WorkerDto;
import workerService.entity.Worker;

import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;


@NamedEntityGraph(name = "worker.tasks", attributeNodes = @NamedAttributeNode("tasks"))
@Repository

public interface Workerrepo extends JpaRepository<Worker,Integer> , JpaSpecificationExecutor<Worker> {

boolean existsByNameIgnoreCaseInAndId(List<String> lst,int id);

    @Query("SELECT DISTINCT name FROM Worker WHERE name NOT IN (:names)")
    List<String> findNonReferencedNames(@Param("names") List<String> names);

    @Query(" select count(x) from Worker x where x.name=?1 and " +
            " (  x.joindate between ?2 and ?3 " +
            " or x.admitDate between ?2 and ?3 ) ")
    Long checkNameExistOrNot(String name , LocalDate joinDate , LocalDate admitDate);

    @Query(" select avg(x.salary) from Worker x where x.name=?1 and " +
            " (  x.joindate between ?2 and ?3 " +
            " or x.admitDate between ?2 and ?3 ) ")
    Long checkNameExistOrNot2(String name , LocalDate joinDate , LocalDate admitDate);

    boolean existsByNameAndJoindateInOrAdmitDateIn(String name , List<LocalDate> lst, List<LocalDate> lst2);

    @Query(" select count(x) from Worker x where x.id=:namelist ")
    int getnamelistIn(@Param("namelist") Long namelist);

    //Worker findByName(String name);
    Worker findByName(String name);
    @Query(" select count(x.salary) from Worker x where UPPER(x.name)= UPPER(?1) ")
    Long getnameignorecase(String name);
    @Query(" select x.id as id , x.salary as salary , x.name as name  from Worker x where " +
            " ( CAST( :day1 AS date )  is null or  x.admitDate2  >= CAST( :day1 AS date )    )  and " +
            " ( CAST( :day2 AS date )  is null or  x.admitDate2  <= CAST( :day2 AS date )  )  " )
    List<Map<String, Object>> getdateBetween(@Param("day1") Date day1, @Param("day2") Date day2);

    @Query(" select  x.name    from Worker x where upper(x.name) like %:nam% ")
    List<String> getnamelike(@Param("nam") String nam);

    @Query("SELECT x FROM Worker x " +
            " where  ( ?1 IS NOT NULL and  x.name=?1 ) or " +
            " (?2 IS NOT NULL and x.salary=?2 ) ")
    List<Worker> getcasewhen(String nam,  Integer sal);
    @Query("SELECT max(x.salary) FROM Worker x ")
    int getMaxSalary();

    @Query(value = "select x.admitDate2 as name , x.salary as salary , x.id as id from Worker x order by x.salary desc limit 1", nativeQuery = true)
    Map<String, Object> nativeQuery1();


    @Query("select x.admitDate  from  Worker x where x.admitDate=(select max(x.admitDate) from Worker x ) ")
    List<Object> getMaxDate();

    @EntityGraph(value = "worker.tasks", type = EntityGraph.EntityGraphType.FETCH)
    @Query("select x from Worker x where x.id=:id ")
    Worker getWorkerId(@Param("id") Integer id);

    Long countByName(String name);

    Long countByNameAndJoindate(String name, LocalDate parse);

    List<Worker> findTop5ByNameOrderByIdDesc(String name);

    Object existsByNameInIgnoreCase(List<String> asList);

    List<Worker>  findByJoindate(LocalDate currentDate);

    List<Worker> findByJoindateBetween(LocalDate day1 , LocalDate day2);
    @Query("select x  from  Worker x where x.name = :name and x.admitDate2 is null ")
    List<Worker> getWoprbyByDate2(@Param("name") String name);

    @Query("select DISTINCT  x.name  , x.salary from Worker  x ")
    List<Object[]> getdistinctEmplyeeWithSalary();

    @Query("select x  from  Worker x where x.name = :nm")
    Worker findByNameCustom(@Param("nm") String nm);
    @Query("select x.id as id , x.salary as salary , x.name as name  from  Worker x ")
    List<WorkerDto> setIndto();

    @Query("select count(x) from  Worker x  " +
            " where x.id=:namelist ")
    Long filtername(@Param("namelist") Integer namelist);

    Worker findTopByNameOrderByIdDesc(String chadni_akter);

    Worker findTopByNameAndSalary(String tania,Integer sal);
}

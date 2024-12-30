package applicant.repo;

import applicant.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ApplicantRepo  extends JpaRepository<Applicant,Long> {


    @Query(" select x.session as session , x.unit as unit , x.district as district , COUNT(x.id) as totalFound " +
            "  from Applicant x  " +
            " where x.session='2013-14' and x.unit='K'  " +
            " group by x.session , x.unit , x.district ")
   // List<Map<String, Object>> sessionUnitDistrict();
   // Optional<Object[]>
    Object[] sessionUnitDistrict();

    @Query(" select x.id as id , " +
            "x.session as session , x.unit as unit , x.district as district , cast(x.createDate as date) as  createDate " +
            "  from Applicant x  " +
            " where  " +
            "  ( COALESCE(:disList, NULL) is null or x.district in :disList ) " +
            " and ( COALESCE(:unit, NULL)  is null or x.unit in :unit ) " +
            " and ( COALESCE(:group, NULL) is null or x.groupName in :group ) order  BY x.id desc")
    List<Map<String, Object>>  sessionUnitDistrict2(
    @Param("disList") List<String>  disList ,
            @Param("group")  List<String>  group ,
              @Param("unit")  List<String>  unit
    );

    @Query(" select  count(x.gpaTotal)  "+
            "  from Applicant x  " +
            " where ( x.session =:sess  and x.unit =:unt ) ")
    Optional<Integer> countquery(@Param("sess")String sess ,
    @Param("unt") String unt );

    List<Applicant> findBySessionAndDistrictAndUnit(String s, String brahmmanbaria, String a);
}

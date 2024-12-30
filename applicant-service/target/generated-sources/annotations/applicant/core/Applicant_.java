package applicant.core;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Applicant.class)
public abstract class Applicant_ {

	public static volatile SingularAttribute<Applicant, Integer> gpaTotal;
	public static volatile SingularAttribute<Applicant, String> unit;
	public static volatile SingularAttribute<Applicant, String> groupName;
	public static volatile SingularAttribute<Applicant, String> hscBoard;
	public static volatile SingularAttribute<Applicant, String> gender;
	public static volatile SingularAttribute<Applicant, String> session;
	public static volatile SingularAttribute<Applicant, String> district;
	public static volatile SingularAttribute<Applicant, String> name;
	public static volatile SingularAttribute<Applicant, Long> id;
	public static volatile SingularAttribute<Applicant, Date> createDate;

	public static final String GPA_TOTAL = "gpaTotal";
	public static final String UNIT = "unit";
	public static final String GROUP_NAME = "groupName";
	public static final String HSC_BOARD = "hscBoard";
	public static final String GENDER = "gender";
	public static final String SESSION = "session";
	public static final String DISTRICT = "district";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String CREATE_DATE = "createDate";

}


package applicant.entity;

import lombok.Data;

@Data
public class ApplicantDTO {
    private Long id;
    private String session;
    private String district;
    private String unit;
    private Long name;
    private String gender;
    private String groupName;
    private String gpaTotal;
    private String hscBoard;

}

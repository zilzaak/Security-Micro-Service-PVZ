package applicant.controller;

import applicant.repo.ApplicantRepo;
import applicant.entity.Applicant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/applicant")
public class ApplicantController {

    @Autowired
    private ApplicantRepo applicantRepo;
    @Autowired
    private RestTemplate rstc;

    @PostMapping("/create")
    public ResponseEntity<?> save(@RequestBody Applicant applicants){
       applicantRepo.save(applicants);
        return new  ResponseEntity<>(applicants, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(HttpServletRequest request , @PathVariable Long id){
        Applicant applicant = applicantRepo.findById(id).orElse(null);
        String header = request.getHeader("Authorization");
        String jwtToken = header.replace("Bearer ", "");
        this.callToWorker(jwtToken);
        return new  ResponseEntity<>(applicant, HttpStatus.OK);
    }

    public void callToWorker(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = rstc.exchange(
                "http://worker-service/worker/get/"+5,
                HttpMethod.GET,
                entity,
                String.class
        );
        System.out.println("Response from Worker Service: " + response.getBody());

    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(HttpServletRequest request ){
        return new  ResponseEntity<>(
                applicantRepo.findAll(),HttpStatus.OK);
    }


    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody List<Applicant> applicants){
        applicantRepo.saveAll(applicants);
        return new  ResponseEntity<>(applicants, HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Applicant applicant = applicantRepo.findById(id).orElse(null);
        applicantRepo.delete(applicant);
        return new  ResponseEntity<>("deleted", HttpStatus.OK);
    }


}

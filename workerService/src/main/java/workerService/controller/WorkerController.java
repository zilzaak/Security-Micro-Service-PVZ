package workerService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import workerService.repo.Taskrepo;
import workerService.entity.Worker;
import workerService.repo.Workerrepo;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/worker")
public class WorkerController {

   @Autowired
   private Workerrepo wrr;
    @Autowired
    private Taskrepo trr;

    @Autowired
    private RestTemplate rstc;

 @PostMapping("/create")
public ResponseEntity<?> addworker(@RequestBody Worker worker){
wrr.save(worker);
return new  ResponseEntity<Worker>(worker, HttpStatus.OK);
}


    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deletre(@PathVariable Integer id){
        wrr.deleteById(id);
        return new  ResponseEntity<>(id,HttpStatus.OK);
    }


    @PostMapping("/update")
    public ResponseEntity<?> update(HttpServletRequest request,@RequestBody Worker worker){
        wrr.save(worker);
        String header = request.getHeader("Authorization");
        String jwtToken = header.replace("Bearer ", "");
        this.callToApplicant(jwtToken);
        return new  ResponseEntity<Worker>(worker, HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(HttpServletRequest request , @PathVariable Integer id){
        String header = request.getHeader("Authorization");
        String jwtToken = header.replace("Bearer ", "");
       // this.callToApplicant(jwtToken);
        return new  ResponseEntity<Worker>(wrr.findById(id).orElse(null), HttpStatus.OK);
    }

    public void callToApplicant(String token){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = rstc.exchange(
                "http://applicant-service/applicant/getAll",
                HttpMethod.GET,
                entity,
                String.class
        );
        System.out.println("Response from Applicant Service: " + response.getBody());

    }

}

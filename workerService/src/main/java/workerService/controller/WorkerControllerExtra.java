package workerService.controller;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import workerService.entity.Task;
import workerService.entity.WorkerDto;
import workerService.repo.Taskrepo;
import workerService.entity.Worker;
import workerService.repo.Workerrepo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/workerExtra")
public class WorkerControllerExtra {
    @Autowired
    private Workerrepo wrr;
    @Autowired
    private Taskrepo trr;

    @PersistenceContext
    private EntityManager em;

    @GetMapping("/distinct")
    //@PreAuthorize("hasAuthority('SUPER_ADMIN') or hasAuthority('PLAYER_ADMIN')")
    public ResponseEntity<?> distinctEmpSalary(){
        List<Object[]> lst = wrr.getdistinctEmplyeeWithSalary();
        return new  ResponseEntity<>(lst, HttpStatus.OK);
    }

    @GetMapping("/existByName")
    public ResponseEntity<?> existByName(){

        return new ResponseEntity<>(wrr.existsByNameInIgnoreCase(Arrays.asList("tanzir KHAN")),HttpStatus.OK);

    }
    @GetMapping("/setdto")
    public ResponseEntity<?> setdto(){

        List<WorkerDto> resp = wrr.setIndto();
        return new ResponseEntity<>(resp,HttpStatus.OK);

    }
    @GetMapping("/filtername")
    public ResponseEntity<?> filtername(){
        String []stringArray={"Chandni akter"};
        Set<String> namelist = Arrays.stream(stringArray)
                .collect(Collectors.toSet());
        long count = wrr.filtername(415);
        return new ResponseEntity<>(count,HttpStatus.OK);

    }



    @GetMapping("/byWorkerId/{id}")
    public ResponseEntity<?> byWorkerId(@PathVariable Integer id){
        Worker wk=wrr.findById(id).get();
        Task w=wk.getTasks().get(0);
        System.out.println("worker name "+w.getName()+" worker age"+w.getName()+" worker"+w.getDate());
        int h=45+556;
        int k=h+2;
        System.out.println("worker name "+w.getName()+" worker age"+w.getName()+" worker"+w.getDate());
        return new  ResponseEntity<Worker>(wk, HttpStatus.OK);
    }

    @GetMapping("/deleteChild/{id}")
    public ResponseEntity<?> deleteChild(@PathVariable Integer id){
        Worker wk=wrr.findById(id).get();
        List<Task> lst=wk.getTasks();
        lst.clear();
        System.out.println("before removing the list size is "+lst.size());
        Task f=new Task();
        f.setName("xxxxxxxx");f.setDate(LocalDate.now());
        f.setTechnology("xxxxxxxxxxxxxxxxx");
        f.setWorker(wk);
        Task f2=new Task();
        f2.setWorker(wk);
        f2.setName("xxxxxxxx");f2.setDate(LocalDate.now());
        f2.setTechnology("xxxxxxxxxxxxxxxxx");
        lst.add(f);
        lst.add(f2);
        System.out.println("after removing the list size is "+lst.size());
        wk.setTasks(lst);
        wrr.save(wk);
        return new  ResponseEntity<Worker>(wk, HttpStatus.OK);
    }


    @GetMapping("/byTaskId/{id}")
    public ResponseEntity<?> byTaskId(@PathVariable Integer id){
        Task wk=trr.findById(id).get();

        Worker w=wk.getWorker();
        System.out.println("worker name "+w.getName()+" worker age"+w.getDob()+" worker"+w.getNid());
        int h=45+556;
        int k=h+2;
        System.out.println("worker name "+w.getName()+" worker age"+w.getDob()+" worker"+w.getNid());
        return new  ResponseEntity<Task>(wk, HttpStatus.OK);
    }

    @GetMapping("/datebetween")
    public ResponseEntity<?> datebetween(@RequestParam String d1 ,@RequestParam String d2 ) throws ParseException {

        SimpleDateFormat sdf= new SimpleDateFormat("dd-MM-yyyy");
        Date day1=null;

        Date day2=null ;

        if(d1.length()>0){
            day1=sdf.parse(d1);
        }
        if(d2.length()>0){
            day2=sdf.parse(d2);
        }

        List<Map<String,Object>> lst=wrr.getdateBetween(day1,day2) ;
        return new  ResponseEntity<>(lst, HttpStatus.OK);
    }

    @GetMapping("/between")
    public ResponseEntity<?> between(@RequestParam String d1 ,@RequestParam String d2 ) throws ParseException {

        List<Worker> lst=wrr.findByJoindateBetween(LocalDate.parse(d1),LocalDate.parse(d2));
        return new  ResponseEntity<>(lst, HttpStatus.OK);
    }




    @GetMapping("/getbyname/{name}")
    public ResponseEntity<?> getbyname(@PathVariable String name ){
        Worker wk = wrr.findByName(name);
        return new  ResponseEntity<>(wrr.getWoprbyByDate2(name), HttpStatus.OK);
    }


    @GetMapping("/nameilike/{name}")
    public ResponseEntity<?> nameilike(@PathVariable String name ){
        Long  wk = wrr.getnameignorecase(name);
        return new  ResponseEntity<>(wk, HttpStatus.OK);
    }



    @GetMapping("/namedate/{name}/{date1}/{date2}")
    public ResponseEntity<?> getbyname(@PathVariable String name ,
                                       @PathVariable String date1 ,
                                       @PathVariable String date2){

        LocalDate joinDate = LocalDate.parse(date1, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate admitDate = LocalDate.parse(date2,DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        List<LocalDate> lst=new ArrayList<>();
        lst.add(joinDate);
        lst.add(admitDate);
        Boolean wk = wrr.existsByNameAndJoindateInOrAdmitDateIn(name,lst,lst);
        return new  ResponseEntity<>(wk, HttpStatus.OK);
    }


    @GetMapping("/existbyname")
    public ResponseEntity<?> existbyname(@RequestParam Map<String,String> cp){
        String sql = " select a.name , a.id from Worker a ";
        TypedQuery<Tuple> query = em.createQuery(sql,Tuple.class);
        List<Tuple> LIST= query.getResultList();

        return new  ResponseEntity<>(LIST, HttpStatus.OK);
    }

    @GetMapping("/topBy")
    public ResponseEntity<?> topBy(){
        Worker wr = wrr.findTopByNameAndSalary("tania",84000);
        return new  ResponseEntity<>(wr, HttpStatus.OK);
    }



    @GetMapping("/specific1")
    public ResponseEntity<?>  specificcolumnFetch(){
        int f=20000; int id=10;int id2=21 ;
        Session session=em.unwrap(Session.class);
        Query query=session.createQuery("select s.id , s.salary from Worker s where s.salary>"+f+"and s.id>"+id+"and s.id<"+id2);
        List<Object[]> rows=query.list();
        return new  ResponseEntity<List<Object[]>>(rows, HttpStatus.OK);
    }



    @GetMapping("/join")
    public ResponseEntity<?>  rightjoindfd(){
        String q="select distinct a.name from Worker a";

        Session session=em.unwrap(Session.class);
        Query query=session.createQuery(q);
        List<Object[]> rows=query.list();
        Map<String,Object> response=new HashMap<>();
        response.put("total reord",rows.stream().count());
        response.put("list",rows);
        return new  ResponseEntity<>(response, HttpStatus.OK);
    }



    @GetMapping("/selectall")
    public ResponseEntity<?> selectall(@RequestParam Map<String,String> cp){
        CriteriaBuilder cb=em.getCriteriaBuilder();
        CriteriaQuery<Worker> cq= cb.createQuery(Worker.class);
        Root<Worker> root = cq.from(Worker.class);
        cq.select(root);
        List<Worker> results = em.createQuery(cq).getResultList();

        return new  ResponseEntity<List<Worker>>(results, HttpStatus.OK);
    }


    @GetMapping("/existbyin")
    public ResponseEntity<?> existbyin(){
        List<String> lst=Arrays.asList("chad akter","peeotnixxx");
        if(wrr.existsByNameIgnoreCaseInAndId(lst,4)){
            return new  ResponseEntity<>("the list exist", HttpStatus.OK);
        }

        return new  ResponseEntity<>("the list not exist", HttpStatus.OK);

    }

    @GetMapping("/names")
    public ResponseEntity<?> finddistinctnamenotin(){
        List<String> name=Arrays.asList("chadni akter","kupassa");
        List<String> lst=wrr.findNonReferencedNames(name);
        return new  ResponseEntity<>(lst, HttpStatus.OK);

    }

    @GetMapping("/maxDays")
    public ResponseEntity<?> getMaxDays(){
        Object getMaxDays=wrr.getMaxDate();
        @SuppressWarnings("unchecked")
        List<LocalDate> dd= (List<LocalDate>) getMaxDays;
        String empCode=checkName("isak");
        System.out.println("xxxxxxxxxxxxxxxxxx"+empCode);
        return new  ResponseEntity<>(dd, HttpStatus.OK);
    }

    private String checkName(String name){

        // THE BREAK STATEMENT BREAK THE LOOP OR EXIT FROM ITS IMMEDIATE LOOP
        // ON THE OTHER HAND THE CONTINUE STATEMENT DONT GO TO NEXT LINE OR IT SKIP THE NEXT LINE AND AGAIN DO NEXT TASK

        if(name.equalsIgnoreCase("a")){
            return  "A";
        }
        else if(name.equalsIgnoreCase("a")){
            return  "B";
        }
        else{
            return null;
        }

    }

    List<LocalDate> lst=new ArrayList<>();

    @GetMapping("/test")
    public ResponseEntity<?> finddistidfghdfnctnamenotin(){

        lst=addvalues();
        if(lst.size()>0){
            System.out.println(lst.size());
        }
        lst.remove(LocalDate.now());

        return new  ResponseEntity<>(lst, HttpStatus.OK);

    }

    private List<LocalDate> addvalues() {

        if(lst.size()<1){
            lst.add(LocalDate.now());
            lst.add(LocalDate.parse("2023-02-12",DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            lst.add(LocalDate.parse("2025-02-12",DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            lst.add(LocalDate.parse("2024-02-12",DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        }

        return lst;

    }
    @GetMapping("/spec/{namd}")
    public ResponseEntity<?> spec(@PathVariable String namd){
        List<String> getNames = wrr.getnamelike(namd.toUpperCase());
        return new  ResponseEntity<>(getNames, HttpStatus.OK);

    }

    @GetMapping("/nativeQuery1")
    public ResponseEntity<?> nativeQuery1(){
        Map<String,Object> list = wrr.nativeQuery1();
        return new  ResponseEntity<>(list, HttpStatus.OK);

    }

    @GetMapping("/casewhen/{nam}")
    public ResponseEntity<?> dfsgdf(@PathVariable String nam  ){
        Integer Salary=null;
        List<Worker> getNames = wrr.getcasewhen(nam,Salary);
        return new  ResponseEntity<>(getNames, HttpStatus.OK);

    }

    @GetMapping("/taskById/{id}")
    public ResponseEntity<?> taskById(@PathVariable Integer id  ){
        Task task = trr.findById(id).get();
        Worker w = task.getWorker();

        return new  ResponseEntity<>(task, HttpStatus.OK);

    }
    @GetMapping("/workerById/{id}")
    public ResponseEntity<?> workerById(@PathVariable Integer id  ){
        // Worker wrkr = wrr.findById(id).get();
        Worker wrkr = wrr.getWorkerId(id);
        return new  ResponseEntity<>(wrkr, HttpStatus.OK);

    }


    @GetMapping("/count/{name}")
    public ResponseEntity<?> countByName(@PathVariable String name  ){
        Long  count = wrr.countByNameAndJoindate(name,LocalDate.parse("2012-07-10"));
        return new  ResponseEntity<>(count, HttpStatus.OK);
    }

    int getcount(Long id){

        int count = wrr.getnamelistIn(id);

        return  count;
    }

    @GetMapping("/namein")
    public ResponseEntity<?> getnamelistIn(){

        Worker wk = wrr.findByNameCustom("chadni akter");

        return new  ResponseEntity<>(wk, HttpStatus.OK);

    }


    @PostMapping("/updateTask")
    public ResponseEntity<?> updateTask(@RequestBody Worker w){
        Task t =trr.findById(69).get();
        t.setWorker(w);
        trr.save(t);
        return new  ResponseEntity<>(w, HttpStatus.OK);

    }

}

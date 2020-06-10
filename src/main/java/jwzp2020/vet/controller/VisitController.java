package jwzp2020.vet.controller;

import jwzp2020.vet.model.StatusVisit;
import jwzp2020.vet.model.Visit;
import jwzp2020.vet.service.PetService;
import jwzp2020.vet.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController()
@RequestMapping("/visits")
public class VisitController {

    private final VisitService visitService;

    @Autowired
    public VisitController(VisitService visitService) {
        this.visitService = visitService;
    }

    @GetMapping
    public List<Visit> getAllVisit(){
        return visitService.getAllVisit();
    }

    @GetMapping("/{id}")
    public Visit getVisit(@PathVariable int id){
        return visitService.getVisitById(id);
    }

    @GetMapping("/client/{id}")
    public List<Visit> getUserVisit(@PathVariable int id){
        return visitService.getClientVisit(id);
    }

    @GetMapping("/pet/{id}")
    public List<Visit> getPetVisit(@PathVariable int id){
        return visitService.getPetVisit(id);
    }

    @PostMapping
    public Visit addVisit(@RequestBody Visit visit){
        return visitService.addVisit(visit);
    }

    @PatchMapping("/{id}")
    public void addStatus(@PathVariable int id, @RequestParam(required = true)StatusVisit statusVisit){
        visitService.setStatus(id, statusVisit);
    }

    @PutMapping("/{id}")
    public void addDescripe(@PathVariable int id, @RequestParam String descripe){
        visitService.addDescripe(id, descripe);
    }

    @GetMapping("/day")
    public List<Visit> getAllVisitInOneDay(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return visitService.allVisitInDate(date);
    }
}

package jwzp2020.vet.service;

import jwzp2020.vet.model.StatusVisit;
import jwzp2020.vet.model.Visit;
import jwzp2020.vet.repository.VisitRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VisitService {

    private final VisitRepository visitRepository;

    public VisitService(VisitRepository visitRepository){
        this.visitRepository = visitRepository;
    }

    public List<Visit> getAllVisit() {
        return visitRepository.findAll();
    }

    public Visit getVisitById(int id){
        return visitRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown visit with id " + id));
    }

    public List<Visit> getClientVisit(int id){
        return visitRepository.findByClientId(id);
    }

    public List<Visit> getPetVisit(int id){
        return visitRepository.findByPetId(id);
    }


    public Visit addVisit(Visit visit){
        visitRepository.save(visit);
        return visit;
    }

    public void addDescripe(int id, String descripe){
        visitRepository.setDescripe(id, descripe);
    }

    public void setStatus(int id, StatusVisit statusVisit){
        visitRepository.setStatusOfVisit(id, statusVisit);
    }

    public List<Visit> allVisitInDate(LocalDate date){
        return visitRepository.findByDay(Date.valueOf(date));
    }
}

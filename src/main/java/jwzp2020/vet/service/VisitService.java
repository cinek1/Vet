package jwzp2020.vet.service;

import jwzp2020.vet.exception.ResourceNotFoundException;
import jwzp2020.vet.exception.WrongDateOrTimeException;
import jwzp2020.vet.model.StatusVisit;
import jwzp2020.vet.model.TimeVisit;
import jwzp2020.vet.model.Visit;
import jwzp2020.vet.repository.VisitRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.time.*;
import java.util.List;

@Service
public class VisitService {

    private final VisitRepository visitRepository;
    private final Clock clock;

    public VisitService(VisitRepository visitRepository, Clock clock){
        this.visitRepository = visitRepository;
        this.clock = clock;
    }

    public List<Visit> getAllVisit() {
        return visitRepository.findAll();
    }

    public Visit getVisitById(int id){
        return visitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unknown visit with id " + id));
    }

    public List<Visit> getClientVisit(int id){
        return visitRepository.findByClientId(id);
    }

    public List<Visit> getPetVisit(int id){
        return visitRepository.findByPetId(id);
    }


    public Visit addVisit(Visit visit){
        if (checkDate(visit.getDateVisit())) {
            visitRepository.save(visit);
            return visit;
        }
        else throw  new WrongDateOrTimeException("Time can't overlaps");
    }

    private boolean checkVisitHour(LocalDate date, TimeVisit timeVisit, LocalTime hour){
        var visits = visitRepository.findByDay(java.sql.Date.valueOf(date));
        boolean isVisitStart =  visits.stream().anyMatch(w -> w.getPlanHour().isBefore(hour) &&
                w.getPlanHour().plusMinutes(w.getTimeVisit().getMinutes()).isAfter(hour));
        boolean isVisitEnd =  visits.stream().anyMatch(w -> w.getPlanHour().isBefore(hour.plusMinutes(timeVisit.getMinutes())) &&
                w.getPlanHour().plusMinutes(w.getTimeVisit().getMinutes()).isAfter(hour.plusMinutes(timeVisit.getMinutes())));
        return !(isVisitEnd && isVisitStart);
    }

    private boolean checkDate(LocalDate date){
        return LocalDate.now(clock).isBefore(date);
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

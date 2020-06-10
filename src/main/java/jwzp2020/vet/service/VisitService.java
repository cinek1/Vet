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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class VisitService {

    final static Logger logger = LoggerFactory.getLogger(VisitService.class);


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
        if (checkDate(visit.getDateVisit()) && checkVisitHour(visit.getDateVisit(), visit.getTimeVisit(), visit.getPlanHour())) {
            visitRepository.save(visit);
            logger.info("Add visit: "  + visit.getId());
            return visit;
        }
        else throw  new WrongDateOrTimeException("Time visit is incorrect");
    }

    private boolean checkVisitHour(LocalDate date, TimeVisit timeVisit, LocalTime hour){
        if (!(hour.isAfter(LocalTime.of(8, 0)) && hour.plusMinutes(timeVisit.getMinutes()).isBefore(LocalTime.of(20, 0)))) return false;
        var visits = visitRepository.findByDay(java.sql.Date.valueOf(date));
        return ! visits.stream().anyMatch(w -> (w.getPlanHour().isBefore( hour.plusMinutes(timeVisit.getMinutes())))
                &&  (w.getPlanHour().plusMinutes(w.getTimeVisit().getMinutes()).isAfter(hour) ));
    }

    private boolean checkDate(LocalDate date){
        return LocalDate.now(clock).isBefore(date);
    }

    public void addDescripe(int id, String descripe){
        visitRepository.setDescripe(id, descripe);
        logger.info("Set descripe in visit: "  + id);
    }

    public void setStatus(int id, StatusVisit statusVisit){
        visitRepository.setStatusOfVisit(id, statusVisit);
        logger.info("Set status in visit: "  + id);
    }

    public List<Visit> allVisitInDate(LocalDate date){
        return visitRepository.findByDay(Date.valueOf(date));
    }
}

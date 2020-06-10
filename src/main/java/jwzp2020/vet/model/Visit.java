package jwzp2020.vet.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@JsonSerialize
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int petId;
    private String descripe;
    private StatusVisit statusVisit;
    private TimeVisit timeVisit;
    private LocalDate dateVisit;
    private LocalTime planHour;

    public Visit(){

    }

    public Visit(int petId, String descripe, StatusVisit statusVisit, TimeVisit timeVisit, LocalDate dateVisit, LocalTime planHour) {
        this.petId = petId;
        this.descripe = descripe;
        this.statusVisit = statusVisit;
        this.timeVisit = timeVisit;
        this.dateVisit = dateVisit;
        this.planHour = planHour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public String getDescripe() {
        return descripe;
    }

    public void setDescripe(String descripe) {
        this.descripe = descripe;
    }


    public LocalDate getDateVisit() {
        return dateVisit;
    }

    public void setDateVisit(LocalDate dateVisit) {
        this.dateVisit = dateVisit;
    }


    public StatusVisit getStatusVisit() {
        return statusVisit;
    }

    public void setStatusVisit(StatusVisit statusVisit) {
        this.statusVisit = statusVisit;
    }

    public TimeVisit getTimeVisit() {
        return timeVisit;
    }

    public void setTimeVisit(TimeVisit timeVisit) {
        this.timeVisit = timeVisit;
    }


    public LocalTime getPlanHour() {
        return planHour;
    }

    public void setPlanHour(LocalTime planHour) {
        this.planHour = planHour;
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", petId=" + petId +
                ", descripe='" + descripe + '\'' +
                ", statusVisit=" + statusVisit +
                ", timeVisit=" + timeVisit +
                ", dateVisit=" + dateVisit +
                ", planHour=" + planHour +
                '}';
    }
}


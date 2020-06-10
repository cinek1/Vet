package jwzp2020.vet.service;

import jwzp2020.vet.exception.ResourceNotFoundException;
import jwzp2020.vet.exception.WrongDateOrTimeException;
import jwzp2020.vet.model.*;
import jwzp2020.vet.repository.ClientRepository;
import jwzp2020.vet.repository.PetRepository;
import jwzp2020.vet.repository.VisitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VisitServiceTest {

    private final static LocalDate LOCAL_DATE = LocalDate.of(2020, 10, 11);
    @Mock
    private VisitRepository visitRepository;

    @Mock
    private Clock clock;

    @Test
    void getVisitById() {
        Visit visit = new Visit(2, "Szczepienie", StatusVisit.Planned, TimeVisit.HalfHour, LocalDate.of(2020, 12, 12), LocalTime.of(10, 22));
        VisitService visitService = new VisitService(visitRepository, clock);
        given(visitRepository.findById(1)).willReturn(Optional.of(visit));
        assertThat(visitService.getVisitById(1)).isEqualTo(visit);
    }

    @Test
    void getVisitByWrongId() {
        VisitService visitService = new VisitService(visitRepository, clock);
        given(visitRepository.findById(1)).willThrow(new ResourceNotFoundException("Unknown visit with id " + 1));
        assertThatThrownBy(() -> visitService.getVisitById(1)).isInstanceOfAny(ResourceNotFoundException.class);
    }


    @Test
    void addVisit() {
        Visit visit = new Visit(2, "Szczepienie", StatusVisit.Planned, TimeVisit.HalfHour, LocalDate.of(2020, 12, 12), LocalTime.of(10, 22));
        Clock realClock = Clock.fixed(LOCAL_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        VisitService visitService = new VisitService(visitRepository, realClock);
        assertThat(visitService.addVisit(visit)).isEqualTo(visit);
    }

    @Test
    void AddVisitOnTheSameHour(){
        Visit visit = new Visit(2, "Szczepienie", StatusVisit.Planned, TimeVisit.HalfHour, LocalDate.of(2020, 12, 12), LocalTime.of(10, 22));
        Visit visitToAdd = new Visit(3, "Operacja", StatusVisit.Planned, TimeVisit.HalfHour, LocalDate.of(2020, 12, 12), LocalTime.of(10, 22));
        Clock realClock = Clock.fixed(LOCAL_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        VisitService visitService = new VisitService(visitRepository, realClock);
        given(visitRepository.findByDay(Date.valueOf(LocalDate.of(2020, 12, 12))))
        .willReturn(List.of(visit));

        assertThatThrownBy(() -> visitService.addVisit(visitToAdd)).isInstanceOfAny(WrongDateOrTimeException.class);
    }

    @Test
    void AddVisit5MiutesEarlier(){
        Visit visit = new Visit(2, "Szczepienie", StatusVisit.Planned, TimeVisit.HalfHour, LocalDate.of(2020, 12, 12), LocalTime.of(10, 22));
        Visit visitToAdd = new Visit(3, "Operacja", StatusVisit.Planned, TimeVisit.HalfHour, LocalDate.of(2020, 12, 12), LocalTime.of(10, 27));
        Clock realClock = Clock.fixed(LOCAL_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        VisitService visitService = new VisitService(visitRepository, realClock);
        given(visitRepository.findByDay(Date.valueOf(LocalDate.of(2020, 12, 12))))
                .willReturn(List.of(visit));

        assertThatThrownBy(() -> visitService.addVisit(visitToAdd)).isInstanceOfAny(WrongDateOrTimeException.class);
    }

    @Test
    void AddVisit5MinutesLater(){
        Visit visit = new Visit(2, "Szczepienie", StatusVisit.Planned, TimeVisit.HalfHour, LocalDate.of(2020, 12, 12), LocalTime.of(10, 22));
        Visit visitToAdd = new Visit(3, "Operacja", StatusVisit.Planned, TimeVisit.HalfHour, LocalDate.of(2020, 12, 12), LocalTime.of(10, 17));
        Clock realClock = Clock.fixed(LOCAL_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        VisitService visitService = new VisitService(visitRepository, realClock);
        given(visitRepository.findByDay(Date.valueOf(LocalDate.of(2020, 12, 12))))
                .willReturn(List.of(visit));

        assertThatThrownBy(() -> visitService.addVisit(visitToAdd)).isInstanceOfAny(WrongDateOrTimeException.class);
    }

    @Test
    void AddVisitOneHouerEarlier(){
        Visit visit = new Visit(2, "Szczepienie", StatusVisit.Planned, TimeVisit.OneHour, LocalDate.of(2020, 12, 12), LocalTime.of(10, 22));
        Visit visitToAdd = new Visit(3, "Operacja", StatusVisit.Planned, TimeVisit.OneHour, LocalDate.of(2020, 12, 12), LocalTime.of(11, 23));
        Clock realClock = Clock.fixed(LOCAL_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        VisitService visitService = new VisitService(visitRepository, realClock);
        given(visitRepository.findByDay(Date.valueOf(LocalDate.of(2020, 12, 12))))
                .willReturn(List.of(visit));

        assertThat(visitService.addVisit(visitToAdd)).isEqualTo(visitToAdd);

    }

    @Test
    void AddVisitOneHourLater(){
        Visit visit = new Visit(2, "Szczepienie", StatusVisit.Planned, TimeVisit.OneHour, LocalDate.of(2020, 12, 12), LocalTime.of(10, 22));
        Visit visitToAdd = new Visit(3, "Operacja", StatusVisit.Planned, TimeVisit.OneHour, LocalDate.of(2020, 12, 12), LocalTime.of(9, 21));
        Clock realClock = Clock.fixed(LOCAL_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        VisitService visitService = new VisitService(visitRepository, realClock);
        given(visitRepository.findByDay(Date.valueOf(LocalDate.of(2020, 12, 12))))
                .willReturn(List.of(visit));

        assertThat(visitService.addVisit(visitToAdd)).isEqualTo(visitToAdd);
    }

    @Test
    void AddVisitWrongDate(){
        Visit visitToAdd = new Visit(3, "Operacja", StatusVisit.Planned, TimeVisit.HalfHour, LocalDate.of(1998, 12, 12), LocalTime.of(10, 17));
        Clock realClock = Clock.fixed(LOCAL_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        VisitService visitService = new VisitService(visitRepository, realClock);
        assertThatThrownBy(() -> visitService.addVisit(visitToAdd)).isInstanceOfAny(WrongDateOrTimeException.class);
    }

}
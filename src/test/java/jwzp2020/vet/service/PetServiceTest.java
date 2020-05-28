package jwzp2020.vet.service;


import jwzp2020.vet.exception.ResourceNotFoundException;
import jwzp2020.vet.exception.WrongDateOrTimeException;
import jwzp2020.vet.model.Pet;
import jwzp2020.vet.model.Specie;
import jwzp2020.vet.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.ZoneId;
import java.util.List;




import java.time.Clock;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;


@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    private final static LocalDate LOCAL_DATE = LocalDate.of(2020, 10, 11);
    @Mock
    private PetRepository petRepository;
    @Mock
    private Clock clock;

    @Test
    void addPetWithFutureDateOfBirth() {
        Pet dog = new Pet("Bounty", Specie.dog,  LocalDate.of(2021, 4, 4), null);
        Clock realClock = Clock.fixed(LOCAL_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        PetService petService = new PetService(petRepository, realClock);
        assertThatThrownBy(() -> petService.addPet(dog)).isInstanceOfAny(WrongDateOrTimeException.class)
                .hasMessageContaining("Wrong date of birth " + LocalDate.of(2021, 4, 4));
    }

    @Test
    void addPet(){
        Pet dog = new Pet("Bounty", Specie.dog,  LocalDate.of(2019, 4, 4), null);
        Clock realClock = Clock.fixed(LOCAL_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        PetService petService = new PetService(petRepository, realClock);
        given(petRepository.save(dog)).willReturn(dog);
        assertThat(petService.addPet(dog)).isEqualTo(dog);
    }

    @Test
    void getAllPets() {
        Pet dog = new Pet("Bounty", Specie.dog,  LocalDate.of(2012, 4, 4), null);
        Pet cat = new Pet("Dzwoneczek", Specie.cat, LocalDate.of(2019, 3, 3), null);
        PetService petService = new PetService(petRepository, clock);
        given(petRepository.findAll()).willReturn(List.of(dog, cat));
        List<Pet> pets = petService.getAllPets();
        assertThat(pets.size()).isEqualTo(2);
    }

    @Test
    void getPetByCorrectId() {
        Pet dog = new Pet("Bounty", Specie.dog,  LocalDate.of(2012, 4, 4), null);
        PetService petService = new PetService(petRepository, clock);
        given(petRepository.findById(1)).willReturn(Optional.of(dog));
        assertThat(petService.getPet(1).getName()).isEqualTo(dog.getName());
    }

    @Test
    void getPetByUncorrectedId() {
        PetService petService = new PetService(petRepository, clock);
        given(petRepository.findById(1)).willReturn(Optional.empty());
        assertThatThrownBy(() -> petService.getPet(1)).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Unknown pet with id 1");
    }

//    @Test
//    void assignOwner() {
//        Pet dog = new Pet("Bounty", Specie.dog,  LocalDate.of(2012, 4, 4), null);
//        PetService petService = new PetService(petRepository, clock);
//        doNothing().when(petRepository).assignOwner(1, 2);
//
//    }


    @Test
    void setUncorrectedDateOfDeathFuture() {
        Pet dog = new Pet("Bounty", Specie.dog,  LocalDate.of(2012, 4, 4), null);
        Clock realClock = Clock.fixed(LOCAL_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        PetService petService = new PetService(petRepository, realClock);
        given(petRepository.findById(1)).willReturn(Optional.of(dog));
        assertThatThrownBy(() -> petService.setDateOfDeath(1,  LocalDate.of(2021, 12, 22))).isInstanceOfAny(WrongDateOrTimeException.class)
                .hasMessageContaining("Wrong date of death " + LocalDate.of(2021, 12, 22));
    }

    @Test
    void setUncorrectedDateOfDeathEarlierThanBirth(){
        Pet dog = new Pet("Bounty", Specie.dog,  LocalDate.of(2012, 4, 4), null);
        Clock realClock = Clock.fixed(LOCAL_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant(), ZoneId.systemDefault());
        PetService petService = new PetService(petRepository, realClock);
        given(petRepository.findById(1)).willReturn(Optional.of(dog));
        assertThatThrownBy(() -> petService.setDateOfDeath(1,  LocalDate.of(2011, 12, 22))).isInstanceOfAny(WrongDateOrTimeException.class)
                .hasMessageContaining("Wrong date of death " + LocalDate.of(2011, 12, 22));
    }

//    @Test
//    void delete() {
//
//    }
}
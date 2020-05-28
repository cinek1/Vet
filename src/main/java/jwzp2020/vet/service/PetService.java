package jwzp2020.vet.service;

import jwzp2020.vet.exception.ResourceNotFoundException;
import jwzp2020.vet.exception.WrongDateOrTimeException;
import jwzp2020.vet.model.Pet;
import jwzp2020.vet.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

@Service
public class PetService {
    private final PetRepository petRepository;
    private final Clock clock;

    public PetService(PetRepository petRepository, Clock clock){
        this.petRepository = petRepository;
        this.clock = clock;
    }

    public Pet addPet(Pet pet){
        if (checkDate(pet.getDateOfBirth())){
            return petRepository.save(pet);
        }
        else {
            throw new WrongDateOrTimeException("Wrong date of birth " + pet.getDateOfBirth());
        }
    }

    private boolean checkDate(LocalDate date){
        return LocalDate.now(clock).isAfter(date) || LocalDate.now(clock).isEqual(date);
    }

    private boolean checkPetDateOfDeath(int id, LocalDate date){
            return getPet(id).getDateOfBirth().isBefore(date) && checkDate(date);
    }

    public List<Pet> getAllPets(){
        return petRepository.findAll();
    }

    public Pet getPet(int id){
        return petRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Unknown pet with id " + id));
    }

    public void assignOwner(int id, int ownerId){
        petRepository.assignOwner(id, ownerId);

    }

    public void setDateOfDeath(int id, LocalDate date){
        if (checkPetDateOfDeath(id, date)) {
            petRepository.setDateOfDeath(id, java.sql.Date.valueOf(date));
        }
        else{
            throw new WrongDateOrTimeException("Wrong date of death " + date);
        }
    }

    public void delete(int id){
        petRepository.deleteById(id);
    }
}

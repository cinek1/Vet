package jwzp2020.vet.service;

import jwzp2020.vet.model.Pet;
import jwzp2020.vet.repository.PetRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class PetService {
    private final PetRepository petRepository;

    public PetService(PetRepository petRepository){
        this.petRepository = petRepository;
    }

    public Pet addPet(Pet pet){
        petRepository.save(pet);
        return pet;
    }

    public List<Pet> getAllPets(){
        return petRepository.findAll();
    }

    public Pet getPet(int id){
        return petRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown pet with id " + id));
    }

    public void assignOwner(int id, int ownerId){
        petRepository.assignOwner(id, ownerId);
    }

    public void setDateOfDeath(int id, LocalDate date){
        petRepository.setDateOfDeath(id,  java.sql.Date.valueOf(date));
    }

    public void delete(int id){
        petRepository.deleteById(id);
    }
}

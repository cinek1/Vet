package jwzp2020.vet.controller;

import jwzp2020.vet.model.Pet;
import jwzp2020.vet.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    @Autowired
    public PetController(PetService petService){
        this.petService = petService;
    }

    @GetMapping
    public List<Pet> getAllPets(){
        return petService.getAllPets();
    }

    @GetMapping("/{id}")
    public Pet getPet(@PathVariable int id){
        return petService.getPet(id);
    }

    @PostMapping
    public Pet addPet(@RequestBody Pet pet){
        return petService.addPet(pet);
    }
}

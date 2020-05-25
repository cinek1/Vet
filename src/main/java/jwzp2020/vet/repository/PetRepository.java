package jwzp2020.vet.repository;

import jwzp2020.vet.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAll();
    Pet save(Pet p);
    Optional<Pet> findById(int id);
    void deleteById(int id);
}


package jwzp2020.vet.repository;

import jwzp2020.vet.model.Pet;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAll();
    Pet save(Pet p);
    Optional<Pet> findById(int id);
    void deleteById(int id);

    @Transactional
    @Modifying
    @Query("update Pet set owner_Id = ?2 where id = ?1")
    void assignOwner(int id, int ownerId);

    @Transactional
    @Modifying
    @Query("update Pet set date_Of_Death = ?2 where id = ?1")
    void setDateOfDeath(int id, Date date);

    @Query(
            value = "SELECT * FROM Pet WHERE Pet.owner_id = ?1",
            nativeQuery = true)
    List<Pet> getClientPets(int id);
}


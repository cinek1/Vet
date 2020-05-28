package jwzp2020.vet.repository;

import jwzp2020.vet.model.Pet;
import jwzp2020.vet.model.StatusVisit;
import jwzp2020.vet.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findAll();
    Visit save(Visit p);
    Optional<Visit> findById(int id);
    void deleteById(int id);
    List<Visit> findByDateVisit(LocalDateTime dateVisit);
    List<Visit> findByPetId(int petId);
    @Query(
            value = "SELECT * FROM Visit INNER JOIN Client ON Client.Id = ?1",
            nativeQuery = true)
    List<Visit> findByClientId(int id);

    @Query(
            value = "    SELECT * FROM Visit" +
                    "    WHERE Visit.date_visit  = ?1",
            nativeQuery = true)
    List<Visit> findByDay(Date date);




    @Transactional
    @Modifying
    @Query("update Visit set status_visit = ?2 where id = ?1")
    void setStatusOfVisit(int id, StatusVisit statusVisit);

    @Transactional
    @Modifying
    @Query("update Visit set descripe = ?2 where id = ?1")
    void setDescripe(int id, String descripe);
}

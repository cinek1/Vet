package jwzp2020.vet.repository;

import jwzp2020.vet.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>{
    List<Client> findAll();
    Client save(Client p);
    Optional<Client> findById(int id);
    Optional<Client> findByMail(String mail);
    void deleteById(int id);

}

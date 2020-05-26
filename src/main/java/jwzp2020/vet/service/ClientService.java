package jwzp2020.vet.service;

import jwzp2020.vet.model.Client;
import jwzp2020.vet.model.ClientWithPets;
import jwzp2020.vet.repository.ClientRepository;
import jwzp2020.vet.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final PetRepository petRepository;

    public ClientService(ClientRepository clientRepository, PetRepository petRepository){
        this.clientRepository = clientRepository;
        this.petRepository = petRepository;
    }

    public Client getClient(int id) {
        return clientRepository.findById(id).get();
    }

    public ClientWithPets getClientWithPets(int id){
        var client = clientRepository.findById(id).get();
        var pets = petRepository.getClientPets(id);
        return new ClientWithPets(client, pets);
    }

    public void delete (int id){
        clientRepository.deleteById(id);
    }

    public Client addClient(Client client) {
        if (clientRepository.findAll().stream().noneMatch(w -> w.getMail().equals(client.getMail()))){
            clientRepository.save(client);
            return client;
        };
        return null;
    }

    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    public Client getClientByEmail(String email){
        if (clientRepository.findByMail(email).isPresent()) return clientRepository.findByMail(email).get();
        return null;
    }
}

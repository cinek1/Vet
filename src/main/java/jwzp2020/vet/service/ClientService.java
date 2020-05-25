package jwzp2020.vet.service;

import jwzp2020.vet.model.Client;
import jwzp2020.vet.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository){
        this.clientRepository = clientRepository;
    }

    public Client getClient(int id){
        return clientRepository.getOne((long) id);
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

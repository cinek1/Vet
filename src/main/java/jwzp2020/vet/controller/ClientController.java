package jwzp2020.vet.controller;


import jwzp2020.vet.model.Client;
import jwzp2020.vet.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    @GetMapping("/{id}")
    public Client getClientById(@PathVariable int id) {
            return clientService.getClient(id);
    }
    @GetMapping("/{mail}")
    public Client getClientByMail(@PathVariable String mail) {
        return clientService.getClientByEmail(mail);
    }

    @GetMapping
    public List<Client> getAllClient(){
        return clientService.getAllClients();
    }
    @PostMapping
    public Client addClient(@RequestBody Client newClient) {
        return clientService.addClient(newClient);
    }

}


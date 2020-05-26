package jwzp2020.vet.controller;


import jwzp2020.vet.model.Client;
import jwzp2020.vet.model.ClientWithPets;
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
    public Client getClientById(@PathVariable int id,  @RequestParam(required = false) boolean withPet) {
            if (withPet) return clientService.getClientWithPets(id);
            return  clientService.getClient(id);
    }


    @GetMapping
    public List<Client> getAllClient(){
        return clientService.getAllClients();
    }
    @PostMapping
    public Client addClient(@RequestBody Client newClient) {
        return clientService.addClient(newClient);
    }

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable int id){
        clientService.delete(id);
    }

}


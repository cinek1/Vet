package jwzp2020.vet.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Collection;
import java.util.List;

public class ClientWithPets extends Client {
    private final List<Pet> pets;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ClientWithPets(Client client, List<Pet> pets){
        super(client.getFirstName(), client.getSecondName(), client.getHomeAdress(), client.getMail());
        this.pets = pets;
    }
}

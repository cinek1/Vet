package jwzp2020.vet.service;


import jwzp2020.vet.exception.ResourceNotFoundException;
import jwzp2020.vet.exception.WrongDateOrTimeException;
import jwzp2020.vet.model.Client;
import jwzp2020.vet.model.ClientWithPets;
import jwzp2020.vet.model.Pet;
import jwzp2020.vet.model.Specie;
import jwzp2020.vet.repository.ClientRepository;
import jwzp2020.vet.repository.PetRepository;
import net.bytebuddy.description.annotation.AnnotationList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.ZoneId;
import java.util.List;




import java.time.Clock;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    @Mock
    private PetRepository petRepository;
    @Mock
    private ClientRepository clientRepository;

    @Test
    void getClientsWithPets() {
        Pet dog = new Pet("Bounty", Specie.dog,  LocalDate.of(2012, 4, 4), null);
        Pet cat = new Pet("Dzwoneczek", Specie.cat, LocalDate.of(2019, 3, 3), null);
        Client client = new Client("Marcin", "Galas", "krakow", "mgalas1@op.lp");
        ClientService clientService = new ClientService(clientRepository, petRepository);
        given(petRepository.getClientPets(1)).willReturn(List.of(dog, cat));
        given(clientRepository.findById(1)).willReturn(Optional.of(client));
        assertThat(clientService.getClientWithPets(1)).isEqualToComparingFieldByField(new ClientWithPets(client, List.of(dog, cat)));
    }

    @Test
    void getClientWrongId() {
        ClientService clientService = new ClientService(clientRepository, petRepository);
        given(clientRepository.findById(1)).willReturn(Optional.empty());
        assertThatThrownBy(() -> clientService.getClient(1)).isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Unknown client with id: 1");
    }

    @Test
    void addClientWithExistingMail(){
        Client clientMaja = new Client("Maja", "Kowalska", "krakow", "kowalska@op.lp");
        Client clientMajaAfterWedding = new Client("Maja", "Nowak", "krakow", "kowalska@op.lp");
        ClientService clientService = new ClientService(clientRepository, petRepository);
        given(clientRepository.findAll()).willReturn(List.of(clientMaja));
        assertThat(clientService.addClient(clientMajaAfterWedding)).isNull();
    }

    @Test
    void addClient(){
        Client clientMaja = new Client("Maja", "Kowalska", "krakow", "kowalska@op.lp");
        ClientService clientService = new ClientService(clientRepository, petRepository);
        given(clientRepository.findAll()).willReturn(List.of());
        assertThat(clientService.addClient(clientMaja)).isEqualToComparingFieldByField(clientMaja);
    }

}

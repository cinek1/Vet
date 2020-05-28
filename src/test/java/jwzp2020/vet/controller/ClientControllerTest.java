package jwzp2020.vet.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jwzp2020.vet.exception.ResourceNotFoundException;
import jwzp2020.vet.model.Client;
import jwzp2020.vet.model.Pet;
import jwzp2020.vet.model.Specie;
import jwzp2020.vet.service.ClientService;
import jwzp2020.vet.service.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {



    private MockMvc mockMvc;

    private Client client;
    @Mock
    private ClientService clientService;


    @BeforeEach
    private void setup() {
        client = new Client("Marcin", "Galas",  "Warszawa", "sdds@op.pl");
        mockMvc = MockMvcBuilders.standaloneSetup(new ClientController(clientService))
                .build();
    }

    @Test
    void getClientById() throws Exception {
        given(clientService.getClient(1)).willReturn(client);
        MockHttpServletResponse response = mockMvc.perform(
                get("/clients/1").accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void getClientByWrongId() throws Exception {
        given(clientService.getClient(431)).willThrow(new ResourceNotFoundException());
        MockHttpServletResponse response = mockMvc.perform(
                get("/clients/431").accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void getAllClient() throws Exception {
        given(clientService.getAllClients()).willReturn(List.of());
        MockHttpServletResponse response = mockMvc.perform(
                get("/clients").accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void addClient() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonClient = objectMapper.writeValueAsString(client);
        MockHttpServletResponse response = mockMvc.perform(post("/clients").contentType(MediaType.APPLICATION_JSON).content(jsonClient))
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

}
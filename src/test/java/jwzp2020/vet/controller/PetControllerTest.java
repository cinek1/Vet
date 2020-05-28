package jwzp2020.vet.controller;
import jwzp2020.vet.exception.ResourceNotFoundException;
import jwzp2020.vet.model.Pet;
import jwzp2020.vet.model.Specie;
import jwzp2020.vet.service.PetService;
import netscape.javascript.JSObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.JsonbHttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.lang.model.element.Element;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;




@ExtendWith(MockitoExtension.class)
class PetControllerTest {


    private MockMvc mockMvc;
    private Pet dog;
    @Mock
    private PetService petService;


    @BeforeEach
    private void setup() {
        dog = new Pet("Bounty", Specie.dog,  null, null);
        mockMvc = MockMvcBuilders.standaloneSetup(new PetController(petService))
                .build();
    }

    @Test
    void getAllPets() throws Exception {
        given(petService.getAllPets()).willReturn(List.of());
        MockHttpServletResponse response = mockMvc.perform(
                get("/pets").accept(MediaType.APPLICATION_JSON))
                         .andReturn()
                .getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }


    @Test
    void getPet() throws Exception {

        given(petService.getPet(1)).willReturn(dog);
        MockHttpServletResponse response = mockMvc.perform(
                get("/pets/1").accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void addPetWithWrongId() throws Exception {
        given(petService.getPet(2)).willThrow(new ResourceNotFoundException());
        MockHttpServletResponse response = mockMvc.perform(
                get("/pets/2").accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void assignOwner() throws Exception {
//        doNothing().when(petService).assignOwner(1, 2);
        MockHttpServletResponse response = mockMvc.perform(
                get("/pets/1?ownerId=2").accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void setDateDeath() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                 patch("/pets/death/17?dateOfDeath=2044-02-10").accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void addPet() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonDog = objectMapper.writeValueAsString(dog);
        MockHttpServletResponse response = mockMvc.perform(post("/pets").contentType(MediaType.APPLICATION_JSON).content(jsonDog))
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

    }

}
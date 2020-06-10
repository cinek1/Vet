package jwzp2020.vet.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jwzp2020.vet.exception.ResourceNotFoundException;
import jwzp2020.vet.exception.WrongDateOrTimeException;
import jwzp2020.vet.model.Client;
import jwzp2020.vet.model.StatusVisit;
import jwzp2020.vet.model.TimeVisit;
import jwzp2020.vet.model.Visit;
import jwzp2020.vet.service.ClientService;
import jwzp2020.vet.service.VisitService;
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

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
class VisitControllerTest {




    private MockMvc mockMvc;

    private Visit visit;
    @Mock
    private VisitService visitService;


    @BeforeEach
    private void setup() {
        visit = new Visit(2, "Szczepienie", StatusVisit.Planned, TimeVisit.OneHour, LocalDate.of(2020, 12, 12), LocalTime.of(10, 22));

        mockMvc = MockMvcBuilders.standaloneSetup(new VisitController(visitService))
                .build();
    }

    @Test
    void getVisit() throws Exception {
        given(visitService.getVisitById(1)).willReturn(visit);
        MockHttpServletResponse response = mockMvc.perform(
         get("/visits/1").accept(MediaType.APPLICATION_JSON))
            .andReturn()
                .getResponse();
    assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void getVisitByWrongId() throws Exception {
        given(visitService.getVisitById(231)).willThrow(new ResourceNotFoundException());
        MockHttpServletResponse response = mockMvc.perform(
                get("/visits/231").accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

//    @Test
//    void addVisit() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonVisit = objectMapper.writeValueAsString(visit);
//        given(visitService.addVisit(visit)).willReturn(visit);
//        MockHttpServletResponse response = mockMvc.perform(post("/visits").contentType(MediaType.APPLICATION_JSON).content(jsonVisit))
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn()
//                .getResponse();
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//
//    }
//
//    @Test
//    void addVisitWithWrongData() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jsonVisit = objectMapper.writeValueAsString(visit);
//        given(visitService.addVisit(visit)).willThrow(new WrongDateOrTimeException());
//        MockHttpServletResponse response = mockMvc.perform(post("/visits").contentType(MediaType.APPLICATION_JSON).content(jsonVisit))
//                .andDo(MockMvcResultHandlers.print())
//                .andReturn()
//                .getResponse();
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
//
//    }

    @Test
    void addStatus() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                patch("/visits/1?statusVisit=NotCome").accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void addDescripe() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(
                put("/visits/1?descripe=zaszczepiony").accept(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

}
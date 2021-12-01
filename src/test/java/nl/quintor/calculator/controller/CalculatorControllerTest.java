package nl.quintor.calculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.quintor.calculator.controller.dto.CalculationResultDTO;
import nl.quintor.calculator.controller.exception.InvalidCalculation;
import nl.quintor.calculator.model.CalculationAction;
import nl.quintor.calculator.model.CalculationResult;
import nl.quintor.calculator.service.SimpleCalculator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CalculatorControllerTest {

    @MockBean
    private SimpleCalculator simpleCalculator;
    @InjectMocks
    private CalculatorController calculatorController;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void calculate_addTest() throws Exception {
        int value1 = 5;
        int value2 = 2;

        when(simpleCalculator.add(value1, value2)).thenReturn(new CalculationResult(7.0, value1, value2, CalculationAction.DIVIDE, LocalDateTime.now()));

        this.mockMvc.perform(post("/calculate")
                        .contentType(MediaType.APPLICATION_JSON).content("{\n" +
                                "    \"value1\": " + value1 + ",\n" +
                                "    \"value2\": " + value2 + ",\n" +
                                "    \"action\": \"" + CalculationAction.ADD + "\"\n" +
                                "}")).andExpect(status().isOk())
                .andReturn().getResponse();

        verify(simpleCalculator, times(1)).add(value1, value2);
    }

    @Test
    void calculate_subtractTest() throws Exception {
        int value1 = 5;
        int value2 = 2;

        when(simpleCalculator.subtract(value1, value2)).thenReturn(new CalculationResult(3.0, value1, value2, CalculationAction.DIVIDE, LocalDateTime.now()));

        this.mockMvc.perform(post("/calculate")
                        .contentType(MediaType.APPLICATION_JSON).content("{\n" +
                                "    \"value1\": " + value1 + ",\n" +
                                "    \"value2\": " + value2 + ",\n" +
                                "    \"action\": \"" + CalculationAction.SUBTRACT + "\"\n" +
                                "}")).andExpect(status().isOk())
                .andReturn().getResponse();

        verify(simpleCalculator, times(1)).subtract(value1, value2);
    }

    @Test
    void calculate_multiplyTest() throws Exception {
        int value1 = 5;
        int value2 = 2;

        when(simpleCalculator.multiply(value1, value2)).thenReturn(new CalculationResult(2.5, value1, value2, CalculationAction.DIVIDE, LocalDateTime.now()));

        this.mockMvc.perform(post("/calculate")
                        .contentType(MediaType.APPLICATION_JSON).content("{\n" +
                                "    \"value1\": " + value1 + ",\n" +
                                "    \"value2\": " + value2 + ",\n" +
                                "    \"action\": \"" + CalculationAction.MULTIPLY + "\"\n" +
                                "}")).andExpect(status().isOk())
                .andReturn().getResponse();

        verify(simpleCalculator, times(1)).multiply(value1, value2);
    }

    @Test
    void calculate_divideTest() throws Exception {
        int value1 = 5;
        int value2 = 2;
        double result = 2.5;

        when(simpleCalculator.divide(value1, value2)).thenReturn(new CalculationResult(result, value1, value2, CalculationAction.DIVIDE, LocalDateTime.now()));

        this.mockMvc.perform(post("/calculate")
                        .contentType(MediaType.APPLICATION_JSON).content("{\n" +
                                "    \"value1\": " + value1 + ",\n" +
                                "    \"value2\": " + value2 + ",\n" +
                                "    \"action\": \"" + CalculationAction.DIVIDE + "\"\n" +
                                "}")).andExpect(status().isOk())
                .andReturn().getResponse();

        verify(simpleCalculator, times(1)).divide(value1, value2);
    }

    @Test
    void calculate_divide_DivideByZero_ShouldThrowExceptionTest() throws Exception {
        int value1 = 5;
        int value2 = 2;

        when(simpleCalculator.divide(value1, value2)).thenThrow(new InvalidCalculation("Cannot divide by zero"));

        this.mockMvc.perform(post("/calculate")
                        .contentType(MediaType.APPLICATION_JSON).content("{\n" +
                                "    \"value1\": " + value1 + ",\n" +
                                "    \"value2\": " + value2 + ",\n" +
                                "    \"action\": \"" + CalculationAction.DIVIDE + "\"\n" +
                                "}")).andExpect(status().isBadRequest())
                .andReturn().getResponse();

        verify(simpleCalculator, times(1)).divide(value1, value2);
    }

    @Test
    void getHistoryTest() throws Exception {
        List<CalculationResult> mockList = new ArrayList<>();
        mockList.add(new CalculationResult(1, 2, 1, 1, CalculationAction.ADD, LocalDateTime.now()));
        mockList.add(new CalculationResult(1, 1, 2, 1, CalculationAction.SUBTRACT, LocalDateTime.now()));

        when(simpleCalculator.getHistory()).thenReturn(mockList);

        var response = this.mockMvc.perform(get("/calculate")).andExpect(status().isOk())
                .andReturn().getResponse();

        ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

        CalculationResultDTO[] result = mapper.readValue(response.getContentAsString(), CalculationResultDTO[].class);

        assertThat(result[0], is(new CalculationResultDTO(mockList.get(0))));
        assertThat(result[1], is(new CalculationResultDTO(mockList.get(1))));
    }
}

package nl.quintor.calculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nl.quintor.calculator.controller.dto.CalculationResultDTO;
import nl.quintor.calculator.controller.exception.InvalidCalculation;
import nl.quintor.calculator.model.CalculationOperation;
import nl.quintor.calculator.model.CalculationResult;
import nl.quintor.calculator.service.CalculatorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
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
    private CalculatorService calculatorService;
    @InjectMocks
    private CalculatorController calculatorController;
    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @CsvFileSource(resources = "/testdata.csv", numLinesToSkip = 1)
    void calculateParamTest(double value1, double value2, double result, CalculationOperation operation) throws Exception {

        when(calculatorService.calculate(value1, value2, operation)).thenReturn(new CalculationResult(result, value1, value2, operation, LocalDateTime.now()));

        this.mockMvc.perform(post("/calculate")
                        .contentType(MediaType.APPLICATION_JSON).content("{\n" +
                                "    \"value1\": " + value1 + ",\n" +
                                "    \"value2\": " + value2 + ",\n" +
                                "    \"operation\": \"" + operation.name() + "\"\n" +
                                "}")).andExpect(status().isOk())
                .andReturn().getResponse();

        verify(calculatorService, times(1)).calculate(value1, value2, operation);
    }

    @Test
    void calculate_divide_DivideByZero_ShouldThrowExceptionTest() throws Exception {
        int value1 = 5;
        int value2 = 2;

        when(calculatorService.calculate(value1, value2, CalculationOperation.DIVIDE)).thenThrow(new InvalidCalculation("Can not divide by zero"));

        this.mockMvc.perform(post("/calculate")
                        .contentType(MediaType.APPLICATION_JSON).content("{\n" +
                                "    \"value1\": " + value1 + ",\n" +
                                "    \"value2\": " + value2 + ",\n" +
                                "    \"operation\": \"" + CalculationOperation.DIVIDE + "\"\n" +
                                "}")).andExpect(status().isBadRequest())
                .andReturn().getResponse();

        verify(calculatorService, times(1)).calculate(value1, value2, CalculationOperation.DIVIDE);
    }

    @Test
    void getHistoryTest() throws Exception {
        List<CalculationResult> mockList = new ArrayList<>();
        mockList.add(new CalculationResult(1, 2, 1, 1, CalculationOperation.ADD, LocalDateTime.now()));
        mockList.add(new CalculationResult(1, 1, 2, 1, CalculationOperation.SUBTRACT, LocalDateTime.now()));

        when(calculatorService.getHistory()).thenReturn(mockList);

        var response = this.mockMvc.perform(get("/calculate")).andExpect(status().isOk())
                .andReturn().getResponse();

        ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();

        CalculationResultDTO[] result = mapper.readValue(response.getContentAsString(), CalculationResultDTO[].class);

        assertThat(result[0], is(new CalculationResultDTO(mockList.get(0))));
        assertThat(result[1], is(new CalculationResultDTO(mockList.get(1))));
    }
}

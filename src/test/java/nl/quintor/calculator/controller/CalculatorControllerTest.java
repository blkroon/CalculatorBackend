package nl.quintor.calculator.controller;

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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
    void addTest() throws Exception {
        int value1 = 5;
        int value2 = 2;

        when(simpleCalculator.add(value1, value2)).thenReturn(new CalculationResult());

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
    void subtractTest() throws Exception {
        int value1 = 5;
        int value2 = 2;

        when(simpleCalculator.subtract(value1, value2)).thenReturn(new CalculationResult());

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
    void multiplyTest() throws Exception {
        int value1 = 5;
        int value2 = 2;

        when(simpleCalculator.multiply(value1, value2)).thenReturn(new CalculationResult());

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
    void divideTest() throws Exception {
        int value1 = 5;
        int value2 = 2;
        Double result = 2.5;

        when(simpleCalculator.divide(value1, value2)).thenReturn(new CalculationResult());

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
    void divide_DivideByZero_ShouldThrowExceptionTest() throws Exception {
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
}

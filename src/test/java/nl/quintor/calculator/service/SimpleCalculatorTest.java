package nl.quintor.calculator.service;

import nl.quintor.calculator.controller.exception.InvalidCalculation;
import nl.quintor.calculator.model.CalculationAction;
import nl.quintor.calculator.model.CalculationResult;
import nl.quintor.calculator.repository.CalculationResultRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SimpleCalculatorTest {

    @Mock
    private CalculationResultRepository calculationResultRepository;
    @InjectMocks
    private SimpleCalculator simpleCalculator;

    @Test
    void addTest() {
        int value1 = 5;
        int value2 = 2;
        double expectedResult = 7.0;

        when(calculationResultRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        CalculationResult result = simpleCalculator.add(value1, value2);

        assertThat(result.getResult(), is(expectedResult));
        assertThat(result.getValue1(), is((double) value1));
        assertThat(result.getValue2(), is((double) value2));
        assertThat(result.getAction(), is(CalculationAction.ADD));

        verify(calculationResultRepository, times(1)).save(any());
    }

    @Test
    void subtractTest() {
        int value1 = 5;
        int value2 = 2;
        double expectedResult = 3.0;

        when(calculationResultRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        CalculationResult result = simpleCalculator.subtract(value1, value2);

        assertThat(result.getResult(), is(expectedResult));
        assertThat(result.getValue1(), is((double) value1));
        assertThat(result.getValue2(), is((double) value2));
        assertThat(result.getAction(), is(CalculationAction.SUBTRACT));

        verify(calculationResultRepository, times(1)).save(any());
    }

    @Test
    void multiplyTest() {
        int value1 = 5;
        int value2 = 2;
        double expectedResult = 10.0;

        when(calculationResultRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        CalculationResult result = simpleCalculator.multiply(value1, value2);

        assertThat(result.getResult(), is(expectedResult));
        assertThat(result.getValue1(), is((double) value1));
        assertThat(result.getValue2(), is((double) value2));
        assertThat(result.getAction(), is(CalculationAction.MULTIPLY));

        verify(calculationResultRepository, times(1)).save(any());
    }

    @Test
    void divideTest() {
        int value1 = 5;
        int value2 = 2;
        double expectedResult = 2.5;

        when(calculationResultRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        CalculationResult result = simpleCalculator.divide(value1, value2);

        assertThat(result.getResult(), is(expectedResult));
        assertThat(result.getValue1(), is((double) value1));
        assertThat(result.getValue2(), is((double) value2));
        assertThat(result.getAction(), is(CalculationAction.DIVIDE));

        verify(calculationResultRepository, times(1)).save(any());
    }

    @Test
    void divide_cannotDivideByZeroTest() {
        int value1 = 5;
        int value2 = 0;

        assertThrows(InvalidCalculation.class, () -> simpleCalculator.divide(value1, value2));
    }

    @Test
    void getHistoryTest() {
        List<CalculationResult> mockList = new ArrayList<>();
        mockList.add(new CalculationResult(1, 2, 1, 1, CalculationAction.ADD, LocalDateTime.now()));
        mockList.add(new CalculationResult(1, 1, 2, 1, CalculationAction.SUBTRACT, LocalDateTime.now()));

        when(calculationResultRepository.findAll(Sort.by(Sort.Direction.DESC, "timeOfCalculation"))).thenReturn(mockList);

        List<CalculationResult> result = simpleCalculator.getHistory();

        assertThat(result, is(mockList));
    }
}

package nl.quintor.calculator.service;

import nl.quintor.calculator.controller.exception.InvalidCalculation;
import nl.quintor.calculator.model.CalculationResult;
import nl.quintor.calculator.repository.CalculationResultRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

        when(calculationResultRepository.save(any())).thenReturn(new CalculationResult());

        double result = simpleCalculator.add(value1, value2);

        assertThat(result, is(expectedResult));
        verify(calculationResultRepository, times(1)).save(any());
    }

    @Test
    void subtractTest() {
        int value1 = 5;
        int value2 = 2;
        double expectedResult = 3.0;

        when(calculationResultRepository.save(any())).thenReturn(new CalculationResult());

        double result = simpleCalculator.subtract(value1, value2);

        assertThat(result, is(expectedResult));
        verify(calculationResultRepository, times(1)).save(any());
    }

    @Test
    void multiplyTest() {
        int value1 = 5;
        int value2 = 2;
        double expectedResult = 10.0;

        when(calculationResultRepository.save(any())).thenReturn(new CalculationResult());

        double result = simpleCalculator.multiply(value1, value2);

        assertThat(result, is(expectedResult));
        verify(calculationResultRepository, times(1)).save(any());
    }

    @Test
    void divideTest() {
        int value1 = 5;
        int value2 = 2;
        double expectedResult = 2.5;

        when(calculationResultRepository.save(any())).thenReturn(new CalculationResult());

        double result = simpleCalculator.divide(value1, value2);

        assertThat(result, is(expectedResult));
        verify(calculationResultRepository, times(1)).save(any());
    }

    @Test
    void divide_cannotDivideByZeroTest() {
        int value1 = 5;
        int value2 = 0;

        when(calculationResultRepository.save(any())).thenReturn(new CalculationResult());

        assertThrows(InvalidCalculation.class, () -> simpleCalculator.divide(value1, value2));

        verify(calculationResultRepository, times(1)).save(any());
    }
}

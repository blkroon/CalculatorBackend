package nl.quintor.calculator.service;

import nl.quintor.calculator.controller.exception.InvalidCalculation;
import nl.quintor.calculator.model.CalculationOperation;
import nl.quintor.calculator.model.CalculationResult;
import nl.quintor.calculator.repository.CalculationResultRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
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
public class CalculatorServiceTest {

    @Mock
    private CalculationResultRepository calculationResultRepository;
    @InjectMocks
    private CalculatorService calculatorService;

    @ParameterizedTest
    @CsvFileSource(resources = "/testdata.csv", numLinesToSkip = 1)
    void calculateParamTest(double value1, double value2, double result, CalculationOperation operation) throws Exception {
        when(calculationResultRepository.save(any())).then(AdditionalAnswers.returnsFirstArg());

        CalculationResult actual = calculatorService.calculate(value1, value2, operation);

        assertThat(actual.getResult(), is(result));
        assertThat(actual.getValue1(), is(value1));
        assertThat(actual.getValue2(), is(value2));
        assertThat(actual.getOperation(), is(operation));

        verify(calculationResultRepository, times(1)).save(any());
    }

    @Test
    void divide_cannotDivideByZeroTest() {
        int value1 = 5;
        int value2 = 0;

        assertThrows(InvalidCalculation.class, () -> calculatorService.divide(value1, value2));
    }

    @Test
    void getHistoryTest() {
        List<CalculationResult> mockList = new ArrayList<>();
        mockList.add(new CalculationResult(1, 2, 1, 1, CalculationOperation.ADD, LocalDateTime.now()));
        mockList.add(new CalculationResult(1, 1, 2, 1, CalculationOperation.SUBTRACT, LocalDateTime.now()));

        when(calculationResultRepository.findAll(Sort.by(Sort.Direction.DESC, "timeOfCalculation"))).thenReturn(mockList);

        List<CalculationResult> result = calculatorService.getHistory();

        assertThat(result, is(mockList));
    }
}

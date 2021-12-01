package nl.quintor.calculator.service;

import lombok.AllArgsConstructor;
import nl.quintor.calculator.controller.exception.InvalidCalculation;
import nl.quintor.calculator.model.CalculationAction;
import nl.quintor.calculator.model.CalculationResult;
import nl.quintor.calculator.repository.CalculationResultRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class SimpleCalculator {

    private CalculationResultRepository calculationResultRepository;

    public CalculationResult add(int value1, int value2) {
        double result = (double) value1 + (double) value2;
        return save(result, value1, value2, CalculationAction.ADD);
    }

    public CalculationResult subtract(int value1, int value2) {
        double result = (double) value1 - (double) value2;
        return save(result, value1, value2, CalculationAction.SUBTRACT);
    }

    public CalculationResult multiply(int value1, int value2) {
        double result = (double) value1 * (double) value2;
        return save(result, value1, value2, CalculationAction.MULTIPLY);
    }

    public CalculationResult divide(int value1, int value2) {
        if (value2 == 0) {
            throw new InvalidCalculation("Can't divide by zero");
        }
        double result = (double) value1 / (double) value2;
        return save(result, value1, value2, CalculationAction.DIVIDE);
    }

    public List<CalculationResult> getHistory() {
        return calculationResultRepository.findAll(Sort.by(Sort.Direction.DESC, "timeOfCalculation"));
    }

    private CalculationResult save(double result, int value1, int value2, CalculationAction action) {
        CalculationResult calculationResult = new CalculationResult(result, value1, value2, action, LocalDateTime.now());
        return calculationResultRepository.save(calculationResult);
    }
}

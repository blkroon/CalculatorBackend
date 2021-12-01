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

    public double add(int value1, int value2) {
        double result = (double) value1 + (double) value2;
        save(result, value1, value2, CalculationAction.ADD);
        return result;
    }

    public double subtract(int value1, int value2) {
        double result = (double) value1 - (double) value2;
        save(result, value1, value2, CalculationAction.SUBTRACT);
        return result;
    }

    public double multiply(int value1, int value2) {
        double result = (double) value1 * (double) value2;
        save(result, value1, value2, CalculationAction.MULTIPLY);
        return result;
    }

    public double divide(int value1, int value2) {
        if (value2 == 0) {
            throw new InvalidCalculation("Can't divide by zero");
        }
        double result = (double) value1 / (double) value2;
        save(result, value1, value2, CalculationAction.DIVIDE);
        return result;
    }

    public List<CalculationResult> getHistory() {
        return calculationResultRepository.findAll(Sort.by(Sort.Direction.DESC, "timeOfCalculation"));
    }

    private void save(double result, int value1, int value2, CalculationAction action) {
        CalculationResult calculationResult = new CalculationResult(result, value1, value2, action, LocalDateTime.now());
        calculationResultRepository.save(calculationResult);
    }
}

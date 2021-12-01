package nl.quintor.calculator.service;

import lombok.AllArgsConstructor;
import nl.quintor.calculator.model.CalculationAction;
import nl.quintor.calculator.model.CalculationResult;
import nl.quintor.calculator.repository.CalculationResultRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class SimpleCalculator {

    private CalculationResultRepository calculationResultRepository;

    public double add(int value1, int value2) {
        // TODO IMPLEMENT
        return 0.0;
    }

    public double subtract(int value1, int value2) {
        // TODO IMPLEMENT
        return 0.0;
    }

    public double multiply(int value1, int value2) {
        // TODO IMPLEMENT
        return 0.0;
    }

    public double divide(int value1, int value2) {
        // TODO IMPLEMENT
        return 0.0;
    }

    private void save(double result, int value1, int value2, CalculationAction action) {
        CalculationResult calculationResult = new CalculationResult(result, value1, value2, action, LocalDateTime.now());
        calculationResultRepository.save(calculationResult);
    }
}

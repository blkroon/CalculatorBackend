package nl.quintor.calculator.service;

import lombok.AllArgsConstructor;
import nl.quintor.calculator.controller.exception.InvalidCalculation;
import nl.quintor.calculator.model.CalculationOperation;
import nl.quintor.calculator.model.CalculationResult;
import nl.quintor.calculator.repository.CalculationResultRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CalculatorService {

    private CalculationResultRepository calculationResultRepository;

    public CalculationResult calculate(double value1, double value2, CalculationOperation operation) {
        switch (operation) {
            case ADD:
                return this.add(value1, value2);
            case SUBTRACT:
                return this.subtract(value1, value2);
            case MULTIPLY:
                return this.multiply(value1, value2);
            case DIVIDE:
                return this.divide(value1, value2);
            default:
                throw new UnsupportedOperationException("Unsupported operator type");
        }
    }

    public CalculationResult add(double value1, double value2) {
        double result = value1 + value2;
        return save(rounding(result), value1, value2, CalculationOperation.ADD);
    }

    public CalculationResult subtract(double value1, double value2) {
        double result = value1 - value2;
        return save(rounding(result), value1, value2, CalculationOperation.SUBTRACT);
    }

    public CalculationResult multiply(double value1, double value2) {
        double result = value1 * value2;
        return save(rounding(result), value1, value2, CalculationOperation.MULTIPLY);
    }

    public CalculationResult divide(double value1, double value2) {
        if (value2 == 0) {
            throw new InvalidCalculation("Can't divide by zero");
        }
        double result = value1 / value2;
        return save(rounding(result), value1, value2, CalculationOperation.DIVIDE);
    }

    public List<CalculationResult> getHistory() {
        return calculationResultRepository.findAll(Sort.by(Sort.Direction.DESC, "timeOfCalculation"));
    }

    private CalculationResult save(double result, double value1, double value2, CalculationOperation operation) {
        CalculationResult calculationResult = new CalculationResult(result, value1, value2, operation, LocalDateTime.now());
        return calculationResultRepository.save(calculationResult);
    }

    private double rounding(double value) {
        return (double) Math.round(value * 100) / 100;
    }
}

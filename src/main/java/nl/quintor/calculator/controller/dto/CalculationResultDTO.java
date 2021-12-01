package nl.quintor.calculator.controller.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import nl.quintor.calculator.model.CalculationResult;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CalculationResultDTO {

    private double value1;
    private double value2;
    private double result;
    private String operation;
    private LocalDateTime timeOfCalculation;

    public CalculationResultDTO(CalculationResult calculationResult) {
        this.value1 = calculationResult.getValue1();
        this.value2 = calculationResult.getValue2();
        this.result = calculationResult.getResult();
        this.operation = calculationResult.getAction().name();
        this.timeOfCalculation = calculationResult.getTimeOfCalculation();
    }
}

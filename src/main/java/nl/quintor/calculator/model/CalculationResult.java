package nl.quintor.calculator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CalculationResult {

    @Id
    @GeneratedValue
    private Integer id;
    private double result;
    private double value1;
    private double value2;
    @Enumerated
    private CalculationAction action;
    private LocalDateTime timeOfCalculation;

    public CalculationResult(double result, int value1, int value2, CalculationAction action, LocalDateTime timeOfCalculation) {
        this.result = result;
        this.value1 = value1;
        this.value2 = value2;
        this.action = action;
        this.timeOfCalculation = timeOfCalculation;
    }
}

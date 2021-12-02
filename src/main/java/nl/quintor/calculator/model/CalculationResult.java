package nl.quintor.calculator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CalculationResult {

    @Id
    @GeneratedValue
    private Integer id;
    private double result;
    private double value1;
    private double value2;
    @Enumerated
    private CalculationOperation operation;
    private LocalDateTime timeOfCalculation;

    public CalculationResult(double result, double value1, double value2, CalculationOperation operation, LocalDateTime timeOfCalculation) {
        this.result = result;
        this.value1 = value1;
        this.value2 = value2;
        this.operation = operation;
        this.timeOfCalculation = timeOfCalculation;
    }
}

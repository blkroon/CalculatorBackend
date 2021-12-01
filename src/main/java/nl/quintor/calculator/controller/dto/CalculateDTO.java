package nl.quintor.calculator.controller.dto;

import lombok.Data;
import nl.quintor.calculator.model.CalculationOperation;

import javax.validation.constraints.NotNull;

@Data
public class CalculateDTO {
    @NotNull
    private Double value1;
    @NotNull
    private Double value2;
    @NotNull
    private CalculationOperation operation;
}

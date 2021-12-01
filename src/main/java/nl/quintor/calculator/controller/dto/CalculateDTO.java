package nl.quintor.calculator.controller.dto;

import lombok.Data;
import nl.quintor.calculator.model.CalculationAction;

import javax.validation.constraints.NotNull;

@Data
public class CalculateDTO {
    @NotNull
    private int value1;
    @NotNull
    private int value2;
    @NotNull
    private CalculationAction action;
}

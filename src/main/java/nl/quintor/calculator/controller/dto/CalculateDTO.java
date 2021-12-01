package nl.quintor.calculator.controller.dto;

import lombok.Data;
import nl.quintor.calculator.model.CalculationAction;

@Data
public class CalculateDTO {
    private int value1;
    private int value2;
    private CalculationAction action;
}

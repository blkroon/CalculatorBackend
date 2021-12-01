package nl.quintor.calculator.controller;

import lombok.AllArgsConstructor;
import nl.quintor.calculator.service.SimpleCalculator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("calculate")
@AllArgsConstructor
public class CalculatorController {

    private SimpleCalculator simpleCalculator;

}

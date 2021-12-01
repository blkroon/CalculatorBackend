package nl.quintor.calculator.controller;

import lombok.AllArgsConstructor;
import nl.quintor.calculator.controller.dto.CalculateDTO;
import nl.quintor.calculator.model.CalculationAction;
import nl.quintor.calculator.model.CalculationResult;
import nl.quintor.calculator.service.SimpleCalculator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("calculate")
@AllArgsConstructor
public class CalculatorController {

    private SimpleCalculator simpleCalculator;

    @PostMapping
    public ResponseEntity<CalculationResult> calculate(@RequestBody CalculateDTO values) {
        int value1 = values.getValue1();
        int value2 = values.getValue2();
        CalculationAction action = values.getAction();

        CalculationResult result = null;
        switch (action) {
            case ADD:
                result = simpleCalculator.add(value1, value2);
                break;
            case SUBTRACT:
                result = simpleCalculator.subtract(value1, value2);
                break;
            case MULTIPLY:
                result = simpleCalculator.multiply(value1, value2);
                break;
            case DIVIDE:
                result = simpleCalculator.divide(value1, value2);
                break;
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<CalculationResult>> getHistory() {
        return ResponseEntity.ok(simpleCalculator.getHistory());
    }
}

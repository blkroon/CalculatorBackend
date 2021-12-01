package nl.quintor.calculator.controller;

import lombok.AllArgsConstructor;
import nl.quintor.calculator.controller.dto.ValuesDTO;
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

    @PostMapping("/add")
    public ResponseEntity<Double> add(@RequestBody ValuesDTO values) {
        double result = simpleCalculator.add(values.getValue1(), values.getValue2());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/subtract")
    public ResponseEntity<Double> subtract(@RequestBody ValuesDTO values) {
        double result = simpleCalculator.subtract(values.getValue1(), values.getValue2());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/multiply")
    public ResponseEntity<Double> multiply(@RequestBody ValuesDTO values) {
        double result = simpleCalculator.multiply(values.getValue1(), values.getValue2());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/divide")
    public ResponseEntity<Double> divide(@RequestBody ValuesDTO values) {
        double result = simpleCalculator.divide(values.getValue1(), values.getValue2());
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<CalculationResult>> getHistory() {
        return ResponseEntity.ok(simpleCalculator.getHistory());
    }
}

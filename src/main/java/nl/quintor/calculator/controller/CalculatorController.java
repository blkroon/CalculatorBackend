package nl.quintor.calculator.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.quintor.calculator.controller.dto.CalculateDTO;
import nl.quintor.calculator.controller.dto.CalculationResultDTO;
import nl.quintor.calculator.model.CalculationOperation;
import nl.quintor.calculator.model.CalculationResult;
import nl.quintor.calculator.service.CalculatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("calculate")
@AllArgsConstructor
@Slf4j
public class CalculatorController {

    private CalculatorService calculatorService;

    @PostMapping
    public ResponseEntity<CalculationResultDTO> calculate(@Valid @RequestBody CalculateDTO values) {
        double value1 = values.getValue1();
        double value2 = values.getValue2();
        CalculationOperation operation = values.getOperation();
        log.info("Calculate called with {}, {} and operation {}", value1, value2, operation.name());

        CalculationResult result = calculatorService.calculate(value1, value2, operation);

        log.info("Result is {}", result.getResult());
        return ResponseEntity.ok(new CalculationResultDTO(result));
    }

    @GetMapping
    public ResponseEntity<List<CalculationResultDTO>> getHistory() {
        log.info("Accessing historic calculation data");
        List<CalculationResultDTO> result = new ArrayList<>();
        calculatorService.getHistory().forEach(r -> result.add(new CalculationResultDTO(r)));
        return ResponseEntity.ok(result);
    }
}

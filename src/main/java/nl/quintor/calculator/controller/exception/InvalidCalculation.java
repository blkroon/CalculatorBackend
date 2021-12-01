package nl.quintor.calculator.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCalculation extends RuntimeException{

    public InvalidCalculation(String msg) {
        super(msg);
    }
}

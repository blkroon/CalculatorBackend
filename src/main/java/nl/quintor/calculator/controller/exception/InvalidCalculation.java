package nl.quintor.calculator.controller.exception;

public class InvalidCalculation extends RuntimeException{

    public InvalidCalculation(String msg) {
        super(msg);
    }
}

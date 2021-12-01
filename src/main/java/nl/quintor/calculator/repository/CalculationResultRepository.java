package nl.quintor.calculator.repository;

import nl.quintor.calculator.model.CalculationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculationResultRepository extends JpaRepository<CalculationResult, Integer> {
}

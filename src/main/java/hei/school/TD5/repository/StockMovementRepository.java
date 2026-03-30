package hei.school.TD5.repository;

import hei.school.TD5.entity.StockMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.Instant;
import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Integer> {
    List<StockMovement> findByIngredientIdAndCreationDatetimeBefore(Integer ingredientId, Instant date);
}
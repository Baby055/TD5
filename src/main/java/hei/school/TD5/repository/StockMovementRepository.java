package hei.school.TD5.repository;

import hei.school.TD5.entity.MovementTypeEnum;
import hei.school.TD5.entity.StockMovement;
import hei.school.TD5.entity.StockValue;
import hei.school.TD5.entity.Unit;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
public class StockMovementRepository {

    private final List<StockMovement> movements = new ArrayList<>();

    public List<StockMovement> findByIngredientIdAndCreationDatetimeBefore(Integer ingredientId, Instant date) {
        if (ingredientId == null || date == null) {
            throw new IllegalArgumentException("ingredientId and date must not be null");
        }
        List<StockMovement> result = new ArrayList<>();
        for (StockMovement m : movements) {
            if (m.getIngredient() == null || m.getIngredient().getId() == null) {
                continue;
            }
            if (!ingredientId.equals(m.getIngredient().getId())) {
                continue;
            }
            if (m.getCreationDatetime() != null && !m.getCreationDatetime().isAfter(date)) {
                result.add(m);
            }
        }
        return result;
    }

    public void save(StockMovement movement) {
        if (movement == null) {
            throw new IllegalArgumentException("StockMovement cannot be null");
        }
        if (movement.getId() == null) {
            movement.setId(movements.size() + 1);
        }
        movements.removeIf(m -> movement.getId().equals(m.getId()));
        movements.add(movement);
    }
}
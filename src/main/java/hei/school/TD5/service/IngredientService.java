package hei.school.TD5.service;

import hei.school.TD5.entity.Ingredient;
import hei.school.TD5.entity.StockMovement;
import hei.school.TD5.entity.StockValue;
import hei.school.TD5.repository.IngredientRepository;
import hei.school.TD5.repository.StockMovementRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final StockMovementRepository stockMovementRepository;

    public IngredientService(IngredientRepository ingredientRepository, StockMovementRepository stockMovementRepository) {
        this.ingredientRepository = ingredientRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public Optional<Ingredient> getIngredientById(Integer id) {
        return ingredientRepository.findById(id);
    }

    public StockValue getStockAtDate(Integer ingredientId, String unit, Instant date) {
        if (!ingredientRepository.existsById(ingredientId)) {
            return null;
        }

        List<StockMovement> movements = stockMovementRepository.findByIngredientIdAndCreationDatetimeBefore(ingredientId, date);

        Ingredient ingredient = new Ingredient();
        ingredient.setStockMovements(movements);
        StockValue stock = ingredient.getStockValueAt(date);

        if (stock != null && !stock.getUnit().name().equalsIgnoreCase(unit)) {
            throw new IllegalArgumentException("Unité demandée différente de l'unité du stock");
        }
        return stock;
    }
}

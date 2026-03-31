package hei.school.TD5.service;

import hei.school.TD5.entity.Dish;
import hei.school.TD5.entity.DishIngredient;
import hei.school.TD5.entity.Ingredient;
import hei.school.TD5.entity.Unit;
import hei.school.TD5.repository.DishRepository;
import hei.school.TD5.repository.IngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DishService {

    private final DishRepository dishRepository;
    private final IngredientRepository ingredientRepository;

    public DishService(DishRepository dishRepository, IngredientRepository ingredientRepository) {
        this.dishRepository = dishRepository;
        this.ingredientRepository = ingredientRepository;
    }

    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    public Optional<Dish> getDishById(Integer id) {
        return dishRepository.findById(id);
    }

    @Transactional
    public boolean updateDishIngredients(Integer dishId, List<Ingredient> newIngredients) {
        Optional<Dish> dishOpt = dishRepository.findById(dishId);
        if (dishOpt.isEmpty()) {
            return false;
        }
        Dish dish = dishOpt.get();

        List<Integer> ingredientIds = newIngredients.stream()
                .map(Ingredient::getId)
                .filter(id -> id != null)
                .collect(Collectors.toList());
        List<Ingredient> existingIngredients = ingredientRepository.findAllById(ingredientIds);

        // Création des nouveaux DishIngredient
        List<DishIngredient> newDishIngredients = new ArrayList<>();
        for (Ingredient ing : existingIngredients) {
            DishIngredient di = new DishIngredient();
            di.setDish(dish);
            di.setIngredient(ing);
            di.setQuantity(1.0);   // valeur par défaut
            di.setUnit(Unit.PCS);  // valeur par défaut
            newDishIngredients.add(di);
        }
        dish.setDishIngredients(newDishIngredients);

        dishRepository.updateDishIngredients(dish.getId(), newDishIngredients);
        return true;
    }

    //TD6
    public List<Ingredient> getIngredientsByDishIdWithFilters(Integer dishId,
                                                              String ingredientName,
                                                              Double ingredientPriceAround) {
        Dish dish = dishRepository.findById(dishId)
                .orElseThrow(() -> new RuntimeException("Dish not found"));

        List<Ingredient> ingredients = dish.getDishIngredients().stream()
                .map(DishIngredient::getIngredient)
                .collect(Collectors.toList());

        if (ingredientName != null && !ingredientName.isBlank()) {
            String lowerName = ingredientName.toLowerCase();
            ingredients = ingredients.stream()
                    .filter(i -> i.getName().toLowerCase().contains(lowerName))
                    .collect(Collectors.toList());
        }

        if (ingredientPriceAround != null) {
            double min = ingredientPriceAround - 50;
            double max = ingredientPriceAround + 50;
            ingredients = ingredients.stream()
                    .filter(i -> i.getPrice() >= min && i.getPrice() <= max)
                    .collect(Collectors.toList());
        }

        return ingredients;
    }
}

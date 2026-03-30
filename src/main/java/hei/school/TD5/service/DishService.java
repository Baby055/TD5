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

        dish.getDishIngredients().clear();

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

        dishRepository.save(dish);
        return true;
    }
}

package hei.school.TD5.repository;

import hei.school.TD5.entity.Dish;
import hei.school.TD5.entity.DishIngredient;
import hei.school.TD5.entity.DishTypeEnum;
import hei.school.TD5.entity.Ingredient;
import hei.school.TD5.entity.Unit;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class DishRepository {

    private final List<Dish> dishes = new ArrayList<>();
    private final List<DishIngredient> dishIngredients = new ArrayList<>();

    public List<Dish> findAll() {
        return new ArrayList<>(dishes);
    }

    public Optional<Dish> findById(Integer id) {
        if (id == null) {
            return Optional.empty();
        }
        return dishes.stream()
                .filter(d -> id.equals(d.getId()))
                .findFirst()
                .map(d -> {
                    d.setDishIngredients(loadDishIngredientsWithIngredients(d.getId(), d));
                    return d;
                });
    }

    private List<DishIngredient> loadDishIngredientsWithIngredients(Integer dishId, Dish dish) {
        List<DishIngredient> result = new ArrayList<>();
        for (DishIngredient di : dishIngredients) {
            if (di.getDish() != null && dishId.equals(di.getDish().getId())) {
                result.add(di);
            }
        }
        return result;
    }

    public void updateDishIngredients(Integer dishId, List<DishIngredient> newDishIngredients) {
        if (dishId == null) {
            throw new IllegalArgumentException("dishId must not be null");
        }
        if (newDishIngredients == null) {
            throw new IllegalArgumentException("newDishIngredients must not be null");
        }

        dishIngredients.removeIf(di -> di.getDish() != null && dishId.equals(di.getDish().getId()));

        for (DishIngredient di : newDishIngredients) {
            if (di.getDish() == null) {
                Dish dishRef = new Dish();
                dishRef.setId(dishId);
                di.setDish(dishRef);
            }
            dishIngredients.add(di);
        }
    }

    public void save(Dish dish) {
        if (dish == null) {
            throw new IllegalArgumentException("Dish cannot be null");
        }
        if (dish.getId() == null) {
            dish.setId(dishes.size() + 1);
        }
        dishes.removeIf(d -> dish.getId().equals(d.getId()));
        dishes.add(dish);
    }
}

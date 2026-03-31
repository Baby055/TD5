package hei.school.TD5.controller;

import hei.school.TD5.entity.Ingredient;
import hei.school.TD5.service.DishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dishes")
public class DishController {

    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping
    public ResponseEntity<?> getAllDishes() {
        return ResponseEntity.ok(dishService.getAllDishes());
    }

    @PutMapping("/{id}/ingredients")
    public ResponseEntity<?> updateDishIngredients(@PathVariable Integer id,
                                                   @RequestBody(required = false) List<Ingredient> ingredients) {
        if (ingredients == null) {
            return ResponseEntity.badRequest().body("Le corps de la requête est obligatoire.");
        }
        boolean updated = dishService.updateDishIngredients(id, ingredients);
        if (!updated) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Dish.id=" + id + " is not found");
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/ingredients")
    public ResponseEntity<?> getDishIngredients(@PathVariable Integer id,
                                                @RequestParam(required = false) String ingredientName,
                                                @RequestParam(required = false) Double ingredientPriceAround) {
        try {
            List<Ingredient> ingredients = dishService.getIngredientsByDishIdWithFilters(id, ingredientName, ingredientPriceAround);
            return ResponseEntity.ok(ingredients);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Dish.id=" + id + " is not found");
        }
    }
}
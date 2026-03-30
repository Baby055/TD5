package hei.school.TD5.controlleur;

import hei.school.TD5.entity.Ingredient;
import hei.school.TD5.entity.StockValue;
import hei.school.TD5.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    // a) GET /ingredients
    @GetMapping
    public ResponseEntity<?> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.getAllIngredients());
    }

    // b) GET /ingredients/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> getIngredientById(@PathVariable Integer id) {
        Optional<Ingredient> ingredient = ingredientService.getIngredientById(id);
        if (ingredient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ingredient.id=" + id + " is not found");
        }
        return ResponseEntity.ok(ingredient.get());
    }
}

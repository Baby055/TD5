package hei.school.TD5.controller;

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

    @GetMapping
    public ResponseEntity<?> getAllIngredients() {
        return ResponseEntity.ok(ingredientService.getAllIngredients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIngredientById(@PathVariable Integer id) {
        Optional<Ingredient> ingredient = ingredientService.getIngredientById(id);
        if (ingredient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ingredient.id=" + id + " is not found");
        }
        return ResponseEntity.ok(ingredient.get());
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<?> getStock(@PathVariable Integer id,
                                      @RequestParam("at") String at,
                                      @RequestParam("unit") String unit) {
        if (at == null || unit == null || at.isBlank() || unit.isBlank()) {
            return ResponseEntity.badRequest()
                    .body("Either mandatory query parameter `at` or `unit` is not provided.");
        }

        Instant date;
        try {
            date = Instant.parse(at);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid date format. Use ISO-8601 (e.g., 2025-01-01T10:00:00Z)");
        }

        Optional<Ingredient> ingredientOpt = ingredientService.getIngredientById(id);
        if (ingredientOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Ingredient.id=" + id + " is not found");
        }

        StockValue stock = ingredientService.getStockAtDate(id, unit, date);
        if (stock == null) {
            Map<String, Object> response = new HashMap<>();
            response.put("unit", unit);
            response.put("value", 0.0);
            return ResponseEntity.ok(response);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("unit", stock.getUnit().name());
        response.put("value", stock.getQuantity());
        return ResponseEntity.ok(response);
    }
}

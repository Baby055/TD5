package hei.school.TD5.repository;

import hei.school.TD5.entity.CategoryEnum;
import hei.school.TD5.entity.Ingredient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class IngredientRepository {

    private final List<Ingredient> ingredients = new ArrayList<>();

    public List<Ingredient> findAll() {
        return new ArrayList<>(ingredients);
    }

    public Optional<Ingredient> findById(Integer id) {
        if (id == null) {
            return Optional.empty();
        }
        return ingredients.stream()
                .filter(i -> id.equals(i.getId()))
                .findFirst();
    }

    public boolean existsById(Integer id) {
        if (id == null) {
            return false;
        }
        return ingredients.stream().anyMatch(i -> id.equals(i.getId()));
    }

    public List<Ingredient> findAllById(Iterable<Integer> ids) {
        List<Ingredient> result = new ArrayList<>();
        if (ids == null) {
            return result;
        }
        for (Integer id : ids) {
            if (id == null) continue;
            findById(id).ifPresent(result::add);
        }
        return result;
    }

    // Méthode utilitaire pour initialiser ou ajouter des ingrédients si besoin
    public void save(Ingredient ingredient) {
        if (ingredient == null) {
            throw new IllegalArgumentException("Ingredient cannot be null");
        }
        if (ingredient.getId() == null) {
            ingredient.setId(ingredients.size() + 1);
        }
        ingredients.removeIf(i -> ingredient.getId().equals(i.getId()));
        ingredients.add(ingredient);
    }
}
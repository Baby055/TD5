package edu.hei.school.ingredients.dto;

import java.util.List;

public class DishRest {
    private Integer id;
    private Double price;
    private String name;
    private List<DishIngredientRest> dishIngredients;

    public DishRest(Integer id, Double price, String name, List<DishIngredientRest> dishIngredients) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.dishIngredients = dishIngredients;
    }

    public Integer getId() {
        return id;
    }

    public Double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public List<DishIngredientRest> getDishIngredients() {
        return dishIngredients;
    }
}

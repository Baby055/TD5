package edu.hei.school.ingredients.dto;

import edu.hei.school.ingredients.entity.CategoryEnum;

public class DishIngredientRest {
    private Integer id;
    private String name;
    private CategoryEnum category;
    private Double price;

    public DishIngredientRest(Integer id, String name, CategoryEnum category, Double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public Double getPrice() {
        return price;
    }
}

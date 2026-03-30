package hei.school.TD5.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "dish_ingredient")
public class DishIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_dish")
    private Dish dish;

    @ManyToOne
    @JoinColumn(name = "id_ingredient")
    private Ingredient ingredient;

    private Double quantity;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}

package hei.school.TD5.entity;
import java.util.List;

public class Dish {
    private Integer id;

    private String name;

    private Double price;

    private DishTypeEnum dishType;

    private List<DishIngredient> dishIngredients;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public DishTypeEnum getDishType() {
        return dishType;
    }

    public void setDishType(DishTypeEnum dishType) {
        this.dishType = dishType;
    }

    public List<DishIngredient> getDishIngredients() {
        return dishIngredients;
    }

    public void setDishIngredients(List<DishIngredient> dishIngredients) {
        this.dishIngredients = dishIngredients;
    }

    public Double getDishCost() {
        double totalPrice = 0;
        for (DishIngredient dishIngredient : dishIngredients) {
            Double quantity = dishIngredient.getQuantity();
            if (quantity == null) {
                throw new RuntimeException("Some ingredients have undefined quantity");
            }
            totalPrice = totalPrice + dishIngredient.getIngredient().getPrice() * quantity;
        }
        return totalPrice;
    }

    public Double getGrossMargin() {
        if (price == null) {
            throw new RuntimeException("Price is null");
        }
        return price - getDishCost();
    }
}
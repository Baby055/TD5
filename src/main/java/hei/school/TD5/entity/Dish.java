package hei.school.TD5.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "dish")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "selling_price")
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "dish_type")
    private DishTypeEnum dishType;

    @OneToMany(mappedBy = "dish", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DishIngredient> dishIngredients;

    // Méthodes métier : getDishCost(), getGrossMargin()...
}